package com.example.arturmusayelyan.threadasynctask1;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView showJsonTv;
    private Button getJsonBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showJsonTv = findViewById(R.id.show_json_tv);
        progressBar = findViewById(R.id.progressBar1);
    }

    public void onClick(View view) {
        new BackGroundTask().execute();
    }

    class BackGroundTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
            showJsonTv.setText("Loading...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url("https://freemegalist.com/api.php/?action=categories").build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    SystemClock.sleep(2000); // yes em avelacrel
                    return response.body().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download failed";
        }

        @Override
        protected void onPostExecute(String result) {
            progressBar.setVisibility(View.GONE);
            showJsonTv.setText(result);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case M
//        }
//    }
}
