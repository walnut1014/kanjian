package name.walnut.kanjian.app.ui.main;

import android.content.Intent;
import android.os.Bundle;

import name.walnut.kanjian.app.support.ActionBarFragment;

/**
 * 个人主页
 */
public class PersonalFragment extends ActionBarFragment {

    private String userName = "";
    private long userId;

    public static PersonalFragment newInstance() {
        PersonalFragment fragment = new PersonalFragment();
        return fragment;
    }

    @Override
    protected String getTitle() {
        return userName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        userId = intent.getLongExtra("userId", 0);
        userName = intent.getStringExtra("userName");

    }
}
