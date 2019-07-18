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
		 * 1.��װ�����ݵ�customer������
		 * 2.��ȫsid��ʹ��uuid
		 * 3.ʹ��service����������
		 * 4.��request���б���ɹ���Ϣ
		 * 5.ת����msg.jsp��
		 */
		Customer c=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		
		//��ʱ�����ݿ�û����������������������Ҫ�ô����Զ�������ȫ
		c.setCid(CommonUtils.uuid());
		
		customerService.add(c);
		request.setAttribute("msg", "Add Customer Successfully!");
		
		return "f:/msg.jsp";//���ת������Ϣҳ��	
	}
	
	
	
//	/**
//	 * check all customers information from database
//	 */
//	public String find(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		/*
//		 * 1.����service�õ����пͻ�
//		 * 2.���浽request��
//		 * 3.ת����list.jsp��
//		 */
//		request.setAttribute("cstmList", customerService.findAll());
//		return "f:/list.jsp";
//	}
//	
	
	
	/**
	 * ����������ˣ��������浥���Ĳ�ѯ�����û�������Ҫ���Ƿ�ҳ����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String find(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.��ȡҳ�洫�ݵ�pc����ǰҳ�����page code��
		 * 2.����ps��ֵ��ÿҳ��¼��page size��
		 * 3.����pc��ps��dao��dao����pageBean����
		 * 4.PageBean���浽request���У���ת����ҳ��list.jsp
		 */
		
		int pc=getPc(request);//�õ�pc
		int ps=10;//�涨��ÿҳ10�м�¼
		PageBean<Customer> pb=customerService.findAll(pc,ps);//���ݸ�service������pageBean����
		
		//����url
		pb.setUrl(getUrl(request));
		
		request.setAttribute("pb", pb);
		return "f:/list.jsp";
		
	}
	
	/**
	 * ��ȡpc
	 *  1.���pc�����ڣ���Ĭ�ϵ�һҳ��pc����Ϊ1
	 *   2.pc���ڣ�ת����int���ͼ���
	 * @param request
	 * @return
	 */
	public int getPc(HttpServletRequest request){
		/*
		 * һ.pc���������
		 *   1.���pc�����ڣ���Ĭ�ϵ�һҳ��pc����Ϊ1
		 *   2.pc���ڣ�ת����int���ͼ���
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
		 * 1.��ȡcid
		 * 2.��cid����service�������õ�customer����
		 * 3.��customer���󱣴浽request����
		 * 4.ת����edit.jsp����ʾ�ڱ���
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
	 * 1.��װ�����ݵ�customer������
	 * 2.����service��������޸�
	 * 3.����ɹ���Ϣ��request����
	 * 4.ת����msg.jsp����ʾ�ɹ���Ϣ 
	 */
		Customer cstm=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		customerService.edit(cstm);
		request.setAttribute("msg", "Congratulations! Edit Successfully!");
		return "f:/msg.jsp";
	}
	
	
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		/*
		 * 1.��ȡcid
		 * 2.��cid����service���������ɾ��
		 * 3.����ɹ���Ϣ��request����
		 * 4.ת����msg.jsp��ʾ�ɹ���Ϣ
		 */
		String cid=request.getParameter("cid");
		customerService.delete(cid);
		request.setAttribute("msg", "Congratulations! Delete Successfully!");
		return "f:/msg.jsp";
	}
	
	
//	/**
//	 * ��������ϲ�ѯ
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws ServletException
//	 * @throws IOException
//	 */
//	public String query(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException{
//		/*
//		 * 1.��װ�����ݵ�customer�����У�ֻ���ĸ����ԣ�cname,gender,cellphone,email
//		 * 2.ʹ��customer����service�������õ�list<Customer>
//		 * 3.���浽request����
//		 * 4.ת����list.jsp��
//		 */
//		Customer criteria=CommonUtils.toBean(request.getParameterMap(), Customer.class);
//		List<Customer> cstm=customerService.query(criteria);
//		request.setAttribute("cstmList", cstm);
//		return "f:/list.jsp";
//		
//	}
	
	
	
	/**
	 * ��������ϲ�ѯ�޸�����
	 * ��Ϊ�����˷�ҳ������Ҫ�����޸�
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
	   
		/*
		 * 0.��������װ��customer������
		 * 1.�õ�pc
		 * 2.����ps
		 * 3.ʹ��pc��ps���Լ������������service�����õ�pageBean
		 * 4.��pageBean���浽request����
		 */
		//��ȡ��ѯ����
		Customer criteria=CommonUtils.toBean(request.getParameterMap(), Customer.class);
		int pc=getPc(request);//�õ�pc
		int ps=10;//�涨��ÿҳ10�м�¼
		PageBean<Customer>pb= customerService.query(criteria,pc,ps);
		
		//�õ�url���浽pb��
		pb.setUrl(getUrl(request));
		
		request.setAttribute("pb", pb);
		return "f:/list.jsp";
	}
	
	
	/**
	 * ��ȡurl
	 *        /��Ŀ��/servlet·���������ַ���
	 * @param request
	 * @return
	 */
	private String getUrl(HttpServletRequest request){
		String contextPath=request.getContextPath();//��ȡ��Ŀ��
		String servletPath=request.getServletPath();//��ȡservlet·��
		String 	queryString=request.getQueryString();//��ȡ��������
		
		//�жϲ����Ƿ����pc�������
		//����������ͽ�ȡ
		if(queryString.contains("&pc=")){
			int index=queryString.lastIndexOf("&pc=");
			queryString=queryString.substring(0, index);
		}
		return contextPath+servletPath+"?"+queryString;
		
	}

}
