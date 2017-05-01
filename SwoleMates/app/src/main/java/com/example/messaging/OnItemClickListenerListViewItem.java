package com.example.messaging;

/**
 * Created by einwo on 4/28/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.example.swolemates.R;

/*
 * Here you can control what to do next when the user selects an item
 */
public class OnItemClickListenerListViewItem implements OnItemClickListener {
//    private final Context context;
//
//    public OnItemClickListenerListViewItem(Context context) {
//        this.context = null;
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();
//        TwoLineListItem groupMessageItem = ((TwoLineListItem) view.findViewById(R.id.groupMess));
        // get the clicked group message names
//        String memberNames = groupMessageItem.getText1().toString();
        // get the clicked gm message
//        String lastMessage = groupMessageItem.getText2().toString();

        String memberNames = ((TextView) view.findViewById(R.id.members)).getText().toString();

        String lastMessage = ((TextView) view.findViewById(R.id.message)).getText().toString();

        String key = ((TextView) view.findViewById(R.id.key)).getText().toString();

        Intent intent = new Intent(context, MessageActivity.class);
        intent.putExtra("key", key);
        intent.putExtra("names", memberNames);
        context.startActivity(intent);
    }
}

