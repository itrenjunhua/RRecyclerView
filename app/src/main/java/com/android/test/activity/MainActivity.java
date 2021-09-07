package com.android.test.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.test.R;
import com.android.test.databinding.DBBlockLoadActivity;
import com.android.test.databinding.DBListActivity;

public class MainActivity extends BaseActivity {
    private TextView title;
    private Button button1, button2, button3, button4, button5, button6, button7,
            button8, button9, button10, button11, button12, button13,button14;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        title = (TextView) findViewById(R.id.title);
        title.setText("RecyclerView 基本使用");

        button1 = (Button) findViewById(R.id.bt1);
        button2 = (Button) findViewById(R.id.bt2);
        button3 = (Button) findViewById(R.id.bt3);
        button4 = (Button) findViewById(R.id.bt4);
        button5 = (Button) findViewById(R.id.bt5);
        button6 = (Button) findViewById(R.id.bt6);
        button7 = (Button) findViewById(R.id.bt7);
        button8 = (Button) findViewById(R.id.bt8);
        button9 = (Button) findViewById(R.id.bt9);
        button10 = (Button) findViewById(R.id.bt10);
        button11 = (Button) findViewById(R.id.bt11);
        button12 = (Button) findViewById(R.id.bt12);
        button13 = (Button) findViewById(R.id.bt13);
        button14 = (Button) findViewById(R.id.bt14);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button10.setOnClickListener(this);
        button11.setOnClickListener(this);
        button12.setOnClickListener(this);
        button13.setOnClickListener(this);
        button14.setOnClickListener(this);
    }

    @Override
    protected void handlerClick(View view, int vId) {
        Intent intent;
        switch (vId) {
            case R.id.bt1:
                intent = new Intent(this, List1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt2:
                intent = new Intent(this, List2Activity.class);
                startActivity(intent);
                break;
            case R.id.bt3:
                intent = new Intent(this, Grid1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt4:
                intent = new Intent(this, Grid2Activity.class);
                startActivity(intent);
                break;
            case R.id.bt5:
                intent = new Intent(this, Staggered1Activity.class);
                startActivity(intent);
                break;
            case R.id.bt6:
                intent = new Intent(this, Staggered2Activity.class);
                startActivity(intent);
                break;
            case R.id.bt7:
                intent = new Intent(this, Grid3Activity.class);
                startActivity(intent);
                break;
            case R.id.bt8:
                intent = new Intent(this, MultipleItemActivity.class);
                startActivity(intent);
                break;
            case R.id.bt9:
                intent = new Intent(this, RefreshActivity.class);
                startActivity(intent);
                break;
            case R.id.bt10:
                intent = new Intent(this, ItemTouchActivity.class);
                startActivity(intent);
                break;
            case R.id.bt11:
                intent = new Intent(this, MyRefreshRecyclerViewActivity.class);
                startActivity(intent);
                break;
            case R.id.bt12:
                intent = new Intent(this, BlockLoadActivity.class);
                startActivity(intent);
                break;
            case R.id.bt13:
                intent = new Intent(this, DBListActivity.class);
                startActivity(intent);
                break;
            case R.id.bt14:
                intent = new Intent(this, DBBlockLoadActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
