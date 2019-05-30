package com.summer.blog.async;

/**
 * @author     ：lightingSummer
 * @date       ：2019/5/30 0030
 * @description：
 */
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;

    EventType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
