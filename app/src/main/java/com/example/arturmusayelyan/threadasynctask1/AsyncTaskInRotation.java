package com.example.arturmusayelyan.threadasynctask1;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class AsyncTaskInRotation extends AppCompatActivity {


    Fragment mContent;
    Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_in_rotation);

//        if (savedInstanceState != null) {
//            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");
//        } else {
//            mContent = RotateAsyncFragment.newInstance();
//            mContent.setRetainInstance(true);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(R.id.frame_layout_container, mContent, "AsyncFragment");
//            transaction.commit();
//        }

        if (savedInstanceState != null) {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState,"SavedFragmentKey");
        } else {
            fragment = RotateAsyncFragment.newInstance();
            fragment.setRetainInstance(true);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_layout_container, fragment, "fragmentRotate");
            fragmentTransaction.commit();
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState,"SavedFragmentKey",fragment);
    }
    //    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        getSupportFragmentManager().putFragment(outState, "myFragmentName", mContent);
//    }
}
