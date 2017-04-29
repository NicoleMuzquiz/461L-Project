package com.example.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by einwo on 3/30/2017.
 */

public class GroupTextItem {

    private String groupMemberNames;
    private String lastMessageSent;
    private Long time;

    public GroupTextItem() {}

    public GroupTextItem(String names, String lastMessage) {
        this.groupMemberNames = names;
        this.lastMessageSent = lastMessage;
    }

    public String getGroupMemberNames() {
        return groupMemberNames;
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public Map<String, String> getTime() { return ServerValue.TIMESTAMP; }
    public void setTime(Long time) { this.time = time; }

    @JsonIgnore
    public Long getTimeOfLastMessage() { return time; }
}
