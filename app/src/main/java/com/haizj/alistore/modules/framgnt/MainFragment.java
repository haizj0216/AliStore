package com.haizj.alistore.modules.framgnt;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.haizj.alistore.R;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.utils.UiThreadHandler;

/**
 * Created by weilei on 17/3/6.
 */
public class MainFragment extends BaseUIFragment<UIFragmentHelper> {
    private boolean mExitMode = false;

    @Override
    public void onCreateImpl(Bundle savedInstanceState) {
        super.onCreateImpl(savedInstanceState);
        setSlideable(false);
    }

    @Override
    public View onCreateViewImpl(Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.layout_main_fragment, null);
    }

    @Override
    public void onViewCreatedImpl(View view, Bundle savedInstanceState) {
        super.onViewCreatedImpl(view, savedInstanceState);
        view.findViewById(R.id.add).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.find).setOnClickListener(mOnClickListener);
        view.findViewById(R.id.importFile).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.add:
                    AddGoodsFragment addGoodsFragment = AddGoodsFragment.newFragment(getActivity(),
                            AddGoodsFragment.class, null);
                    showFragment(addGoodsFragment);
                    break;
                case R.id.find:
                    FindGoodFragment findGoodFragment = FindGoodFragment.newFragment(getActivity(),
                            FindGoodFragment.class, null);
                    showFragment(findGoodFragment);
                    break;
                case R.id.importFile:
                    break;
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (getActivity() != null) {
                if (mExitMode) {
                    getActivity().finish();
                } else {
                    Toast.makeText(getActivity(), "再按一次退出", Toast.LENGTH_SHORT).show();
                    mExitMode = true;
                    UiThreadHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mExitMode = false;
                        }
                    }, 2000);
                }
            }
            return true;
        }
        return false;
    }
}
