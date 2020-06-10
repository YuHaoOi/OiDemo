package com.yh.basemodule.base;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yh.basemodule.R;


public class Title {

    public View container;
    public ImageView leftImage, rightImage, centerImage;
    public TextView leftText, rightText, centerText;
    private View left, right, center;
    private OnTitleEvent titleEvent;

    public Title(View view) {
        this(view, null);
    }

    public Title(View view, OnTitleEvent event) {
        this.container = view;
        this.leftText = getView(R.id.title_left_text);
        this.leftImage = getView(R.id.title_left_img);
        this.centerText = getView(R.id.title_center_text);
        this.centerImage = getView(R.id.title_center_img);
        this.rightText = getView(R.id.title_right_text);
        this.rightImage = getView(R.id.title_right_img);
        this.left = getView(R.id.title_left);
        this.center = getView(R.id.title_center);
        this.right = getView(R.id.title_right);
        this.left.setOnClickListener(listener);
        this.center.setOnClickListener(listener);
        this.right.setOnClickListener(listener);
        this.titleEvent = event;
    }

    private <T> T getView(int id) {
        return (T) container.findViewById(id);
    }

    public void setTitleText(CharSequence sequence) {
        this.centerText.setText(sequence);
    }


    public void setTitleImage(int img) {
        this.centerImage.setImageResource(img);
    }

    public void setLeftText(CharSequence sequence) {
        this.leftText.setVisibility(View.VISIBLE);
        this.leftImage.setVisibility(View.GONE);
        this.leftText.setText(sequence);
    }

    public void setLeftImage(int img) {
        this.leftImage.setImageResource(img);
    }

    public void setRightText(CharSequence sequence) {
        this.rightText.setText(sequence);
    }

    public void setRightImage(int img) {
        this.rightImage.setVisibility(View.VISIBLE);
        this.rightImage.setImageResource(img);
    }

    public void setTitle(int left, CharSequence center, CharSequence right) {
        this.leftImage.setImageResource(left);
        this.centerText.setText(center);
        this.rightText.setText(right);
    }

    public void setTitle(CharSequence center, CharSequence right) {
        this.leftImage.setImageResource(R.mipmap.aaa_back);
        this.centerText.setText(center);
        this.rightText.setText(right);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (titleEvent != null) {
                int id = v.getId();
                if (id == R.id.title_left) {
                    titleEvent.onLeftClick(v);
                } else if (id == R.id.title_center) {
                    titleEvent.onCenterClick(v);
                } else if (id == R.id.title_right) {
                    titleEvent.onRightClick(v);
                }
            }
        }
    };
}
