package com.microwise.blueplanet.sys.cache;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microwise.blueplanet.bean.vo.*;
import com.microwise.blueplanet.service.*;
import com.microwise.blueplanet.sys.Blueplanet;
import com.microwise.common.sys.Constants;
import com.microwise.common.util.BeanMaps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.*;

/**
 * 区域设备树(ztree) 缓存加载
 *
 * @author gaohui
 * @date 13-1-23 10:30
 */
@Component
@Scope("singleton")
@Blueplanet
public class ZoneDeviceTreeCacheLoader extends CacheLoader<String, List<Map<String, Object>>> {
    public static final String P_ID = "pId";

    @Autowired
    protected SiteService siteService;

    @Autowired
    private ZoneService zoneService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SensorinfoService sensorinfoService;

    @Override
    public List<Map<String, Object>> load(String siteId) throws Exception {
        SiteVO site = siteService.findSiteById(siteId);
        // 站点下所有的区域
        List<ZoneVO> zones = zoneService.findZonesBySiteId(siteId);

        // 按区域名称排序
        Collections.sort(zones, new Comparator<ZoneVO>() {
            Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);

            @Override
            public int compare(ZoneVO o1, ZoneVO o2) {
                String name1 = replaceLetterToNumber(o1.getZoneName());
                String name2 = replaceLetterToNumber(o2.getZoneName());
                return comparator.compare(name1, name2);
            }
        });

        // 站点下所有的位置点
        List<LocationVO> locations = locationService.findLocationsBySiteIdAndLocationName(siteId, null);

        // 站点下所有的设备
        List<DeviceVO> devices = deviceService.findDevicesBySiteId(siteId);

        List<SensorinfoVO> sensorinfos = sensorinfoService.findSensorinfo();
        Map<Integer, SensorinfoVO> sensorinfoMap = Maps.uniqueIndex(sensorinfos, new Function<SensorinfoVO, Integer>() {
            @Override
            public Integer apply(SensorinfoVO sensorinfoVO) {
                return sensorinfoVO.getSensorPhysicalid();
            }
        });

