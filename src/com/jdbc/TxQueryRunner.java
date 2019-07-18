package com.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import com.jdbc.JdbcUtils;

/**
 * this class is from previous class
 * 
 * @author Gloria
 *
 */
public class TxQueryRunner extends QueryRunner{
	
	//批处理方法
	public int[] batch(String sql, Object[][] params) throws SQLException {
		/*
		 * 1.得到连接
		 * 2.执行父类方法
		 * 3.释放连接
		 * 4.返回值
		 */
		Connection con=JdbcUtils.getConnection();
		int[] result=super.batch(con,sql,params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	public int execute(String sql, Object... params) throws SQLException {
		// TODO Auto-generated method stub
		return super.execute(sql, params);
	}

	public <T> List<T> execute(String sql, ResultSetHandler<T> rsh,
			Object... params) throws SQLException {
		// TODO Auto-generated method stub
		return super.execute(sql, rsh, params);
	}

	public <T> T insert(String sql, ResultSetHandler<T> rsh, Object... params)
			throws SQLException {
		// TODO Auto-generated method stub
		return super.insert(sql, rsh, params);
	}

	@Override
	public <T> T insert(String sql, ResultSetHandler<T> rsh)
			throws SQLException {
		// TODO Auto-generated method stub
		return super.insert(sql, rsh);
	}

	@Override
	public <T> T insertBatch(String sql, ResultSetHandler<T> rsh,
			Object[][] params) throws SQLException {
		// TODO Auto-generated method stub
		return super.insertBatch(sql, rsh, params);
	}

	@Override
	public <T> T query(String sql, Object param, ResultSetHandler<T> rsh)
			throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		T result=super.query(con,sql,param,rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, Object[] params, ResultSetHandler<T> rsh)
			throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		T result=super.query(con,sql,params,rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params)
			throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		T result=super.query(con,sql,rsh,params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		T result=super.query(con,sql,rsh);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object... params) throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		int result=super.update(con,sql,params);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql, Object param) throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		int result=super.update(con,sql,param);
		JdbcUtils.releaseConnection(con);
		return result;
	}

	@Override
	public int update(String sql) throws SQLException {
		
		Connection con=JdbcUtils.getConnection();
		int result=super.update(con,sql);
		JdbcUtils.releaseConnection(con);
		return result;
	}
	
}
