package name.walnut.kanjian.app.support;

import android.view.View;

import name.walnut.kanjian.app.R;

public abstract class ActionBarFragment extends BaseFragment{

    protected ActionBarBuilder builder;

    @Override
	public void onResume() {
		super.onResume();

        builder = new ActionBarBuilder(getActionBarActivity())
                .setTitle(getTitle())
                .showBack(showBack())
                .setBackStyle(getActionBarBackStyle())
                .setBackText(getActionBarBackText())
                .setBackClickListener(getActionBarBackClickListener())
                .setMenuView(getActionBarMenuView())
                .build();


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

    /**
     * 顶部标题
     * @return
     */
	protected abstract String getTitle();

    /**
     * 返回按钮样式
     * @return
     */
    protected ActionBarBuilder.BackStyle getActionBarBackStyle() {
        return ActionBarBuilder.BackStyle.ARROW;
    }

    protected String getActionBarBackText() {
        return getString(R.string.action_submit);
    }

    /**
     * 右上menu
     * @return
     */
    protected View getActionBarMenuView() {
        return null;
    }

    /**
     * 返回按钮监听器
     * @return
     */
    protected View.OnClickListener getActionBarBackClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActionBarActivity().onBackPressed();
            }
        };
    }

}
