<%@page import="org.franken.message.sql.SqlOperate"%>
<%@page import="org.franken.message.sql.UserDomain"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>旅行笔记</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="refresh" content="5">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
    <font color="#ff0000" size="24">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;旅行笔记&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;微信墙<br></font>
  	<%
         List<UserDomain> list=SqlOperate.readContent();
         Iterator<UserDomain> iterator=list.iterator();
         while(iterator.hasNext()){
         UserDomain domain=(UserDomain)iterator.next();
         if(domain.getHeadImageUrl() != null) {
         out.print("<img src="+domain.getHeadImageUrl()+" width='120' height='120' />");
         }
         out.print("<font size='12'>"+domain.getUsername()+": "+domain.getContent()+"</br></font>");
         }
     %>
  </body>
</html>
