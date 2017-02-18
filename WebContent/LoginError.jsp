<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Login Error</jsp:attribute>
	
	<jsp:body>
		<p>Wrong Username or Password</p>
		<a href="javascript:window.history.back();">Try Again :(</a>
	</jsp:body>
</t:genericpage>