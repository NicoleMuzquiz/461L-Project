package com.example.system;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.swolemates.R;

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
            TextView textView = (TextView) itemView.findViewById(R.id.textView);

//            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView10);


            if (textView != null) {
                textView.setText(stackItem.getItemText());

//                String imageName= stackItem.getImageName();

//                int resId= this.getDrawableResIdByName(imageName);

//                imageView.setImageResource(resId);
//                imageView.setBackgroundColor(Color.rgb(211,204,188));
            }

        }
        return itemView;
    }

    public class ViewHolder {
        ImageView image;
    }

}
//import android.content.Context;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import com.example.swolemates.R;
//
//import java.util.List;
//
//public class StackAdapter extends ArrayAdapter<StackItem> {
//
//    private List<StackItem> items;
//    private Context context;
//
//    public StackAdapter(Context context, int textViewResourceId,
//                        List<StackItem> objects) {
//        super(context, textViewResourceId, objects);
//        this.items = objects;
//        this.context = context;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View itemView = convertView;
//        if (itemView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            itemView = layoutInflater.inflate(R.layout.stack_item, null);
//        }
//        StackItem stackItem = items.get(position);
//        if (stackItem != null) {
//            // TextView defined in the stack_item.xml
//            TextView textView = (TextView) itemView.findViewById(R.id.textView);
//
////            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView10);
//
//
//            if (textView != null) {
//                textView.setText(stackItem.getItemText());
//
////                String imageName= stackItem.getImageName();
//
////                int resId= this.getDrawableResIdByName(imageName);
//
////                imageView.setImageResource(resId);
////                imageView.setBackgroundColor(Color.rgb(211,204,188));
//            }
//
//        }
//        return itemView;
//    }
//
//
//    // Find Image ID corresponding to the name of the image (in the drawable folder).
//    public int getDrawableResIdByName(String resName)  {
//        String pkgName = context.getPackageName();
//        // Return 0 if not found.
//        int resID = context.getResources().getIdentifier(resName, "drawable", pkgName);
//        Log.i("MyLog", "Res Name: " + resName + "==> Res ID = " + resID);
//        return resID;
//    }
//
//}