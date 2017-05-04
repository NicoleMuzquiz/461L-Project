package com.example.messaging;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

/**
 * Created by einwo on 5/3/2017.
 */

public class OnUserBoxClickListener implements OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Context context = view.getContext();

        // When clicked, show a toast with the TextView text
        UserBox user = (UserBox) parent.getItemAtPosition(position);
        Toast.makeText(context,
                "User Stats: \n" + user.getUser(),
                Toast.LENGTH_LONG).show();
    }
}
