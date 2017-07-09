<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:genericpage>
	<jsp:attribute name="title">Price Chart</jsp:attribute>
	
	<jsp:attribute name="head">
      <script src="<c:url value="/Scripts/highcharts.js"/>"></script>
	  <script src="<c:url value="/Scripts/data.js"/>"></script>
	  <!-- <script src="Content/exporting.js"></script> -->
	  <script src="<c:url value="/Scripts/highcharts_dark.js"/>"></script>
      <script src="<c:url value="/Scripts/PriceChart.js"/>"></script>
    </jsp:attribute>

	<jsp:body>
		<select id="combobox">
			<option selected="selected">Loading...</option>
		</select>
		<br> <br> 
		<input id="refreshCheck" type="checkbox" checked><a
				title="every 60 seconds">Auto-Refresh</a>
		<input id="historicalCheck" type="checkbox"><a
				title="full results, but broken zoom">Historical</a>
		<input id="resourceCheck" type="checkbox"><a
				title="uses current market values for resources">Add Factory Cost</a>
		<input id="recycleCheck" type="checkbox"><a
				title="uses current market values for lv10 recycle results">Add Recycle Yield</a>
		<br>
		<br>
		<p id="resourceCalc" style="text-align: center; margin: auto"></p> 
		<p id="recycleCalc" style="text-align: center; margin: auto"></p> 
		<br>
		
		<div id="priceChart"></div>
    </jsp:body>
</t:genericpage>