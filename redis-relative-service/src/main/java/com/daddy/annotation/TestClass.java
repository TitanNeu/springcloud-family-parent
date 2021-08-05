package com.daddy.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName TestClass
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/15 14:13
 * @Version 1.0
 **/
@SimpleAnnotation(className = "com.daddy.annotation.TestAnnotation", methodName = "testAnnotation")
public class TestClass {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        Class<TestClass> testClassClass = TestClass.class;
        boolean annotationPresent = testClassClass.isAnnotationPresent(SimpleAnnotation.class);
        if (annotationPresent) {
            SimpleAnnotation annotation = testClassClass.getAnnotation(SimpleAnnotation.class);


            Class<?> classObj = Class.forName(annotation.className());
            Object o = classObj.newInstance();
            Method method = classObj.getMethod(annotation.methodName());

            method.invoke(o);

        }
    }
}
