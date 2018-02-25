package com.duopoints.models.messages;


import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class SendMessage {
    private String to;

    @SerializedName("collapse_key")
    private String collapseKey;

    private Integer ttl;

    private Map<String, Object> data;

    public String getCollapseKey() {
        return this.collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public Integer getTtl() {
        return this.ttl;
    }

    public void setTtl(Integer ttl) {
        this.ttl = ttl;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Map<String, Object> getData() {
        return this.data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}