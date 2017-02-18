<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Unauthorized (401)</jsp:attribute>
	
	<jsp:body>
		<p>You lack the required permissions to access this page.</p>
		<div class="card_menu">
			<a href="<c:url value="/"/>" class="card">Return To Hub</a>
		</div>
	</jsp:body>
</t:genericpage>