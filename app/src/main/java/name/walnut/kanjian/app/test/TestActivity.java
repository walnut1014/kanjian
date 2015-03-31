package name.walnut.kanjian.app.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONObject;

import name.walnut.kanjian.app.R;
import name.walnut.kanjian.app.resource.ResourceAction;
import name.walnut.kanjian.app.resource.ResourceRegister;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.setContentView(R.layout.activity_test);

        final TextView textView = (TextView)this.findViewById(R.id.test_result);

/*
        Resource  resource = ResourceFactory.INSTANCE.getResource(ResourceRegister);

        resource.setResourceAction(new TestResourceAction() {
            @Override
            public void onResponse(JSONObject object) {
                textView.setText(object.toString());
            }
        });
        resource.addParam("mobilephone", "13000000001").send();

        resource = ResourceFactory.INSTANCE.getResource(ResourceRegister.registerResource);

        resource.setResourceAction(new TestResourceAction() {
            @Override
            public void onResponse(JSONObject object) {
                textView.setText(object.toString());
            }
        });
        resource.addParam("nickName", "123456")
                .addParam("password", "sdfsdf").send();*/
    }
}
