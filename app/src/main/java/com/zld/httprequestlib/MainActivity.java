package com.zld.httprequestlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zld.httplib.HttpRequest;
import com.zld.httplib.callback.IHttpCallback;
import com.zld.httplib.callback.IRequestCallback;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private CompositeDisposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        mDisposable = new CompositeDisposable();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestData();
            }
        });
    }

    private void requestData() {
        new HttpRequest("/api/random/data/Android/20")
                .params("param1", "param1")
                .headers("header1", "header1")
                .requestCallback(new IRequestCallback() {
                    @Override
                    public void onPreRequest() {
                        //做一些请求前的准备，比如显示等待对话框
                    }

                    @Override
                    public void onAfterRequest() {
                        //请求后的操作，比如取消等待对话框
                    }
                })
                .callback(new IHttpCallback() {
                    @Override
                    public void success(String url, String res) {
                        Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void error(String url, int code, String msg) {
                        Toast.makeText(MainActivity.this, code + msg, Toast.LENGTH_LONG).show();
                    }
                })
                .get(mDisposable);
    }
}
