<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>测试页面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
</head>

<body>
	<form action="servlet/UserLoginServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type="text" name="userName" placeholder="请输入用户名" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="password" name="password" placeholder="请输入密码" required>
			</div>
			<br /> <input type="submit" value="登录">
		</center>
	</form>
	
	<form action="servlet/AdminLoginServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type="text" name="adminName" placeholder="请输入管理员账号" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="password" name="adminPassword" placeholder="请输入密码" required>
			</div>
			<br /> <input type="submit" value="登录">
		</center>
	</form>
	
	<form action="servlet/UserRegServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type="text" name="userName" placeholder="请输入用户名" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="password" name="password" placeholder="请输入密码" required>
			</div>
			<div class="inputWrap">
				<input type="text" name="phone" placeholder="请输入手机号" required>
			</div>
			<br /> <input type="submit" value="注册">
		</center>
	</form>
	<form action="servlet/MenuServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type="text" name="type" placeholder="请输入菜品类型，0为所有" autofocus
					required>
			</div>
			<br /> <input type="submit" value="查看菜单">
		</center>
	</form>
	<form action="servlet/MealAddServlet" method="post" enctype="multipart/form-data">
		<center>
			<div class="inputWrap">
				<input type="text" name="mealType" placeholder="请输入菜品类型" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="text" name="mealName" placeholder="请输入菜名" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="text" name="mealPrice" placeholder="请输入菜品价格" autofocus
					required>
			</div>
			<div class="inputWrap">
				<input type="file" name="mealImage" >
			</div>
			<div class="inputWrap">
				<input type="text" name="mealInfo" placeholder="请输入菜品信息" autofocus>
			</div>
			<br /> <input type="submit" value="添加菜品">
		</center>
	</form>
	<form action="servlet/NewOrderServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type=hidden name="order" autofocus
					required>
			</div>
			<br /> <input type="submit" value="提交订单">
		</center>
	</form>
	<form action="servlet/DBSyncServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type=hidden name="order" autofocus
					required>
			</div>
			<br /> <input type="submit" value="获取所有数据">
		</center>
	</form>
	<form action="servlet/MealModifyServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type=hidden name="order" autofocus
					required>
			</div>
			<br /> <input type="submit" value="修改菜品">
		</center>
	</form>
	<form action="servlet/MealDeleteServlet" method="post">
		<center>
			<div class="inputWrap">
				<input type=text name="meal_id" autofocus
					required>
			</div>
			<br /> <input type="submit" value="删除菜品">
		</center>
	</form>
</body>
</html>
