package com.cstm.service;

import java.util.List;

import com.cstm.dao.CustomerDao;
import com.cstm.domain.Customer;
import com.cstm.domain.PageBean;

/**
 * to deal with business
 * @author Gloria
 *
 */
public class CustomerService {
	private CustomerDao customerDao=new CustomerDao();
	
	public void add(Customer c){
		customerDao.addUser(c);
	}
	
	public PageBean<Customer> findAll(int pc,int ps){
		return customerDao.findUser(pc,ps);
	}

	public Customer load(String cid) {
		
		return customerDao.load(cid);
	}

	public void edit(Customer cstm) {
		customerDao.edit(cstm);
		
	}

	public void delete(String cid) {
		customerDao.delete(cid);
	}

	public PageBean<Customer> query(Customer criteria,int pc,int ps) {
		return customerDao.query(criteria,pc,ps);
	}

}
