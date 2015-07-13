package name.walnut.kanjian.app.ui.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;
import name.walnut.kanjian.app.support.ActionBarBuilder;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.main.action.NewMessageAction;

/**
 * 消息界面
 */
public class MessageFragment extends ActionBarFragment{

    @ResourceWeave(actionClass = NewMessageAction.class)
    public Resource newMessageResource;

    @InjectView(R.id.list)
    public SuperRecyclerView recyclerView;

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_message);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        ButterKnife.inject(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        newMessageResource.send();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
