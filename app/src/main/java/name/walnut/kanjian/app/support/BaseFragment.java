package name.walnut.kanjian.app.support;


import android.app.Fragment;
import android.util.Log;

import java.lang.reflect.Field;

import name.walnut.kanjian.app.resource.impl.Resource;
import name.walnut.kanjian.app.resource.impl.ResourceActionFactory;
import name.walnut.kanjian.app.resource.impl.ResourceFactory;
import name.walnut.kanjian.app.resource.ResourceRegister;
import name.walnut.kanjian.app.resource.impl.ResourceWeave;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    {
        Field[] fields = this.getClass().getDeclaredFields();
        for(Field field : fields){
            Class c = field.getType();
            if(c.equals(Resource.class) ||
                    (c.getSuperclass() != null && c.getSuperclass().equals(Resource.class))) {
                Resource resource = ResourceFactory.INSTANCE.getResource(ResourceRegister.valueOf(field.getName()));
                ResourceWeave resourceWeave = field.getAnnotation(ResourceWeave.class);
                if(resourceWeave == null)
                    throw new RuntimeException("没有找到对应的ResourceWeave");
                resource.setResourceAction(ResourceActionFactory.INSTANCE
                                                                 .getResourceAction(resourceWeave.actionClass()));
                try {
                    field.set(this, resource);
                } catch (IllegalAccessException e) {
                    Log.e("System", "系统错误", e);
                }
            }
        }
    }

}
