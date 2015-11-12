package name.walnut.kanjian.app.newui.album;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.utils.ActivityUtils;

/**
 * Created by linxunjian on 15/11/10.
 */
public class AlbumActivity extends Activity {

    private ImageButton mBtnBack;
    private Button mBtnDelete;
    private boolean mTouch = false;

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        mBtnBack = (ImageButton) findViewById(R.id.btn_back);
        mBtnDelete = (Button) findViewById(R.id.btn_delete);
    }

    /**
     * 单击返回按钮
     * @param view
     */

    public void onClickBack(View view)
    {
        onBackPressed();
    }

    /**
     * 单击删除按钮
     * @param view
     */
    public void onClickDelete(View view)
    {
        ActivityUtils.showError(AlbumActivity.this, "已删除");
    }

}

