var MyApp = angular.module('MyApp', [ 'commonMenuApp' ]);
$(function() {
	$.ajax({
		type : "GET",
		url : "visit.ajax?cmd=queryOrders",
		data : "type=1",//{username:$("#username").val(), content:$("#content").val()}
		dataType : "json",
		async : false,
		success : function(data) {
			$('#container').highcharts({
				chart : {
					type : 'column',
					margin : 75,
					options3d : {
						enabled : true,
						alpha : 10,
						beta : 25,
						depth : 70
					}
				},
				title : {
					text : '璁㈠崟缁熻锲捐〃'
				},
				subtitle : {
					text : '镙规嵁链堜唤缁熻璁㈠崟镐绘暟'
				},
				plotOptions : {
					column : {
						depth : 25
					}
				},
				xAxis : {
					categories : [ '1链?, '2链?, '3链?, '4链?, '5链?, '6链?,
							'7链?, '8链?, '9链?, '10链?, '11链?, '12链? ]
				},
				yAxis : {
					opposite : false
				},
				series : [ {
					name : '璁㈠崟鏁?,
					data : data.items
				} ]
			});
		}
	});
});