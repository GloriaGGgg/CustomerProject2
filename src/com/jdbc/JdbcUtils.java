package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	//�������ӷ�ʽ�������ļ���Ĭ�����ӷ�ʽ������Ҫ����c3p0-config.xml�ļ�
	private static ComboPooledDataSource ds=new ComboPooledDataSource();
	
	//���������ר������,Ҫ���Ƕ��̵߳�����
	private static ThreadLocal<Connection> tl =new  ThreadLocal<Connection>();
	
/**
 * ʹ�����ӳط���һ������
 * @return
 * @throws SQLException
 */
	public static Connection getConnection() throws SQLException{
		
		Connection con=tl.get();//��ȡ�Լ��̵߳�connection����
		/*
		 * ����Ҫ����һЩ��Ϣ
		 * ��Ϊxml�ļ��Ѿ����ú���
		 * ����ֱ�ӵ���
		 */
		
		//��con��Ϊnull��˵���Ѿ�����beginTransaction��������ʾ����������
		if(con!=null) return con;
		
		return ds.getConnection();//ֱ��ds�Ϳ��Է���һ������
	}
	
	/**
	 * �������ӳض���
	 * @return
	 */
	public static ComboPooledDataSource getDataSource(){
		return ds;
	}
	
	/*
	 * ��������
	 * 1.��ȡһ��connection������setAutoCommitΪfalse
	 * 2.��Ҫ��֤dao�е�connection�����Ǹոմ�����
	 * -------------------------------------------
	 * 1.����һ��connection������Ϊ�ֶ��ύ
	 * 2.�����connection��dao��
	 * 3.����Ҫ��commitTransaction��rollbackTransaction���Ի�ȡ��
	 * 
	 */
	public static void beginTransaction() throws SQLException{
		
		Connection con=tl.get();//��ȡ�Լ��̵߳�connection����
		
		//���connection���ǿգ���ʾ�Ѿ���һ�����񣬲���Ҫ�ٴο���connection��
		if(con!=null) throw new SQLException("You have already started a connection, use that!");
		
		/*
		 * 1.��con��ֵ
		 * 2.��con����Ϊ�ֶ��ύ
		 */
		con=getConnection();
		con.setAutoCommit(false);
		
		tl.set(con);//�ѵ�ǰ�̱߳�������
		
	}
	
	
	/*
	 * �ύ����
	 * 1.��ȡbeginTransaction�ṩ��connection������commit����
	 */
	public static void commitTransaction() throws SQLException{
		Connection con=tl.get();//��ȡ�Լ��̵߳�connection����
		
		//��connectionΪ�գ���ô��ʾ��û�п���һ�������������׳��쳣
		if(con==null) throw new SQLException("Have not begin a new connection!");
		/*
		 * 1.ֱ��ʹ��con.commit�ύ����
		 */
		con.commit();
		con.close();
		
		tl.remove();//�����connection��tl���Ƴ�
	}
	
	
	
	/*
	 * �ύ����
	 * 1.��ȡbeginTransaction�ṩ��connection������rollback����
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection con=tl.get();//��ȡ�Լ��̵߳�connection����
		
		//��connectionΪ�գ���ô��ʾ��û�п���һ�������������׳��쳣
		if(con==null) throw new SQLException("Have not begin a new connection!");
		/*
		 * 1.ʹ��con.rollback��ɻع�
		 */
		con.rollback();
		con.close();

        tl.remove();
	}

	
	/**
	 * �ͷ�����
	 * @param connection
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		Connection con=tl.get();//��ȡ�Լ��̵߳�connection����
		
		/*
		 * �ж��ǲ�������ר�����ӡ�����ǣ��Ͳ��ر�
		 * ������ǣ���Ҫ�رգ�����û���˰����ر���
		 */
		//���conΪnull��˵������û��������ôconnection��������ר�õ�
		if(con==null) connection.close();
		//���con��Ϊnull��˵����������Ҫ�ж�connection��con�Ƿ���ȣ������ȣ���connection������������
		if(con!=connection) connection.close();
	}

}
