package com.example.messaging;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swolemates.R;
import com.example.swolemates.SwoleUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SelectGM_Activity extends AppCompatActivity {

    private int NUMBER_OF_GROUPS;

    private LinearLayout groupTextView;

    private ArrayList<UserBox> userList;
    MyCustomAdapter dataAdapter = null;

    // Firebase keys commonly used with backend Servlet instances
    private static final String MATCH = "connections";
    private static final String MSG = "messages";
    private static final String USR = "users";
    private static final String REQLOG = "requestLogger";

    private static final int RC_SIGN_IN = 9001;

    private static String TAG = "GroupTextActivity";
    private static FirebaseLogger fbLog;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private SwoleUser swoleUser, mySwoleUser;
    private String email;
    private String currentChannel;
    private List<String> channels;
    private ChildEventListener channelListener;
    private SimpleDateFormat fmt;
    private Bitmap img;

    private TextView channelLabel;
    private ListView gm_list;
    private List<Map<String, String>> groupMessages;
    private SimpleAdapter gmAdapter;
    private EditText messageText;
    private TextView status;
    private Lock swoleUserLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swoleUserLock = new ReentrantLock();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.select_gm_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringBuffer responseText = new StringBuffer();
                String message_id = "";
                String userNames = "";
                responseText.append("The following were selected...\n");

                ArrayList<UserBox> matchList = dataAdapter.matchList;
                ArrayList<String> matchIdList = new ArrayList<String>();
                ArrayList<String> displayNameList = new ArrayList<String>();

                int count = 0;
                for (int i = 0; i < matchList.size(); i++) {
                    UserBox user = matchList.get(i);
                    if (user.isSelected()) {
                        count++;
                    }
                }

                if (count > 0) {
                    matchIdList.add(firebaseUser.getUid());
                    displayNameList.add(firebaseUser.getDisplayName());
                    for (int i = 0; i < matchList.size(); i++) {
                        String userID = matchList.get(i).getId();
                        String userName = matchList.get(i).getName();
                        matchIdList.add(userID);
                        displayNameList.add(userName);
                    }
                    Collections.sort(matchIdList);
                    Collections.sort(displayNameList);

                    message_id += matchIdList.get(0);
                    userNames += displayNameList.get(0);
                    for (int i = 1; i < matchIdList.size(); i++) {
                        String user = matchIdList.get(i);
                        message_id += "_" + user;
                        userNames += ", " + displayNameList.get(i);
                    }
                    Toast.makeText(getApplicationContext(),
                            responseText, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
                    intent.putExtra("key", message_id);
                    intent.putExtra("names", userNames);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "None were selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebase = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        email = firebaseUser.getEmail();
        displayListView();

        firebase.child(USR + "/" + firebaseUser.getUid() + "/" + MATCH)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String prevKey) {
                        synchronized (swoleUserLock) {
                            swoleUser = (SwoleUser) dataSnapshot.getValue(SwoleUser.class);
                            if (swoleUser != null && !email.equals(swoleUser.getEmail()))
                                getUserImg(swoleUser.getPhotoUrl());
                            swoleUserLock.notifyAll();
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String prevKey) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String prevKey) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    private void addUser(String members, String message, String key) {
        Map<String, String> groupMessage = new HashMap<String, String>();
        groupMessage.put("names", members);
        groupMessage.put("message", message);
        groupMessage.put("key", key);
        groupMessages.add(groupMessage);

        gmAdapter.notifyDataSetChanged();
        channels.add(key);
    }

    private void displayListView() {

        //Array list of countries
        userList = new ArrayList<UserBox>();

        //create an ArrayAdaptar from the String Array
        dataAdapter = new MyCustomAdapter(this, userList);
        ListView listView = (ListView) findViewById(R.id.gm_select);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                UserBox user = (UserBox) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + user.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<UserBox> matchList;
        private Context context;

        public MyCustomAdapter(Context context, ArrayList<UserBox> matchList) {
            this.matchList = matchList;
            this.context = context;
        }

        private class ViewHolder {
            TextView name;
            CheckBox check;
            ImageView image;
        }

        /**
         * How many items are in the data set represented by this Adapter.
         *
         * @return Count of items.
         */
        @Override
        public int getCount() {
            return matchList.size();
        }

        /**
         * Get the data item associated with the specified position in the data set.
         *
         * @param position Position of the item whose data we want within the adapter's
         *                 data set.
         * @return The data at the specified position.
         */
        @Override
        public Object getItem(int position) {
            return matchList.get(position);
        }

        /**
         * Get the row id associated with the specified position in the list.
         *
         * @param position The position of the item within the adapter's data set whose row id we want.
         * @return The id of the item at the specified position.
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            View itemView = convertView;

            if (itemView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                itemView = layoutInflater.inflate(R.layout.user_checkbox, null);
            }

            holder = new ViewHolder();
            UserBox user = userList.get(position);

            if (user != null) {
                holder.name = (TextView) itemView.findViewById(R.id.checkbox_desc);
                holder.check = (CheckBox) itemView.findViewById(R.id.check_box);
                holder.image = (ImageView) itemView.findViewById(R.id.checkbox_user_img);

                if (holder.name != null) {
                    holder.name.setText(user.getName());
                }
                if (holder.image != null) {
                    holder.image.setImageBitmap(user.getImage());
                }
                if (holder.check != null) {
                    holder.check.setChecked(user.isSelected());
                    holder.check.setTag(user);
                }

            }

            holder.check.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CheckBox cb = (CheckBox) v;
                    UserBox match = (UserBox) cb.getTag();
                    Toast.makeText(getApplicationContext(),
                            "Clicked on Checkbox: " + cb.getText() +
                                    " is " + cb.isChecked(),
                            Toast.LENGTH_LONG).show();
                    match.setSelected(cb.isChecked());
                }
            });

            return itemView;

        }

    }

    private void getUserImg(String imgURLs) {
        //attempt to download image
        try {
            //try to download
            img = null;
            URL imgURL = new URL(imgURLs);
            URLConnection imgConn = imgURL.openConnection();
            imgConn.connect();
            InputStream imgIn = imgConn.getInputStream();
            BufferedInputStream imgBuff = new BufferedInputStream(imgIn);
            img = BitmapFactory.decodeStream(imgBuff);
            imgBuff.close();
            imgIn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        UserBox userBox = new UserBox(img, swoleUser.getName(), false, swoleUser.getId());
        userList.add(0, userBox);
        dataAdapter.notifyDataSetChanged();
    }

//    private class GetUserImg extends AsyncTask<String, Void, String> {
//        //get book thumbnail
//
//        @Override
//        protected String doInBackground(String... imgURLs) {
//            //attempt to download image
//            try {
//                //try to download
//                img = null;
//                URL imgURL = new URL(imgURLs[0]);
//                URLConnection imgConn = imgURL.openConnection();
//                imgConn.connect();
//                InputStream imgIn = imgConn.getInputStream();
//                BufferedInputStream imgBuff = new BufferedInputStream(imgIn);
//                img = BitmapFactory.decodeStream(imgBuff);
//                imgBuff.close();
//                imgIn.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return "";
//        }
//
//        protected void onPostExecute(String result) {
//            UserBox userBox = new UserBox(img, swoleUser.getName(), false, swoleUser.getId());
//            userList.add(0, userBox);
//            dataAdapter.notifyDataSetChanged();
//        }
//
//    }

}
