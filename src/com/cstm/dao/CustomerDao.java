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
			 * 1.得到pc，ps，创建pageBean对象
			 * 2.查询db，得到tr（total record总记录数）和beanList（当前页面的所有数据）
			 * 3.返回pageBean对象
			 */
			PageBean<Customer> pb=new PageBean<Customer>();
			pb.setPc(pc);
			pb.setPs(ps);
			
			/*
			 * 得到tr
			 */
			String sql="select count(*) from t_customer;";
			Number num=(Number)qr.query(sql, new ScalarHandler());//用这个结果集处理器,因为返回的是单行单列
			int tr=num.intValue();//把num转换成int类型
			pb.setTr(tr);
			
			/*
			 * 得到beanList
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
//	 * 多条件组合查询
//	 * @param criteria
//	 * @return
//	 */
//	public List<Customer> query(Customer criteria) {
//	
//		try{
//		/*
//		 * 1.给sql模板
//		 * 2.给参数
//		 * 3.调用query方法，使用结果集处理器:BeanListHandler
//		 */
//		/*
//		 * 一.给sql模板
//		 * 二.给参数
//		 */
//		/*
//		 * 1.给sql语句前半部分
//		 */
//		StringBuilder sql=new StringBuilder("select * from t_customer where 1=1 ");
//		/*
//		 * 2.判断条件，完成向sql中追加where子句
//		 */
//		/*
//		 * 3.创建ArrayList用来装载参数
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
//		 * 三.调用BeanListHandler
//		 */
//		return qr.query(sql.toString(), 
//				new BeanListHandler<Customer>(Customer.class),
//				params.toArray());
//		}catch(Exception e){
//			throw new RuntimeException(e);
//	    }
//}
	
	
	
	
	
	/**
	 * 更改上面的query方法，因为不同功能对应不同的分页信息
	 * @param criteria
	 * @return
	 */
	public PageBean<Customer> query(Customer criteria,int pc,int ps) {
		try{
			/*
			 * 1.创建pageBean对象
			 * 2.设置已有属性pc和ps
			 * 3.得到tr
			 * 4.得到beanList
			 */
			PageBean<Customer>pb=new PageBean<Customer>();
			pb.setPc(pc);
			pb.setPs(ps);
			
			
			/*
			 * 得到tr
			 */
			/*
			 * 1.给sql语句前半部分
			 */
			StringBuilder cntSql=new StringBuilder("select count(*)  from t_customer ");
			StringBuilder whereSql=new StringBuilder(" where 1=1");
			/*
			 * 2.判断条件，完成向sql中追加where子句
			 */
			/*
			 * 3.创建ArrayList用来装载参数
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
			 * select count(*) from t_customer where 子句
			 * 执行这条sql语句
			 */
			Number num=(Number)qr.query(cntSql.append(whereSql).toString(), new ScalarHandler(),params.toArray());
			int tr=num.intValue();
			pb.setTr(tr);
			
			/*
			 * 得到beanList
			 */
			//拆成两半是为了方便重用
			StringBuilder sql=new StringBuilder("select *  from t_customer ");
			//还需要给出limit子句
			StringBuilder limitSql=new StringBuilder(" limit ?,?");
			//params中需要给出limit后两个？对应的值
			params.add((pc-1)*ps);
			params.add(ps);
			//执行这个子句
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
