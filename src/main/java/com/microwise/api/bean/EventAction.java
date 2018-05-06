package com.microwise.api.bean;

import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 */
public class EventAction {

    public EventAction() {
    }

    public EventAction(String text, String url, String type, Map<String, Object> parameters) {
        this.text = text;
        this.url = url;
        this.type = type;
        this.parameters = parameters;
    }

    private String text;

    private String url;

    private String type;

    private Map<String, Object> parameters;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
