package com.example.messaging;

/**
 * Created by einwo on 4/28/2017.
 */

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import com.example.swolemates.R;

/*
 * Here you can control what to do next when the user selects an item
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
        TwoLineListItem groupMessageItem = ((TwoLineListItem) view.findViewById(R.id.group_message));
        // get the clicked group message names
        String listItemText = groupMessageItem.getText1().toString();
        // get the clicked gm message
        String listItemId = groupMessageItem.getText2().toString();
        // just toast it
        Toast.makeText(context, "Item: " + listItemText + ", Item ID: " + listItemId, Toast.LENGTH_SHORT).show();
    }
}

