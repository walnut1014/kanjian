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

    /**
     * 添加一个item
     * @param item
     */
    public void add(T item) {
        insert(item, list.size());
    }

    /**
     * 插入一个item
     * @param item
     * @param position
     */
    public void insert(T item, int position) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 删除position
     * @param position
     */
    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 清空
     */
    public void clear() {
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
