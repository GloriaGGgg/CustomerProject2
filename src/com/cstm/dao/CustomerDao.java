package com.cstm.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cstm.domain.Customer;
import com.cstm.domain.PageBean;
import com.jdbc.TxQueryRunner;

/**
 * to manupulate Domain layer
 * 
 * @author Gloria
 * 
 */
public class CustomerDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * add customer
	 */
	public void addUser(Customer c) {
		try {
			String sql = "insert into t_customer values(?,?,?,?,?,?,?);";
			Object[] params = { c.getCid(), c.getCname(), c.getGender(),
					c.getBirthday(), c.getCellphone(), c.getEmail(),
					c.getDescription() };
			qr.update(sql, params);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * find all customers in database
	 */
	public PageBean<Customer> findUser(int pc,int ps) {
		try {
			/*
			 * 1.�õ�pc��ps������pageBean����
			 * 2.��ѯdb���õ�tr��total record�ܼ�¼������beanList����ǰҳ����������ݣ�
			 * 3.����pageBean����
			 */
			PageBean<Customer> pb=new PageBean<Customer>();
			pb.setPc(pc);
			pb.setPs(ps);
			
			/*
			 * �õ�tr
			 */
			String sql="select count(*) from t_customer;";
			Number num=(Number)qr.query(sql, new ScalarHandler());//����������������,��Ϊ���ص��ǵ��е���
			int tr=num.intValue();//��numת����int����
			pb.setTr(tr);
			
			/*
			 * �õ�beanList
			 */
			String sql2="select * from t_customer order by cname limit ?,?;";
			List<Customer> beanList=null;
			if(pc<=0){
				beanList=qr.query(sql2, new BeanListHandler<Customer>(Customer.class),0,ps); 
			}else{
				 beanList=qr.query(sql2, new BeanListHandler<Customer>
				(Customer.class),(pc-1)*ps,ps);
			};
			
			pb.setBeanList(beanList);
			return pb;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	

	/**
	 * load customers information
	 * @param cid
	 * @return
	 */
	public Customer load(String cid) {
		try{
		String sql="select * from t_customer where cid=?;";
		return qr.query(sql, new BeanHandler<Customer>(Customer.class),cid);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}


	
	/**
	 * edit customer information
	 * @param cstm
	 */
	public void edit(Customer c) {
		try{
		String sql="update t_customer set cname=?,gender=?,birthday=?,cellphone=?," +
				"email=?,description=? where cid=?;";
		Object[] params = { c.getCname(), c.getGender(),
				c.getBirthday(), c.getCellphone(), c.getEmail(),
				c.getDescription(),c.getCid()};
		qr.update(sql,params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}


	public void delete(String cid) {
		try{
		String sql="delete from t_customer where cid=?;";
		Object[] params={cid};
		qr.update(sql,params);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}


	
	
//	/**
//	 * ��������ϲ�ѯ
//	 * @param criteria
//	 * @return
//	 */
//	public List<Customer> query(Customer criteria) {
//	
//		try{
//		/*
//		 * 1.��sqlģ��
//		 * 2.������
//		 * 3.����query������ʹ�ý����������:BeanListHandler
//		 */
//		/*
//		 * һ.��sqlģ��
//		 * ��.������
//		 */
//		/*
//		 * 1.��sql���ǰ�벿��
//		 */
//		StringBuilder sql=new StringBuilder("select * from t_customer where 1=1 ");
//		/*
//		 * 2.�ж������������sql��׷��where�Ӿ�
//		 */
//		/*
//		 * 3.����ArrayList����װ�ز���
//		 */
//		List<Object> params=new ArrayList<Object>();
//		String 	cname=criteria.getCname();
//		if(cname!=null && !cname.trim().isEmpty()){
//			sql.append(" and cname like ?");
//			params.add("%"+cname+"%");
//		}
//		
//		String 	gender=criteria.getGender();
//		if(gender!=null && !gender.trim().isEmpty()){
//			sql.append(" and gender=?");
//			params.add(gender);
//		}
//		
//		String 	cellphone=criteria.getCellphone();
//		if(cellphone!=null && !cellphone.trim().isEmpty()){
//			sql.append(" and cellphone=?");
//			params.add(cellphone);
//		}
//		
//		String 	email=criteria.getEmail();
//		if(email!=null && !email.trim().isEmpty()){
//			sql.append(" and email=?");
//			params.add(email);
//		}
//		
//		/*
//		 * ��.����BeanListHandler
//		 */
//		return qr.query(sql.toString(), 
//				new BeanListHandler<Customer>(Customer.class),
//				params.toArray());
//		}catch(Exception e){
//			throw new RuntimeException(e);
//	    }
//}
	
	
	
	
	
	/**
	 * ���������query��������Ϊ��ͬ���ܶ�Ӧ��ͬ�ķ�ҳ��Ϣ
	 * @param criteria
	 * @return
	 */
	public PageBean<Customer> query(Customer criteria,int pc,int ps) {
		try{
			/*
			 * 1.����pageBean����
			 * 2.������������pc��ps
			 * 3.�õ�tr
			 * 4.�õ�beanList
			 */
			PageBean<Customer>pb=new PageBean<Customer>();
			pb.setPc(pc);
			pb.setPs(ps);
			
			
			/*
			 * �õ�tr
			 */
			/*
			 * 1.��sql���ǰ�벿��
			 */
			StringBuilder cntSql=new StringBuilder("select count(*)  from t_customer ");
			StringBuilder whereSql=new StringBuilder(" where 1=1");
			/*
			 * 2.�ж������������sql��׷��where�Ӿ�
			 */
			/*
			 * 3.����ArrayList����װ�ز���
			 */
			List<Object> params=new ArrayList<Object>();
			String 	cname=criteria.getCname();
			if(cname!=null && !cname.trim().isEmpty()){
				whereSql.append(" and cname like ?");
				params.add("%"+cname+"%");
			}
			
			String 	gender=criteria.getGender();
			if(gender!=null && !gender.trim().isEmpty()){
				whereSql.append(" and gender= ?");
				params.add(gender);
			}
			
			String 	cellphone=criteria.getCellphone();
			if(cellphone!=null && !cellphone.trim().isEmpty()){
				whereSql.append(" and cellphone like ?");
				params.add("%"+cellphone+"%");
			}
			
			String 	email=criteria.getEmail();
			if(email!=null && !email.trim().isEmpty()){
				whereSql.append(" and email like ?");
				params.add("%"+email+"%");
			}
			
			/*
			 * select count(*) from t_customer where �Ӿ�
			 * ִ������sql���
			 */
			Number num=(Number)qr.query(cntSql.append(whereSql).toString(), new ScalarHandler(),params.toArray());
			int tr=num.intValue();
			pb.setTr(tr);
			
			/*
			 * �õ�beanList
			 */
			//���������Ϊ�˷�������
			StringBuilder sql=new StringBuilder("select *  from t_customer ");
			//����Ҫ����limit�Ӿ�
			StringBuilder limitSql=new StringBuilder(" limit ?,?");
			//params����Ҫ����limit����������Ӧ��ֵ
			params.add((pc-1)*ps);
			params.add(ps);
			//ִ������Ӿ�
			List<Customer>beanList=qr.query(sql.append(whereSql).append(limitSql).toString(), 
					new BeanListHandler<Customer>(Customer.class),
					params.toArray()); 
			
			pb.setBeanList(beanList);
			
			return pb;
		}catch(Exception e){
			throw new RuntimeException(e);
	    }
}
}
