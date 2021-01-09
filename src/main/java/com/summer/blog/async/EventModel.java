package com.summer.blog.async;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
@Data
public class EventModel {
    private EventType type;
    private int actorId;
    private int entityType;
    private int entityId;
    private int entityOwnerId;
    private Map<String, String> exts = new HashMap<String, String>();

    public EventModel(EventType type) {
        this.type = type;
    }

    public EventModel() {

    }

    public String getExts(String key) {
        return exts.get(key);
    }

    public void setExts(String key, String value) {
        exts.put(key, value);
    }
}
