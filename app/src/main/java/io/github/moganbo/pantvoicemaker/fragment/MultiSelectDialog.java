package io.github.moganbo.pantvoicemaker.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.github.moganbo.pantvoicemaker.utils.OnItemClickListener;

public class MultiSelectDialog extends DialogFragment {

    private static final String ITEMS = "items";
    private static final String SELECTED = "selected";
    private static final String CANCEL_LABEL = "cancelLabel";
    private static final String PARAMS = "params";
    private static final String LISTENER = "listener";
    private static final String CANCELABLE = "cancelable";

    public static class Builder{
        final FragmentActivity activity;

        public <A extends FragmentActivity> Builder(@NonNull final A activity) {
            this.activity = activity;
        }

        private ArrayList<String> items;
        private int selected = 0;
        private String cancelLabel = "cancel";
        private Bundle params;
        private String tag = "dialog";
        private boolean cancelable = true;
        private OnItemClickListener listener;

        public Builder items(@NonNull final ArrayList<String> items) {
            this.items = items;
            return this;
        }

        public Builder selected(final int selected){
            this.selected = selected;
            return this;
        }

        public Builder cancelLabel(@NonNull final String cancelLabel) {
            this.cancelLabel = cancelLabel;
            return this;
        }

        public Builder params(@NonNull final Bundle params) {
            this.params = params;
            return this;
        }

        public Builder tag(@NonNull final String tag) {
            this.tag = tag;
            return this;
        }

        public Builder canselable(final boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder listener(@NonNull final OnItemClickListener listener){
            this.listener = listener;
            return this;
        }



        public void show(){
            final Bundle args = new Bundle();
            args.putStringArrayList(ITEMS, items);
            args.putInt(SELECTED, selected);
            args.putString(CANCEL_LABEL, cancelLabel);
            args.putBoolean(CANCELABLE, cancelable);
            args.putSerializable(LISTENER, listener);
            if (params != null) {
                args.putBundle(PARAMS, params);
            }
            final MultiSelectDialog d = new MultiSelectDialog();
            d.setArguments(args);
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.add(d, tag);
            ft.commitAllowingStateLoss();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() == null || getActivity() == null) {
            return super.onCreateDialog(savedInstanceState);
        }

        Activity activity = this.getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Bundle bundle = this.getArguments();
        List<String> itemList = bundle.getStringArrayList(ITEMS);
        if (itemList == null){
            itemList = new ArrayList<>();
        }
        String[] items = itemList.toArray(new String[0]);
        int selected = bundle.getInt(SELECTED);
        final OnItemClickListener listener = (OnItemClickListener) bundle.getSerializable(LISTENER);
        builder.setSingleChoiceItems(items, selected, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // A, B, Cが押されたときの処理
                if (listener != null){
                    listener.onItemSelected(which);
                }
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(bundle.getString(CANCEL_LABEL), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (listener != null){
                    listener.onItemSelectCanceled();
                }
                dialog.dismiss();
            }
        });
        builder.setCancelable(bundle.getBoolean(CANCELABLE));
        return builder.create();
    }
}
