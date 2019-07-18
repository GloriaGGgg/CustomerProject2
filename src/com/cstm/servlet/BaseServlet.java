package com.cstm.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseServlet extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.��ȡ����������ʶ���û�������ķ���
		 * 2.�ж�����һ��������Ȼ����ö�Ӧ�ķ�����ִ��
		 */
		String methodName=request.getParameter("method");
		
		if(methodName==null || methodName.trim().isEmpty()){
			throw new RuntimeException("Please input methodName and ensure that there is no space inside of it!");
		}
		
		/*
		 * �õ��������ƣ��Ƿ�ͨ�����������÷�����
		 * 1.�õ���������ͨ���������õ�method��Ķ���
		 *  -��Ҫ�õ�class��Ȼ��������ķ������в�ѯ���õ�method
		 *  -����Ҫ��ѯ���ǵ�ǰ���class������Ҫ�õ���ǰ���class
		 */
		Class c=this.getClass();//�õ���ǰ��
		Method method=null;
				try {
					method=c.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
				} catch (Exception e) {
					throw new RuntimeException("The method " +methodName+" you are looking for is not exist!!!");
				} 
				
				/*
				 * ����method��Ӧ�ķ���
				 * ���Ƿ������
				 */
				try {
					String result=(String)method.invoke(this, request, response);
					
					/*
					 * ��ȡ���������󷵻ص��ַ���
					 * ��ʾת�����ض����·��
					 */
					/*
					 * �û�����null���ߡ�������ʲô������
					 */
					if(result==null || result.trim().isEmpty()){
						return;
					}
					/*
					 * ������Ҫ���ת�������ض���
					 */
					/*
					 * 1.��ȡǰ׺���ж���ת�������ض���
					 *        *�鿴���ص��ַ�����û��ð�š����û��ð�ţ���Ĭ����ת��
					 *        *����У�ʹ��ð�ŷָ��ַ�����ǰ׺f��ʾת����r��ʾ�ض��򣻺�׺��ʾ·����
					 * 2.��ȡ�����ַ����ʾ�����ĵ�ַ
					 */
					if(result.contains(":")){
						//ʹ��ð�ŷָ��ַ������õ�ǰ׺�ͺ�׺
						int index=result.indexOf(":");//�õ�ð�ŵ�λ��
						String prefix=result.substring(0,index);//�õ�ǰ׺
						String path=result.substring(index+1);//�õ���׺
						
						if(prefix.equalsIgnoreCase("r")){
							//��ʾ�ض���
							response.sendRedirect(request.getContextPath()+path);
						}else if(prefix.equalsIgnoreCase("f")){
							//��ʾת��
							request.getRequestDispatcher(path).forward(request, response);
						}else{
							throw new RuntimeException("Wrong direction! Please choose 'r' or 'f' to be the operation!");
						}
					}else{
						//û��ð�ţ���Ĭ��ת��
						request.getRequestDispatcher(result).forward(request, response);
					}
				
				} catch (Exception e) {
					System.out.println("There are exceptions inside your method!");
					throw new RuntimeException(e);
				} 
	}


}
