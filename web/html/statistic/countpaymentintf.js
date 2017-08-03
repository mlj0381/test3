var MyApp = angular.module('MyApp', [ 'commonMenuApp' ]);

$(function () {
	$.ajax({
		type : "GET",
		url : "userDateInfo.ajax?cmd=countPaymentIntf",
		data : "type=1",//{username:$("#username").val(), content:$("#content").val()}
		dataType : "json",
		async : false,
		success : function(result) {
			$('#container').highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: false
		        },
		        title: {
		            text: result.CountTile
		        },
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                }
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: '鍗犳湁鐜?,
		            data: [
		                [result.NotTip, Number(result.NotPercentage)],
		                {
		                    name: result.Tip,
		                    y: Number(result.Percentage),
		                    sliced: true,
		                    selected: true
		                }
		            ]
		        }]
		    });
		}
	});
});				