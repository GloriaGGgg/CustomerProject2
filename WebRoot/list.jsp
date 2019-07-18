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
    
    <title>This is the list for all the customers.</title>
    
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
	<h3 align="center">Customer List</h3>
	<table border="1" width="70%" align="center">
		<tr>
			<th>Customer Name</th>
			<th>Customer Gender</th>
			<th>Birthday</th>
			<th>Cell Phone</th>
			<th>Email Address</th>
			<th>Description</th>
			<th>Operations</th>
		</tr>
		<c:forEach items="${pb.beanList }" var="cstm">
		<tr>
			<th>${cstm.cname }</th>
			<th>${cstm.gender }</th>
			<th>${cstm.birthday }</th>
			<th>${cstm.cellphone }</th>
			<th>${cstm.email }</th>
			<th>${cstm.description }</th>
			<th>
				<a href="<c:url value='/CustomerServlet?method=preEdit&cid=${cstm.cid }' />">Edit</a>
				<a href="<c:url value='/CustomerServlet?method=delete&cid=${cstm.cid }' />">Delete</a>
			</th>
		</tr>
		</c:forEach>
	</table>
<br/>
<center>
<h3>Now <strong>${pb.pc }</strong> / Total <strong>${pb.tp }</strong> Pages </h3>
	<a href="${pb.url }&pc=1">First</a>
	<c:if test="${pb.pc>1 }">
	<a href="${pb.url }&pc=${pb.pc-1 }">Previous</a>
	</c:if>
	<c:choose>
	<%--如果页数不足10，那么显示所有页数 --%>
	<c:when test="${pb.tp<=10 }">
		<c:set var="begin" value="1"/>
		<c:set var="end" value="${pb.tp }"/>
	</c:when>
	<c:otherwise>
	<%--如果页数大于10，通过公式计算出begin和end --%>
		<c:set var="begin" value="${pb.pc-5 }"/>
		<c:set var="end" value="${pb.pc+4 }"/>
		<%--头溢出，即当begin小于1时，第一个数还是1；大于1时，整个页码数向左移动,右面的页码数也要对应移动 --%>
		<c:if test="${begin<1 }">
			<c:set var="begin" value="1"/>
			<c:set var="end" value="10"/>
		</c:if>
		<%--尾溢出，即当end大于整个页码数，就把end设置为整个页码 --%>
		<c:if test="${end>pb.tp }">
			<c:set var="begin" value="${pb.tp-9 }"/>
			<c:set var="end" value="${pb.tp }"/>
		</c:if>
	</c:otherwise>
</c:choose>
<%--循环遍历页码列表 --%>
<c:forEach var="i"  begin="${begin }"  end="${end }">
	<c:choose>
		<c:when test="${i eq pb.pc }">
			[${i }]
		</c:when>
		<c:otherwise>
			<a href="${pb.url }&pc=${i }">[${i }]</a>
		</c:otherwise>
	</c:choose>
</c:forEach>
	<c:if test="${pb.pc<pb.tp }">
	<a href="${pb.url }&pc=${pb.pc+1 }">Next</a>
	</c:if>
	<a href="${pb.url }&pc=${pb.tp }">Last</a>
</center>
  </body>
</html>
