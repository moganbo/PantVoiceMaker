package io.github.moganbo.pantvoicemaker.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.moganbo.pantvoicemaker.R;
import io.github.moganbo.pantvoicemaker.application.AppController;
import io.github.moganbo.pantvoicemaker.constants.PantVoice;
import io.github.moganbo.pantvoicemaker.constants.WordCount;
import io.github.moganbo.pantvoicemaker.utils.OnItemClickListener;
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
    @ViewById(R.id.fragment_main_situation_button)
    protected Button situationButton;
    @ViewById(R.id.fragment_main_word_count_button)
    protected Button wordCountButton;
    @ViewById(R.id.fragment_main_name_layout)
    protected LinearLayout nameLayout;
    @ViewById(R.id.fragment_main_brackets_check_box)
    protected CheckBox bracketsCheckBox;
    @ViewById(R.id.fragment_main_name_edit_text)
    protected EditText nameEditText;

    @ViewById(R.id.fragment_main_result_button)
    protected Button resultButton;

    InputMethodManager inputMethodManager;

    private PantVoice pv = PantVoice.UNDEFINED;
    private WordCount wc = WordCount.UNDEFINED;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @AfterViews
    protected void afterViews() {
        if(getActivity() != null) {
            inputMethodManager =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }

        setResultButtonEnable();
    }

    @Click(R.id.fragment_main_main_text_view)
    protected void onClickMainTextView() {
        String text = mainTextView.getText().toString();
        if (StringUtil.isNullOrEmpty(text)) {
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

    @Click(R.id.fragment_main_situation_button)
    protected void OnClickSituationButton() {
        if (getActivity() == null) {
            return;
        }
        new MultiSelectDialog.Builder(getActivity())
                .items(new ArrayList<>(PantVoice.getJpNameList()))
                .selected(pv.getIndex())
                .listener(new OnItemClickListener() {
                    @Override
                    public void onItemSelected(int position) {
                        PantVoice pv = PantVoice.getTypeByIndex(position);
                        setValue(pv);
                    }

                    @Override
                    public void onItemSelectCanceled() {
                    }
                })
                .show();
    }

    @Click(R.id.fragment_main_word_count_button)
    protected void OnClickWordCountButton() {
        if (getActivity() == null) {
            return;
        }
        new MultiSelectDialog.Builder(getActivity())
                .items(new ArrayList<>(WordCount.getLabelList()))
                .selected(wc.getIndex())
                .listener(new OnItemClickListener() {
                    @Override
                    public void onItemSelected(int position) {
                        WordCount wc = WordCount.getTypeByIndex(position);
                        setValue(wc);
                    }

                    @Override
                    public void onItemSelectCanceled() {
                    }
                })
                .show();
    }

    @CheckedChange(R.id.fragment_main_brackets_check_box)
    protected void onCheckChangeBracketsCheckBox(boolean isChecked){
        if (isChecked){
            nameLayout.setVisibility(View.VISIBLE);
        }else{
            nameLayout.setVisibility(View.GONE);
        }
    }

    @Click(R.id.fragment_main_result_button)
    protected void onClickResultButton() {
        List<String> textList = pv.getList();
        if (textList.isEmpty()) {
            return;
        }
        int wordCount = wc.getValue();
        Random r = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wordCount; i++) {
            int index = r.nextInt(textList.size());
            if(i == 0 && textList.get(index).startsWith("♡")){
                i--;
                continue;
            }
            builder.append(textList.get(index));
        }
        String result = builder.toString();
        if(bracketsCheckBox.isChecked()){
            String name = nameEditText.getText().toString();
            result = name + "「" + result + "」";
        }
        this.mainTextView.setText(result);
    }

    private void setValue(PantVoice pv) {
        this.pv = pv;
        situationButton.setText(pv.getJpName());

        setResultButtonEnable();
    }

    private void setValue(WordCount wc) {
        this.wc = wc;
        wordCountButton.setText(wc.getLabel());

        setResultButtonEnable();
    }

    private void setResultButtonEnable(){
        boolean enableButton = true;
        if (pv == PantVoice.UNDEFINED){
            enableButton = false;
        }
        if(wc == WordCount.UNDEFINED){
            enableButton = false;
        }
        resultButton.setEnabled(enableButton);
    }
}
