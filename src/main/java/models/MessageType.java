package models;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MessageType {
    UNKNOWN("unknown"),
    TEXT("text"),
    LINK_("link"),
    PICTURE("picture"),
    VIDEO("video"),
    START_CHATTING("start-chatting"),
    SCAN_DATA("scan-data"),
    STICKER("sticker"),
    IS_TYPING("is-typing"),
    DELIVERY_RECEIPT("delivery-receipt"),
    READ_RECEIPT("read-receipt"),
    FRIEND_PICKER("friend-picker"),
    ADAPTIVE_CARD("adaptive-card");

    private String _typeString;

    static final Map<String, MessageType> __typeMap__;

    MessageType(String typeString) {
        _typeString = typeString;
    }

    @Override
    public String toString() {
        return _typeString;
    }

    static {
        Map<String, MessageType> typeMap = new ConcurrentHashMap<>();
        for(MessageType type : MessageType.values()) {
            typeMap.put(type.toString(), type);
        }
        __typeMap__ = Collections.unmodifiableMap(typeMap);
    }

    public static MessageType fromString(String s) {
        MessageType match = __typeMap__.get(s);
        if(match != null) {
            return match;
        }
        return UNKNOWN;
    }
}
