package io.github.moganbo.pantvoicemaker.activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import io.github.moganbo.pantvoicemaker.R;
import io.github.moganbo.pantvoicemaker.fragment.MainFragment;

@SuppressLint("Registered")
@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    protected void afterViews() {
        replaceFragment(R.id.activity_main_container,
                MainFragment.newInstance(),
                MainFragment.TAG, true);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length == 0){
            return;
        }
    }
}
