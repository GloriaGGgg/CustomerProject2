package com.demo1;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * ���������������е�ServletContext������
 * �������������������ڼ�������
 * 
 * ���ü�������Ҫ��web.xml�н�������
 * 
 * @author Gloria
 *
 *����������������д��һЩ��tomcat����������ʱ����Ҫִ�еĴ���
 */
public class AListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		/*
		 * �������֮ǰ���õļ�����
		 */
		System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzz I am going to die! zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		/*
		 * ����ǳ�������õļ�����
		 */
	System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx I am here! xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

	}

}
