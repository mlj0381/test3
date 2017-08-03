 ver=1.5;
 sysStatic={
		init:function(){
			if(this.isload==undefined || this.ver==undefined || this.isload==false || this.ver!=ver){
				var url = "sysStaticData.ajax?cmd=getStaticData&codeType=QUERY_DATA";
				$.ajax({
					  type: 'GET',
					  url: url,
					  success: function(data){
						  sysStatic.data = data;
					  },
					  dataType:'json',
					});
				this.isload=true;
				this.ver=ver;
			}
		}
	};
sysStatic.init();
