var GoolbleObject = {
			init : function(MyApp){
				this.pagingFuction(MyApp);
				this.contentTypeSet(MyApp);
                this.datePicker(MyApp);
                this.regionSelect(MyApp);
                this.regionFilter(MyApp);
                this.selectParamter(MyApp);
                this.myValidator(MyApp);
			},
			getSelectOptions:function(item,defaultValue,id){
				if($(id).find('option').size()==0)
					{
						 angular.forEach(item,function(ele,index,array){
			                    if(defaultValue!=undefined&&defaultValue==ele.codeValue){
			                        $("<option selected value='"+ele.codeValue+"'>"+"["+ele.codeValue+"]"+ele.codeName+"</option>").appendTo($(id));
			                    }
			                    else{
			                        $("<option value='"+ele.codeValue+"'>"+"["+ele.codeValue+"]"+ele.codeName+"</option>").appendTo($(id));
			                    }
			            })
					}
			},
			pagingFuction : function (MyApp){MyApp.directive('paging', function () {
			    return {
			        restrict: 'E',
			        template: '',
			        replace: true,
			        link: function (scope, element, attrs) {
			            scope.$watch('numPages', function (value) {
			                scope.pages = [];
			                for (var i = 1; i <= value; i++) {
			                    scope.pages.push(i);
			                }
			                //alert(scope.page)
			                if (scope.formData.page > value) {
			                    scope.selectPage(value);
			                }
			            });
			            scope.isActive = function (page) {
			                return scope.formData.page === page;
			            };
			            scope.selectPage = function (page) {
			                if (!scope.isActive(page)) {
			                	scope.formData.page = page;
			                    scope.onSelectPage(page);
			                }
			            };
			            scope.selectPrevious = function () {
			                if (!scope.noPrevious()) {
			                    scope.selectPage(scope.formData.page - 1);
			                }
			            };
			            scope.selectNext = function () {
			                if (!scope.noNext()) {
			                    scope.selectPage(scope.formData.page + 1);
			                }
			            };
			            scope.noPrevious = function () {
			                return scope.formData.page == 1;
			            };
			            scope.noNext = function () {
			                return scope.formData.page == scope.numPages;
			            };
			
			        }
			    }
			});
			},
			contentTypeSet : function(MyApp){

				 MyApp.config(function($httpProvider) {
				      // Use x-www-form-urlencoded Content-Type
				      $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
				     
				      /**
				       * The workhorse; converts an object to x-www-form-urlencoded serialization.
				       * @param {Object} obj
				       * @return {String}
				       */ 
				      var param = function(obj) {
				        var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
				          
				        for(name in obj) {
				          value = obj[name];
				            
				          if(value instanceof Array) {
				            for(i=0; i<value.length; ++i) {
				              subValue = value[i];
				              fullSubName = name + '[' + i + ']';
				              innerObj = {};
				              innerObj[fullSubName] = subValue;
				              query += param(innerObj) + '&';
				            }
				          }
				          else if(value instanceof Object) {
				            for(subName in value) {
				              subValue = value[subName];
				              fullSubName = name + '[' + subName + ']';
				              innerObj = {};
				              innerObj[fullSubName] = subValue;
				              query += param(innerObj) + '&';
				            }
				          }
				          else if(value !== undefined && value !== null)
				            query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
				        }
				          
				        return query.length ? query.substr(0, query.length - 1) : query;
				      };
				     
				      // Override $http service's default transformRequest
				      $httpProvider.defaults.transformRequest = [function(data) {
				        return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
				      }];
				    });
				
			},
            datePicker:function(MyApp){
                MyApp.directive("datePicker",function($parse){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                            element.bind("click", function () {
                                window.WdatePicker({
                                    onpicked: function () {

                                        $parse(attr['ngModel']).assign(scope, this.value);
                                    }
                                });
                            });
                        }
                    };
                });
            },
            regionSelect:function(MyApp){
                MyApp.directive("regionSelect",function(){
                    return {
                        restrict:"A",
                        link:function(scope, element, attr){
                            element.bind("click", function () {
                                //if(value==defaultValue){value=''};
                            	if(this.id=='txt1'){
                                    eye.selectCity.show(this,'sourceProvince','sourceRegion','sourceCounty',150);
                            	}
                            	if(this.id=='txt2'){
                                    eye.selectCity.show(this,'desProvince','desRegion','desCounty',150);
                            	}
                            });
                        }
                    };
                });
            },
            myValidator:function(MyApp){
            	MyApp.directive("billid",function(){
            		return {
            			require: 'ngModel',
            			link: function(scope, elm, attrs, ctrl) {
            			      ctrl.$validators.billid = function(modelValue, viewValue) {
            			    	var INTEGER_REGEXP = /^\d{11}$/;
            			        if (ctrl.$isEmpty(modelValue)) {
            			          // consider empty models to be valid
            			          return true;
            			        }
            			        if (INTEGER_REGEXP.test(viewValue)) {
            			          // it is valid
            			          return true;
            			        }

            			        // it is invalid
            			        return false;
            			      };
            			    }
            			  };
            			});
            },
            regionFilter:function(MyApp){
                MyApp.filter("regionFilter",function(){
                    return function (row) {
                        var htm="";
                        var sourceProvinceName = row.sourceProvinceName;
                        var sourceRegionName = row.sourceRegionName;
                        var sourceCountyName = row.sourceCountyName;
                        if(sourceProvinceName!=null && sourceProvinceName.replace(/(^s*)|(s*$)/g, "").length !=0){
                            htm+=row.sourceProvinceName;
                        }
                        if(sourceRegionName!=null && sourceRegionName.replace(/(^s*)|(s*$)/g, "").length !=0){
                            if(htm==""){
                                htm+=row.sourceRegionName;
                            }else{
                                htm+="路"+row.sourceRegionName;
                            }
                        }
                        if(sourceCountyName!=null && sourceCountyName.replace(/(^s*)|(s*$)/g, "").length !=0){
                            if(htm==""){
                                htm+=row.sourceCountyName;
                            }else{
                                htm+="路"+row.sourceCountyName;
                            }
                        }
                        return htm;
                    }
                });
                MyApp.filter("regionFilterDes",function(){
                	return function (row) {
                		var htm="";
                		var desProvinceName = row.desProvinceName;
                		var desRegionName = row.desRegionName;
                		var desCountyName = row.desCountyName;
                		if(desProvinceName!=null && desProvinceName.replace(/(^s*)|(s*$)/g, "").length !=0){
                			htm+=row.desProvinceName;
                		}
                		if(desRegionName!=null && desRegionName.replace(/(^s*)|(s*$)/g, "").length !=0){
                			if(htm==""){
                				htm+=row.desRegionName;
                			}else{
                				htm+="路"+row.desRegionName;
                			}
                		}
                		if(desCountyName!=null && desCountyName.replace(/(^s*)|(s*$)/g, "").length !=0){
                			if(htm==""){
                				htm+=row.desCountyName;
                			}else{
                				htm+="路"+row.desCountyName;
                			}
                		}
                		return htm;
                	}
                })
            },
            selectParamter:function(MyApp){
                MyApp.directive("wlptSelect",function($http){
                    return {
                        restrict:"A",
                        replace:true,

                        link:function(scope, element, attr){
                                var codeType = attr.codeType;
                                var defaultValue = attr.defaultvalue;
                                var excepArray = attr.exceptArray;
                                var queryString = "codeType="+codeType;
                                var queryObject = {
                                    method  : 'POST',
                                    data:queryString,
                                    url     : 'selectCity.ajax?cmd=getStaticData'
                                };
                                $http(queryObject).success(function(data) {
                                    angular.forEach(data.items,function(ele,index,array){
                                        if(excepArray!=undefined&&excepArray.indexOf(ele.codeValue)!=-1)
                                        {
                                            //eccep
                                        }
                                        else{
                                            if(defaultValue!=undefined&&defaultValue==ele.codeValue){
                                                $("<option selected value='"+ele.codeValue+"'>"+ele.codeName+"</option>").appendTo(element);
                                            }
                                            else{
                                                $("<option value='"+ele.codeValue+"'>"+ele.codeName+"</option>").appendTo(element);
                                            }
                                        }
                                    })
                                });
                        }
                    };
                });
            }
	}

	
