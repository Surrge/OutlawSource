<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:masterPage>
	<jsp:attribute name="title">Home</jsp:attribute>
	
	<jsp:body>
		<p>You are being redirected to the <a href="<c:url value='/Resources/Hub'/>">Resources Hub</a>
	</jsp:body>
</t:masterPage>