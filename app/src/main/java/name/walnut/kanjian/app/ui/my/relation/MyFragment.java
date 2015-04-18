package name.walnut.kanjian.app.ui.my.relation;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyFragment extends ActionBarFragment {

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,false);
		return rootView;
	}


    @Override
    public String getTitle() {
        return getString(R.string.title_fragment_my);
    }

    @Override
    public boolean showBack() {
        return false;
    }
}
