package com.example.ui;

/**
 * Created by einwo on 3/30/2017.
 */

public class GroupTextItem {

    private String groupMemberNames;
    private String lastMessageSent;
    private String timeOfLastMessage;

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

    public String getTimeOfLastMessage() { return lastMessageSent; }
}
