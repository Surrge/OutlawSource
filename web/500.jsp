<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page isErrorPage="true"%>

<t:genericpage>
	<jsp:attribute name="title">Server Error (500)</jsp:attribute>

	<jsp:body>
		<p>Opps, the server encountered an error.</p>
		<table width="100%" border="1">
			<tr valign="top">
				<td width="40%"><b>Error:</b></td>
				<td>${pageContext.exception}</td>
			</tr>
			<tr valign="top">
				<td><b>URI:</b></td>
				<td>${pageContext.errorData.requestURI}</td>
			</tr>
			<tr valign="top">
				<td><b>Status code:</b></td>
				<td>${pageContext.errorData.statusCode}</td>
			</tr>
			<tr valign="top">
				<td><b>Stack trace:</b></td>
				<td>
					<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
						<p>${trace}</p>
					</c:forEach>
				</td>
			</tr>
		</table>

		<div class="card_menu">
			<a href='<c:url value="/"/>' class="card">Return To Hub</a>
		</div>
	</jsp:body>
</t:genericpage>