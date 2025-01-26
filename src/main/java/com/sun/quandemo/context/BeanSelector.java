package com.sun.quandemo.context;

public class BeanSelector
{
	public static <T> T getBean(String beanName)
	{
		return (T) SpringContext.getBean(beanName);
	}

	public static <T> T getBean(Class clazz)
	{
		return (T) SpringContext.getBean(clazz);
	}
}
