package com.ext.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ext.myproject.test.DataBaseHelp;
import com.ext.myproject.test.HistoratyBean;
import com.ext.myproject.test.MoneyHistoryAdapter;
import com.luxiaofeng.smartfind.FindView;
import com.luxiaofeng.smartfind.Injector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 记录每日开支
 */
public class MainActivity extends AppCompatActivity {

    @FindView(R.id.text_view)
    TextView tvAdd;

    @FindView(R.id.et_input)
    EditText editText;
    @FindView(R.id.tv_delete)
    TextView tvDelete;
    @FindView(R.id.tv_show)
    TextView tv_show;
    @FindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private List<HistoratyBean> list = new ArrayList<>();
    private HistoratyBean historatyBean;
    private MoneyHistoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Injector.inject(this, this);
        adapter = new MoneyHistoryAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        queryData();
        tvAdd.setOnClickListener(v -> {
            if (editText.getText().toString().isEmpty()) {
                Toast.makeText(MainActivity.this,"请输入数字",Toast.LENGTH_SHORT).show();
                return;
            }

            DataBaseHelp dataBaseHelp = new DataBaseHelp(this, "my_db", null, 1);
            SQLiteDatabase sqLiteDatabase = dataBaseHelp.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("logDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())));
            contentValues.put("money", editText.getText().toString());
            contentValues.put("formSouse", "wx");
            sqLiteDatabase.insert("money", null, contentValues);
            Cursor cursor = sqLiteDatabase.query("money", new String[]{"money"}, null, null, null, null, null);

            String money = "";
            double number = 0;
            while (cursor.moveToNext()) {
                money = cursor.getString(cursor.getColumnIndex("money"));
                number += Double.valueOf(money);
            }

            tv_show.setText("money = " + number);
            editText.setText("");

            queryData();
        });

        tvDelete.setOnClickListener(v -> {
            DataBaseHelp dataBaseHelp = new DataBaseHelp(MainActivity.this, "my_db", null, 1);
            SQLiteDatabase sqLiteDatabase = dataBaseHelp.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            sqLiteDatabase.delete("money", null, null);

            Cursor cursor = sqLiteDatabase.query("money", new String[]{"money"}, null, null, null, null, null);

            String money = "";
            double number = 0;
            while (cursor.moveToNext()) {
                money = cursor.getString(cursor.getColumnIndex("money"));
                number += Double.valueOf(money);
            }
            tv_show.setText("money = " + number);
        });


    }

    private void queryData() {
        list.clear();
        DataBaseHelp d1 = new DataBaseHelp(this, "my_db", null, 1);
        SQLiteDatabase s1 = d1.getWritableDatabase();
        Cursor c1 = s1.query("money", new String[]{"money", "logDate", "formSouse"}, null, null, null, null, null);
        String m1 = "";
        double n1 = 0;
        String date = "";
        String payMethod = "";
        while (c1.moveToNext()) {
            m1 = c1.getString(c1.getColumnIndex("money"));
            date = c1.getString(c1.getColumnIndex("logDate"));
            payMethod = c1.getString(c1.getColumnIndex("formSouse"));
            n1 += Double.valueOf(m1);

            historatyBean = new HistoratyBean();
            historatyBean.setMoney(m1);
            historatyBean.setDate(date);
            historatyBean.setPayMethod(payMethod);
            list.add(historatyBean);
        }
        tv_show.setText(date + "\t" + n1);
        adapter.notifyDataSetChanged();
    }

    long currnet;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - currnet > 2000) {
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            currnet = System.currentTimeMillis();
        } else {
            finish();
        }

    }
}
