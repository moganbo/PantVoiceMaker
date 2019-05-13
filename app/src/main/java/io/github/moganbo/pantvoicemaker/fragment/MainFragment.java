package io.github.moganbo.pantvoicemaker.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Random;

import io.github.moganbo.pantvoicemaker.R;
import io.github.moganbo.pantvoicemaker.application.AppController;
import io.github.moganbo.pantvoicemaker.constants.DefaultPantVoiceList;
import io.github.moganbo.pantvoicemaker.utils.StringUtil;

@EFragment(R.layout.fragment_main)
public class MainFragment extends BaseFragment {

    public static final String TAG = MainFragment.class.getSimpleName();

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment_();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @ViewById(R.id.fragment_main_main_text_view)
    protected TextView mainTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @AfterViews
    protected void afterViews() {
    }

    @Click(R.id.fragment_main_main_text_view)
    protected void onClickMainTextView(){
        String text = mainTextView.getText().toString();
        if (StringUtil.isNullOrEmpty(text)){
            return;
        }
        // copy to clipboard
        ClipboardManager clipboardManager =
                (ClipboardManager) AppController.getAppContext()
                        .getSystemService(Context.CLIPBOARD_SERVICE);
        if (null == clipboardManager) {
            Toast.makeText(AppController.getAppContext(), "コピーできませんでした", Toast.LENGTH_LONG).show();
            return;
        }
        clipboardManager.setPrimaryClip(ClipData.newPlainText("", text));
        Toast.makeText(AppController.getAppContext(), "コピーしました", Toast.LENGTH_LONG).show();
    }

    @Click(R.id.fragment_main_result_button)
    protected void onClickResultButton(){
        // TODO: 設定ごとに変更
        List<String> textList = DefaultPantVoiceList.MODERATE;
        int wordCount = 10;
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wordCount ; i++) {
            int index = r.nextInt(textList.size());
            builder.append(textList.get(index));
        }
        String result = builder.toString();
        this.mainTextView.setText(result);
    }
}
