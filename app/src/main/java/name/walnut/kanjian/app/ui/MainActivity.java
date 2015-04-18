package name.walnut.kanjian.app.ui;

import android.os.Bundle;
import android.support.v13.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

    private FragmentTabHost tabHost;
    private TabResource tabResource[] = TabResource.values();
    private LayoutInflater layoutInflater;

    public MainActivity() {
        super(0);
    }

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);

        initView();
	}

    private void initView() {
        layoutInflater = LayoutInflater.from(this);
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getFragmentManager(), R.id.content);
        tabHost.getTabWidget().setDividerDrawable(null);

        int tabCount = tabResource.length;

        for (int i = 0; i < tabCount; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec("").setIndicator(getTabItemView(i));
            tabHost.addTab(tabSpec, tabResource[i].getFragment(), null);
        }

    }


    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.tab_item_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
        imageView.setImageResource(tabResource[index].getResId());

        return view;
    }

}
