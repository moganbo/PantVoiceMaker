package io.github.moganbo.pantvoicemaker.fragment;

import android.os.Bundle;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import io.github.moganbo.pantvoicemaker.R;

@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseFragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @AfterViews
    protected void afterViews() {
    }
}
