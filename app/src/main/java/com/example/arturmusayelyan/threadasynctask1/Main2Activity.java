package com.example.arturmusayelyan.threadasynctask1;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private TextView infoTv;
    private ProgressBar progressBar;
    private MyBackGroundTask backGroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        infoTv = findViewById(R.id.info_tv);
        progressBar = findViewById(R.id.prog_bar);
    }

    public void startButtonWork(View view) {
        backGroundTask = new MyBackGroundTask();
        backGroundTask.execute("file_path_1", "file_path_2", "file_path_3", "file_path_4");
    }

    public void cancelButtonWork(View view) {
        if (backGroundTask == null) {
            return;
        }
        backGroundTask.cancel(true);
    }

    public void statusButtonWork(View view) {
        showAsyncTaskStatus();
    }

    private void showAsyncTaskStatus() {
        if (backGroundTask != null) {
            if(backGroundTask.isCancelled()){
                Toast.makeText(this, "CANCELED", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, backGroundTask.getStatus().toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class MyBackGroundTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            infoTv.setText("Begin");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... urls) {
            int count = 0;
            for (String url : urls) {
                imitateDownloadFile(url);
                publishProgress(++count);
                if (isCancelled()) {
                    return null;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            infoTv.setText("Downloaded " + values[0] + " files");
            //progressBar.setMax(values.length);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            infoTv.setText("End");
            progressBar.setVisibility(View.GONE);
        }

        private void imitateDownloadFile(String url) {
            SystemClock.sleep(2000);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            infoTv.setText("Cancel");
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(0);
        }
    }
}
