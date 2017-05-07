package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ui.UserBox;
import com.example.swolemates.R;

import java.util.ArrayList;

/**
 * Created by einwo on 5/7/2017.
 */

public class SelectGM_Adapter extends BaseAdapter {

    private ArrayList<UserBox> matchList;
    private Context context;

    public SelectGM_Adapter(Context context, ArrayList<UserBox> matchList) {
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

    public ArrayList<UserBox> getMatchList() { return matchList; }

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
        UserBox user = matchList.get(position);

        if (user != null) {
            holder.name = (TextView) itemView.findViewById(R.id.checkbox_desc);
            holder.check = (CheckBox) itemView.findViewById(R.id.check_box);
            holder.image = (ImageView) itemView.findViewById(R.id.checkbox_user_img);

            if (holder.name != null) {
                holder.name.setText(user.getName());
                holder.name.setTag(user.getUser().toString());
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
                if (cb.isChecked()) {
                    Toast.makeText(v.getContext(),
                            "Clicked on user: " + match.getUser().getName(),
                            Toast.LENGTH_LONG).show();
                }
                match.setSelected(cb.isChecked());
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView user_name = (TextView) v;
                Toast.makeText(v.getContext(),
                        "User Stats: \n" + user_name.getTag(),
                        Toast.LENGTH_LONG).show();
            }
        });

        return itemView;

    }

}
