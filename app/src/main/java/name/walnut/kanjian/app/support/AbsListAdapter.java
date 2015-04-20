package name.walnut.kanjian.app.support;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的listAdapter for recyclerview
 */
public abstract class AbsListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    protected List<T> list;
    protected Context context;
    protected LayoutInflater inflater;

    public AbsListAdapter(Context context, List<T> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        setDataSet(list);
    }

    public void setDataSet(List<T> list) {
        this.list = list;
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
