package name.walnut.kanjian.app.support;

import android.app.ActionBar;
import android.widget.TextView;

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

    public void showMessage(int resId) {
        showMessage(getString(resId));
    }

    public void showMessage(String message) {
        getActionBarActivity().showMessage(message);
    }

    public void dismissMessage() {
        getActionBarActivity().dismissMessage();
    }

	public abstract String getTitle();

}
