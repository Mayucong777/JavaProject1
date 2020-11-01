
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>WTU下载网</title>
	<link rel="stylesheet" href="../css/main.css"/>
</head>
<body>
<ul>
  <li style="background-color:white; borader:left;"><img src="../imgs/logo.png"/>&nbsp;</li>
  <li><a class="active" href="#home">首页</a></li>
  <li><a href="GetDownloadListController">资源下载</a></li>
  <li><a href="userManage.jsp">用户管理</a></li>
  <li><a href="resourseManage.jsp">资源管理</a></li>
  <li><a href="PersonalCenter.jsp">个人中心</a></li>
  <li style="float:right"><a href="LogoutController">安全退出</a></li>
  <li style="float:right"><a href="#about">当前用户：${currentUser.chrName} </a></li>
</ul>
<HR style="border:3 double #987cb9" width="100%" color=#987cb9 SIZE=3>
<div id="container" >
  <img src="../imgs/background.jpg" width="80%" height="auto"  />
</div>
</body>
</html>