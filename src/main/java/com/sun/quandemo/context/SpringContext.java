package com.sun.quandemo.context;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * SpringContext工具类，用于在非 Spring 管理的环境中获取 Spring 容器中的 Bean。
 */
@Component
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContext.applicationContext = applicationContext;
    }

    /**
     * 获取 ApplicationContext
     *
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过名称获取 Bean
     *
     * @param name Bean 名称
     * @return Bean 实例
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    /**
     * 通过类型获取 Bean
     *
     * @param clazz Bean 类型
     * @param <T>   泛型
     * @return Bean 实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过名称和类型获取 Bean
     *
     * @param name  Bean 名称
     * @param clazz Bean 类型
     * @param <T>   泛型
     * @return Bean 实例
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }
}