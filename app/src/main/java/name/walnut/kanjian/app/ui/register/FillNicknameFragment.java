package name.walnut.kanjian.app.ui.register;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 填写昵称Fragment
 * @author walnut
 *
 */
public class FillNicknameFragment extends ActionBarFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fill_nickname, container, false);


        return view;
    }

    @Override
    public String getTitle() {
        return "填写昵称";
    }


}
