package name.walnut.kanjian.app.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
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
import name.walnut.kanjian.app.support.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    private FragmentTabHost tabHost;
    private TabResource tabResource[] = TabResource.values();
    private LayoutInflater layoutInflater;

    @InjectView(R.id.tab_camera)
    ImageButton cameraBtn;

    public MainActivity() {
        super(0);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
	}

    private void initView() {
        layoutInflater = LayoutInflater.from(this);

        initTab();

    }

    @OnClick(R.id.tab_camera)
    void selectPic() {
        Intent intent = new Intent(Constants.Action.UPLOAD_PIC_ACTION);
        startActivity(intent);
    }

    private void initTab() {
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getFragmentManager(), R.id.content);
        tabHost.getTabWidget().setDividerDrawable(null);

        int tabCount = tabResource.length;

        for (int i = 0; i < tabCount; i++) {
            View indicator = getTabItemView(i);
            if (tabResource[i].equals(TabResource.CAMERA)) {
                indicator.setVisibility(View.INVISIBLE);
            }
            TabHost.TabSpec tabSpec = tabHost.newTabSpec("").setIndicator(indicator);
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

        return view;
    }

}
