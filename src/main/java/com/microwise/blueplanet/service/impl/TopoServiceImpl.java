package com.microwise.blueplanet.service.impl;

import com.microwise.blueplanet.bean.vo.TopoViewVO;
import com.microwise.blueplanet.dao.TopoDao;
import com.microwise.blueplanet.service.TopoService;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.annotation.Beans;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiedeng
 * @date 13-9-29
 * @check liuzhu 2013-10-11 #5896
 */
@Blueplanet
@Beans.Service
@Transactional
public class TopoServiceImpl implements TopoService {

    public static final Logger log = LoggerFactory.getLogger(TopoServiceImpl.class);

    @Autowired
    private TopoDao topoDao;

    /**
     * 拓扑图点信息
     */
    private List<TopoViewVO> topos = new ArrayList<TopoViewVO>();

    /**
     * 拓扑图映射关系
     */
    private List<Map<String, String>> topoReferences = new ArrayList<Map<String, String>>();


    private List<String> nodeCycleCount = new ArrayList<String>();

    private boolean isCycle = false;

    @Override
    public List<Object> getTopoData(String nodeId) {
        List<Object> topoInfo = new ArrayList<Object>();
        TopoViewVO topoViewVO = topoDao.getNodeInfoByNodeId(nodeId);
        int nodeVersion = topoViewVO.getNodeVersion();
        int nodeType = topoViewVO.getNodeType();
        int rssi = topoViewVO.getRssi();
        String parentIp = getParentIp(nodeId, topoViewVO.getParentIp(), nodeVersion);
        //网关不需要向上查找,节点和从模块不需要向下查找
        switch (nodeType) {
            case 1:
            case 4:
                nodeCycleCount.clear();
                getUpTopos(nodeId, parentIp, rssi);
                break;
            case 2:
            case 3:
            case 5:
                // 如果不是根节点，则往上找
                if (!isRoot(topoViewVO)) {
                    nodeCycleCount.clear();
                    getUpTopos(nodeId, parentIp, rssi);
                }
                if (isCycle){
                    isCycle = false;
                    break;
                }
                nodeCycleCount.clear();
                getDownTopos(nodeId, nodeVersion);
                break;
            case 7:
                nodeCycleCount.clear();
                getDownTopos(nodeId, nodeVersion);
                break;
        }
        topos.add(topoViewVO);
        topoInfo.add(topos);
        topoInfo.add(topoReferences);
        return topoInfo;
    }

    @Override
    public List<TopoViewVO> getLoadDevices(String siteId, int anomaly) {
        List<TopoViewVO> topos = topoDao.getCountDevices(siteId, anomaly);
        for (TopoViewVO topo : topos) {
            //根据版本号截取站点号和IP号
            int nodeVersion = topo.getNodeVersion();
            int strLen = (nodeVersion == 1) ? 10 : 8;
            int selfId = Integer.parseInt(topo.getNodeId().substring(strLen));
            String str = topo.getNodeId().substring(0, strLen);
            int childrenCount = topoDao.getChildrenCount(selfId, str, strLen, nodeVersion);
            topo.setChildrenCount(childrenCount);
        }
        //根据子节点数量排序
        DeviceLoadComparator comparator = new DeviceLoadComparator();
        Collections.sort(topos, comparator);
        return topos;
    }

    @Override
    public List<TopoViewVO> getLoseRate(Date startDate, Date endDate, int type, String siteId) {
        List<TopoViewVO> topos = getDevices(siteId);
        for (TopoViewVO topo : topos) {
            //获取数据表中的最小时间
            Date minTime = topoDao.getMinTime(topo.getNodeId());
            //最小时间大于用户选择的开始时间，使用最小时间作为开始时间
            if (minTime != null && minTime.compareTo(startDate) > 0) {
                startDate = minTime;
            } else {
                //判断设备创建的时间是否大于用户选择的开始时间，把创建时间作为开始时间
                Date createTime = topo.getCreateTime();
                if (createTime.compareTo(startDate) > 0) {
                    startDate = createTime;
                }
            }
            //判断结束时间是否大于当前时间，把当前时间提前半小时作为结束时间（提前半小时为了防止时间有误差）
            Date currentTime = DateTime.now().minusMinutes(30).toDate();
            if (endDate.compareTo(currentTime) > 0) {
                endDate = currentTime;
            }
            setLoseRate(topo, startDate, endDate);
        }
        DeviceLoseRateComparator comparator = new DeviceLoseRateComparator();
        Collections.sort(topos, comparator);
        return topos;
    }

