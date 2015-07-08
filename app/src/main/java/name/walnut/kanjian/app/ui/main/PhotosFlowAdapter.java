package name.walnut.kanjian.app.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.HeaderRecyclerViewAdapter;
import name.walnut.kanjian.app.ui.Constants;
import name.walnut.kanjian.app.views.TopTipView;

/**
 * 首页照片流
 */
public class PhotosFlowAdapter extends
        HeaderRecyclerViewAdapter<RecyclerView.ViewHolder, Header, PhotosFlow, Footer>{

    private Context context;
    private PhotosFlowFragment flowFragment;
    private PhotosFlowAdapterDelegate adapterDelegate;
    private LayoutInflater inflater;

    public PhotosFlowAdapter(PhotosFlowFragment fragment, List<PhotosFlow> photosFlowList) {
        super();
        flowFragment = fragment;
        this.context = fragment.getActionBarActivity();
        adapterDelegate = new PhotosFlowAdapterDelegate(fragment, photosFlowList);
        inflater = LayoutInflater.from(fragment.getActionBarActivity());
    }

    @Override
    protected RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_main_header, parent, false);
        return new HeaderViewHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return adapterDelegate.onCreateItemViewHolder(parent, viewType);
    }

    @Override
    protected RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.layout_main_footer, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    protected void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        headerViewHolder.topTipView.showRemindTip(getHeader().isShowRemindTip());
        headerViewHolder.topTipView.showNewsTip(getHeader().isShowNewsTip());
        headerViewHolder.topTipView.setNewsTip(
                context.getString(
                        R.string.tip_news, getHeader().getNewsCount()));
        headerViewHolder.topTipView.setRemindTip(context.getString(R.string.tip_remind));
        headerViewHolder.topTipView.setNewsTipClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到新消息界面
                Intent intent = new Intent(Constants.Action.MESSAGE_ACTION);
                context.startActivity(intent);
            }
        });
    }

    @Override
    protected void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        int currPosition = position;
        if (hasHeader()) {
            currPosition --;
        }
        adapterDelegate.onBindItemViewHolder((PhotosFlowViewHolder)holder, currPosition);
    }

    @Override
    protected void onBindFooterViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof PhotosFlowViewHolder) {
            adapterDelegate.onViewRecycled((PhotosFlowViewHolder) holder);
        }
    }

    @Override
    public void setItems(List<PhotosFlow> items) {
        super.setItems(items);
        adapterDelegate.setDataSet(items);
    }

    public List<PhotosFlow> getItems() {
        return adapterDelegate.getDataSet();
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.top_tip_view)
        TopTipView topTipView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

}
