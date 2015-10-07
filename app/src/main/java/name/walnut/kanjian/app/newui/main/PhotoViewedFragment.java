package name.walnut.kanjian.app.newui.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.views.PortraitHorizontalScrollView;

/**
 * Created by linxunjian on 15/9/12.
 */
public class PhotoViewedFragment extends Fragment {

    private final Integer[] imagelist =
            {
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
                    R.drawable.psb,
            };
    private PortraitHorizontalScrollView mHorizontalScrollView;
    private PortraitScrollViewAdapter mAdapter;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_viewer, container, false);
        mHorizontalScrollView = (PortraitHorizontalScrollView) view.findViewById(R.id.id_horizontalScrollView);
        mAdapter = new PortraitScrollViewAdapter(getActivity(), imagelist);
        mHorizontalScrollView.setCurrentImageChangeListener(new PortraitHorizontalScrollView.CurrentImageChangeListener() {
            public void onCurrentImgChanged(int position, View viewIndicator) {
            }
        });
        //添加点击回调
        mHorizontalScrollView.setOnItemClickListener(new PortraitHorizontalScrollView.OnItemClickListener() {
            public void onClick(View view, int position) {
            }
        });
        mHorizontalScrollView.initDatas(mAdapter);
        return view;
    }
}
