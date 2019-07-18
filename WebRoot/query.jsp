<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>This is advanced search page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<h3 align="center">Advanced Search</h3>
<form action="<c:url value='/CustomerServlet' />" method="get">
<input type="hidden" name="method" value="query"/>
	<table border="0" align="center" width="800px" style="padding-left:220px">
		<tr>
			<td width="100px">Customer Name</td>
			<td>
				<input type="text" name="cname" />
			</td>
		</tr>
		<tr>
			<td>Customer Gender</td>
			<td>
				<select name="gender">
					<option value="">===Please select===</option>
					<option value="male">Male</option>
					<option value="female">Female</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>Cell Phone</td>
			<td>
				<input type="text" name="cellphone" />
			</td>
		</tr>
		<tr>
			<td>Email Address</td>
			<td>
				<input type="text" name="email" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<input type="submit" value="Search" />
				<input type="reset" value="Reset" />
			</td>
		</tr>
	</table>
</form>
  </body>
</html>
