package name.walnut.kanjian.app.resource;

import android.test.AndroidTestCase;

import org.json.JSONObject;

import name.walnut.kanjian.app.test.TestResourceAction;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;
import name.walnut.kanjian.app.support.rest.RequestQueueContext;

public class RegisterResourceTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RequestQueueContext.INSTANCE.initRequestQueue(getContext());

        registerSendResource = ResourceFactory.INSTANCE.getResource(ResourceRegister.registerSendResource);
    }

    public void testSendResource() {
        registerSendResource.setResourceAction(new TestResourceAction(){
            @Override
            public void onResponse(JSONObject object) {
                System.out.println(object);
            }
        });

        registerSendResource.addParam("mobilephone", "13000000001").send();
    }

    private Resource registerSendResource;


}
