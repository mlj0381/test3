var mapShowApp = angular.module("mapShowApp", ['commonApp']);
mapShowApp.controller("mapShowCtrl", ["$scope","commonService","$timeout",function($scope,commonService,$timeout) {
	var mapShow={
		init:function(){
			this.bindEvent();
			$(function () {

			    Highcharts.setOptions({
			        lang:{
			            drillUpText:"返回 > {series.name}"
			        }
			    });

			    var data = Highcharts.geojson(Highcharts.maps['countries/cn/custom/cn-all-china']),small = $('#container').width()<400;
			    console.info(data);
			    // 给城市设置随机数据
			    $.each(data, function (i) {
			        this.drilldown = this.properties['drill-key'];
			        console.info(this.drilldown);
			        this.value = i;
			    });
			    //初始化地图
			    $('#container').highcharts('Map', {

			        chart : {
								spacingBottom:30,
							 
			            events: {
			                drilldown: function (e) {
			                	console.info(e);
			                    if (!e.seriesOptions) {
			                        var chart = this;
			                        var cname=e.point.properties["cn-name"];
			                        chart.showLoading('<i class="icon-spinner icon-spin icon-3x"></i>');
			                        // 加载城市数据
			                        $.ajax({
			                            type: "GET",
			                            url: "http://data.hcharts.cn/jsonp.php?filename=GeoMap/json/"+ e.point.drilldown+".geo.json",
			                            contentType: "application/json; charset=utf-8",
			                            dataType:'jsonp',
			                            crossDomain: true,
			                            success: function(json) {
			                                data = Highcharts.geojson(json);
			                                $.each(data, function (i) {
			                                    this.value = i;
												this.events={};
			                                });
			                                chart.hideLoading();
			                                chart.addSeriesAsDrilldown(e.point, {
			                                    name: e.point.name,
			                                    data: data,
												events:{
												},
			                                    dataLabels: {
			                                        enabled: true,
			                                        format: '{point.name}'
			                                    }
			                                });
			                            },
			                            error: function (XMLHttpRequest, textStatus, errorThrown) {

			                            }
			                        });
			                    }
			                    this.setTitle(null, { text: cname });
			                },
			                drillup: function () {
			                    this.setTitle(null, { text: '中国' });
			                }
			            }
			        },
						tooltip: { 
							formatter:function(){
								console.info(this.point);
								 return "师傅分布数量<br />"+this.point.properties['cn-name']+":"+this.point.value;
								 
							}
								},
			        credits:{
			        },
			        title : {
			            text : '中国地图-师傅分布图'
			        },

			        subtitle: {
			            text: '中国',
			            floating: true,
			            align: 'right',
			            y: 50,
			            style: {
			                fontSize: '16px'
			            }
			        },

			        legend: small ? {} : {
								 
			            layout: 'vertical',
			            align: 'right',
			            verticalAlign: 'middle'
			        },
			        //tooltip:{
			        //pointFormat:"{point.properties.cn-name}:{point.value}"
			        //},
			        colorAxis: {
			            min: 0,
			            minColor: '#E6E7E8',
			            maxColor: '#005645',
								labels:{
									style:{
											"color":"red","fontWeight":"bold"
									}
								}
			        },

			        mapNavigation: {
			            enabled: true,
			            buttonOptions: {
			                verticalAlign: 'bottom'
			            }
			        },

			        plotOptions: {
			            map: {
			                states: {
			                    hover: {
			                        color: '#EEDD66'
			                    }
			                }
			            }
			        },

			        series : [{
			            data : data,
			            name: '中国',
			            dataLabels: {
			                enabled: true,
			                format: '{point.properties.cn-name}'
			            }
			        }],

			        drilldown: {
								
			            activeDataLabelStyle: {
			                color: '#FFFFFF',
			                textDecoration: 'none',
			                textShadow: '0 0 3px #000000'
			            },
			            drillUpButton: {
			                relativeTo: 'spacingBox',
			                position: {
			                    x: 0,
			                    y: 60
			                }
			            }
			        }
			    });
			});

			var base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";  
			var base64DecodeChars = new Array(  
			    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
			    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
			    -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63,  
			    52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1,  
			    -1,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14,  
			    15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1,  
			    -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40,  
			    41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1);  
			function base64decode(str) {  
			    var c1, c2, c3, c4;  
			    var i, len, out;  
			    len = str.length;  
			    i = 0;  
			    out = "";  
			    while(i < len) {  
			    /* c1 */  
			    do {  
			        c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
			    } while(i < len && c1 == -1);  
			    if(c1 == -1)  
			        break;  
			  
			    /* c2 */  
			    do {  
			        c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff];  
			    } while(i < len && c2 == -1);  
			    if(c2 == -1)  
			        break;  
			  
			    out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));  
			  
			    /* c3 */  
			    do {  
			        c3 = str.charCodeAt(i++) & 0xff;  
			        if(c3 == 61)  
			        return out;  
			        c3 = base64DecodeChars[c3];  
			    } while(i < len && c3 == -1);  
			    if(c3 == -1)  
			        break;  
			  
			    out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));  
			  
			    /* c4 */  
			    do {  
			        c4 = str.charCodeAt(i++) & 0xff;  
			        if(c4 == 61)  
			        return out;  
			        c4 = base64DecodeChars[c4];  
			    } while(i < len && c4 == -1);  
			    if(c4 == -1)  
			        break;  
			    out += String.fromCharCode(((c3 & 0x03) << 6) | c4);  
			    }  
			    return out;  
			}  
		},
		bindEvent:function(){
			$scope.query = this.query;
			$scope.doQuery = this.doQuery;
		},
		query:{
			
		},
		doQuery:function(){
			var url = "addressBO.ajax?cmd=doQuery";
			$timeout(function(){
				$scope.page.load({
							url:url,
							params:$scope.query
						});
			},500);
		},
		selectStreet:function(districtId){
			if(parseInt(districtId)>0){
				var param = {"isAll":"Y","districtId":districtId};
				var url = "staticDataBO.ajax?cmd=selectStreet";
				commonService.postUrl(url,param,function(data){
					if(data!=null && data!=undefined && data!=""){
							$scope.streetData=data;
							mapShow.query.townId=data.items[0].id;
		 	    	}
				});
			}
		},
	};
	mapShow.init();
}]);
