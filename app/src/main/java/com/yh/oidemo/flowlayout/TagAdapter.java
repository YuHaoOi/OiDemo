package com.yh.oidemo.flowlayout;

import android.util.Log;
import android.view.View;

import java.util.List;

public abstract class TagAdapter<T> {
    private List<T> tagData;
    private OnDataChangedListener mOnDataChangedListener;

    public TagAdapter(List<T> data) {
        tagData = data;
    }

    interface OnDataChangedListener {
        void onChanged();
    }

    void setOnDataChangedListener(OnDataChangedListener listener) {
        mOnDataChangedListener = listener;
    }

    public int getCount() {
        return tagData == null ? 0 : tagData.size();
    }

    public void notifyDataChanged() {
        if (mOnDataChangedListener != null) {
            mOnDataChangedListener.onChanged();
        }
    }

    public T getItem(int position) {
        return tagData.get(position);
    }

    public abstract View getView(FlowLayout parent, int position, T t);


    public void onSelected(int position, View view) {
        Log.d("zhy", "onSelected " + position);
    }

    public void unSelected(int position, View view) {
        Log.d("zhy", "unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }


}