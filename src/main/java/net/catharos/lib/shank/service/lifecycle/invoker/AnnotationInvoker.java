package net.catharos.lib.shank.service.lifecycle.invoker;

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
    public void invoke(Object obj) {
        super.invoke(obj);
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                try {
                    method.invoke(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