    @Override
    public TopoViewVO getLoseRate(Date startDate, Date endDate, String nodeId) {
        TopoViewVO topo = topoDao.getNodeInfoByNodeId(nodeId);
        //获取数据表中的最小时间
        Date minTime = topoDao.getMinTime(topo.getNodeId());
        //最小时间大于用户选择的开始时间，使用最小时间作为开始时间
        if (minTime != null && minTime.compareTo(startDate) > 0) {
            startDate = minTime;
        } else {
            //判断设备创建的时间是否大于用户选择的开始时间，把创建时间作为开始时间
            Date createTime = topo.getCreateTime();
            if (createTime.compareTo(startDate) > 0) {
                startDate = createTime;
            }
        }
        //判断结束时间是否大于当前时间，把当前时间提前半小时作为结束时间（提前半小时为了防止时间有误差）
        Date currentTime = DateTime.now().minusMinutes(30).toDate();
        if (endDate.compareTo(currentTime) > 0) {
            endDate = currentTime;
        }
        setLoseRate(topo, startDate, endDate);
        return topo;
    }

    @Override
    public Map<Integer, Integer> getDeviceCount() {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        int gatewayCount = 0;
        int relayCount = 0;
        int hostCount = 0;
        int slaveCount = 0;
        int nodeCount = 0;
        int controlCount = 0;
        for (TopoViewVO topoViewVO : this.topos) {
            switch (topoViewVO.getNodeType()) {
                case 1:
                    nodeCount++;
                    break;
                case 2:
                    relayCount++;
                    break;
                case 3:
                    hostCount++;
                    break;
                case 4:
                    slaveCount++;
                    break;

                case 5:
                    controlCount++;
                    break;

                case 7:
                    gatewayCount++;
                    break;
            }
        }
        counts.put(1, nodeCount);
        counts.put(2, relayCount);
        counts.put(3, hostCount);
        counts.put(4, slaveCount);
        counts.put(7, gatewayCount);
        counts.put(5, controlCount);
        return counts;
    }

    @Override
    public List<TopoViewVO> getHistoryRoute(String nodeId, Date startDate, Date endDate, int startIndex, int pageSize) {
        List<TopoViewVO> topos = topoDao.getHistoryRoute(nodeId, startDate, endDate, startIndex, pageSize);
        for (TopoViewVO topo : topos) {
            topo.setParentIpStr(getParentIp(topo.getNodeId(), topo.getParentIp(), topo.getNodeVersion()));
        }
        return topos;
    }

    @Override
    public TopoViewVO getNodeInfoByNodeId(String nodeId) {
        return topoDao.getNodeInfoByNodeId(nodeId);
    }

    @Override
    public List<TopoViewVO> getTimeoutDevice(String siteId) {
        List<TopoViewVO> topos = topoDao.getTimeoutDevice(siteId);
        for (TopoViewVO topo : topos) {
            topo.setParentIpStr(getParentIp(topo.getNodeId(), topo.getParentIp(), topo.getNodeVersion()));
        }
        return topos;
    }

    @Override
    public List<TopoViewVO> getRssi(String siteId, int anomaly) {
        List<TopoViewVO> topos = topoDao.getRssi(siteId, anomaly);
        //根据信号质量排序
        DeviceRssiComparator comparator = new DeviceRssiComparator();
        Collections.sort(topos, comparator);
        return topos;
    }

    @Override
    public List<TopoViewVO> getDevices(String siteId) {
        return topoDao.getDevices(siteId);
    }

    @Override
    public Integer getHistoryRouteCount(String nodeId, Date startDate, Date endDate) {
        return topoDao.getHistoryRouteCount(nodeId, startDate, endDate);
    }

