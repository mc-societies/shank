package net.catharos.lib.shank.service.lifecycle.invoker;

import net.catharos.lib.shank.service.lifecycle.LifecycleContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Represents a AnnotationInvoker
 */
public abstract class AnnotationInvoker extends DefaultInvoker {

    private final Class<? extends Annotation> annotation;

    public AnnotationInvoker(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    @Override
    public void invoke(Object obj, LifecycleContext context) {
        super.invoke(obj, context);
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.invoke(obj, context);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
