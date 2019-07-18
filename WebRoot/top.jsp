<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<base target="main" />
    <base href="<%=basePath%>">
    
    <title>My JSP 'top.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
	a:link {
		color:blue;
	}
	a:hover {
		color:red;
	}
</style>	
  </head>
  
  
  <body style="text-align:center;">
  <br/>
<h1>Customer Relationship Management System</h1>
<a href="<c:url value='/add.jsp' />">Add Customer</a>
<a href="<c:url value='/CustomerServlet?method=find' /> ">Search All Customers</a> 
<a href="<c:url value='/query.jsp' />">Advanced Search</a>
  </body>
</html>
