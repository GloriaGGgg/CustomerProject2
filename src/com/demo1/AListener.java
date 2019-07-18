package com.demo1;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * 这个是三大监听器中的ServletContext监听器
 * 生死监听器（生命周期监听器）
 * 
 * 配置监听器都要在web.xml中进行配置
 * 
 * @author Gloria
 *
 *可以在这个监听器中存放一些在tomcat服务器启动时就需要执行的代码
 */
public class AListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/*
		 * 这个是死之前调用的监听器
		 */
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz I am going to die! zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * 这个是出生后调用的监听器
		 */
	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx I am here! xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

	}

}
