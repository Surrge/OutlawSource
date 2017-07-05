<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Forbidden (404)</jsp:attribute>
	
	<jsp:body>
		<p>You do not have the proper permissions to access this page</p>
		<div class="card_menu">
			<a href="<c:url value="/"/>" class="card">Return To Hub</a>
		</div>
	</jsp:body>
</t:genericpage>