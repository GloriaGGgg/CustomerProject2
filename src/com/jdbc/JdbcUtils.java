package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	//这种连接方式是配置文件的默认连接方式。但是要给出c3p0-config.xml文件
	private static ComboPooledDataSource ds=new ComboPooledDataSource();
	
	//这个是事务专用连接,要考虑多线程的问题
	private static ThreadLocal<Connection> tl =new  ThreadLocal<Connection>();
	
/**
 * 使用连接池返回一个对象
 * @return
 * @throws SQLException
 */
	public static Connection getConnection() throws SQLException{
		
		Connection con=tl.get();//获取自己线程的connection连接
		/*
		 * 不需要配置一些信息
		 * 因为xml文件已经配置好了
		 * 它会直接调用
		 */
		
		//当con不为null，说明已经调用beginTransaction（），表示开启了事务
		if(con!=null) return con;
		
		return ds.getConnection();//直接ds就可以返回一个连接
	}
	
	/**
	 * 返回连接池对象
	 * @return
	 */
	public static ComboPooledDataSource getDataSource(){
		return ds;
	}
	
	/*
	 * 开启事务
	 * 1.获取一个connection，设置setAutoCommit为false
	 * 2.还要保证dao中的connection是我们刚刚创建的
	 * -------------------------------------------
	 * 1.创建一个connection，设置为手动提交
	 * 2.把这个connection给dao用
	 * 3.还需要让commitTransaction和rollbackTransaction可以获取到
	 * 
	 */
	public static void beginTransaction() throws SQLException{
		
		Connection con=tl.get();//获取自己线程的connection连接
		
		//如果connection不是空，表示已经有一个事务，不需要再次开启connection了
		if(con!=null) throw new SQLException("You have already started a connection, use that!");
		
		/*
		 * 1.给con赋值
		 * 2.给con设置为手动提交
		 */
		con=getConnection();
		con.setAutoCommit(false);
		
		tl.set(con);//把当前线程保存起来
		
	}
	
	
	/*
	 * 提交事务
	 * 1.获取beginTransaction提供的connection，调用commit方法
	 */
	public static void commitTransaction() throws SQLException{
		Connection con=tl.get();//获取自己线程的connection连接
		
		//若connection为空，那么表示还没有开启一个新事务，所以抛出异常
		if(con==null) throw new SQLException("Have not begin a new connection!");
		/*
		 * 1.直接使用con.commit提交连接
		 */
		con.commit();
		con.close();
		
		tl.remove();//把这个connection从tl中移除
	}
	
	
	
	/*
	 * 提交事务
	 * 1.获取beginTransaction提供的connection，调用rollback方法
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection con=tl.get();//获取自己线程的connection连接
		
		//若connection为空，那么表示还没有开启一个新事务，所以抛出异常
		if(con==null) throw new SQLException("Have not begin a new connection!");
		/*
		 * 1.使用con.rollback完成回滚
		 */
		con.rollback();
		con.close();

        tl.remove();
	}

	
	/**
	 * 释放连接
	 * @param connection
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		Connection con=tl.get();//获取自己线程的connection连接
		
		/*
		 * 判断是不是事务专用连接。如果是，就不关闭
		 * 如果不是，就要关闭，否则没有人帮它关闭了
		 */
		//如果con为null，说明现在没有事务，那么connection不是事务专用的
		if(con==null) connection.close();
		//如果con不为null，说明有事务，需要判断connection和con是否相等；若不等，则connection不是事务连接
		if(con!=connection) connection.close();
	}

}
