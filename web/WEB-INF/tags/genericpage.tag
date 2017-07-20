<%@tag description="Overall Page Template" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
           
<%@attribute name="title" fragment="true" %>
<%@attribute name="head" fragment="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="shortcut icon" href="<c:url value='/res/favicon.ico'/>" />
		<title>OutlawSource - <jsp:invoke fragment="title"/></title>
			
		<script src="<c:url value='/res/Scripts/jquery-1.12.0.js'/>"></script>		
		<script src="<c:url value='/res/Scripts/jquery-ui/jquery-ui.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/res/Scripts/jquery-ui/jquery-ui.css'/>" />		
		<script src="<c:url value='/res/Scripts/js.cookie.js'/>"></script>
		
		<script type="text/javascript">
			window.baseSiteUrl = '${pageContext.request.contextPath}';
		</script>
		<script src="<c:url value='/res/Scripts/Global.js'/>"></script>		
		<link rel="stylesheet" href="<c:url value='/res/Styles/Site.css'/>" />		
		<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
		<link href='https://fonts.googleapis.com/css?family=Roboto+Condensed' rel='stylesheet' type='text/css'>
		
		<jsp:invoke fragment="head"/>
		
		<script type="text/javascript">
			window.onload = function() {
				document.getElementById("sidebar-toggle").addEventListener('click', toggleMobileSidebar());
				document.getElementById("main").addEventListener('click', closeMobileSidebar());
				document.getElementById("mobile-sidebar").style.display = "none";
			}
			function toggleMobileSidebar() {	
				var mobileSidebar = document.getElementById("mobile-sidebar");
				if(mobileSidebar.style.display == "none") {
					mobileSidebar.style.display = "";
				}
				else {
					mobileSidebar.style.display = "none";
				}
			}
			function closeMobileSidebar() {
				var mobileSidebar = document.getElementById("mobile-sidebar");
				mobileSidebar.style.display = "none";
			}
		</script>
	</head>
  <body>
    <div id="menu">
    	<div class="non-mobile">
    		<a class="menu-link" href="<c:url value="/About"/>">About</a>
    		<div class="menu-dropdown">
    			<a class="menu-link" href="<c:url value="/Resources/Hub"/>">Resources</a>
    			<div class="dropdown-content">
    				<a class="menu-link" href="<c:url value="/Resources/Summary"/>">Summary</a>
    				<a class="menu-link" href="<c:url value="/Resources/Chart"/>">Chart</a>
    				<a class="menu-link" href="<c:url value="/Resources/Alert"/>">Alerts</a>
    				<a class="menu-link" href="<c:url value="/Resources/Stats"/>">Stats</a>
    				
    			</div>
    		</div>
    	</div>
    	
    	<div class="mobile">
    		<div id="sidebar-toggle" onclick="toggleMobileSidebar()">Menu</div>
    	</div>
    	
	    <c:if test="${!empty user}">
	    	<span>Logged in as ${user.userId} | <a href="<c:url value='/Logout'/>">Logout</a></span>
	    </c:if>
	    <span class="site-title"><a href="<c:url value="/"/>">OutlawSource</a></span>
	</div>
	
	<div id="mobile-sidebar" class="mobile">
		<p>Resources<p>
		<ul>
			<li><a class="menu-link" href="<c:url value="/Resources/Summary"/>">Summary</a></li>
			<li><a class="menu-link" href="<c:url value="/Resources/Chart"/>">Chart</a></li>
			<li><a class="menu-link" href="<c:url value="/Resources/Alert"/>">Alerts</a></li>
			<li><a class="menu-link" href="<c:url value="/Resources/Stats"/>">Stats</a></li>
		</ul>
	</div> 
	
	<div id="banner">
		<c:if test="${!empty messages}">
    		<c:forEach var="message" items="${messages}">
    			<p class="message">${message}</p>
    		</c:forEach>
    		<br />
    	</c:if>
	</div>
	
    <div id="main">	
    	<h1><jsp:invoke fragment="title"/></h1>
    	
    	<br />
    	
    	<div id="serverDialog" title="Server Messages"></div>
    	
    	<jsp:doBody/>
    </div>
    
    <span id="footer">
		v1.1
	</span>
  </body>
</html>