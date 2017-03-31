/**
 * Copyright (C) 2015 The AndroidPhoneStudent Project
 */
package com.haizj.alistore.widgets;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.hyena.framework.app.widget.TitleBar;
import com.hyena.framework.utils.UiThreadHandler;

public class BoxTitleBar extends TitleBar {

    private TextView mBackBtn;
    private TextView mTitleTxt;
    private TextView mTitleBarMoreTxt;
    private RelativeLayout mTitleBarlayout;

    public BoxTitleBar(Context context) {
        super(context);
        initView();
    }


    private void initView() {
        View.inflate(getContext(), R.layout.layout_common_title_bar, this);
        mTitleBarlayout = (RelativeLayout) findViewById(R.id.common_title_bar_layout);
        mBackBtn = (TextView) findViewById(R.id.title_bar_back);
        mBackBtn.setOnClickListener(mOnClickListener);
        mTitleTxt = (TextView) findViewById(R.id.title_bar_title);
        mTitleBarMoreTxt = (TextView) findViewById(R.id.title_bar_rightView);
    }

    /**
     * 右侧点击动作
     *
     * @param txt
     * @param clickListener
     */
    public void setRightMoreTxt(final String txt,
                                final OnClickListener clickListener) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
                mTitleBarMoreTxt.setVisibility(View.VISIBLE);
                mTitleBarMoreTxt.setText(txt);
                mTitleBarMoreTxt.setOnClickListener(clickListener);
            }
        });
    }


    /**
     * 设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(final String title) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                setVisibility(View.VISIBLE);
                if (mTitleTxt != null) {
                    mTitleTxt.setText(title);
                }
            }
        });
    }

    public void setTitle(final int id, final String title) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {

                setVisibility(View.VISIBLE);
                if (mTitleTxt != null && mTitleBarIcon != null) {
                    if (id > 0) {
                        mTitleBarIcon.setImageResource(id);
                        mTitleBarIcon.setVisibility(View.VISIBLE);
                    } else {
                        mTitleBarIcon.setVisibility(View.GONE);
                    }
                    mTitleTxt.setText(title);
                }
            }
        });
    }

    /**
     * 添加自定义内容
     *
     * @param view
     */
    public void addCustomTitle(View view) {
        LayoutParams titlePanelParams = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        titlePanelParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(view, titlePanelParams);
    }

    private OnClickListener mOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.title_bar_back: {
                    if (mListener != null)
                        mListener.onBackPressed(v);
                    break;
                }
            }
        }
    };
    private ImageView mTitleBarIcon;
}
