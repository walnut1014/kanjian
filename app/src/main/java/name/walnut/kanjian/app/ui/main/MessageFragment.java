package name.walnut.kanjian.app.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarBuilder;
import name.walnut.kanjian.app.support.ActionBarFragment;

/**
 * 消息界面
 */
public class MessageFragment extends ActionBarFragment{

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_message);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
