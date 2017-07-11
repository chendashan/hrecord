package com.chends.shrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chends on 2017/6/30.
 */

public class OutAdapter extends ArrayAdapter<Out> {

    private int resourceId;


    public OutAdapter(Context context, int textViewResourceId, List<Out> objects) {
        super(context,  textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Out out = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,null);

        TextView outExplain = (TextView) view.findViewById(R.id.out_explain);
        TextView outMoney = (TextView) view.findViewById(R.id.out_money);
        TextView outDate = (TextView) view.findViewById(R.id.out_date);
        TextView outType = (TextView) view.findViewById(R.id.out_type);
        TextView outMode = (TextView) view.findViewById(R.id.out_mode);

        outExplain.setText(out.getExplain());
        outMoney.setText(out.getMoney()+"");
        outDate.setText(out.getDate());
        outType.setText(out.getType());
        outMode.setText(out.getMode());

        return view;
    }
}
