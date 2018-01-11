package com.example.arturmusayelyan.threadasynctask1;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HandlerInRotation extends AppCompatActivity implements View.OnClickListener {
    private Button startBtn;
    private TextView showResultTv;
    private ProgressBar progBar;
    private Handler handler;
    private int progressBarProgress;
    private String dataTv;

    private final int START_TASK = 48;
    private final int END_TASK = 49;

    Handler.Callback handlerCallback = new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == START_TASK) {
                progBar.setVisibility(View.VISIBLE);
                showResultTv.setText("Loading...");
                startBtn.setEnabled(false);
            } else if (msg.what >= 0 && msg.what <= 9) {
                progressBarProgress = msg.what;
                progBar.setProgress(progressBarProgress);
                dataTv = String.valueOf(msg.what);
                showResultTv.setText(dataTv);
            } else if (msg.what == END_TASK) {
                progBar.setVisibility(View.GONE);
                progressBarProgress = 0;
                progBar.setProgress(progressBarProgress);
                showResultTv.setText("");
                startBtn.setEnabled(true);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_handler);
        startBtn = findViewById(R.id.start_btn);
        showResultTv = findViewById(R.id.show_result_tv);
        progBar = findViewById(R.id.progressBar);
        startBtn.setOnClickListener(this);
        handler = new Handler(handlerCallback);

//        if (savedInstanceState != null) {
//            //  handler.sendEmptyMessage(savedInstanceState.getInt("ProgressBarID"));
//            Log.d("Art", savedInstanceState.getInt("ProgressBarID") + "");
//            progressBarProgress=savedInstanceState.getInt("ProgressBarID");
//            progBar.setProgress(progressBarProgress);
//            handler.sendEmptyMessage(progressBarProgress);
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn:
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(START_TASK);
                        SystemClock.sleep(2000);
                        imitateDownload();
                        SystemClock.sleep(1500);
                        handler.sendEmptyMessage(END_TASK);
                    }
                });
                thread.start();
                break;
        }
    }

//    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        outState.putInt("ProgressBarID", progBar.getProgress());
//        outState.putString("TextViewID", showResultTv.getText().toString());
//    }

    private void imitateDownload() {
        for (int i = 0; i <= 9; i++) {
            SystemClock.sleep(1000);
            handler.sendEmptyMessage(i);
        }
    }
}
