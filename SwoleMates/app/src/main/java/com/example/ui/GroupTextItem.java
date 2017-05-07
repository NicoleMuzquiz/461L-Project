package com.example.ui;

import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by einwo on 3/30/2017.
 */

public class GroupTextItem implements Comparable<GroupTextItem> {

    private String groupMemberNames;
    private String lastMessageSent;
    private Long time;

    public GroupTextItem() {}

    public GroupTextItem(String names, String lastMessage) {
        this.setGroupMemberNames(names);
        this.setLastMessageSent(lastMessage);
    }

    public String getGroupMemberNames() {
        return groupMemberNames;
    }

    public String getLastMessageSent() {
        return lastMessageSent;
    }

    public Map<String, String> getTime() {
        return ServerValue.TIMESTAMP;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @JsonIgnore
    public Long getTimeOfLastMessage() {
        return time;
    }

    public void setLastMessageSent(String lastMessageSent) {
        this.lastMessageSent = lastMessageSent;
    }

    public void setGroupMemberNames(String groupMemberNames) {
        this.groupMemberNames = groupMemberNames;
    }

    @Override
    public int compareTo(@NonNull GroupTextItem o) {

        if (this.getTimeOfLastMessage() > o.getTimeOfLastMessage()) {
            return -1;

        } else if (this.getTimeOfLastMessage() < o.getTimeOfLastMessage()) {
            return 1;

        }

        return 0;
    }

    @Override
    public boolean equals(@NonNull Object o) {

        if(o instanceof GroupTextItem) {
            GroupTextItem p = (GroupTextItem) o;
            if (this.getTimeOfLastMessage() == p.getTimeOfLastMessage() &&
                    this.getLastMessageSent() == this.getLastMessageSent() &&
                    this.getGroupMemberNames() == this.getGroupMemberNames()) {
                return true;
            }
        }

        return false;
    }
}
