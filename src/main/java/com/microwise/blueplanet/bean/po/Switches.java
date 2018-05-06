package com.microwise.blueplanet.bean.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author gaohui
 * @date 14-2-14 17:27
 */
public class Switches {
    private String id;

    private String nodeId;

    private int enable;

    private int onOff;

    private Date timestamp;

    private List<Switch> values;

    /**
     * 解析状态到 values
     *
     */
    public void parseToValues(){
        List<Switch> switches = new ArrayList<Switch>();
        for(int i = 0; i< 8; i++){
            Switch switcH = new Switch();
            switcH.setRoute(i + 1);
            switcH.setEnable(((enable >> i ) & 0x0001) == 1);
            switcH.setOnOff(((onOff >> i ) & 0x0001) == 1);

            switches.add(switcH);
        }

        this.values = switches;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int getOnOff() {
        return onOff;
    }

    public void setOnOff(int onOff) {
        this.onOff = onOff;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Switch> getValues() {
        return values;
    }

    public void setValues(List<Switch> values) {
        this.values = values;
    }
}
