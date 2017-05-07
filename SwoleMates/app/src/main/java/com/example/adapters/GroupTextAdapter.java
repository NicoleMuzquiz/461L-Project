package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ui.GroupTextItem;
import com.example.swolemates.R;

import java.util.ArrayList;
import java.util.Collections;

public class GroupTextAdapter extends BaseAdapter {

    ArrayList<GroupTextItem> items;
    Context context;

    public GroupTextAdapter(Context context, ArrayList arrayList) {
        this.items = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public GroupTextItem getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(ArrayList<GroupTextItem> it) {
        this.items = it;
    }

    @Override
    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.grouptext_item, null);
        }
        GroupTextItem groupTextItem = items.get(position);

        // sort group texts by most recent messages first
        Collections.sort(items);

        if (groupTextItem != null) {

            TextView groupMemberNames = (TextView) itemView.findViewById(R.id.members);
            TextView lastMessage = (TextView) itemView.findViewById(R.id.message);
//            TextView lastMessageDate = (TextView) itemView.findViewById(R.id.lastMessageDate);

            if (groupMemberNames != null) {
                groupMemberNames.setText(groupTextItem.getGroupMemberNames());
            }
            if (lastMessage != null) {
                lastMessage.setText(groupTextItem.getLastMessageSent());
            }
//            if (lastMessageDate != null) {
//                lastMessageDate.setText(groupTextItem.getTimeOfLastMessage());
//            }

        }
        return itemView;
    }

    public class ViewHolder {
        ImageView image;
    }

}
