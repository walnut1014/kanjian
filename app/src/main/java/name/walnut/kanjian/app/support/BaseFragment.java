package name.walnut.kanjian.app.support;


import android.app.Fragment;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import name.walnut.kanjian.app.resource.ResourceAction;
import name.walnut.kanjian.app.resource.impl.DefaultResourceAction;
import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceActionFactory;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;
import name.walnut.kanjian.app.resource.ResourceRegister;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;

/**
 * 项目基础BaseFragment，
 */
public abstract class BaseFragment extends Fragment {

    private Set<Resource> resources = new HashSet<>();
    private Set<DefaultResourceAction> resourceActions = new HashSet<>();

    {
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            Class c = field.getType();
            if(c.equals(Resource.class) ||
                    (c.getSuperclass() != null && c.getSuperclass().equals(Resource.class))) {
                Resource resource = ResourceFactory.INSTANCE.getResource(ResourceRegister.valueOf(field.getName()));
                resources.add(resource);
                ResourceWeave resourceWeave = field.getAnnotation(ResourceWeave.class);
                if(resourceWeave == null)
                    throw new RuntimeException("没有找到对应的ResourceWeave");
                DefaultResourceAction resourceAction =
                        (DefaultResourceAction) ResourceActionFactory
                                                 .INSTANCE.getResourceAction(resourceWeave.actionClass());
                resourceActions.add(resourceAction);
                resourceAction.setFragment(this);
                resource.setResourceAction(resourceAction);
                try {
                    field.set(this, resource);
                } catch (IllegalAccessException e) {
                    Log.e("System", "系统错误", e);
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //清理所有resource的参数
        for(Resource resource : resources)
            resource.clear();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(DefaultResourceAction resourceAction : resourceActions) {
            resourceAction.setFragment(null);
        }
    }
}
