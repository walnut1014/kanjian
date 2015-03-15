package name.walnut.kanjian.app.resource.impl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import name.walnut.kanjian.app.resource.ResourceAction;

/**
 * Created by user on 2015/3/14.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResourceWeave {
    public Class<? extends ResourceAction> actionClass();
}
