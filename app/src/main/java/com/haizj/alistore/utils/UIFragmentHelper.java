package com.haizj.alistore.utils;

import com.haizj.alistore.widgets.BoxEmptyView;
import com.haizj.alistore.widgets.BoxLoadingView;
import com.haizj.alistore.widgets.BoxTitleBar;
import com.hyena.framework.app.fragment.BaseUIFragment;
import com.hyena.framework.app.fragment.BaseUIFragmentHelper;

/**
 * Created by weilei on 17/3/6.
 */
public class UIFragmentHelper extends BaseUIFragmentHelper {
    public UIFragmentHelper(BaseUIFragment<?> fragment) {
        super(fragment);
    }

    public BoxTitleBar getTitleBar() {
        return (BoxTitleBar) getBaseUIFragment().getTitleBar();
    }

    public BoxEmptyView getEmptyView() {
        return (BoxEmptyView) getBaseUIFragment().getEmptyView();
    }

    public BoxLoadingView getLoadingView() {
        return (BoxLoadingView) getBaseUIFragment().getLoadingView();
    }
}
