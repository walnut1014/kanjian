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
import retrofit.Call;
import retrofit.Retrofit;

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

        Retrofit retrofit = new Retrofit
                                .Builder()
                                .baseUrl("http://192.168.1.103:8080").build();
        Call<User> list = retrofit.create(RetrofitService.class).list();


    }
}
