package com.example.cookr;

public enum ModelObject {

    RED(R.string.red, R.layout.view_step2),
    BLUE(R.string.blue, R.layout.view_step3),
    GREEN(R.string.green, R.layout.view_step1);

    private int mTitleResId;
    private int mLayoutResId;

    ModelObject(int titleResId, int layoutResId) {
        mTitleResId = titleResId;
        mLayoutResId = layoutResId;
    }

    public int getTitleResId() {
        return mTitleResId;
    }

    public int getLayoutResId() {
        return mLayoutResId;
    }

}