    /**
     * 设备负载量比较器
     *
     * @author xiedeng
     * @date 2013-10-08
     */
    public static class DeviceLoadComparator implements Comparator<TopoViewVO> {
        @Override
        public int compare(TopoViewVO topo1, TopoViewVO topo2) {
            if (topo1.getChildrenCount() > topo2.getChildrenCount()) {
                return -1;
            } else if (topo1.getChildrenCount() < topo2.getChildrenCount()) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * 丢包率比较器
     *
     * @author xiedeng
     * @date 2013-10-11
     */
    public static class DeviceLoseRateComparator implements Comparator<TopoViewVO> {
        @Override
        public int compare(TopoViewVO topo1, TopoViewVO topo2) {
            if (topo1.getLoseRate() > topo2.getLoseRate()) {
                return -1;
            } else if (topo1.getLoseRate() < topo2.getLoseRate()) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * 设备信号质量比较器
     *
     * @author xiedeng
     * @date 2013-10-08
     */
    public static class DeviceRssiComparator implements Comparator<TopoViewVO> {
        @Override
        public int compare(TopoViewVO topo1, TopoViewVO topo2) {
            if (topo1.getRssi() > topo2.getRssi()) {
                return 1;
            } else if (topo1.getRssi() < topo2.getRssi()) {
                return -1;
            }
            return 0;
        }
    }

    /**
     * 获取父节点编号
     *
     * @param nodeId      设备编号
     * @param nodeVersion 协议版本号
     * @return String 父节点编号
     * @author xiedeng
     * @date 2013-09-29
     */
    private String getParentIp(String nodeId, int parentIp, int nodeVersion) {
        int strLen = (nodeVersion == 1) ? 10 : 8;
        int prefixLen = (nodeVersion == 1) ? 3 : 5;
        return nodeId.substring(0, strLen) + fillZero(parentIp, prefixLen);
    }


    /**
     * 获取拓扑图信息     向上查找
     *
     * @param nodeId   设备编号
     * @param parentId 父节点编号
     * @param rssi     信号质量
     * @author xiedeng
     * @date 2013-09-29
     */
    private void getUpTopos(String nodeId, String parentId, int rssi) {
        if (nodeCycleCount.contains(nodeId)) {
            isCycle = true;
            log.info("网络拓扑图向上查找发生异常，设备链路出现死环，节点编号：" + nodeId + "，父节点编号：" + parentId);
            return;
        } else {
            nodeCycleCount.add(nodeId);
        }
        if (nodeId.equals(parentId)) {
            log.info("网络拓扑图错误，" + nodeId + "父节点和自身节点一样");
            return;
        }
        TopoViewVO topoViewVo = getNodeInfoByNodeId(parentId);
        if (topoViewVo == null) {
            return;
        }
        topos.add(topoViewVo);
        setTopoReference(parentId, nodeId, rssi);
        // 如果是根节点，则停止
        if (isRoot(topoViewVo)) {
            return;
        }
        String _parentId = getParentIp(topoViewVo.getNodeId(), topoViewVo.getParentIp(), topoViewVo.getNodeVersion());
        getUpTopos(topoViewVo.getNodeId(), _parentId, topoViewVo.getRssi());
    }


    /**
     * 获取拓扑图信息          向下查找
     *
     * @param nodeId      设备编号
     * @param nodeVersion 协议版本
     * @author xiedeng
     * @date 2013-09-29
     */
    private void getDownTopos(String nodeId, int nodeVersion) {
        if (nodeCycleCount.contains(nodeId)) {
            log.info("网络拓扑图向下查找发生异常，设备链路出现死环，节点编号：" + nodeId);
            return;
        } else {
            nodeCycleCount.add(nodeId);
        }
        //根据版本号截取站点号和IP号
        int strLen = (nodeVersion == 1) ? 10 : 8;
        int id = Integer.parseInt(nodeId.substring(strLen));
        String siteId = nodeId.substring(0, 8);
        List<TopoViewVO> topos = topoDao.getTopos(id, siteId, strLen, nodeVersion);
        for (TopoViewVO topo : topos) {
            //添加拓扑图点信息
            this.topos.add(topo);
            //添加拓扑图映射信息
            setTopoReference(nodeId, topo.getNodeId(), topo.getRssi());
            getDownTopos(topo.getNodeId(), topo.getNodeVersion());
        }
    }

    /**
     * 拓扑图映射关系
     *
     * @param source 指向源
     * @param target 指向目标
     * @param rssi   目标设备的rssi
     * @author xiedeng
     * @date 2013-09-29
     */
    private void setTopoReference(String source, String target, int rssi) {
        Map<String, String> topoRef = new HashMap<String, String>();
        topoRef.put("source", source);
        topoRef.put("target", target);
        if (rssi >= -80) {
            topoRef.put("color", "green");
        } else {
            topoRef.put("color", "red");
        }
        topoReferences.add(topoRef);
    }

    /**
     * 设置丢包率
     *
     * @param topo      丢包率信息
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @author xiedeng
     * @date 2013-10-11
     */
    private void setLoseRate(TopoViewVO topo, Date startDate, Date endDate) {
        //从数据表中查询实际的包数
        int actualCount = topoDao.getNodeCount(topo.getNodeId(), startDate, endDate);
        //获取间隔周期，用来计算预计包数
        int interval = topo.getInterval();
        int expectedCount = 0;
        float loseRate = 0;
        //当间隔周期大于0时，开始计算
        if (interval > 0) {
            //结束时间减去开始时间再除以间隔周期等于预计的包数
            expectedCount = (int) ((endDate.getTime() - startDate.getTime()) / (interval * 1000));
            //预计的包数不能为0
            if (expectedCount > 0) {
                //得出丢包数
                int lose = expectedCount - actualCount;
                //计算丢包率
                loseRate = (float) lose / (float) expectedCount * 100;
                //丢包率范围0-100
                if (loseRate < 0) {
                    loseRate = 0;
                } else if (loseRate > 100) {
                    loseRate = 100;
                }
            } else {
                expectedCount = 0;
            }
        }
        topo.setExpectedCount(expectedCount);
        topo.setActualCount(actualCount);
        topo.setLoseRate(new BigDecimal(loseRate).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
    }

    private String fillZero(int srcValue, int length) {
        return String.format("%0" + length + "d", srcValue);
    }


    /**
     * 是否是根节点
     *
     * @param topoViewVO
     * @return
     */
    private static boolean isRoot(TopoViewVO topoViewVO) {
        if (topoViewVO.getNodeType() == 7) {
            return true;
        }
        if (topoViewVO.getSelfIp() == topoViewVO.getParentIp()) {
            return true;
        }

        return false;
    }
}
