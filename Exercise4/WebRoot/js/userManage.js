var total; //总的用户数量
var pageSize = "10"; //每页的记录数量
var pageNumber = "1"; //页码
var pageCount; //总页数
var sort = "userName"; //用户名排序
var sortOrder = "desc"; //排序方式

$(document).ready(function() {
	reload(); //加载网页页面

	$("tbody").on("mouseover", "tr", function() {
		$(this).addClass('tr_hover'); ////通过jQuery控制实现鼠标悬停上的背景色
	});
	$("tbody").on("mouseout", "tr", function() {
		$(this).removeClass('tr_hover'); //通过jQuery控制实现鼠标悬停上的背景色
	});
	//tbody中被选中的行变色
	$("tbody").on("click", " tr input:checkbox", function() {
		if (this.checked == true) {
			$(this).parents("tr").addClass('tr_select');
		} else {
			$(this).parents("tr").removeClass('tr_select');
		}
	});
	//表格全选
	$("#checkAll").click(function() {
		if (this.checked == true) {
			$("tbody tr input:checkbox").prop("checked", true);
			$("tbody tr").addClass('tr_select');
		} else {
			$("tbody tr input:checkbox").prop("checked", false);
			$("tbody tr").removeClass('tr_select');
		}
	});

	//上下页翻页
	$("#firstPage,#prePage,#nextPage,#lastPage").click(function() {
		var id = $(this).attr("id");

		if (id == "firstPage") {
			pageNumber = 1;
		} else
		if (id == "prePage" && pageNumber>1) {
			pageNumber--;
		} else
		if (id == "nextPage" && pageNumber<pageCount) {
			pageNumber++;
		} else
		if (id == "lastPage") {
			pageNumber = pageCount;
		}
		
		if (pageNumber != 0 && pageNumber <= pageCount) {
			pageNumber = pageNumber.toString();
			reload();
		}
	});

	$("#pageSize").change(function() {
		reload(); //选取页面大小后重加载
	});

	//条件的模糊查询
	$("#btSearch").click(function() {
		reload(); //重新加载页面
	});

	//清除输入框 重加载
	$("#btClear").click(function() {
		document.getElementById("searchForm").reset();
		reload(); //重新加载页面
	});

	//删除记录
	$("#btDelete").click(function() {
		var len = $('tbody tr input:checkbox:checked');
		if(len.length==0){
			alert("至少选择一项");
			return;
		}
		var vals = [];//定义数组
		
		$('tbody tr input:checkbox:checked').each(function(index,item){
			vals.push($(this).val());//循环选择将复选框的value值加入到数组中

		});
		$.ajax({
		    type: "post",
		    url: "deleteUser.do",
		    data: { ids: vals.join(",") }, //每个userName间用,隔开
		    dataType: "json",
		    success: function(response) {
		        alert(response.info);
		        if (response.code == 0) {
		            reload();
		        }
		    }
		});
	});

	//绑定动态生成的表格中每行的删除按钮事件
	$('table').on('click', '#btnDel', function() {
	    var userName = $(this).attr("value");
	    $.ajax({
	        type: "post",
	        url: "deleteUser.do",
	        data: { ids: userName },
	        dataType: "json",
	        success: function(response) {
	            alert(response.info);
	            if (response.code == 0) {
	                reload();
	            }
	        }
	    });
	});
	
	
	//修改记录
	$("#btUpdate").click(function() {
		var len = $('tbody tr input:checkbox:checked').length;
		if (len == 0) {
		    alert("请先选择一项");
		    return;
		}
		if (len != 1) {
		    alert("只能选择一项");
		    return;
		}
		$("#action1").text("修改");
		$("#action").val("update");
		var rowData = $('tbody tr input:checkbox:checked').parents("tr").attr("data");//表中选中的那一行的数据
		var data = JSON.parse(rowData);//将选中行的数据转换成json格式
		//填表格
		$("#registerForm").find(':input').each(function() {
		    $(this).val(data[$(this).attr('id')]);
			console.log(this.value);
		});
		fillCity(data['cityCode']);
		$("#checkedPassword").val(data['password']);
		$("#userName").attr("readonly", true);
		userT=true;
		
		
		$("#name").blur(checkName);
		$("#password").blur(checkPassword);
		$("#checkedPassword").blur(recheckedPassword);
		
		ShowDiv('MyDiv', 'fade');
	});

	//增加记录
	$("#btAdd").click(function() {
		$("#action1").text("新增");
		$("#action").val("insert");
		userT=false;
		nameT=false;
		passwordT=false;
		mailT=false;
		proT=false;
		city_correct = false;
		province_correct = false;
		
		$("#name").blur(checkName);
		$("#password").blur(checkPassword);
		$("#checkedPassword").blur(recheckedPassword);
		
		ShowDiv('MyDiv', 'fade');
	});

});
//加载页面
function reload() {
	var queryParams = new Object(); //实例化用户查询参数对象
	var array = $("#searchForm").serializeArray(); //输入框的格式转换
	$.each(array, function(index, element) {
		//遍历数组得到键值对存入参数对象中
		queryParams[element.name] = element.value;
	});

	var pageParams = new Object(); //页面查询参数
	pageSize = $("#pageSize").find("option:selected").text(); //得到用户选择的页面大小
	pageParams.pageSize = pageSize;
	pageParams.pageNumber = pageNumber;
	pageParams.sort = sort;
	pageParams.sortOrder = sortOrder;

	var queryData = new Object(); //整个页面总的查询参数
	//格式转换
	queryData.queryParams = JSON.stringify(queryParams);
	queryData.pageParams = JSON.stringify(pageParams);

	$.ajax({
		type: "post",
		url: "getUser.do",
		data: queryData,
		dataType: "json",
		success: function(response) {
			var rows = response.rows;
			total = response.total;
			pageCount = Math.ceil(total / pageSize); //计算页数  ceil向上取整
			$("#total").text(total); //传值
			$("#pageCount").text(pageCount); //传值
			$("#pageNumber").text(pageNumber);//传页数
			$("tbody").empty(); //清空表格
			$.each(rows, function(index, row) {
				var s = JSON.stringify(row);
				var str = "<tr data='" + s + "'>";
				str = str + '<td><input type="checkbox" value=' + row.userName + ' /></td>';
				str = str + '<td>' + row.userName + '</td>';
				str = str + '<td>' + row.chrName + '</td>';
				str = str + '<td>' + row.mail + '</td>';
				str = str + '<td>' + row.provinceName + '</td>';
				str = str + ' <td>' + row.cityName + '</td>';
				str = str + ' <td><a href = "#" id="btnDel" value=' + row.userName + '>删除</a> ';
				str = str + '<a href="#" id="btnUpdate">修改</a></td>';
				str = str + ' </tr>';
				$("tbody").append(str);
			});
			//根据行数奇偶不同设置不同颜色便于区分
			$('tbody tr:even').addClass('tr_even'); //偶数行
			$('tbody tr:odd').addClass('tr_odd'); //奇数行
		}
	});
}
	
//弹出隐藏层
function ShowDiv(show_div, bg_div) {
    document.getElementById(show_div).style.display = "block";
    document.getElementById(bg_div).style.display = "block";
	
    //设置背景层高宽
    // var bgdiv = document.getElementById(bg_div);
    // bgdiv.style.width = $(window).width();
    // $("#" + bg_div).height($(window).height());

    //将弹出层居中
    var windowHeight = $(window).height(); //当前窗口高度
    var windowWidth = $(window).width(); //当前窗口宽度
    var popupHeight = $("#" + show_div).height(); //设置弹出层高度
    var popupWeight = $("#" + show_div).width(); //设置弹出层宽度
    var posiTop = (windowHeight - popupHeight) / 2;
    var posiLeft = (windowWidth - popupWeight) / 2;
    $("#" + show_div).css({ "left": posiLeft + "px", "top": posiTop + "px", "display": "block" }); 
}

//关闭弹出层
function CloseDiv(show_div, bg_div) {
    document.getElementById(show_div).style.display = "none";
    document.getElementById(bg_div).style.display = "none";
}

