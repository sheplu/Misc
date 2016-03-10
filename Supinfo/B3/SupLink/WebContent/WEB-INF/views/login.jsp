<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SupLink - Login</title>
<link href="/SupLink/assets/css/style.css" rel="stylesheet"  type="text/css" />
</head>
<body>
	<%@ include file="/WEB-INF/includes/nav.jsp"%>
	<h1>Login</h1>

	<form action="login" method="post">

		<fieldset>
			<legend>Identification</legend>

			<label for="mail">Mail : </label> <input id="mail"
				name="mail" type="email" placeholder="Mail" />
			<label for="password">Password : </label> <input id="password"
				name="password" type="password" placeholder="Password" />
			<input type="submit">
		</fieldset>

	</form>

	<%@ include file="/WEB-INF/includes/footer.jsp"%>
</body>
</html>