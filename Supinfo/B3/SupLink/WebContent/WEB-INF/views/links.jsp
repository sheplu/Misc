<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>SupLink - Links</title>
<link href="/SupLink/assets/css/style.css" rel="stylesheet"  type="text/css" />
</head>
<body>

	<%@ include file="/WEB-INF/includes/nav.jsp"%>

	<form action="links" method="post">
		<fieldset>
			<legend>Link</legend>

			<label for="name">Name : </label> <input id="name"
				name="name" type="text" placeholder="Name" />
			<label for="url">Url : </label> <input id="url"
				name="url" type="text" placeholder="Url" />
			<input type="submit">
		</fieldset>
	</form>

	<table>
		<thead>
			<tr>
				<th>Name</th>
				<th>Original Url</th>
				<th>Shortened Url</th>
				<th>Clicks</th>
				<th>Created</th>
				<th>State</th>
				<th>Delete</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${links}" var="p">
				<tr>
						<td>${ p.name }</td>
						<td>${ p.url }</td>
						<td>${ p.shortened }</td>
						<td></td>
						<td>${ p.date }</td>
						<td>
							<a href="/SupLink/auth/links/${ p.id }/${ p.state }">${ p.state }</a>
						</td>
						<td>
							<a href="/SupLink/auth/links/delete/${ p.id }">Delete</a>
						</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<%@ include file="/WEB-INF/includes/footer.jsp"%>


</body>
</html>