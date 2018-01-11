package com.example.arturmusayelyan.threadasynctask1;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by artur.musayelyan on 11/01/2018.
 */

public class RotateAsyncFragment extends Fragment implements View.OnClickListener {
    // kam karanq uxaki aveli hesht dzevov manifesti mej mi tox grenq u verj
    // android:configChanges="orientation|screenSize|keyboardHidden"
    private Button startBtn;
    private TextView showResultTv;
    private ProgressBar progressBar;
    private MyBackGroundTask myBackGroundTask;

    public RotateAsyncFragment() {

    }

    public static RotateAsyncFragment newInstance() {
        Bundle args = new Bundle();
        RotateAsyncFragment fragment = new RotateAsyncFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_btn_for_AsyncTask:
                myBackGroundTask = new MyBackGroundTask();
                myBackGroundTask.execute();
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_async_rotate, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startBtn = view.findViewById(R.id.start_btn_for_AsyncTask);
        showResultTv = view.findViewById(R.id.show_result_tv_for_AsyncTask);
        progressBar = view.findViewById(R.id.progressBar_for_AsyncTask);
        startBtn.setOnClickListener(this);
    }


    class MyBackGroundTask extends AsyncTask<String, Integer, String> {
        @SuppressLint("WrongConstant")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showResultTv.setText("Begin...");
            progressBar.setVisibility(View.VISIBLE);
            startBtn.setEnabled(false);

        }

        @Override
        protected String doInBackground(String... strings) {
            int count = 0;
            for (int i = 1; i <= 10; i++) {
                imitateDownLoad();
                ++count;
                publishProgress(count);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            showResultTv.setText(values[0] + "");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showResultTv.setText("End");
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
            startBtn.setEnabled(true);

        }

        private void imitateDownLoad() {
            SystemClock.sleep(1000);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("State_Task_Running", false)) {
                progressBar.setVisibility(View.VISIBLE);
                startBtn.setEnabled(false);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (isTaskRunning()) {
            outState.putBoolean("State_Task_Running", true);
        }
    }


    private boolean isTaskRunning() {
        return (myBackGroundTask != null) && (myBackGroundTask.getStatus() == AsyncTask.Status.RUNNING);
    }

}
