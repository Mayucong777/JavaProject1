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
	<h2 style="text-align:center; font-color='white'" >资源下载</h2>
			<c:forEach items="${downloadList}" var="download" varStatus="message">
				<div class="margin">
					<p>${download.name}</p>
					<img src=${download.image} style="height: 49px; width: 60px; ">
					${download.description}
					<form action="DownloadController" method="get" name="postform">
						<button name="path" value="${download.path}" id="path" class="button">点击下载</button>
					</form>
				</div>
			</c:forEach>
</body>
</html>