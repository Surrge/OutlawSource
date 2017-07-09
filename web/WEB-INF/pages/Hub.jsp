<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<t:genericpage>
	<jsp:attribute name="title">Resources Hub</jsp:attribute>
	
	<jsp:attribute name="head">
      <style>
      	#changelist {
      		padding-left:10px;
      	}
      	#changelist td:nth-child(odd) {
      		font-weight: bold;
      		color: yellow;      		
      	}
      	#changelist td:nth-child(even) {
      		padding-left: 5px;
      	}
      </style>
    </jsp:attribute>

	<jsp:body>
		<div class="card_menu">
			<a href="<c:url value="/Resources/Summary"/>" class="card">Market Summary</a>
	        <a href="<c:url value="/Resources/Chart"/>" class="card">Price Chart</a>
	        <a href="<c:url value="/Resources/Alerts"/>" class="card">Item Alerts</a>
	        <a href="<c:url value="/Resources/Stats"/>" class="card">Game Stats</a>
		</div>        
        
        <br><br><hr>
        <h3>Game Links</h3>
        <a href="http://www.resources-game.ch/en">Official Resources Site</a>
        <br>
        <a href="http://resources-game.wikia.com/wiki/Resources-game_Wikia">Resources Wiki</a>
        <br>
      	<a href="https://plus.google.com/u/0/communities/110102596899585849307">Resources G+ Community</a>
      	      	
      	<br><br><hr>
      	<h3><a href="<c:url value="/changelist.txt"/>" style="color:white">Changelist</a></h3>
      	
      	<table id="changelist"></table>
      	
      	<script>
	      	$(document).ready(function() {
	      		$.ajax({
	      			url : "<c:url value="/changelist.txt"/>",
	      			type : "GET",
	      			dataType : "json",
	      			success : function(file) {
	      				window.test = file;
	      				PopulateChangelist(file);
	      			},
	      			error : function(file) {
	      				$('<tr>')
	      					.append($('<td>').text("If you see this it means I suck at JSON"))
	      					.appendTo('#changelist');
	      			}
	      		});
	      	});
	      	
	      	function PopulateChangelist(json) {
	      		$.each(json, function(i, date) {
	      			$('<tr>').append(
	      					$('<td>').text(date.date), 
	      					$('<td>').text(date.items[0]))
     						.appendTo('#changelist');
	      			
	      			for(var i = 1; i < date.items.length; i++) {
	      				$('<tr>').append(
	      						$('<td>'), 
	      						$('<td>').text(date.items[i]))
	      						.appendTo('#changelist');
	      			}
	      		});
	      	}
      	</script>
    </jsp:body>
</t:genericpage>