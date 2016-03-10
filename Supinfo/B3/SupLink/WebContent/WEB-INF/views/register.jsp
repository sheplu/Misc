<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SupLink - Register</title>
<link href="/SupLink/assets/css/style.css" rel="stylesheet"  type="text/css" />
</head>
<body>

	<%@ include file="/WEB-INF/includes/nav.jsp"%>

	<h1>Créer un nouveau compte</h1>

	<form action="register" method="post">

		<fieldset>
			<legend>Informations</legend>
			
			<label>Pseudo :<br /> <input name="pseudo" type="text"
				placeholder="Pseudo" />
			</label><br />
			<label>Mail :<br /> <input name="mail" type="email"
				placeholder="Mail" />
			</label><br />
			<label>Password :<br /> <input name="password" type="password"
				placeholder="Password" />
			</label><br />
			<label>Confirmation :<br /> <input name="confirm" type="password"
				placeholder="Confirmation password" />
			</label><br />
		</fieldset>

		<input type="submit" value="Ajouter" />

	</form>

	<%@ include file="/WEB-INF/includes/footer.jsp"%>


</body>
</html>