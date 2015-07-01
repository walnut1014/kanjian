package name.walnut.kanjian.app.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.push.PushBusProvider;
import name.walnut.kanjian.app.push.message.BadgeMessage;
import name.walnut.kanjian.app.support.ActionBarActivity;
import name.walnut.kanjian.app.views.BadgeView;

public class MainActivity extends ActionBarActivity {

    public static final int ACTIVITY_REQUEST_SELECT_PHOTO = 0xf;

    private TabResource tabResource[] = TabResource.values();
    private LayoutInflater layoutInflater;
    private BadgeView[] badgeViews = new BadgeView[2];  // badgeViews[0]为首页消息数量，badgeViews[1]为个人界面消息

    @InjectView(android.R.id.tabhost)
    FragmentTabHost tabHost;

    @InjectView(R.id.tab_camera)
    ImageButton cameraBtn;

    @InjectView(R.id.divide)
    View divide;

    public MainActivity() {
        super(0);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        PushBusProvider.getInstance().register(this);
	}

    private void initView() {
        layoutInflater = LayoutInflater.from(this);

        initTab();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PushBusProvider.getInstance().unregister(this);
        for (int i = 0; i < badgeViews.length; i++) {
            badgeViews[i] = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 小红点示例
        showMessageCount(12);
    }

    @OnClick(R.id.tab_camera)
    void selectPic() {
        Intent intent = new Intent(Constants.Action.UPLOAD_PIC_ACTION);
        startActivityForResult(intent, ACTIVITY_REQUEST_SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_SELECT_PHOTO && resultCode == RESULT_OK) {
            // 上传图片成功, 切换到第一页
            tabHost.setCurrentTabByTag(getString(TabResource.MESSAGE.getTitleId()));
        }
    }

    /**
     * 小红点消息推送
     * @param message
     */
    public void onEventMainThread(BadgeMessage message) {

    }

    /**
     * 隐藏底部tab栏， 键盘弹出时调用
     */
    public void hideTab() {
        tabHost.setVisibility(View.GONE);
        cameraBtn.setVisibility(View.GONE);
        divide.setVisibility(View.GONE);
    }

    /**
     * 显示底部tab栏，键盘隐藏时调用
     */
    public void showTab() {
        tabHost.setVisibility(View.VISIBLE);
        cameraBtn.setVisibility(View.VISIBLE);
        divide.setVisibility(View.VISIBLE);
    }

    private void initTab() {
        tabHost.setup(this, getFragmentManager(), R.id.content);
        tabHost.getTabWidget().setDividerDrawable(null);

        int tabCount = tabResource.length;

        for (int i = 0; i < tabCount; i++) {
            View indicator = getTabItemView(i);
            if (tabResource[i].equals(TabResource.CAMERA)) {
                indicator.setVisibility(View.INVISIBLE);
            }
            String tag = getString(tabResource[i].getTitleId());
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tag).setIndicator(indicator);
            tabHost.addTab(tabSpec, tabResource[i].getFragment(), null);
        }
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        TabResource tab = tabResource[index];

        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        TextView tabView = (TextView) view.findViewById(R.id.tab_item);
        LinearLayout parent = (LinearLayout) tabView.getParent();

        if (index == 0) {
            parent.setGravity(Gravity.START);
        } else if (index == tabResource.length - 1) {
            parent.setGravity(Gravity.END);
        }

        Drawable drawable = this.getResources().getDrawable(tab.getResId());
        tabView.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        tabView.setText(tab.getTitleId());

        int badgePosition = -1;
        if (tab == TabResource.MESSAGE) {
            badgePosition = 0;
        } else if (tab == TabResource.MY) {
            badgePosition = 1;
        }

        if (badgePosition != -1) {
            BadgeView badgeView = new BadgeView(this, tabView);
            badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badgeView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            badgeView.setBadgeMargin(35, 0);
            badgeView.setBackgroundResource(R.drawable.bg_notification);
            badgeView.setGravity(Gravity.CENTER);

            badgeViews[badgePosition] = badgeView;
        }

        return view;
    }

    /**
     * 显示首页未读数量
     * @param count
     */
    public void showMessageCount(int count) {
        showBadgeCount(0, count);
    }

    /**
     * 显示个人界面未读数量
     * @param count
     */
    public void showMyCount(int count) {
        showBadgeCount(1, count);
    }

    /**
     * 显示小红点
     * @param position
     * @param count
     */
    private void showBadgeCount(int position, int count) {
        if (position < 0 || position >= badgeViews.length) {
            return;
        }
        BadgeView badgeView = badgeViews[position];
        badgeView.setText("" + count);
        badgeView.show();
    }

    /**
     * 隐藏小红点
     * @param position
     */
    private void dismissBadge(int position) {
        if (position < 0 || position >= badgeViews.length) {
            return;
        }
        BadgeView badgeView = badgeViews[position];
        badgeView.setText("");
        badgeView.hide();
    }

}
