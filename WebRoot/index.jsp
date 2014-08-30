<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	

<script language="javascript">
	var filePath;
	function getFullPath(obj){
		if(obj){
			if(window.navigator.userAgent.indexOf("MSIE")>=1){
				obj.select();
				filePath= document.selection.createRange().text;
			}else if(window.navigator.userAgent.indexOf("Firefox")>=1){
				if(obj.files){
					filePath= obj.files.item(0).getAsDataURL();
				}
				filePath= obj.value;
			}
		}
		return filePath;
	}
</script>
</head>
  
  <body>
  
    <input type="file"  onchange="getFullPath(this);"/><br>
    
    <input type="button" name="getData" value="存入Excel数据" 
    onclick="window.location.href='add_equips?fileName='"+filePath+""/>
    
    <button onclick="window.location.href='equip_list'">得到数据</button>
    <s:property value="fileName"/>
    <s:debug></s:debug>
    <table>
    <tr>
	<td>名称</td>
	<td>类型</td>
	<td>单位</td>
	<td>数量</td>
	<td>日期</td>
	</tr>
    <s:iterator value="equips">
    	<tr>
    	<td><s:property value="name"/></td>
    	<td><s:property value="type"/></td>
    	<td><s:property value="unit"/></td>
    	<td><s:property value="number"/></td>
    	<td><s:property value="date"/></td>
    	</tr>
    </s:iterator>
    </table>
  </body>
</html>
