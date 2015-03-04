package name.walnut.kanjian.app.ui.register;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.support.ActionBarFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * 手机验证码Fragment
 * @author walnut
 *
 */
@SuppressLint("ValidFragment")
public class VerifiCodeFragment extends ActionBarFragment {


    public VerifiCodeFragment(CharSequence mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_verificode, container, false);

        Button button = (Button) view.findViewById(R.id.btnCommit);
        mobilephoneText = (TextView) view.findViewById(R.id.verificode_txtMobilephone);

        mobilephoneText.setText(mobilephone);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                switchFragment(new FillNicknameFragment());
            }
        });
        return view;
    }

    @Override
    public String getTitle() {
        return "输入验证码";
    }

    private CharSequence mobilephone;

    private TextView mobilephoneText;


}
