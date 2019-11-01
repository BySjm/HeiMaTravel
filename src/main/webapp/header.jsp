<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--bootstrap--%>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="css/common.css">
<script src="js/jquery-3.3.1.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/getParameter.js"></script>

<!-- 头部 start -->
<header id="header">
    <%--广告--%>
    <div class="top_banner">
        <img src="images/top_banner.jpg" alt="">
    </div>
    <%--右侧按钮--%>
    <div class="shortcut">
        <c:if test="${empty user}">
            <!-- 未登录状态 -->
            <div class="login_out">
                <a id="loginBtn" data-toggle="modal" data-target="#loginModel" style="cursor: pointer;">登录</a>
                <a href="register.jsp" style="cursor: pointer;">注册</a>
            </div>
        </c:if>
        <c:if test="${not empty user}">
            <!-- 登录状态 -->
            <div class="login">
                <span>欢迎回来，${user.username}</span>
                <a href="${pageContext.request.contextPath}/user?action=userInfo" class="collection">个人中心</a>
                <a href="cart.jsp" class="collection">购物车</a>
                <a href="${pageContext.request.contextPath}/user?action=logout">退出</a>
            </div>
        </c:if>
    </div>
    <%--搜索框--%>
    <div class="header_wrap">
        <div class="topbar">
            <div class="logo">
                <a href="/"><img src="images/logo.jpg" alt=""></a>
            </div>
            <div class="search">
                <input id="rname" name="rname" type="text" placeholder="请输入路线名称" class="search_input" value="${rname}"
                       autocomplete="off">
                <a href="javascript:void(0);" onclick="searchClick()" class="search-button">搜索</a>
                <script>
                    function searchClick() {
                        let rname = $('#rname').val();
                        location.href = "${pageContext.request.contextPath}/route?action=findByPage&rname=" + rname;
                    }
                </script>
            </div>
            <div class="hottel">
                <div class="hot_pic">
                    <img src="images/hot_tel.jpg" alt="">
                </div>
                <div class="hot_tel">
                    <p class="hot_time">客服热线(9:00-6:00)</p>
                    <p class="hot_num">400-618-9090</p>
                </div>
            </div>
        </div>
    </div>
</header>
<!-- 头部 end -->
<!-- 首页导航 -->
<div class="navitem">
    <ul class="nav" id="categoryUI">
        <li class="nav-active"><a href="index.jsp">首页</a></li>

    </ul>
    <script>
        $(function () {
            let url = '${pageContext.request.contextPath}/category?action=findAll';
            $.get(url, function (resp) {
                for (let c of resp) {
                    $('#categoryUI').append(`<li><a href="${pageContext.request.contextPath}/route?action=findByPage&cid=\${c.cid}">\${c.cname}</a></li>`)
                }
            })
        })
    </script>
</div>
<!-- 登录模态框 -->
<div class="modal fade" id="loginModel" tabindex="-1" role="dialog" aria-labelledby="loginModelLable">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <%--头部--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="loginModelLable">
                    <ul id="myTab" class="nav nav-tabs" style="width: auto">
                        <li class="active">
                            <a href="#pwdReg" data-toggle="tab">
                                密码登录
                            </a>
                        </li>
                        <li><a href="#telReg" data-toggle="tab">短信登录</a></li>
                    </ul>
                    <span id="loginErrorMsg" style="color: red;"></span>
                </h4>

            </div>
            <%--内容--%>
            <div id="myTabContent" class="tab-content">
                <%--密码登录--%>
                <div class="tab-pane fade in active" id="pwdReg">
                    <form id="pwdLoginForm" action="#" method="post">
                        <input type="hidden" name="action" value="pwdLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>用户名</label>
                                <input type="text" class="form-control" id="login_username" name="username"
                                       placeholder="请输入用户名">
                            </div>
                            <div class="form-group">
                                <label>密码</label>
                                <input type="password" class="form-control" id="login_password" name="password"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="modal-footer">
                            <span id="login_info" style="color: red"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" id="pwdLogin" class="btn btn-primary" value="登录"/>
                        </div>
                    </form>
                    <script>
                        $('#pwdLogin').click(function () {
                            let data = $('#pwdLoginForm').serialize();
                            let url = '${pageContext.request.contextPath}/user';
                            $.post(url, data, function (resp) {
                                if (resp.success) {
                                    location.reload();
                                } else {
                                    $('#login_info').text(resp.message);
                                }
                            })
                        })
                    </script>
                </div>
                <%--短信登录--%>
                <div class="tab-pane fade" id="telReg">
                    <form id="telLoginForm" method="post" action="#">
                        <input type="hidden" name="action" value="telLogin">
                        <div class="modal-body">
                            <div class="form-group">
                                <label>手机号</label>
                                <input type="text" class="form-control" name="telephone" id="login_telephone"
                                       placeholder="请输入手机号">
                            </div>
                            <div class="form-group">
                                <label>手机验证码</label>
                                <input type="text" class="form-control" id="login_check" name="smsCode"
                                       placeholder="请输入手机验证码">
                            </div>
                            <%--                            <a href="javaScript:" id="login_sendSmsCode">发送手机验证码</a>--%>
                            <input class="btn btn-link" type="text" id="login_sendSmsCode" value="发送手机验证码">
                        </div>
                        <div class="modal-footer">
                            <span style="color: red" id="telLoginInfo"></span>
                            <input type="reset" class="btn btn-primary" value="重置">
                            <input type="button" class="btn btn-primary" id="telLogin" value="登录"/>
                        </div>
                    </form>
                    <script>
                        //发短信的
                        $('#login_sendSmsCode').click(function () {
                            let telephone = $('#login_telephone').val();
                            let url = '${pageContext.request.contextPath}/user';
                            let data = 'action=sendSms&telephone=' + telephone;
                            $.post(url, data, function (resp) {
                                if (resp.success) {
                                    alert("短信发送成功")
                                } else {
                                    alert("服务器繁忙...")
                                }
                            });
                            countDown(this);
                        });
                        //60秒倒计时
                        let nums = 60;

                        function countDown(obj) {
                            if (nums == 0) {
                                $(obj).prop('disabled', false);
                                $(obj).val('重新发送短信');
                                nums = 60;
                            } else {
                                nums--;
                                $(obj).prop('disabled', true);
                                $(obj).val(`\${num}秒后重新发送`)
                                setTimeout(function () {
                                    countDown(obj);
                                }, 1000)
                            }
                        }

                        //短信登录
                        $('#telLogin').click(function () {
                            let data = $('#telLoginForm').serialize();
                            let url = '${pageContext.request.contextPath}/user';
                            $.post(url, data, function (resp) {
                                if (resp.success) {
                                    location.reload();
                                } else {
                                    $('#telLoginInfo').text(resp.message);
                                }
                            })
                        });
                    </script>
                </div>
            </div>
        </div>
    </div>
</div>
