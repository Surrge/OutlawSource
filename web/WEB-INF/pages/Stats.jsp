<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="hh" uri="http://www.outlawsource.net/web/htmlhelper" %>

<t:genericpage>
	<jsp:attribute name="title">Game Stats</jsp:attribute>
	
	<jsp:attribute name="head">
		<script src="<c:url value='/res/Scripts/FactoryTables.js'/>"></script>
		<script src="<c:url value='/res/Scripts/MineTables.js'/>"></script>
		
		<style>
			#mineTable .longInput {
				width: 90px;
			}
			#mineTable td:nth-child(3),
			#mineTable td:nth-child(5) {
			    border-right: 1px solid black;
			}
			#factoryTable td:nth-child(2),
			#factoryTable td:nth-child(4) {
			    border-right: 1px solid black;
			}
		</style>
	</jsp:attribute>
	
	<jsp:body>
		<h2>Your Mines</h2>
		<p>All values assume 100% quality unteched/non-HQ mines, multiply accordingly</p>
       	<hr>
       	
       	<table id="mineTable" class="displayTable"></table>
       	
		<h2>Your Factories</h2>
       	<hr>
       	
       	<table id="factoryTable" class="displayTable"></table>
       	
       	<script type="text/javascript">
       		$(window).load(function() {
       			GetUserMines($("#mineTable"));
       			GetUserFactories($("#factoryTable"));       			
       		});
       	</script>
	</jsp:body>
</t:genericpage>