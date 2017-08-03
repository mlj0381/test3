var MyApp = angular.module('MyApp', [ 'commonMenuApp' ]);

$(function () {
	$.ajax({
		type : "GET",
		url : "cars.ajax?cmd=countCyMatchInfo",
		data : "type=1",
		dataType : "json",
		async : false,
		success : function(result) {
			var colors = Highcharts.getOptions().colors,
			categories = result.CountCategories,
			name = result.CountName,
			data = result.datas;
			var firstLevelData = [];// 绗竴灞傜骇鏁版嵁
			var secondLevelData = [];// 绗簩灞傜骇鏁版嵁
			for (var i = 0; i < data.length; i++) {
				firstLevelData.push({ 
					name: categories[i],
					y: data[i].y,
					color: data[i].color
				});
				for (var j = 0; j < data[i].drilldown.data.length; j++) {
					var brightness = 0.2 - (j / data[i].drilldown.data.length) / 5 ;
					secondLevelData.push({
						name: data[i].drilldown.categories[j],
						y: data[i].drilldown.data[j],
						color: Highcharts.Color(data[i].color).brighten(brightness).get()
					});
				}
			}
			// Create the chart
			$('#container').highcharts({
				chart: {
					type: 'pie'
				},
				title: {
					text: result.CountTip
				},
				yAxis: {
					title: {
						text: 'Total percent market share'
					}
				},
				plotOptions: {
					pie: {
						shadow: false,
						center: ['50%', '50%']
					}
				},
				tooltip: {
					valueSuffix: '%'
				},
				series: [{
					name: result.FirstLevelName,
					data: firstLevelData,
					size: '60%',
					dataLabels: {
						formatter: function() {
							return this.y > 5 ? this.point.name : null;
						},
						color: 'white',
						distance: -30
					}
				}, {
					name: result.SecondLevelName,
					data: secondLevelData,
					size: '80%',
					innerSize: '60%',
					dataLabels: {
						formatter: function() {
							return this.y > 1 ? '<b>'+ this.point.name +':</b> '+ this.y +'%'  : null;
						}
					}
				}]
			});
		}
	});
});		