<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>资源下载</title>
<link rel="stylesheet" href="../css/download.css"/>
</head>
<body>
	<div class="div1">
		<p><font size="30" color="black">资源下载</font></p>
	</div>
	<div class="line"></div>
	
	<c:forEach items="${downloadList}" var="download" varStatus="downloadvst">
		<table style="width: 50%;border-radius: 20px;" align="center" bgcolor="white" border="3" cellspacing="3" frame="void" rules="none">
			<!--
			<caption>定义表格的标题</caption>
			-->
			<tr align="left">
				<th rowspan="1" colspan="10" style="height: 5;"><font size="5px">&nbsp;&nbsp;${download.name}</font></th>
				<th rowspan="3" colspan="3" style="width: 10%;">
					<form action="DownloadController" method="get" name="postform">
						<button name="path" value="${download.path}" id="path" class="button">点击下载</button>
					</form>
				</th>
			</tr>
			<tr>
				<td rowspan="2" colspan="2" align="center" style="width: 10%;"><img src="${download.image}" style="width: 80px;height: 100px"></td>
				<td rowspan="1" colspan="8" align="left" style="width: 80%; width: 3;">${download.description}</td>
			</tr>
			<tr align="left">
				<td rowspan="1" colspan="8" align="left" style="height: 3;">
					时间：${download.time}&nbsp;&nbsp;
					大小：${download.size}KB&nbsp;&nbsp;
					星级：<div class="star">
							<span style="width: ${download.star/5*100}%;"></span>
						  </div>
				</td>
			</tr>
		</table>
		<div class="div2"></div>
	</c:forEach>
</body>
</html>