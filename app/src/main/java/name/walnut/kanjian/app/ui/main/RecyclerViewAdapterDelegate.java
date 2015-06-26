package name.walnut.kanjian.app.ui.main;

import android.view.ViewGroup;

import java.util.List;

/**
 * 首页照片流 adapter代理，显示列表部分
 */
public interface RecyclerViewAdapterDelegate<T> {

    public void onViewRecycled(PhotosFlowViewHolder holder);
    public void onBindItemViewHolder(PhotosFlowViewHolder holder, int position);
    public PhotosFlowViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType);
    public void setDataSet(List<T> dataSet);
}