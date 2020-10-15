function changeCode(){
            var codeImage=document.getElementById("verifyCode");
            codeImage.src="servlet/CreateVerifyCodeImage?t="+Math.random();
        }

        function emptyInput(){
            var username = document.getElementById("userName");
            var password = document.getElementById("password");
            var Code = document.getElementById("userCode");
            var form = document.getElementById("formLogin");
            if(username.value==""){
                alert("用户名不能为空");
                form.action="";
                return;
            }
            if(password.value==""){
                alert("密码不能为空");
                form.action="";
                return;
            }
            if(Code.value==""){
                alert("验证码不能为空");
                form.action="";
                return;
            }
}