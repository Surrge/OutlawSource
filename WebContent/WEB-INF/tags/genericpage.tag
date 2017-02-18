<%@tag description="Overall Page Template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
<%@attribute name="title" fragment="true" %>
<%@attribute name="head" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="<c:url value='/favicon.ico'/>" />
		<title>OutlawSource - <jsp:invoke fragment="title"/></title>
			
		<script src="<c:url value='/Scripts/jquery-1.12.0.js'/>"></script>		
		<script src="<c:url value='/Scripts/jquery-ui/jquery-ui.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/Scripts/jquery-ui/jquery-ui.css'/>" />		
		<script src="<c:url value='/Scripts/js.cookie.js'/>"></script>
		
		<script type="text/javascript">
			window.baseSiteUrl = '${pageContext.request.contextPath}';
		</script>
		<script src="<c:url value='/Scripts/Global.js'/>"></script>		
		<link rel="stylesheet" href="<c:url value='/Styles/Site.css'/>" />		
		<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'>
		
		<jsp:invoke fragment="head"/>
	</head>
  <body>
    <div id="upper_menu">
    	<p id="site_banner">Welcome to OutlawSource</p>
	    <a href="<c:url value="/Resources/Hub"/>" class="link">Hub</a>
	    <a href="<c:url value="/Resources/Summary"/>" class="link">Summary</a>
	    <a href="<c:url value="/Resources/Chart"/>" class="link">Chart</a>
	    <a href="<c:url value="/Resources/Alert"/>" class="link">Alerts</a>
	    <a href="<c:url value="/Resources/Stats"/>" class="link">Stats</a>
	    <c:if test="${!empty user}">
	    	<p id="user_banner">Logged in as ${user.userId} | <a href="<c:url value='/Logout'/>">Logout</a></p>
	    </c:if>
	</div>
	
    <div id="main">	
    	<h1><jsp:invoke fragment="title"/></h1>
    	
    	<br />
    	
    	<c:if test="${!empty messages}">
    		<c:forEach var="message" items="${messages}">
    			<p class="message">${message}</p>
    		</c:forEach>
    		<br />
    	</c:if>
    	
    	<div id="serverDialog" title="Server Messages"></div>
    	
    	<jsp:doBody/>
    </div>
  </body>
</html>