        return new ZoneDeviceTree(site, zones, locations, devices, sensorinfoMap).toZTree();
    }

    private static class ZoneDeviceTree {
        private SiteVO site;
        private List<ZoneVO> zones;
        private List<LocationVO> locations;
        private List<DeviceVO> devices;

        private Map<Integer, SensorinfoVO> sensorinfoMap;

        // ztree 设备树
        private List<Map<String, Object>> deviceTree = new LinkedList<Map<String, Object>>();

        List<LocationVO> undeployedLocations = Lists.newArrayList();
        List<DeviceVO> unboundDevices = Lists.newArrayList();
        // 区域的子区域
        ArrayListMultimap<String, ZoneVO> subZones = ArrayListMultimap.create();
        // 已部署的位置点
        ArrayListMultimap<String, LocationVO> deployedLocationMap = ArrayListMultimap.create();
        // 已绑定的设备
        ArrayListMultimap<String, DeviceVO> boundDeviceMap = ArrayListMultimap.create();
        // 从模块位置点
        ArrayListMultimap<String, LocationVO> slaveLocationMap = ArrayListMultimap.create();
        // 从模块设备
        ArrayListMultimap<String, DeviceVO> slaveModuleMap = ArrayListMultimap.create();

        public ZoneDeviceTree(SiteVO site, List<ZoneVO> zones, List<LocationVO> locations, List<DeviceVO> devices, Map<Integer, SensorinfoVO> sensorinfoMap) {
            this.site = site;
            this.zones = zones;
            this.locations = locations;
            this.devices = devices;
            this.sensorinfoMap = sensorinfoMap;

            // 将设备排序 先网关，再中继，再传感器
            Collections.sort(devices, new DeviceOrderComparator());
        }

        public List<Map<String, Object>> toZTree() {
            parseRelationShip();
            return getZtree();
        }

        /**
         * 解析区域，设备父子关系
         */
        private void parseRelationShip() {
            Iterator<ZoneVO> zoneIterator = zones.iterator();
            while (zoneIterator.hasNext()) {
                ZoneVO zone = zoneIterator.next();
                if (!Strings.isNullOrEmpty(zone.getParentId())) {
                    subZones.put(zone.getParentId(), zone);
                    zoneIterator.remove();
                }
            }

            for (Iterator<LocationVO> locationIterator = locations.iterator(); locationIterator.hasNext(); ) {
                LocationVO location = locationIterator.next();
                if (location.getDevice() != null && location.getDevice().getNodeType() == DeviceVO.DEVICE_TYPE_SLAVE_MODULE) {
                    // 根据IP关系组织主模块与从模块关系
                    String parentIP = location.getDevice().getParentIP();
                    slaveLocationMap.put(parentIP, location);
                    locationIterator.remove();
                } else if (!Strings.isNullOrEmpty(location.getZoneId())) {
                    deployedLocationMap.put(location.getZoneId(), location);
                    locationIterator.remove();
                }
            }
            undeployedLocations = locations;

            for (Iterator<DeviceVO> deviceIterator = devices.iterator(); deviceIterator.hasNext(); ) {
                DeviceVO device = deviceIterator.next();
                if (device.getNodeType() == DeviceVO.DEVICE_TYPE_SLAVE_MODULE) {
                    // 根据 IP 关系组织主模块与从模块的关系
                    String parentIP = device.getParentIP();
                    slaveModuleMap.put(parentIP, device);
                    deviceIterator.remove();
                } else if (device.getLocation() != null) {
                    if (Strings.isNullOrEmpty(device.getLocation().getZoneId())) {
                        boundDeviceMap.put("undeployed", device);
                    }
                    deviceIterator.remove();
                }
            }
            unboundDevices = devices;
        }

        private List<Map<String, Object>> getZtree() {
            int siteTreeId = 1;
            addSiteNode(deviceTree);
            // 添加区域
            for (ZoneVO zone : zones) {
                addZoneNode(deviceTree, zone, siteTreeId);
            }
            // 添加未部署位置点
            if (!undeployedLocations.isEmpty()) {
                addUndeployedLocationsNode(deviceTree, siteTreeId);
            }
            // 添加未部署设备
            if (!unboundDevices.isEmpty()) {
                addUnboundDevicesNode(deviceTree, siteTreeId);
            }
            return deviceTree;
        }

        /**
         * 添加站点
         * logicGroup id 为 1, type 为 "site"
         */
        private void addSiteNode(List<Map<String, Object>> deviceTree) {
            Map<String, Object> siteMap = new HashMap<String, Object>();
            siteMap.put("id", 1);
            siteMap.put(P_ID, 0);
            siteMap.put("name", site.getSiteName());
            siteMap.put("siteId", site.getSiteId());
            siteMap.put("type", "site");
            siteMap.put("open", true);
            siteMap.put("isParent", true);
            siteMap.put("iconSkin", "site");
            siteMap.put("description", site.getSiteId());

            deviceTree.add(siteMap);
        }

        /**
         * 递归添加区域节点
         *
         * @param deviceTree
         * @param zone
         * @param parentTreeId
         */
        private void addZoneNode(List<Map<String, Object>> deviceTree, ZoneVO zone, int parentTreeId) {
            // 添加区域节点
            Integer zoneTreeId = deviceTree.size() + 1;
            Map<String, Object> zoneMap = createZoneNodeView(zone, parentTreeId, zoneTreeId);
            deviceTree.add(zoneMap);


            // 添加位置点
            int repeatorCategoryTreeId = -1;
            for (LocationVO location : deployedLocationMap.get(zone.getZoneId())) {
                // 中继归类
                if (location.getDevice() != null && location.getDevice().getNodeType() == DeviceVO.DEVICE_TYPE_REPETER) {
                    if (repeatorCategoryTreeId == -1) {
                        repeatorCategoryTreeId = addRepeatorCategoryNode(deviceTree, zoneTreeId);
                    }
                    addLocationNode(deviceTree, location, repeatorCategoryTreeId);
                } else {
                    addLocationNode(deviceTree, location, zoneTreeId);
                }
            }

            // 添加子区域
            for (ZoneVO childZone : subZones.get(zone.getZoneId())) {
                addZoneNode(deviceTree, childZone, zoneTreeId);
            }

        }

        private void addDeviceListNode(List<Map<String, Object>> deviceTree, ZoneVO zone, Integer zoneTreeId) {
            // 添加区域设备集合
            Integer deviceListTreeId = deviceTree.size() + 1;
            Map<String, Object> deviceListMap = createDeviceListView(zoneTreeId, deviceListTreeId);
            deviceTree.add(deviceListMap);
            // 添加设备
            int repeatorCategoryTreeId = -1;
            for (DeviceVO childDevice : boundDeviceMap.get(zone.getZoneId())) {
                // 中继归类
                if (childDevice.getNodeType() == DeviceVO.DEVICE_TYPE_REPETER) {
                    if (repeatorCategoryTreeId == -1) {
                        repeatorCategoryTreeId = addRepeatorCategoryNode(deviceTree, deviceListTreeId);
                    }
                    addDeviceNode(deviceTree, childDevice, repeatorCategoryTreeId, zone.getZoneId());
                } else {
                    addDeviceNode(deviceTree, childDevice, deviceListTreeId, zone.getZoneId());
                }
            }
        }

        /**
         * 添加设备节点
         *
         * @param deviceTree
         * @param device
         * @param parentTreeId
         */
        private void addDeviceNode(List<Map<String, Object>> deviceTree, DeviceVO device, int parentTreeId, String zoneId) {
            Integer deviceTreeId = deviceTree.size() + 1;
            Map<String, Object> deviceMap = createDeviceNodeView(device, parentTreeId, deviceTreeId, zoneId);
            deviceTree.add(deviceMap);

            // 如果是主模块，添加从模块
            if (device.getNodeType() == DeviceVO.DEVICE_TYPE_MASTER_MODULE) {
                for (DeviceVO slaveModule : slaveModuleMap.get(device.getCurrentIP())) {
                    addDeviceNode(deviceTree, slaveModule, deviceTreeId, zoneId);
                }
            }
        }


        private void addLocationNode(List<Map<String, Object>> deviceTree, LocationVO location, int parentTreeId) {
            Integer locationTreeId = deviceTree.size() + 1;
            Map<String, Object> locationMap = createLocationView(location, parentTreeId, locationTreeId);
            deviceTree.add(locationMap);

            // 如果是主模块，添加从模块
            if (location.getDevice() != null && location.getDevice().getNodeType() == DeviceVO.DEVICE_TYPE_MASTER_MODULE) {
                for (LocationVO slaveLocation : slaveLocationMap.get(location.getDevice().getCurrentIP())) {
                    addLocationNode(deviceTree, slaveLocation, locationTreeId);
                }
            }
        }

        private void addUndeployedLocationsNode(List<Map<String, Object>> deviceTree, int parentTreeId) {
            int treeId = deviceTree.size() + 1;
            // 未部署设备目录
            Map<String, Object> undeployedCategory = new HashMap<String, Object>();
            undeployedCategory.put("id", treeId);
            undeployedCategory.put("pId", parentTreeId);
            undeployedCategory.put("iconSkin", "undeploy");
            undeployedCategory.put("type", "undeployed");
            undeployedCategory.put("name", "未部署位置点");
            undeployedCategory.put("isParent", "true");
            undeployedCategory.put("description", "未部署位置点");
            deviceTree.add(undeployedCategory);

            for (LocationVO location : undeployedLocations) {
                addLocationNode(deviceTree, location, treeId);
            }

            if (!boundDeviceMap.get("undeployed").isEmpty()) {
                addDeviceListNode(deviceTree, new ZoneVO("undeployed"), treeId);
            }
        }

        /**
         * 未部署设备目录
         *
         * @param deviceTree
         * @param parentTreeId
         */
        private void addUnboundDevicesNode(List<Map<String, Object>> deviceTree, int parentTreeId) {
            int treeId = deviceTree.size() + 1;
            // 未部署设备目录
            Map<String, Object> unboundCategory = new HashMap<String, Object>();
            unboundCategory.put("id", treeId);
            unboundCategory.put("pId", parentTreeId);
            unboundCategory.put("iconSkin", "undeploy");
            unboundCategory.put("type", "unbound");
            unboundCategory.put("name", "未绑定设备");
            unboundCategory.put("isParent", "true");
            unboundCategory.put("description", "未绑定设备");
            deviceTree.add(unboundCategory);

            for (DeviceVO device : unboundDevices) {
                if (device.getNodeType() == DeviceVO.DEVICE_TYPE_SLAVE_MODULE) {
                    // 从模块跳过
                    continue;
                }
                addDeviceNode(deviceTree, device, treeId, null);
            }
        }

        /**
         * 创建区域 view
         *
         * @param zone
         * @param parentTreeId
         * @param zoneTreeId
         * @return
         */
        private Map<String, Object> createZoneNodeView(ZoneVO zone, int parentTreeId, Integer zoneTreeId) {
            Map<String, Object> zoneMap = BeanMaps.toMap(zone, new String[]{"zoneId", "zoneName", "parentId"});
            zoneMap.put(P_ID, parentTreeId);
            zoneMap.put("id", zoneTreeId);
            zoneMap.put("name", zone.getZoneName());
            zoneMap.put("isParent", true);
            zoneMap.put("iconSkin", "area");
            zoneMap.put("type", "zone");
            zoneMap.put("description", zone.getZoneName());
            return zoneMap;
        }

        private Map<String, Object> createLocationView(LocationVO location, int parentTreeId, int locationTreeId) {
            Map<String, Object> locationMap = BeanMaps.toMap(location, new String[]{"zoneId", "locationName", "nodeId"});
            locationMap.put("locationId", location.getId());
            locationMap.put(P_ID, parentTreeId);
            locationMap.put("id", locationTreeId);
            locationMap.put("name", location.getLocationName());
            locationMap.put("isParent", false);
            String locationIcon = location.getType() == 0 ? "location" : "offline-location";
            //如果有绑定设备
            if (location.getDevice() != null) {
                int nodeType = location.getDevice().getNodeType();
                switch (nodeType) {
                    case Constants.NODE:
                        locationIcon = "device";
                        break;
                    case Constants.RELAY:
                        locationIcon = "relay";
                        break;
                    case Constants.MAIN_MODULE:
                        locationIcon = "master-module";
                        break;
                    case Constants.CHILD_MODULE:
                        locationIcon = "device";
                        break;
                    case Constants.CONTROL_MODULE:
                        locationIcon = "control-module";
                        break;
                    case Constants.GATEWAY:
                        locationIcon = "gateway";
                        break;
                }
            }

            locationMap.put("iconSkin", locationIcon);
            locationMap.put("type", "location");
            locationMap.put("sensorPhysicalIds", location.getSensorIdList()); // 添加监测指标
            locationMap.put("description", descriptionOfLocation(location));

            return locationMap;
        }

        private Map<String, Object> createDeviceListView(int parentTreeId, int deviceListTreeId) {
            Map<String, Object> deviceListMap = new HashMap<String, Object>();
            deviceListMap.put(P_ID, parentTreeId);
            deviceListMap.put("id", deviceListTreeId);
            deviceListMap.put("name", "设备");
            deviceListMap.put("isParent", true);
            deviceListMap.put("iconSkin", "devices");
            deviceListMap.put("type", "deviceList");
            deviceListMap.put("description", "设备");
            return deviceListMap;
        }


        /**
         * 创建设备 view
         *
         * @param device
         * @param parentTreeId
         * @param deviceTreeId
         * @return
         */
        private Map<String, Object> createDeviceNodeView(DeviceVO device, int parentTreeId, Integer deviceTreeId, String zoneId) {
            Map<String, Object> deviceMap = BeanMaps.toMap(device, new String[]{"nodeId", "nodeType"});
            deviceMap.put(P_ID, parentTreeId);
            deviceMap.put("id", deviceTreeId);
            // 设备名称为空，显示设备 id
            String nodeName = device.getNodeId().substring(8);
            deviceMap.put("name", nodeName);
            deviceMap.put("zoneId", zoneId);
            deviceMap.put("shortNodeId", device.getNodeId().substring(8));
            deviceMap.put("description", device.getNodeId().substring(8));
            switch (device.getNodeType()) {
                case DeviceVO.DEVICE_TYPE_GATEWAY:
                    deviceMap.put("iconSkin", "gateway");
                    deviceMap.put("type", "gateway");
                    break;
                case DeviceVO.DEVICE_TYPE_REPETER:
                    deviceMap.put("iconSkin", "relay");
                    deviceMap.put("type", "repeator");
                    break;
                case DeviceVO.DEVICE_TYPE_MASTER_MODULE:
                    deviceMap.put("iconSkin", "master-module");
                    deviceMap.put("type", "masterModule");
                    deviceMap.put("isParent", true);
                    break;
                case DeviceVO.DEVICE_TYPE_SLAVE_MODULE:
                    deviceMap.put("iconSkin", "device");
                    deviceMap.put("type", "slaveModule");
                    deviceMap.put("sensorPhysicalIds", device.getSensorPhysicalidList()); // 添加监测指标
                    deviceMap.put("description", descriptionOfDevice(device));
                    break;
                case DeviceVO.DEVICE_TYPE_CONTROL_MODUlE:
                    deviceMap.put("iconSkin", "control-module");
                    deviceMap.put("type", "controlModule");
                    break;
                case DeviceVO.DEVICE_TYPE_SENSOR:
                    deviceMap.put("iconSkin", "device");
                    deviceMap.put("type", "device");
                    deviceMap.put("sensorPhysicalIds", device.getSensorPhysicalidList()); // 添加监测指标
                    deviceMap.put("description", descriptionOfDevice(device));
                    break;
            }
            return deviceMap;
        }

        /**
         * 添加中继归类, 并返回归类 treeId
         *
         * @param deviceTree
         * @param parentTreeId
         * @return
         */
        private static int addRepeatorCategoryNode(List<Map<String, Object>> deviceTree, int parentTreeId) {
            Map<String, Object> repeatorCategory = new HashMap<String, Object>();
            repeatorCategory.put(P_ID, parentTreeId);
            int id = deviceTree.size() + 1;
            repeatorCategory.put("id", id);
            repeatorCategory.put("name", "中继");
            repeatorCategory.put("iconSkin", "relay");
            repeatorCategory.put("type", "repeators");
            repeatorCategory.put("description", "中继");

            deviceTree.add(repeatorCategory);
            return id;
        }

        private String descriptionOfLocation(LocationVO location) {
            StringBuilder sb = new StringBuilder();
            sb.append(location.getLocationName());
            sb.append("\r\n");
            if (!Strings.isNullOrEmpty(location.getNodeId())) {
                sb.append(location.getNodeId()).append("\r\n");
            }

            for (Integer sensorPhysicalId : location.getSensorIdList()) {
                SensorinfoVO sensorinfo = sensorinfoMap.get(sensorPhysicalId);
                sb.append("\r\n");
                sb.append(sensorinfo.getCnName());
            }
            return sb.toString();
        }

        private String descriptionOfDevice(DeviceVO device) {
            StringBuilder sb = new StringBuilder();
            sb.append(device.getNodeId().substring(8));
            sb.append("\r\n");

            for (Integer sensorPhysicalId : device.getSensorPhysicalidList()) {
                SensorinfoVO sensorinfo = sensorinfoMap.get(sensorPhysicalId);
                sb.append("\r\n");
                sb.append(sensorinfo.getCnName());
            }
            return sb.toString();
        }
    }

    /**
     * 设备在树中的显示顺序
     */
    static class DeviceOrderComparator implements Comparator<DeviceVO> {
        @Override
        public int compare(DeviceVO o1, DeviceVO o2) {
            return typeToOrder(o1.getNodeType()) - typeToOrder(o2.getNodeType());
        }

        // 将类型转换为顺序值，小的在前大的在后，依次为：
        // 网关、中继、主模块、传感器节点
        private int typeToOrder(int type) {
            switch (type) {
                case DeviceVO.DEVICE_TYPE_GATEWAY:
                    return 0;
                case DeviceVO.DEVICE_TYPE_REPETER:
                    return 1;
                case DeviceVO.DEVICE_TYPE_CONTROL_MODUlE:
                    return 2;
                case DeviceVO.DEVICE_TYPE_MASTER_MODULE:
                    return 3;
                case DeviceVO.DEVICE_TYPE_SENSOR:
                    return 4;
                default:
                    return Integer.MAX_VALUE;
            }
        }
    }

    /**
     * 目前只支持一位的中文大写数字
     *
     * @param chinese
     * @return
     */
    public static String replaceLetterToNumber(String chinese) {
        String result = chinese;
        result = result.replaceAll("一", "1");
        result = result.replaceAll("二", "2");
        result = result.replaceAll("三", "3");
        result = result.replaceAll("四", "4");
        result = result.replaceAll("五", "5");
        result = result.replaceAll("六", "6");
        result = result.replaceAll("七", "7");
        result = result.replaceAll("八", "8");
        result = result.replaceAll("九", "9");
        result = result.replaceAll("十", "10");
        return result;
    }

}
