<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" href="css/register.css">
</head>
<body>
<!--引入头部-->
<jsp:include page="header.jsp"></jsp:include>
<!-- 头部 end -->
<div class="rg_layout">
    <div class="rg_form clearfix">
        <%--左侧--%>
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <!--注册表单-->
            <form id="registerForm" action="${pageContext.request.contextPath}/user" method="post">
                <!--提交处理请求的标识符-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;width: 558px">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号">
							<span id="userInfo" style="font-size:10px"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="telephone">手机号</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="telephone" name="telephone" placeholder="请输入您的手机号">
                            <span id="telephoneInfo" style="font-size:10px"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="password" id="password" name="password" placeholder="请输入密码">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="smsCode">验证码</label>
                        </td>
                        <td class="td_right check">
                            <input type="text" id="smsCode" name="smsCode" class="check" placeholder="请输入验证码">
                            <input type="button" id="sendSmsCode" value="发送手机验证码" class="btn btn-link"/>
                        </td>
                        <script>
                            $('#sendSmsCode').click(function () {
                                let telephone = $('#telephone').val();
                                let url = '${pageContext.request.contextPath}/user';
                                let data = 'action=sendSms&telephone=' + telephone;
                                $.post(url,data,function (resp) {
                                    //提示一下
                                    alert("发送成功");
                                });
                                countDown(this);
                            });
                            //60秒倒计时
                            let num = 60;
                            function countDown(obj) {
                                if (num == 0){
                                    $(obj).prop("disabled",false);
                                    $(obj).val('重新发送');
                                    num = 60;
                                }else {
                                    num --;
                                    $(obj).prop("disabled",true);// 按钮不可用
                                    $(obj).val(`(\${num})秒后，重新发送`);
                                    setTimeout(function () {
                                        countDown(obj);
                                    },1000)
                                }
                            }
                        </script>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <input type="submit" class="submit" value="注册">
                            <span id="msg" style="color: red;">${resultInfo.message}</span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <%--右侧--%>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="javascript:$('#loginBtn').click()">立即登录</a>
            </p>
        </div>
    </div>
</div>
<!--引入尾部-->
<jsp:include page="footer.jsp"></jsp:include>

<script>
    $('#username').blur(function () {
        let username = $(this).val();
        let url = '${pageContext.request.contextPath}/user';
        let data = 'action=findByUsername&username=' + username;
        $.post(url,data,function (resp) {
            if (!resp.success){
                $('#userInfo').html(`<font color="red">\${resp.message}\</font>`);
            }else {
                $('#userInfo').html(`<font color="green">\${resp.message}\</font>`);
            }
        })
    });
    $('#telephone').blur(function () {
        let telephone = $(this).val();
        let url = '${pageContext.request.contextPath}/user';
        let data = 'action=findByTelephone&telephone=' + telephone;
        $.post(url,data,function (resp) {
            if (!resp.success){
                $('#telephoneInfo').html(`<font color="red">\${resp.message}\</font>`);
            }else {
                $('#telephoneInfo').html(`<font color="green">\${resp.message}\</font>`);
            }
        })
    });

</script>
</body>
</html>
