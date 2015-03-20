package name.walnut.kanjian.app.ui.password;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import name.walnut.kanjian.app.support.BaseFragment;

public class ForgotPasswordFragment extends ActionBarFragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);

        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public String getTitle() {
        return getString(R.string.title_forget_password);
    }
}
