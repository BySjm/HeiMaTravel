<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="css/webbase.css">
    <link rel="stylesheet" type="text/css" href="css/pages-getOrderInfo.css">

    <title>结算页</title>

</head>
<body>
<!--引入头部-->
<%@include file="header.jsp" %>

<div class="container-fluid">
    <form action="${pageContext.request.contextPath}/cart?action=subOrder" method="post">
        <!-- 头部 end -->
        <div class="cart py-container">
            <!--主内容-->
            <div class="checkout py-container">
                <div class="step-tit">

                    <h4 style="font-weight: bold">填写并核对订单信息</h4>
                </div>
                <div class="checkout-steps">
                    <!--收件人信息-->
                    <div class="step-tit">
                        <h5>收件人信息</h5>
                    </div>
                    <div class="step-cont">
                        <div class="addressInfo">
                            <ul class="addr-detail">
                                <li class="addr-item">
                                    <div>
                                        <c:forEach items="${addressList}" var="address">
                                            <div class="con address">
                                                <input type="radio" name="addressId" value="${address.aid}" <c:if test="${address.isDefault == 1}">checked</c:if>> ${address.contact}
                                                    ${address.address} <span>${address.telephone}</span>
                                            </div>
                                        </c:forEach>
                                    </div>
                                </li>
                            </ul>
                        </div>
                        <div class="hr"></div>

                    </div>
                    <div class="hr"></div>
                    <!--支付和送货-->
                    <div class="payshipInfo">
                        <div class="step-tit">
                            <h5>支付方式</h5>
                        </div>
                        <div class="step-cont">
                            <ul class="payType">
                                <li class="selected">微信付款</li>
                            </ul>
                        </div>
                        <div class="hr"></div>
                        <div class="step-tit">
                            <h5>送货清单</h5>
                        </div>
                        <div class="step-cont">
                            <ul class="send-detail">
                                <li>
                                    <div class="sendGoods">
                                        <c:forEach items="${cart.cartItems}" var="cartItem">
                                            <ul class="yui3-g">
                                                <li class="yui3-u-1-6">
                                                    <span><img src="${cartItem.route.rimage}"/></span>
                                                </li>
                                                <li class="yui3-u-7-12">
                                                    <div class="desc">${cartItem.route.rname}</div>
                                                    <div class="seven">7天无理由退货</div>
                                                </li>
                                                <li class="yui3-u-1-12">
                                                    <div class="price">￥${cartItem.route.price}</div>
                                                </li>
                                                <li class="yui3-u-1-12">
                                                    <div class="num">X${cartItem.num}</div>
                                                </li>
                                                <li class="yui3-u-1-12">
                                                    <div class="exit">有货</div>
                                                </li>
                                            </ul>
                                        </c:forEach>
                                    </div>
                                </li>
                                <li></li>
                                <li></li>
                            </ul>
                        </div>
                        <div class="hr"></div>
                    </div>
                </div>
            </div>
            <div class="clearfix trade">
                <div class="fc-price">
                    <span class="number">1</span>件商品，应付金额:　<span class="price">¥5399.00</span>
                </div>
            </div>
            <div class="submit">
                <button class="sui-btn btn-danger btn-xlarge">提交订单</button>
            </div>
        </div>
    </form>
</div>
<!-- 底部栏位 -->
<!--引入尾部-->
<%@include file="footer.jsp" %>
<script src="js/getOrderInfo.js"></script>
</body>
</html>
