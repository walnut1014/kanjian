package name.walnut.kanjian.app.resource;

import android.test.AndroidTestCase;

import org.json.JSONObject;

import name.walnut.kanjian.app.test.TestResourceAction;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;

public class LoginResourceTest extends AndroidTestCase{

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext();

        loginResource = ResourceFactory.INSTANCE.getResource(ResourceRegister.loginResource);
    }

    public void testRegisterResource() {
        loginResource.setResourceAction(new TestResourceAction(){
            @Override
            public void onResponse(JSONObject object) {

            }
        });
    }

    public void testLoginResource() {

        loginResource.setResourceAction(new TestResourceAction(){
            @Override
            public void onResponse(JSONObject object) {

            }
        });
        loginResource.addParam("aa", "ff").send();
    }



    private Resource loginResource;


}
