var MyApp = angular.module('MyApp', [ 'commonMenuApp' ]);
$(function() {
	$.ajax({
		type : "GET",
		url : "visit.ajax?cmd=queryUsers",
		data : "type=1",// {username:$("#username").val(), content:$("#content").val()}
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
					text : '鐢ㄦ埛缁熻锲捐〃'
				},
				subtitle : {
					text : '镙规嵁链堜唤缁熻鐢ㄦ埛镐绘暟'
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
					name : '鐢ㄦ埛鏁?,
					data : data.items
				} ]
			});
		}
	});
});