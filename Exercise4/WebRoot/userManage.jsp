<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>用户管理</title>
		<link rel="stylesheet" type="text/css" href="css/userManage.css"/>
		<script src="js/jquery-3.5.1.min.js" type="text/javascript" ></script>
		<script src="js/userManage.js" type="text/javascript" charset="utf-8"></script>
		
	</head>
	<body>
		<div class="div1">
			<p><font size="6" color="black" >用户管理</font></p>
		</div>
		<div class="line"></div>
		<div class="page">


			<div class="pageHead">
				<form id="searchForm">
					<input type="text" name="userName" id="userName" placeholder="输入用户名" />
					<input type="text" name="chrName" id="chrName" placeholder="输入姓名" />
					<input type="text" name="provinceName" id="provinceName" placeholder="输入省份" />
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
		</div>
	</body>
</html>
