<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/webbase.css">
    <link rel="stylesheet" href="css/pages-seckillOrder.css">
    <title>个人信息</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp"%>

<div class="container-fluid">
    <!--header-->
    <div id="account">
        <div class="py-container">
            <div class="yui3-g home">
                <!--左侧列表-->
                <%@include file="home_left.jsp"%>
                <!--右侧主内容-->
                <div class="yui3-u-5-6 order-pay">
                    <div class="body userInfo">
                        <ul class="sui-nav nav-tabs nav-large nav-primary ">
                            <li class="active"><a href="#one" data-toggle="tab">基本资料</a></li>
                            <li><a href="#two" data-toggle="tab">头像照片</a></li>
                        </ul>
                        <form action="" method="post" enctype="multipart/form-data">
                            <div class="tab-content ">
                                <div id="one" class="tab-pane active">
                                    <div class="sui-form form-horizontal">
                                        <div class="control-group">
                                            <label for="inputName" class="control-label">昵称：</label>
                                            <div class="controls">
                                                <input type="text" id="inputName" name="nickname" placeholder="昵称">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label">性别：</label>
                                            <div class="controls">

                                                <input type="radio" name="sex" value="1"><b>男</b>
                                                &nbsp;&nbsp;
                                                <input type="radio" name="sex" value="0"><b>女</b>
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label">生日：</label>
                                            <div class="controls">
                                                <input type="text" name="birthday" placeholder="生日">
                                            </div>
                                        </div>
                                        <div class="control-group">
                                            <label class="control-label">邮箱：</label>
                                            <div class="controls">
                                                <input type="text" name="email" placeholder="邮箱">
                                            </div>
                                        </div>
                                        <div class="control-group">

                                            <div class="controls">
                                                <button type="submit" class="sui-btn btn-primary">更新</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="two" class="tab-pane">

                                    <div class="new-photo">
                                        <p>当前头像：</p>
                                        <div class="upload">
                                            <img id="imgShow_WU_FILE_0" width="100" height="100"
                                                 src="img/_/photo_icon.png"
                                                 alt="">
                                            <input type="file" id="up_img_WU_FILE_0" name="pic"/>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </form>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<!--引入尾部-->
<%@include file="footer.jsp"%>

</body>
</html>
