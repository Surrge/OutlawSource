$(window).load(function() {
	// Set Upper Menu Highlight
	var currentUrl = window.location.pathname;
	$("#upper_menu a").each(function() {
		if($(this).attr("href") == currentUrl) {
			$(this).addClass("currentMenuItem");
		}
	});
	
	// Set Side Menu Display
	DisplaySideMenu($(window).scrollTop());
	$(window).scroll(function() { 
		DisplaySideMenu($(this).scrollTop()); 
    });
	
	// Set Message Dialog
	$("#serverDialog").dialog({
		autoOpen: false,
		modal: true,
		buttons: {
			Ok: function() {
				$(this).dialog('close');
			}
		},
		show: {
			effect: "fold",
			duration: 200
		}
	});
	
	// Allow tooltips
	$(document).tooltip({
		content: function () {
            return $(this).prop('title');
        },
        track: true
	});
	
	// google analytics
	(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
		(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
		m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
		})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

	ga('create', 'UA-102997452-1', 'auto');
	ga('send', 'pageview');
});

function DisplaySideMenu(scrollTop) {
	if(scrollTop > 150) {
		$("#sideMenu").css('visibility','visible');   
        $("#sideMenu").fadeIn('slow');  
	}
	else {
		$("#sideMenu").fadeOut("slow"); 
	}
}

function DisplayServerMessages(messages) {
	var dialog = $("#serverDialog");
	dialog.html(messages.join($("<br>")));
	dialog.dialog('open');
}

function FormatCurrency(number) {
	return FormatNumber(number, true, true);
}
function FormatNumber(number, currency, colorCoding) {
	var numberStr = number.toString();
	var len = numberStr.length;
	
	switch (len) {
		case 5:
			numberStr = (numberStr / 1000).toFixed(1) + "<span class='abbreviation'>K</span>";
			break;
		case 6:
			numberStr = (numberStr / 1000).toFixed(0) + "<span class='abbreviation'>K</span>";
			break;
		case 7:
			numberStr = (numberStr /1000000).toFixed(2) + "<span class='abbreviation'>M</span>";
			break;
		case 8:
			numberStr = (numberStr /1000000).toFixed(1) + "<span class='abbreviation'>M</span>";
			break;
		case 9:
			numberStr = (numberStr /1000000).toFixed(0) + "<span class='abbreviation'>M</span>";
			break;
		case 10:
			numberStr = (numberStr / 1000000000).toFixed(2) + "<span class='abbreviation'>B</span>";
			break;
		case 11:
			numberStr = (numberStr /1000000000).toFixed(1) + "<span class='abbreviation'>B</span>";
			break;
		case 12:
			numberStr = (numberStr /1000000000).toFixed(0) + "<span class='abbreviation'>B</span>";
			break;
		case 13:
			numberStr = (numberStr / 1000000000000).toFixed(2) + "<span class='abbreviation'>T</span>";
			break;
		case 14:
			numberStr = (numberStr /1000000000000).toFixed(1) + "<span class='abbreviation'>T</span>";
			break;
		case 15:
			numberStr = (numberStr /1000000000000).toFixed(0) + "<span class='abbreviation'>T</span>";
			break;
	}
	
	if(currency) {
		numberStr = "$" + numberStr;
	}
	
	if(colorCoding) {
		numberStr = (number > 0)
			? "<span class='positive'>" + numberStr + "</span>"
			: "<span class='negative'>" + numberStr + "</span>";
	}

	return numberStr;
}
function FormatHours(hours) {
	if(hours <= 0) {
		return "<span class='negative'>Never</span>";
	}
	else if (hours > 100) {
		return "<span class='days'>" + Math.round(hours / 24) + "</span><span class='abbreviation'>D</span> <span class='hours'>" + pad(Math.round(hours % 24)) + "</span><span class='abbreviation'>H</span>";
	}
	else {
		return "<span class='hours'>" + Math.round(hours) + "</span><span class='abbreviation'>H<span>";
	}
}

function pad(n) {
    return (n < 10) ? ("0" + n) : n;
}