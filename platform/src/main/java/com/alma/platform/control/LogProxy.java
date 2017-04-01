package com.alma.platform.control;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * This class represent a proxy of a plugin. It allows to catch each call of
 * plugin methods.
 *
 */
public class LogProxy implements InvocationHandler {

	private Object target;

	/**
	 * Create a new instance of LogProxy.
	 * 
	 * @param obj The object which have to be proxyfy.
	 */
	public LogProxy(Object obj) {
		target = obj;
	}

	@Override
	public Object invoke(Object obj, Method method, Object[] args) throws Throwable {

		String msg = "Method " + method.getName() + "() has been called by " + obj.getClass().getName();
		Platform.getInstance().addLog(msg);

		System.out.println(msg);

		return method.invoke(target, args);
	}

}
