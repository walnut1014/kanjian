package name.walnut.kanjian.app.support;

import android.app.ActionBar;
import android.app.Fragment;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

public abstract class ActionBarFragment extends BaseFragment{

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = this.getActivity().getActionBar();
		
		TextView textView = (TextView) actionBar.getCustomView().findViewById(android.R.id.title);
		textView.setText(getTitle());
		
		ActionBarActivity activity = this.getActionBarActivity();
		activity.setCurrentFragment(this);
	}

	public void switchFragment(ActionBarFragment actionBarFragment) {
		getActionBarActivity().switchFragment(actionBarFragment);
	}
	
	protected void onBack() {
		getActionBarActivity().getFragmentManager().popBackStack();
	}
	
	public ActionBarActivity getActionBarActivity() {
		return (ActionBarActivity)this.getActivity();
	}
	
	
	public abstract String getTitle();

}
