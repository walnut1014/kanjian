package name.walnut.kanjian.app.resource.impl;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/15.
 */
public enum ResourceActionFactory {
    INSTANCE;

    public synchronized ResourceAction getResourceAction(Class<? extends ResourceAction> type){

        if(!resourceActionMap.containsKey(type)) {
            try {
                resourceActionMap.put(type, type.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                Log.e("system","没有找到类型为"+ type + "ResourceAction", e);
            }
        }
        return resourceActionMap.get(type);
    }

    private Map<Class<? extends  ResourceAction>, ResourceAction> resourceActionMap = new HashMap<>();
}
