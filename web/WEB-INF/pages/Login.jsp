<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Login</jsp:attribute>
	
	<jsp:attribute name="head">
		<style>
			fieldset {
			    width: 35%;
			    display: inline-block;
			    padding: 2%;
			    margin: 2%;
			}
			input {
			    text-indent: .8em;
			    line-height: 1.6em;
			    padding: 2px .3em;
			    border-color: #f0f0f0;
			    border-radius: 2px;
			    border-width: 1px;
			    border-style: solid;
			    position: relative;
			    display: inline-block;
			    width: 12.4em;
			    overflow: visible;
			    font-size: 100%;
			    margin: 3px;
			}
		</style>
	</jsp:attribute>
	
	<jsp:body>
		<c:if test="${!empty cachedMessages}">
    		<c:forEach var="cachedMessage" items="${cachedMessages}">
    			<p class="message">${cachedMessage}</p>
    		</c:forEach>
    		<br />
    	</c:if>
    	
		<fieldset>
			<legend>Login with existing account</legend>
			
			<form method=POST action="j_security_check" >
			    <input type="text"  name= "j_username" >
			    <br />
			    <input type="password"  name= "j_password" >
			    <br />
			    
			    <button type="submit" class="cardButton">Login</button>
			</form>
		</fieldset>
		
		<fieldset>
			<legend>Register a new account</legend>
			
			<form method=POST action="<c:url value='/Register'/>" >
			    <input title="Must be between 4-25 characters" type="text"  name="user_id" >
			    <br />
			    <input title="Must be between 4-35 charcters" type="password"  name="pw" >
			    <br />
			    
			    <button type="submit" class="cardButton">Register</button>
			</form>
		</fieldset>
		
	</jsp:body>
</t:genericpage>