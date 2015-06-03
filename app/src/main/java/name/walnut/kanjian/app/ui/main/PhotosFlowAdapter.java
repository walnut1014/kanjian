package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.AbsListAdapter;

/**
 * 看照片 adapter
 */
public class PhotosFlowAdapter extends AbsListAdapter<PhotosFlow, PhotosFlowAdapter.ViewHolder> {


    public PhotosFlowAdapter(Context context, List<PhotosFlow> list) {
        super(context, list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_photos_flow, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
