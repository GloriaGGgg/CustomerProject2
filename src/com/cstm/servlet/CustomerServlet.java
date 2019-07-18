package com.cstm.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.cstm.domain.Customer;
import com.cstm.domain.PageBean;
import com.cstm.service.CustomerService;

public class CustomerServlet extends BaseServlet {

	private CustomerService customerService=new CustomerService();
	
	
	/**
	 * add customer function
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 1.封装表单数据到customer对象中
		 * 2.补全sid，使用uuid
		 * 3.使用service方法完成添加
		 * 4.向request域中保存成功信息
		 * 5.转发到msg.jsp中
		 */
		Customer c=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		
		//有时候数据库没有设置自增，所以我们需要让代码自动帮它补全
		c.setCid(CommonUtils.uuid());
		
		customerService.add(c);
		request.setAttribute("msg", "Add Customer Successfully!");
		
		return "f:/msg.jsp";//最后转发到消息页面	
	}
	
	
	
//	/**
//	 * check all customers information from database
//	 */
//	public String find(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		/*
//		 * 1.调用service得到所有客户
//		 * 2.保存到request中
//		 * 3.转发到list.jsp中
//		 */
//		request.setAttribute("cstmList", customerService.findAll());
//		return "f:/list.jsp";
//	}
//	
	
	
	/**
	 * 这个方法变了，不是上面单纯的查询所有用户，而是要考虑分页问题
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.获取页面传递的pc（当前页面号码page code）
		 * 2.给定ps的值（每页记录数page size）
		 * 3.传递pc，ps给dao，dao返回pageBean对象
		 * 4.PageBean保存到request域中，并转发给页面list.jsp
		 */
		
		int pc=getPc(request);//得到pc
		int ps=10;//规定：每页10行记录
		PageBean<Customer> pb=customerService.findAll(pc,ps);//传递给service，返回pageBean对象
		
		//设置url
		pb.setUrl(getUrl(request));
		
		request.setAttribute("pb", pb);
		return "f:/list.jsp";
		
	}
	
	/**
	 * 获取pc
	 *  1.如果pc不存在，则默认第一页，pc设置为1
	 *   2.pc存在，转换成int类型即可
	 * @param request
	 * @return
	 */
	public int getPc(HttpServletRequest request){
		/*
		 * 一.pc有两种情况
		 *   1.如果pc不存在，则默认第一页，pc设置为1
		 *   2.pc存在，转换成int类型即可
		 */
		String value=request.getParameter("pc");
		if(value==null || value.trim().isEmpty()){
			return 1;
		}
		return Integer.parseInt(value);
	}
	
	
	/*
	 * before edit, something have to deal with first
	 */
	public String preEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		/*
		 * 1.获取cid
		 * 2.用cid调用service方法，得到customer对象
		 * 3.把customer对象保存到request域中
		 * 4.转发到edit.jsp中显示在表单中
		 */
		String cid=request.getParameter("cid");
		Customer cstm=customerService.load(cid);
		request.setAttribute("cstm", cstm);
		return "f:/edit.jsp";
	}
	
	
	/*
	 * when edit, something have to deal with first
	 */
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
	/**
	 * 1.封装表单数据到customer对象中
	 * 2.调用service方法完成修改
	 * 3.保存成功信息到request域中
	 * 4.转发到msg.jsp中显示成功信息 
	 */
		Customer cstm=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		customerService.edit(cstm);
		request.setAttribute("msg", "Congratulations! Edit Successfully!");
		return "f:/msg.jsp";
	}
	
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		/*
		 * 1.获取cid
		 * 2.用cid调用service方法，完成删除
		 * 3.保存成功信息到request域中
		 * 4.转发到msg.jsp显示成功信息
		 */
		String cid=request.getParameter("cid");
		customerService.delete(cid);
		request.setAttribute("msg", "Congratulations! Delete Successfully!");
		return "f:/msg.jsp";
	}
	
	
//	/**
//	 * 多条件组合查询
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public String query(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException{
//		/*
//		 * 1.封装表单数据到customer对象中，只有四个属性：cname,gender,cellphone,email
//		 * 2.使用customer调用service方法，得到list<Customer>
//		 * 3.保存到request域中
//		 * 4.转发到list.jsp中
//		 */
//		Customer criteria=CommonUtils.toBean(request.getParameterMap(), Customer.class);
//		List<Customer> cstm=customerService.query(criteria);
//		request.setAttribute("cstmList", cstm);
//		return "f:/list.jsp";
//		
//	}
	
	
	
	/**
	 * 多条件组合查询修改如下
	 * 因为加上了分页，所以要重新修改
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
	   
		/*
		 * 0.把条件封装到customer对象中
		 * 1.得到pc
		 * 2.给定ps
		 * 3.使用pc和ps，以及条件对象调用service方法得到pageBean
		 * 4.把pageBean保存到request域中
		 */
		//获取查询条件
		Customer criteria=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		int pc=getPc(request);//得到pc
		int ps=10;//规定：每页10行记录
		PageBean<Customer>pb= customerService.query(criteria,pc,ps);
		
		//得到url保存到pb中
		pb.setUrl(getUrl(request));
		
		request.setAttribute("pb", pb);
		return "f:/list.jsp";
	}
	
	
	/**
	 * 截取url
	 *        /项目名/servlet路径？参数字符串
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request){
		String contextPath=request.getContextPath();//获取项目名
		String servletPath=request.getServletPath();//获取servlet路径
		String 	queryString=request.getQueryString();//获取参数部分
		
		//判断参数是否包含pc这个参数
		//如果包含，就截取
		if(queryString.contains("&pc=")){
			int index=queryString.lastIndexOf("&pc=");
			queryString=queryString.substring(0, index);
		}
		return contextPath+servletPath+"?"+queryString;
		
	}

}
