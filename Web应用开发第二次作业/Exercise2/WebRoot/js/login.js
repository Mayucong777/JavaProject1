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
                alert("鐢ㄦ埛鍚嶄笉鑳戒负绌�");
                form.action="";
                return;
            }
            if(password.value==""){
                alert("瀵嗙爜涓嶈兘涓虹┖");
                form.action="";
                return;
            }
            if(Code.value==""){
                alert("楠岃瘉鐮佷笉鑳戒负绌�");
                form.action="";
                return;
            }
}