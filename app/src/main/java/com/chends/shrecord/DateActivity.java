package com.chends.shrecord;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by chends on 2017/6/30.
 */

public class DateActivity extends Activity {
    private int gyear, gmonth, gday, hour, minute;
    private TimePicker tp;
    private DatePicker dp;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        // 获取当前时间

        Calendar c = Calendar.getInstance();
        gyear = c.get(Calendar.YEAR);
        gmonth = c.get(Calendar.MONTH);
        gday = c.get(Calendar.DAY_OF_MONTH);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_date);
        btn = (Button) findViewById(R.id.btn_data_sub);
        dp = (DatePicker) findViewById(R.id.calendarView1);
        dp.init(gyear, gmonth, gday, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                gyear = year;
                gmonth = monthOfYear;
                gday = dayOfMonth;

            }
        });
        // tp = (TimePicker) findViewById(R.id.timePicker1);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                updateDisplay();
            }
        });
    }

    private void updateDisplay() {
        String ss = gyear + "/" + format(gmonth + 1) + "/" + format(gday);
        Intent intent = new Intent(DateActivity.this, OutActivity.class);
        Bundle b = new Bundle();
        b.putString("time", ss);
        intent.putExtras(b);
        DateActivity.this.setResult(RESULT_OK, intent);
        DateActivity.this.finish();
    }

    private String format(int x) {
        String str = "" + x;
        if (str.length() == 1)
            str = "0" + str;
        return str;
    }
}
