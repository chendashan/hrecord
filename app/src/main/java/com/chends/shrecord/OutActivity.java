package com.chends.shrecord;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by chends on 2017/6/30.
 */

public class OutActivity extends Activity {
    private static final String[] strMode = { "现金", "支付宝" ,"微信支付","银行卡"};
    private static final String[] strType = { "吃饭零食","生活用品", "工作学习", "交通工具","业余爱好","其他" };

    private TextView tvTime;
    private EditText etMoney,etExplain;
    private Button btReset,btOk;
    private Spinner spMode,spType;

    private ArrayAdapter<String> modeAdapter;
    private ArrayAdapter<String> typeAdapter;

    private String mMode,mType,mExplain,mMoney,mDate;

    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private int gyear, gmonth, gday;

    private int width,height;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);
        dbHelper = new MyDatabaseHelper(this,"MoneyNote.db",null,1);
        initView();
    }

    private void initView() {
        Calendar c = Calendar.getInstance();
        gyear = c.get(Calendar.YEAR);
        gmonth = c.get(Calendar.MONTH);
        gday = c.get(Calendar.DAY_OF_MONTH);

        tvTime = (TextView) findViewById(R.id.tv_time);
        etMoney = (EditText) findViewById(R.id.ed_money);
        etExplain = (EditText) findViewById(R.id.ed_explain);
        btReset = (Button) findViewById(R.id.bt_reset);
        btOk = (Button) findViewById(R.id.bt_ok);
        //支出方式
        spMode = (Spinner) findViewById(R.id.sp_mode);
        //支出种类
        spType = (Spinner) findViewById(R.id.sp_type);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() <= 0) {
                    btOk.setEnabled(false);
                } else {
                    btOk.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        modeAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,strMode);
        modeAdapter.setDropDownViewResource(R.layout.my_spinner);
        spMode.setAdapter(modeAdapter);
        spMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMode = strMode[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,strType);
        typeAdapter.setDropDownViewResource(R.layout.my_spinner);
        spType.setAdapter(typeAdapter);
        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = strType[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etExplain.setText("");
                etMoney.setText("");
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowManager.LayoutParams lp=getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);

                ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                        OutActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                View popupView = OutActivity.this.getLayoutInflater().inflate(R.layout.popup_date, null);

                DatePicker dePicker = (DatePicker) popupView.findViewById(R.id.calendarView1);
                Button btnChoice = (Button) popupView.findViewById(R.id.btn_data_sub);
                dePicker.init(gyear, gmonth, gday, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        gyear = year;
                        gmonth = monthOfYear;
                        gday = dayOfMonth;
                    }
                });

                // 创建PopupWindow对象，指定宽度和高度
                final PopupWindow window = new PopupWindow(popupView, width/6*5, height/4*3);
                // 设置动画
                window.setAnimationStyle(R.style.popup_window_anim);
                // 设置背景颜色
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F8F8F8")));
                // 设置可以获取焦点
                window.setFocusable(true);
                // 设置可以触摸弹出框以外的区域
                window.setOutsideTouchable(true);
                // 更新popupwindow的状态
                window.update();
                window.setTouchable(true);
                window.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                btnChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        window.dismiss();
                        int mMonth = gmonth+1;
                        mDate = gyear + "-" + mMonth + "-"  + gday;
                        tvTime.setText(mDate+"");
                    }
                });

                window.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams lp=getWindow().getAttributes();
                        lp.alpha = 1f;
                        getWindow().setAttributes(lp);
                    }
                });

            }
        });
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoney = etMoney.getText().toString();
                if(!mMoney.equals("")) {
                    mExplain = etExplain.getText().toString();

                    db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("money", mMoney);
                    values.put("mode", mMode);
                    values.put("type", mType);
                    values.put("date", mDate);
                    values.put("explain", mExplain);
                    db.insert("Out", null, values);

                    Toast.makeText(OutActivity.this, "记录保存成功", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
