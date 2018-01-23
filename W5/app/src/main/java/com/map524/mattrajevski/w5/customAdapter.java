package com.map524.mattrajevski.w5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class customAdapter extends BaseAdapter {
    String[] descriptions;
    int[] icons;
    Context context;

    public customAdapter(MainActivity c, String[] d, int[] i) {
        descriptions = d;
        icons = i;
        context = c;
    }

    @Override
    public int getCount() {
        return icons.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = null;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(R.layout.custom_list, null);
        TextView textView = (TextView)row.findViewById(R.id.textView);
        textView.setText(descriptions[position]);
        ImageView imageView = (ImageView)row.findViewById(R.id.imageView);
        imageView.setImageResource(icons[position]);
        /*row.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, descriptions[position], Toast.LENGTH_SHORT).show();

            }
        });*/
        return row;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
