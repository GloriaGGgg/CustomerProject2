package com.cstm.dao;

import org.junit.Test;

import cn.itcast.commons.CommonUtils;

import com.cstm.domain.Customer;

/**
 * this class is to insert 300 customers into database
 * @author Gloria
 *
 */
public class CustomerTest {
	@Test
	public void fun1(){
		CustomerDao dao=new CustomerDao();
		for(int i=1;i<=300;i++){
			Customer c=new Customer();
			
			c.setCid(CommonUtils.uuid());
			c.setCname("customer_"+i);
			c.setBirthday("1997/7/22");
			c.setGender(i%2==0?"male":"female");
			c.setCellphone("139"+i);
			c.setEmail("customer_"+i+"@uwlax.edu");
			c.setDescription("I am Gloria "+i);
			
			dao.addUser(c);
		}
	}

}
