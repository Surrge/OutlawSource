<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Not Found (404)</jsp:attribute>
	
	<jsp:body>
		<p>Page Not Found</p>
		<div class="card_menu">
			<a href="<c:url value="/"/>" class="card">Return To Hub</a>
		</div>
	</jsp:body>
</t:genericpage>