package name.walnut.kanjian.app.ui.main;

import java.net.URI;
import java.util.List;
import java.util.Random;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.entity.PhotoContext;
import name.walnut.kanjian.app.support.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

public class UploadActivity extends ActionBarActivity
        implements ViewFactory,OnClickListener {

    public UploadActivity() {
        super(R.id.upload_container);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_upload);

        TextView textView = (TextView) getActionBar().getCustomView()
                .findViewById(android.R.id.title);
        textView.setText("上传照片");

        switch1 = (ImageSwitcher) findViewById(R.id.upload_imswitch1);
        switch2 = (ImageSwitcher) findViewById(R.id.upload_imswitch2);
        switch1.setFactory(this);
        switch2.setFactory(this);

        switch1.setImageResource(R.drawable.default_img);
        switch2.setImageResource(R.drawable.default_img);

        btnSelect = (Button) findViewById(R.id.upload_btnSelect);
        btnSelect.setOnClickListener(this);
    }

    @Override
    public View makeView() {
        return new ImageView(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.upload_btnSelect:
                btnClick();
        }

    }


    private void btnClick() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize =2;
        Random random = new Random();
        List<String> list = PhotoContext.INSTANCE.getPhotoPaths();
        Bitmap bm = BitmapFactory.decodeFile(list.get(random.nextInt(list.size())), options);
        ((ImageView)switch1.getCurrentView()).setImageBitmap(bm);
        bm = BitmapFactory.decodeFile(list.get(random.nextInt(list.size())), options);
        ((ImageView)switch2.getCurrentView()).setImageBitmap(bm);
    }


    private ImageSwitcher switch1;
    private ImageSwitcher switch2;

    private CheckBox checkBox1;
    private CheckBox checkBox2;

    private Button btnSelect;
}
