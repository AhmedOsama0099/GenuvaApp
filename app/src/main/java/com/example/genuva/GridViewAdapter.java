package com.example.genuva;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {
    ArrayList<SeatsModel>arr;
    Context context;
    LayoutInflater inflater;

    public GridViewAdapter(ArrayList<SeatsModel> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null){
            inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null){
            view=inflater.inflate(R.layout.row_grid_view,viewGroup,false);
        }
        ImageView img=view.findViewById(R.id.chair_img);
        TextView txt=view.findViewById(R.id.chair_num);
        if(!arr.get(i).getSeat_state())
            img.setImageResource(R.drawable.ic_seat_green);
        else
            img.setImageResource(R.drawable.ic_seat_red);
        txt.setText(arr.get(i).getId());
        return view;
    }
}
