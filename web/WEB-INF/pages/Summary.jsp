<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="hh" uri="http://www.outlawsource.net/web/htmlhelper" %>

<t:genericpage>
	<jsp:attribute name="title">Market Summary</jsp:attribute>
	
	<jsp:attribute name="head">
		<script>
			window.globalRoutes = {
					getLatestPriceUrl: '<c:url value="/Resources/GetLatestPrice"/>'
			};
		</script>
      <script src="<c:url value="/res/Scripts/Summary.js"/>"></script>
    </jsp:attribute>

	<jsp:body>
			<p class="priceUpdateText"></p>
			
        	<h2>Resources</h2>
        	<hr>
        	
        	<div class="cardSet">        	
	        	<c:forEach var="resource" items="${resources}">
	        		<div class="itemCard">
	        			<div class="table">
		        			<div class="topRow">
		        				<img src="<c:url value='/Images/${resource.machineName}.png'/>">
		        				<div class="title"><a href="<c:url value='/Resources/Chart/${resource.machineName}'/>">${resource.displayName}</a></div>
		        			</div>
		        			<div class="bottomRow">
		        				<div class="price">${hh:FormatCurrency(resource.currentMarketPrice)}</div>
			        			<div class="details">
			        				<div class="topRow">
			        					<p>Avg: ${hh:FormatCurrency(resource.averagePrice)}</p>
			        				</div>
			        				<div class="bottomRow">
			        					<p>${hh:FormatPercent(resource.currentMarketPrice - resource.averagePrice, resource.averagePrice)}</p>
			        				</div>	
			        			</div>
		        			</div>
		       			</div>	
	        		</div>      		
	        	</c:forEach>
        	</div>
        	
        	<h2>Products</h2>
        	<hr>
        	
        	<div class="cardSet">        	
	        	<c:forEach var="product" items="${products}">
	        		<div class="itemCard">
	        			<div class="table">
		        			<div class="topRow">
		        				<img src="<c:url value='/Images/${product.machineName}.png'/>">
		        				<div class="title"><a href="<c:url value='/Resources/Chart/${product.machineName}'/>">${product.displayName}</a></div>
		        			</div>
		        			<div class="bottomRow">
		        				<div class="price">${hh:FormatCurrency(product.currentMarketPrice)}</div>
			        			<div class="details">
			        				<div class="topRow">			        					
			        					<p>FC: ${hh:FormatCurrency(product.rawMaterialPrice)}</p>
			        					<p>Avg: ${hh:FormatCurrency(product.averagePrice)}</p>
			        				</div>
			        				<div class="bottomRow">			        					
			        					<p>${hh:FormatPercent(product.currentMarketPrice - product.rawMaterialPrice, product.rawMaterialPrice)}</p>
			        					<p>${hh:FormatPercent(product.currentMarketPrice - product.averagePrice, product.averagePrice)}</p>
			        				</div>	
			        			</div>
		        			</div>
		       			</div>	
	        		</div>      		
	        	</c:forEach>
        	</div>
        	
        	<h2>Loot</h2>
        	<hr>
        	
        	<div class="cardSet">        	
	        	<c:forEach var="lootItem" items="${loot}">
	        		<div class="itemCard">
	        			<div class="table">
		        			<div class="topRow">
		        				<img src="<c:url value='/Images/${lootItem.machineName}.png'/>">
		        				<div class="title"><a href="<c:url value='/Resources/Chart/${lootItem.machineName}'/>">${lootItem.displayName}</a></div>
		        			</div>
		        			<div class="bottomRow">
		        				<div class="price">${hh:FormatCurrency(lootItem.currentMarketPrice)}</div>
			        			<div class="details">
			        				<div class="topRow">
			        					<p>RY: ${hh:FormatCurrency(lootItem.recycleYieldPrice)}</p>
			        					<p>Avg: ${hh:FormatCurrency(lootItem.averagePrice)}</p>			        					
			        				</div>
			        				<div class="bottomRow">			        					
			        					<p>
			        						<c:choose>
			        							<c:when test="${lootItem.recycleYieldPrice > 0}">
			        								${hh:FormatPercent(lootItem.recycleYieldPrice - lootItem.currentMarketPrice, lootItem.currentMarketPrice)}
			        							</c:when>
			        							<c:otherwise>
			        								-
			        							</c:otherwise>
			        						</c:choose>
			        					</p>
			        					<p>${hh:FormatPercent(lootItem.currentMarketPrice - lootItem.averagePrice, lootItem.averagePrice)}</p>
			        				</div>	
			        			</div>
		        			</div>
		       			</div>	
	        		</div>      		
	        	</c:forEach>
        	</div>
        	
        	<h2>Units</h2>
        	<hr>
        	
        	<div class="cardSet">        	
	        	<c:forEach var="unit" items="${units}">
	        		<div class="itemCard">
	        			<div class="table">
		        			<div class="topRow">
		        				<img src="<c:url value='/Images/${unit.machineName}.png'/>">
		        				<div class="title"><a href="<c:url value='/Resources/Chart/${unit.machineName}'/>">${unit.displayName}</a></div>
		        			</div>
		        			<div class="bottomRow">
		        				<div class="price">${hh:FormatCurrency(unit.currentMarketPrice)}</div>
			        			<div class="details">
			        				<div class="topRow">
			        					<p>Train: ${hh:FormatCurrency(unit.rawMaterialPrice)}</p>
			        					<p>Avg: ${hh:FormatCurrency(unit.averagePrice)}</p>			        					
			        				</div>
			        				<div class="bottomRow">			        					
			        					<p>${hh:FormatPercent(unit.currentMarketPrice - unit.rawMaterialPrice, unit.rawMaterialPrice)}</p>
			        					<p>${hh:FormatPercent(unit.currentMarketPrice - unit.averagePrice, unit.averagePrice)}</p>
			        				</div>	
			        			</div>
		        			</div>
		       			</div>	
	        		</div>      		
	        	</c:forEach>
        	</div>
        	
        	<div id="sideMenu">
				<p class="priceUpdateText"></p>
			</div>
    </jsp:body>
</t:genericpage>