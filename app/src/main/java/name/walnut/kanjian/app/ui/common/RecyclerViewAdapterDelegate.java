package name.walnut.kanjian.app.ui.common;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * adapter代理，显示列表部分
 */
public interface RecyclerViewAdapterDelegate<VH extends RecyclerView.ViewHolder, T> {

    public void onViewRecycled(VH holder);
    public void onBindItemViewHolder(VH holder, int position);
    public VH onCreateItemViewHolder(ViewGroup parent, int viewType);
    public void setDataSet(List<T> dataSet);
    public List<T> getDataSet();
}