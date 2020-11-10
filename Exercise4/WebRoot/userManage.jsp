<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>用户管理</title>
		<link rel="stylesheet" type="text/css" href="css/userManage.css" />
		<link rel="stylesheet" type="text/css" href="css/register.css" />
		<script src="js/jquery-3.5.1.min.js" type="text/javascript"></script>
		<script src="js/userManage.js" type="text/javascript" charset="utf-8"></script>
		<script src="js/register.js?" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<div class="div1">
			<p>
				<font size="6" color="black">用户管理</font>
			</p>
		</div>
		<div class="line"></div>
		<div class="page">


			<div class="pageHead">
				<form id="searchForm">
					<input type="text" name="userName"  placeholder="输入用户名" />
					<input type="text" name="chrName" placeholder="输入姓名" />
					<input type="text" name="provinceName"  placeholder="输入省份" />
				</form>
				<div class="bt">
					<a href="#" id="btSearch">查找</a>
					<a href="#" id="btClear">清空</a>
					<a href="#" id="btAdd">增加</a>
					<a href="#" id="btDelete">删除</a>
					<a href="#" id="btUpdate">修改</a>
				</div>
			</div>

			<table>
				<thead>
					<tr>
						<th width="20"><input type="checkbox" id="checkAll" title="选择" /></th>
						<th width="140" class="bg" id="sortByuserName" data="userName">用户名</th>
						<th width="80">姓名</th>
						<th width="200">邮箱</th>
						<th width="80" class="bg" id="sortByprovinceName" data="provinceName">省份</th>
						<th width="80">城市</th>
						<th width="80">操作</th>
					</tr>
				</thead>
				<tbody>

				</tbody>

			</table>

			<div class="pageBottom">
				<div class="pageSize">每页
					<select id="pageSize">
						<option>5</option>
						<option selected="true">10</option>
						<option>20</option>
					</select>,共
					<span id="total"></span>条数据,
					<span id="pageNumber" style="color: red;"></span>页/<span id="pageCount"></span>页
				</div>
				<div class="pageNav">
					<a href="#firstPage" id="firstPage">首页</a>
					<a href="#prePage" id="prePage">上一页</a>
					<a href="#nextPage" id="nextPage">下一页</a>
					<a href="#lastPage" id="lastPage">尾页</a>
				</div>
			</div>

			<!--弹出层时背景层DIV-->
			<div id="fade" class="black_overlay" onclick="CloseDiv('MyDiv','fade')"></div>
			<div id="MyDiv" class="white_content">
				<div style="text-align: right; height: 20px;">
					<span style="font-size: 24px; cursor: pointer;" title="点击关闭" onclick="CloseDiv('MyDiv','fade')">×</span>
				</div>
				<div>
					<h2 style="text-align: center;" id="formTitle"><span id="action1"></span></h2>
					<form id="registerForm" class="registerForm">
						<input  id="action" name="action" type="text" hidden />
						<p>
							<img src="imgs/用户.png" width="30" height="30" alt=""/>
							<input type="text" placeholder="用户名" name="userName" id="userName" onblur="checkUsername()">
							<span class="auth_err" id="userNameInfo"></span>
						</p>
						<p>
							<img src="imgs/用户.png" width="30" height="30" alt=""/>
							<input type="text" placeholder="真实姓名" name="chrName" id="chrName" onblur="checkName()">
							<span class="auth_err" id="nameInfo"></span>
						</p>
						<p>
							<img src="imgs/邮件2.png" width="30" height="30" alt=""/>
							<input type="text" placeholder="邮箱" name="mail" id="mail" onblur="checkEmail()">
							<span class="auth_err" id="E-mailInfo"></span>
						</p>
						<p>
							<img src="imgs/城市2.png" width="30" height="30" alt=""/>
							<select name="provinceCode" id="provinceCode">
								<option value="">请选择省份</option>
							</select>
							<span class="auth_err" id="provinceError"> </span>
						</p>
						<p>
							<img src="imgs/城市2.png" width="30" height="30" alt=""/>
							<select name="cityCode" id="cityCode">
								<option value="">请选择城市</option>
							</select>
							<span class="auth_err" id="cityError"> </span>
						</p>
						<p>
							<img src="imgs/密码.png" width="30" height="30" alt=""/>
							<input type="password" placeholder="密码" name="password" id="password" onblur="checkPassword()">
							<span class="auth_err" id="passwordInfo"></span>
						</p>
						<p>
							<img src="imgs/密码.png" width="30" height="30" alt=""/>
							<input type="password" placeholder="确认密码" name="checkedPassword" id="checkedPassword" onblur="recheckedPassword()">
							<span class="auth_err" id="checkedPasswordInfo"></span>
						</p>
						<p>
							<span id="button">确定</span>
							<span class="auth_err"  id="checkError"></span>
						</p>
					</form>
				</div>
			</div>
	</body>
</html>
