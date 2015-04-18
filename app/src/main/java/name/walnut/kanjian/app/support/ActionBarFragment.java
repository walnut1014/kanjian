package name.walnut.kanjian.app.support;

import android.app.ActionBar;
import android.view.View;
import android.widget.TextView;

import name.walnut.kanjian.app.R;

public abstract class ActionBarFragment extends BaseFragment{

	@Override
	public void onResume() {
		super.onResume();
		ActionBar actionBar = this.getActivity().getActionBar();

		TextView textView = (TextView) actionBar.getCustomView().findViewById(android.R.id.title);
		textView.setText(getTitle());

        if (showBack()) {
            actionBar.getCustomView().findViewById(
                    R.id.btnActionBack).setVisibility(View.VISIBLE);
        } else {
            actionBar.getCustomView().findViewById(
                    R.id.btnActionBack).setVisibility(View.GONE);
        }
		
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

    /**
     * 显示actionbar 返回按钮
     * @return
     */
    public boolean showBack() {
        return true;
    }

	public abstract String getTitle();

}
