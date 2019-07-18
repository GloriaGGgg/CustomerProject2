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
		 * 1.获取参数，用来识别用户想请求的方法
		 * 2.判断是哪一个方法，然后调用对应的方法来执行
		 */
		String methodName=request.getParameter("method");
		
		if(methodName==null || methodName.trim().isEmpty()){
			throw new RuntimeException("Please input methodName and ensure that there is no space inside of it!");
		}
		
		/*
		 * 得到方法名称，是否通过反射来调用方法？
		 * 1.得到方法名，通过方法名得到method类的对象
		 *  -需要得到class，然后调用它的方法进行查询，得到method
		 *  -我们要查询的是当前类的class，所以要得到当前类的class
		 */
		Class c=this.getClass();//得到当前类
		Method method=null;
				try {
					method=c.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
				} catch (Exception e) {
					throw new RuntimeException("The method " +methodName+" you are looking for is not exist!!!");
				} 
				
				/*
				 * 调用method对应的方法
				 * 这是反射调用
				 */
				try {
					String result=(String)method.invoke(this, request, response);
					
					/*
					 * 获取请求处理方法后返回的字符串
					 * 表示转发或重定向的路径
					 */
					/*
					 * 用户返回null或者”“，那什么都不做
					 */
					if(result==null || result.trim().isEmpty()){
						return;
					}
					/*
					 * 接下来要完成转发或者重定向
					 */
					/*
					 * 1.获取前缀，判断是转发还是重定向
					 *        *查看返回的字符串有没有冒号。如果没有冒号，则默认是转发
					 *        *如果有，使用冒号分割字符串；前缀f表示转发，r表示重定向；后缀表示路径！
					 * 2.获取后面地址，表示操作的地址
					 */
					if(result.contains(":")){
						//使用冒号分割字符串，得到前缀和后缀
						int index=result.indexOf(":");//得到冒号的位置
						String prefix=result.substring(0,index);//得到前缀
						String path=result.substring(index+1);//得到后缀
						
						if(prefix.equalsIgnoreCase("r")){
							//表示重定向
							response.sendRedirect(request.getContextPath()+path);
						}else if(prefix.equalsIgnoreCase("f")){
							//表示转发
							request.getRequestDispatcher(path).forward(request, response);
						}else{
							throw new RuntimeException("Wrong direction! Please choose 'r' or 'f' to be the operation!");
						}
					}else{
						//没有冒号，则默认转发
						request.getRequestDispatcher(result).forward(request, response);
					}
				
				} catch (Exception e) {
					System.out.println("There are exceptions inside your method!");
					throw new RuntimeException(e);
				} 
	}


}
