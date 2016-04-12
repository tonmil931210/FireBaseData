package com.example.asus.firebaseapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

/**
 * Created by ASUS on 12/04/2016.
 */
public class CustomAdapter extends BaseAdapter{
    private static final String TAG = "AS_ListView";
    private Context context;
    private String[] values;
    private String[] keys;
    Firebase root;
    public CustomAdapter(Context context, String[] values, String[] keys, Firebase root){
        this.context = context;
        this.values = values;
        this.keys = keys;
        this.root = root;
    }

    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int position) {
        return values[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = values[position];
        final String key = keys[position];

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_row, null);
        }
        Button btnRemove = (Button) convertView.findViewById(R.id.btnRemove);
        btnRemove.setFocusableInTouchMode(false);
        btnRemove.setFocusable(false);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                root.child(key).removeValue();
            }
        });
        TextView f1 = (TextView) convertView.findViewById(R.id.tvdata);
        f1.setText(s);
        convertView.setTag(s);
        return convertView;
    }
}
