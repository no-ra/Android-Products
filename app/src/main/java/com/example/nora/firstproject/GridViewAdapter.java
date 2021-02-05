package com.example.nora.firstproject;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by NORA on 1/21/2016.
 */
public class GridViewAdapter extends BaseAdapter {
    private ArrayList<String> listMenu;
    private ArrayList<Integer> listImages;
    private Activity activity;

    public GridViewAdapter(Activity activity,ArrayList<String> listMenu, ArrayList<Integer> listImages) {
        super();
        this.listMenu = listMenu;
        this.listImages = listImages;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listMenu.size();
    }

    @Override
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);

            view.txtViewTitle = (TextView) convertView.findViewById(R.id.textView1);
            view.imgViewFlag = (ImageView) convertView.findViewById(R.id.imageView1);

            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }

        view.txtViewTitle.setText(listMenu.get(position));
        view.imgViewFlag.setImageResource(listImages.get(position));

        return convertView;
    }
}


