package com.haizj.alistore;

import android.os.Bundle;

import com.haizj.alistore.modules.framgnt.MainFragment;
import com.haizj.alistore.utils.BoxViewBuilder;
import com.haizj.alistore.utils.UIFragmentHelper;
import com.hyena.framework.app.activity.NavigateActivity;
import com.hyena.framework.app.fragment.BaseFragment;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.fragment.UIViewFactory;

public class MainActivity extends NavigateActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UIViewFactory.getViewFactory().registViewBuilder(new BoxViewBuilder());

        MainFragment fragment = MainFragment.newFragment(this, MainFragment.class, null, BaseUIFragment.AnimType.ANIM_NONE);
        showFragment(fragment);
    }

    @Override
    public UIFragmentHelper getUIFragmentHelper(BaseFragment fragment) {
        if (fragment instanceof BaseUIFragment) {
            return new UIFragmentHelper((BaseUIFragment<?>) fragment);
        }
        return null;
    }
}
