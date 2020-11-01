var userT=false;
var nameT=false;
var passwordT=false;
var mailT=false;
var proT=false;
var city_correct = false;
var province_correct = false;

function checkEmail(){
    
	$("#E-mailInfo").text("");
    var str = $("#E-mail").val(); 
    var pattern = /[\w]*[@][\w]*[\.][com|net|gov]/;
	
    if($("#E-mail").val()==""){
		$("#E-mailInfo").css("color","red");
        $("#E-mailInfo").text("电子邮箱不能为空");
    }else{
        if(pattern.test(str)) {
            	mailT=true;
				$("#E-mailInfo").css("color","#4CAF50");
				$("#E-mailInfo").text("电子邮件地址合法");
        } else {
        	mailT=false;
			$("#E-mailInfo").css("color","red");
			$("#E-mailInfo").text("电子邮件地址非法");
        }
    }
    
    
}
function checkRegister(){
    
    
        var userName=document.getElementById("userName").value;
        var flag="0";
        $.ajax({
            type: "post",
            url: "ajaxRegisterCheck.do",
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            data: {"userName":userName,"flag":flag},
            dataType: "json",
            success: function (response) {
            	
                if(response.code == 0){
                	userT=true;
                	console.log("console.log12324"); 
					$("#userNameInfo").css("color","#4CAF50");
					$("#userNameInfo").text("用户名合法");
                }
                else{
                	userT=false;
                	console.log(response); 
					$("#userNameInfo").css("color","red");
					$("#userNameInfo").text("用户名已存在");
                }
                console.log(response.code+"(("+response.info);
				
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
            	 alert(XMLHttpRequest.status);
            	 alert(XMLHttpRequest.readyState);
            	 alert(textStatus);
            }
            
        });
    
    
}
function checkUsername(){
    var str = $("#userName").val(); 
    var pattern = /^[a-zA_Z][a-zA-Z0-9]{4,15}$/;
    console.log(str); 
    if(str.length==0){
		$("#userNameInfo").css("color","red");
        $("#userNameInfo").text("用户名不能为空");
    }else{
        if(pattern.test(str)) {
        	checkRegister();
        } else {
			//用户名只能使用英文字母和数字，以字母开头，长度为4到15个字符
			$("#userNameInfo").css("color","red");
            $("#userNameInfo").text("用户名不合法");
            userT=false;
        }
    }
    
    
}
function checkName(){
    
    var str = $("#name").val(); 
    var pattern = /^[\u4e00-\u9fa5]{2,5}$/;
	console.log(str);
    if(str.length==0){
		$("nameInfo").css("color","red");
        $("nameInfo").text("真实姓名不能为空");
    }else{
        if(pattern.test(str)) {
            
            $("nameInfo").css("color","#4CAF50");
            $("nameInfo").text("真实姓名合法");
            nameT=true;
        } else {
			//真实姓名只能是2-5长度的中文
			$("nameInfo").css("color","red");
			$("nameInfo").text("真实姓名不合法");
        	nameT=false;
        }
    }
    
    
}

function checkPassword(){
    
    var str = $("#password").val(); 
    var pattern =/^{.{3,}$/;
	console.log(str);
    if(str.length==0){
		$("passwordInfo").css("color","red");
        $("passwordInfo").text("密码不能为空");
    }else{
        if(pattern.test(str)) {
			$("passwordInfo").css("color","red");
			$("passwordInfo").text("密码最小长度为3");
        	
        } else {
        	$("passwordInfo").css("color","#4CAF50");
        	$("passwordInfo").text("密码合法");
            
        }
    }
    
    
}

function recheckedPassword(){
    
    var str1 = $("#checkedPassword").val(); 
    var str2 = $("#password").val();
    
    
    if(str1!=str2||str1.length<3){
    	passwordT=false;
		$("passwordInfo").css("color","red");
		$("passwordInfo").text("密码不一致");
    	
    }
        
    else {
        $("passwordInfo").css("color","#4CAF50");
        $("passwordInfo").text("密码合法");
        passwordT=true;
    }
    
}






function fillProvince(){
    $.ajax({
        type: "post",
        url: "getProvinceCity.do",
        data: {},
        dataType: "json",
        success: function (response) {
            var provinceElement=document.getElementById("province");
            console.log("console.log");
            
            provinceElement.options.length=0;
           
            provinceElement.add(new Option("请选择省份",""));
            
            for(var index=0;index<response.length;index++){
                provinceElement.add(new Option(response[index].provinceName,response[index].provinceCode));
            }
        }
    });
}
function register(){
	console.log(userT); 
	console.log(nameT); 
	console.log(passwordT); 
	console.log(mailT); 
	console.log(province_correct);
	console.log(city_correct);
    if(userT&&nameT&&passwordT&&mailT&&province_correct&&city_correct){
        var flag = "1";
		var userName=document.getElementById("userName").value;
		var password=document.getElementById("password").value;
		var name=document.getElementById("name").value;
		var mail=document.getElementById("E-mail").value;
		var province=document.getElementById("province").value;
		var city=document.getElementById("city").value;
        $.ajax({
            type: "post",
            url: "ajaxRegisterCheck.do",
            data: {"userName":userName,"password":password,"name":name,"E-mail":mail,"provinceCode":province,"cityCode":city,"flag":flag},
            dataType: "json",
            success: function (response) {
				
                if(response.register==0){
                	console.log("注册成功"); 
					alert("注册成功");
                	window.location.href="login.html";
                }else{
					console.log(response.register);
					console.log("注册失败"); 
					alert("注册失败");
				}
                
                    
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
           	 alert(XMLHttpRequest.status);
           	 alert(XMLHttpRequest.readyState);
           	 alert(textStatus);
           }
        });
    }else{
    	
    }
    
}

$(document).ready(function(f){
//    alert("alert");
	fillProvince();
    $("#province").change(function(e){
        $("#city").empty();
        $("#city").append($("<option>").val("").text("请选择城市"));
        if($(this).val()=="请选择城市"){
            $("#provinceError").css("color","red");
            $("#provinceError").text("省份不能为空");
            return;
        }
        province_correct=true;
        proT=true;
        $("#provinceError").text("");
        var provinceCode=$("#province").val();
        $.ajax({
            type: "post",
            url: "getProvinceCity.do",
            data: {provinceCode:provinceCode},
            dataType: "json",
            success: function (response) {
                for(var index=0;index<response.length;index++){
                    var option =$("<option>").val(response[index].cityCode).text(response[index].cityName);
                    $("#city").append(option);
                }
            }
        });
    });
	
	$("#city").blur(function(e) {
	        if ($("#city").val() == "") {
	            $("#cityError").css("color", "red");
	            $("#cityError").text("城市不能为空");
	        } else {
	            $("#cityError").text("");
	            city_correct = true;
	        }
	    });
	
	$("#province").blur(function(e) {
	    if ($(this).val() == "") {
	        $("#provinceError").css("color", "red");
	        $("#provinceError").text("省份不能为空");
	    } else {
	        $("#provinceError").text("");
	        province_correct = true;
	    }
	});
	
	$("#button").click(function(){
		register();
	});
	
	
	$("#name").blur(checkName);
	$("#password").blur(checkPassword);
	$("#checkedPassword").blur(recheckedPassword);
});