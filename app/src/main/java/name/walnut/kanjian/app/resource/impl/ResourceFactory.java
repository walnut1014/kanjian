package name.walnut.kanjian.app.resource.impl;

import java.util.HashMap;
import java.util.Map;

import name.walnut.kanjian.app.resource.ResourceRegister;

/**
 * Resource工厂类
 * @author walnut
 */
public enum ResourceFactory {
    INSTANCE;

    public synchronized Resource getResource(ResourceRegister register){

        if(!resourceMap.containsKey(register)) {
            resourceMap.put(register, new Resource(register));
        }
        return resourceMap.get(register);
    }

    private Map<ResourceRegister, Resource> resourceMap = new HashMap<>();

}
