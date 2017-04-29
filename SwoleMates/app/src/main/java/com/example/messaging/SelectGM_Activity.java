package com.example.messaging;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.FacebookLoginActivity;
import com.example.swolemates.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectGM_Activity extends AppCompatActivity {

    private int NUMBER_OF_GROUPS;

    private LinearLayout groupTextView;

    // Firebase keys commonly used with backend Servlet instances
    private static final String MSG = "messages";
    private static final String CHS = "users";
    private static final String REQLOG = "requestLogger";

    private static final int RC_SIGN_IN = 9001;

    private static String TAG = "GroupTextActivity";
    private static FirebaseLogger fbLog;

    private FirebaseUser firebaseUser;
    private DatabaseReference firebase;
    private String token;
    private String inbox;
    private String currentChannel;
    private List<String> channels;
    private ChildEventListener channelListener;
    private SimpleDateFormat fmt;

    private TextView channelLabel;
    private ListView gm_list;
    private List<Map<String, String>> groupMessages;
    private SimpleAdapter gmAdapter;
    private EditText messageText;
    private TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gm_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        firebaseUser = FacebookLoginActivity.getmAuth().getCurrentUser();

        // Switching a listener to the selected channel.
        initFirebase();
        firebase.child(CHS + "/" + firebaseUser.getUid() + "/" + MSG).addChildEventListener(channelListener);
    }

    private void initFirebase() {
        channels = new ArrayList<String>();
        firebase = FirebaseDatabase.getInstance().getReference();
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

    /*
     * Initialize pre-defined channels as Activity menu.
     * Once a channel is selected, ChildEventListener is attached and
     * waits for groupMessages.
     */
    private void initUI(String channelString) {
        Log.d(TAG, "Channels : " + channelString);

        channelListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String prevKey) {
                GroupTextItem gm = (GroupTextItem) snapshot.getValue(GroupTextItem.class);
                // Extract attributes from Message object to display on the screen.
                addUser(gm.getGroupMemberNames(), gm.getLastMessageSent() + "\t" +
                        fmt.format(new Date(gm.getTimeOfLastMessage())), snapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e(TAG, error.getDetails());
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String prevKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String prevKey) {
            }
        };
    }

    private void displayListView() {

        //Array list of countries
        ArrayList<Country> countryList = new ArrayList<Country>();
        Country country = new Country("AFG", "Afghanistan", false);
        countryList.add(country);
        country = new Country("ALB", "Albania", true);
        countryList.add(country);
        country = new Country("DZA", "Algeria", false);
        countryList.add(country);
        country = new Country("ASM", "American Samoa", true);
        countryList.add(country);
        country = new Country("AND", "Andorra", true);
        countryList.add(country);
        country = new Country("AGO", "Angola", false);
        countryList.add(country);
        country = new Country("AIA", "Anguilla", false);
        countryList.add(country);

        //create an ArrayAdaptar from the String Array
        dataAdapter = new SelectGM.MyCustomAdapter(this,
                R.layout.country_info, countryList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Country country = (Country) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + country.getName(),
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<Country> {

        private ArrayList<Country> countryList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Country> countryList) {
            super(context, textViewResourceId, countryList);
            this.countryList = new ArrayList<Country>();
            this.countryList.addAll(countryList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            SelectGM.MyCustomAdapter.ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.country_info, null);

                holder = new SelectGM.MyCustomAdapter.ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Country country = (Country) cb.getTag();
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        country.setSelected(cb.isChecked());
                    }
                });
            } else {
                holder = (SelectGM.MyCustomAdapter.ViewHolder) convertView.getTag();
            }

            Country country = countryList.get(position);
            holder.code.setText(" (" + country.getCode() + ")");
            holder.name.setText(country.getName());
            holder.name.setChecked(country.isSelected());
            holder.name.setTag(country);

            return convertView;

        }

    }

    private void checkButtonClick() {


        Button myButton = (Button) findViewById(R.id.findSelected);
        myButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                StringBuffer responseText = new StringBuffer();
                responseText.append("The following were selected...\n");

                ArrayList<Country> countryList = dataAdapter.countryList;
                for (int i = 0; i < countryList.size(); i++) {
                    Country country = countryList.get(i);
                    if (country.isSelected()) {
                        responseText.append("\n" + country.getName());
                    }
                }

                Toast.makeText(getApplicationContext(),
                        responseText, Toast.LENGTH_LONG).show();

            }
        });

    }

}
