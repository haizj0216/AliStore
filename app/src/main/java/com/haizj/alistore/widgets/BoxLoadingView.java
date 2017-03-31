/**
 * Copyright (C) 2015 The AndroidPhoneStudent Project
 */
package com.haizj.alistore.widgets;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.haizj.alistore.R;
import com.hyena.framework.app.widget.LoadingView;
import com.hyena.framework.utils.UiThreadHandler;

public class BoxLoadingView extends LoadingView {

    private ProgressBar mLoadingImg;
    private TextView mLoadingHintTxt;

    public BoxLoadingView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.layout_common_loading, this);
        mLoadingImg = (ProgressBar) findViewById(R.id.loading_anim);
        mLoadingHintTxt = (TextView) findViewById(R.id.loading_hint);
    }

    @Override
    public void showLoading(final String hint) {
        UiThreadHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mLoadingHintTxt != null && !TextUtils.isEmpty(hint))
                    mLoadingHintTxt.setText(hint);

                setVisibility(View.VISIBLE);
                getBaseUIFragment().getEmptyView().setVisibility(View.GONE);
            }
        });
    }

}
