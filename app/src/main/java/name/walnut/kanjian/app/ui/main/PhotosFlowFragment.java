package name.walnut.kanjian.app.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.ui.Constants;

/**
 * 首页看照片 Fragment
 */
public class PhotosFlowFragment extends ActionBarFragment {

    @InjectView(R.id.list)
    SuperRecyclerView recyclerView;

    @Override
    protected String getTitle() {
        return getString(R.string.title_activity_photos_flow);
    }

    @Override
    public boolean showBack() {
        return false;
    }

    @Override
    protected View getActionBarMenuView() {
        ImageButton view = (ImageButton) LayoutInflater.from(getActionBarActivity()).inflate(R.layout.action_bar_menu_button, null, false);
        view.setImageResource(R.drawable.icon_add_person);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Constants.Action.ADD_FRIENDS_ACTION);
                startActivity(intent);
            }
        });
        return view;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos_flow, container, false);

        ButterKnife.inject(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActionBarActivity()));
        recyclerView.setAdapter(new PhotosFlowAdapter(getActionBarActivity(), new ArrayList<PhotosFlow>()));

        return view;
    }

    @OnClick(R.id.action_camera)
    void startPublicPhoto() {
        // 开始发表图片
        Intent intent = new Intent(Constants.Action.UPLOAD_PIC_ACTION);
        startActivity(intent);
    }
}
