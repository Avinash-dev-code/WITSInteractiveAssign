package com.example.assignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

class CustomAdapterforGames extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<Object,Object>> List;
    public CustomAdapterforGames(FragmentActivity gameActivity, ArrayList<HashMap<Object, Object>> list) {
        context=gameActivity;
        inflater=LayoutInflater.from(context);
        this.List=list;

    }



    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView t1,t2,t3,t4,t5;
        ImageView imageView;


        convertView=inflater.inflate(R.layout.activity_customforgames,null);

        t1=convertView.findViewById(R.id.Type);
        t2=convertView.findViewById(R.id.Title);
        t3=convertView.findViewById(R.id.Platform);

        imageView=convertView.findViewById(R.id.imagefogames);

        t1.setText(List.get(position).get("type")+"");
        t2.setText(List.get(position).get("title")+"");
        t3.setText(List.get(position).get("platform")+"");

//        t5.setText(List.get(position).get("Erscheinungsjahr")+"");
        String gamesImage= String.valueOf(List.get(position).get("image"));
        Picasso.get().load(gamesImage).into(imageView);



        Log.e(gamesImage,"Abomas");
        return convertView;
    }

}