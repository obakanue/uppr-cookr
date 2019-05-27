package com.example.cookr;

public enum ModelObject {

    GREEN(R.string.green, R.layout.view_step1),
    RED(R.string.red, R.layout.view_step2),
    BLUE(R.string.blue, R.layout.view_step3),
    ORANGE(R.string.orange, R.layout.view_step4),
    PINK(R.string.orange, R.layout.timer_step);

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

