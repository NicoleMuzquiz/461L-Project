package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swolemates.R;
import com.example.ui.StackItem;

import java.util.ArrayList;

public class StackAdapter extends BaseAdapter {

    ArrayList<StackItem> items;
    Context context;

    public StackAdapter(Context context, ArrayList arrayList) {
        this.items = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public StackItem getItem(int pos) {
        return items.get(pos);
    }

    public void setItems(ArrayList<StackItem> it) {
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
            itemView = layoutInflater.inflate(R.layout.stack_item, null);
        }
        StackItem stackItem = items.get(position);
        if (stackItem != null) {
            // TextView defined in the stack_item.xml
            TextView user_desc = (TextView) itemView.findViewById(R.id.user_desc);

            ImageView user_image = (ImageView) itemView.findViewById(R.id.user_image);

            if (user_image != null && stackItem.getUserImg() != null) {
                user_image.setImageBitmap(stackItem.getUserImg());
            }
            if (user_desc != null && stackItem.getUserDesc() != null) {
                user_desc.setText(stackItem.getUserDesc());
            }

        }
        return itemView;
    }

    public class ViewHolder {
        ImageView image;
    }

}
