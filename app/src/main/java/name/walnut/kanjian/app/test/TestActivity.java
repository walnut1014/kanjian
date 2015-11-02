package name.walnut.kanjian.app.test;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import name.walnut.kanjian.app.R;
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
