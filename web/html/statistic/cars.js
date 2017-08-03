var MyApp = angular.module('MyApp', [ 'commonMenuApp' ]);

// Your app's root module...
var plot;
$(function() {
	monthRedraw();
});

function monthRedraw() {
	var visits = [ [ 1, 50 ], [ 2, 40 ], [ 3, 45 ], [ 4, 23 ], [ 5, 55 ], [ 6, 65 ], [ 7, 61 ], [ 8, 70 ], 
	               [ 9, 65 ], [ 10, 75 ], [ 11, 57 ], [ 12, 59 ] ];
	$.ajax({
		type : "GET",
		url : "visit.ajax?cmd=queryGoodsAndCars",
		data : "type=2",// {username:$("#username").val(), content:$("#content").val()}
		dataType : "json",
		async : false,
		success : function(data) {
			visits = (data.items);
			plot = $.plot($("#statsChart"), [ {
				data : visits,
				label : "鎴戠殑鍙戣溅缁熻"
			} ], {
				series : {
					lines : {
						show : true,
						lineWidth : 1,
						fill : true,
						fillColor : {
							colors : [ {
								opacity : 0.1
							}, {
								opacity : 0.13
							} ]
						}
					},
					points : {
						show : true,
						lineWidth : 2,
						radius : 3
					},
					shadowSize : 0,
					stack : true
				},
				grid : {
					hoverable : true,
					clickable : true,
					tickColor : "#f9f9f9",
					borderWidth : 0
				},
				legend : {
					// show: false
					labelBoxBorderColor : "#fff"
				},
				colors : [ "#30a0eb", "#30a0eb" ],
				xaxis : {
					ticks : [ [ 1, "1链? ], [ 2, "2链? ], [ 3, "3链? ], [ 4, "4链? ], [ 5, "5链? ], [ 6, "6链? ], 
					          [ 7, "7链? ], [ 8, "8链? ], [ 9, "9链? ], [ 10, "10链? ], [ 11, "11链? ], [ 12, "12链? ] ],
					font : {
						size : 12,
						family : "Open Sans, Arial",
						variant : "small-caps",
						color : "#697695"
					}
				},
				yaxis : {
					ticks : 3,
					tickDecimals : 0,
					font : {
						size : 12,
						color : "#9da3a9"
					}
				}
			});
		}
	});

	function showTooltip(x, y, contents) {
		$('<div id="tooltip">' + contents + '</div>').css({
			position : 'absolute',
			display : 'none',
			top : y - 30,
			left : x - 50,
			color : "#fff",
			padding : '2px 5px',
			'border-radius' : '6px',
			'background-color' : '#000',
			opacity : 0.80
		}).appendTo("body").fadeIn(200);
	}

	var previousPoint = null;
	$("#statsChart").bind("plothover", function(event, pos, item) {
		if (item) {
			if (previousPoint != item.dataIndex) {
				previousPoint = item.dataIndex;

				$("#tooltip").remove();
				var x = item.datapoint[0].toFixed(0), y = item.datapoint[1]
						.toFixed(0);

				var month = item.series.xaxis.ticks[item.dataIndex].label;

				showTooltip(item.pageX, item.pageY,
						item.series.label + " -- " + month
								+ ": " + y + "鏉?);
			}
		} else {
			$("#tooltip").remove();
			previousPoint = null;
		}
	});
}

function dayRedraw() {
	var visits;

	$.ajax({
		type : "GET",
		url : "visit.ajax?cmd=queryGoodsAndCars",
		data : "type=3",// {username:$("#username").val(),
						// content:$("#content").val()}
		dataType : "json",
		async : false,
		success : function(data) {
			visits = (data.items);
		}
	});
	plot = $.plot($("#statsChart"), [ {
		data : visits,
		label : "鎴戠殑鍙戣溅缁熻"
	} ], {
		series : {
			lines : {
				show : true,
				lineWidth : 1,
				fill : true,
				fillColor : {
					colors : [ {
						opacity : 0.1
					}, {
						opacity : 0.13
					} ]
				}
			},
			points : {
				show : true,
				lineWidth : 2,
				radius : 3
			},
			shadowSize : 0,
			stack : true
		},
		grid : {
			hoverable : true,
			clickable : true,
			tickColor : "#f9f9f9",
			borderWidth : 0
		},
		legend : {
			// show: false
			labelBoxBorderColor : "#fff"
		},
		colors : [ "#30a0eb", "#30a0eb" ],
		xaxis : {
			ticks : [ [ 1, "1镞? ], [ 2, "2镞? ], [ 3, "3镞? ], [ 4, "4镞? ],
					[ 5, "5镞? ], [ 6, "6镞? ], [ 7, "7镞? ], [ 8, "8镞? ],
					[ 9, "9镞? ], [ 10, "10镞? ], [ 11, "11镞? ], [ 12, "12镞? ],
					[ 13, "13镞? ], [ 14, "14镞? ], [ 15, "15镞? ], [ 16, "16镞? ],
					[ 17, "17镞? ], [ 18, "18镞? ], [ 19, "19镞? ], [ 20, "0镞? ],
					[ 21, "21镞? ], [ 22, "22镞? ], [ 23, "23镞? ], [ 24, "24镞? ],
					[ 25, "25镞? ], [ 26, "26镞? ], [ 27, "27镞? ], [ 28, "28镞? ],
					[ 29, "29镞? ], [ 30, "30镞? ], [ 31, "31镞? ] ],
			font : {
				size : 12,
				family : "Open Sans, Arial",
				variant : "small-caps",
				color : "#697695"
			}
		},
		yaxis : {
			ticks : 3,
			tickDecimals : 0,
			font : {
				size : 12,
				color : "#9da3a9"
			}
		}
	});
}