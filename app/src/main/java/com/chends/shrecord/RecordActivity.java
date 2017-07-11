package com.chends.shrecord;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chends on 2017/6/30.
 */

public class RecordActivity extends Activity {

    private List<Out> outList = new ArrayList<Out>();
    private MyDatabaseHelper dbHelper;
    private Out out;
    private OutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initOut();
        adapter = new OutAdapter(RecordActivity.this,R.layout.out_item,outList);
        ListView listView = (ListView) findViewById(R.id.lv_record);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initPopupWindow(position,view);
            }

        });
    }

    private void initPopupWindow(final int position, View view) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int pWidth = (size.x)/2;
        int pHeight = (size.y)/7;

        View popView = RecordActivity.this.getLayoutInflater().inflate(R.layout.popwindow_record,null);
        final PopupWindow popWindow = new PopupWindow(popView,pWidth,pHeight);

        TextView tvEdit = (TextView) popView.findViewById(R.id.tv_edit);
        TextView tvDelete = (TextView) popView.findViewById(R.id.tv_delete);

        //产生背景变暗效果
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);

        popWindow.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));   //背景颜色
        popWindow.setOutsideTouchable(true);      //可以触摸弹出框外区域
        popWindow.setFocusable(true);             //可以获取焦点
        popWindow.setAnimationStyle(R.style.popup_window_anim);  //弹出样式
        popWindow.showAtLocation((View)view.getParent(), Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);   //弹出位置
        popWindow.update();  //更新状态

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDelete(position);
                popWindow.dismiss();
                initOut();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void recordDelete(int position) {

        String dExplain = outList.get(position).getExplain();
        dbHelper = new MyDatabaseHelper(this,"MoneyNote.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Out","explain=?",new String[] {dExplain});
    }


    private void initOut() {
        String date,explain,type,mode;
        double money;
        outList.clear();
        dbHelper = new MyDatabaseHelper(this,"MoneyNote.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Out",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{

                date = cursor.getString(cursor.getColumnIndex("date"));
                explain = cursor.getString(cursor.getColumnIndex("explain"));
                money = cursor.getDouble(cursor.getColumnIndex("money"));
                type = cursor.getString(cursor.getColumnIndex("type"));
                mode = cursor.getString(cursor.getColumnIndex("mode"));
                out = new Out(money,mode,type,date,explain);
                outList.add(out);

            }while (cursor.moveToNext());
        }
        cursor.close();
    }
}
