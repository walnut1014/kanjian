package name.walnut.kanjian.app.newui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.views.RoundImageView;

/**
 * Created by linxunjian on 15/9/15.
 */

//交换照片者 头像显示横向列表
public class PortraitScrollViewAdapter extends BaseAdapter{

    private Context mContext;
    private Integer[] imagelist;

    PortraitScrollViewAdapter(Context context, Integer[] imagelist)
    {
        this.mContext = context;
        this.imagelist= imagelist;
    }

    public int getCount() {
        return imagelist.length;
    }

    public Object getItem(int i) {
        return imagelist[i];
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            convertView = mInflater.inflate(R.layout.view_photo_viewed, null);
            viewHolder.mIvPhotoViewed = (RoundImageView) convertView.findViewById(R.id.iv_personal_portrait);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mIvPhotoViewed.setImageResource(imagelist[i]);
        return convertView;
    }

    private static class ViewHolder
    {
        RoundImageView mIvPhotoViewed;
    }

}


