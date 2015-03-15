package name.walnut.kanjian.app.resource;

import android.test.AndroidTestCase;

import org.json.JSONObject;

import name.walnut.kanjian.app.TestResourceAction;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;

public class ResourceTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext();

        loginResource = ResourceFactory.INSTANCE.getResource(ResourceRegister.loginResource);
    }

    public void testLoginResource() throws InterruptedException {
        
        loginResource.setResourceAction(new TestResourceAction(){
            @Override
            public void onResponse(JSONObject object) {
                super.onResponse(object);
            }
        });
        loginResource.addParam("aa", "ff").send();
    }


    private Resource loginResource;

}
