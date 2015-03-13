package name.walnut.kanjian.app;

import android.test.AndroidTestCase;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import name.walnut.kanjian.app.support.JsonRequestBuilder;
import name.walnut.kanjian.app.support.RequestQueueContext;
import name.walnut.kanjian.app.utils.DefalutJSONListener;
import name.walnut.kanjian.app.utils.RequestUtils;

public class RequestTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext();

    }

    public void testApiLogin() throws InterruptedException {
    }

}
