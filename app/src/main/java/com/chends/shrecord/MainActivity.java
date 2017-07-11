package com.chends.shrecord;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout llCome;
    private LinearLayout llRecord;
    private LinearLayout llOut;
    private TextView tvSumOut;

    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llCome = (LinearLayout) findViewById(R.id.hm_come);
        llRecord = (LinearLayout) findViewById(R.id.hm_record);
        llOut = (LinearLayout) findViewById(R.id.hm_out);
        tvSumOut = (TextView) findViewById(R.id.tv_sum_out);

        llCome.setOnClickListener(this);
        llRecord.setOnClickListener(this);
        llOut.setOnClickListener(this);

        showSumOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showSumOut();
    }

    private void showSumOut() {
        dbHelper = new MyDatabaseHelper(this,"MoneyNote.db",null,1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select sum(money)from Out",null);
        if(cursor.getCount()!=0) {
            cursor.moveToFirst();
            String total = cursor.getString(cursor.getColumnIndex("sum(money)"));
            tvSumOut.setText(total);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hm_come:
                Intent intent1 = new Intent(MainActivity.this,ComeActivity.class);
                startActivity(intent1);
                break;
            case R.id.hm_record:
                Intent intent2 = new Intent(MainActivity.this,RecordActivity.class);
                startActivity(intent2);
                break;
            case R.id.hm_out:
                Intent intent3 = new Intent(MainActivity.this,OutActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
