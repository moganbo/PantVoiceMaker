package io.github.moganbo.pantvoicemaker.utils;

import java.io.Serializable;

public interface OnItemClickListener extends Serializable {

    public abstract void onItemSelected(int position);
    public abstract void onItemSelectCanceled();
}
