(function($, window, document,undefined){
	'use strict';
	
	var initArea = function(url){
		var AreaData;
		if( ! AreaData ){
			$.ajax({
				type:"POST",
				url: url ? url: "allareas2.json",
				async:false,
				dataType : "json",
				success:function(data){
					AreaData=data;
				},
				error:function(e){
					alert("出错："+e);
				}
			});
		}
		var $lt=$(this);
		$lt.each(function(){
			var $prov = $lt.find(".city-province");
			var $city = $lt.find(".city-city");
			var $dist = $lt.find(".city-district");
		
		    
		 // 设置下拉列表的的默认值
			var v_prov = "";
			var v_city = "";
			var v_dist = "";
			// 设置省下拉列表框的数据
			$.each(AreaData, function(k, v) {
				for(var code in v){
					appendOptTo($prov, code, v[code].name, v_prov);
				}
			});
			// 自定义下拉列表的数据函数
			function appendOptTo($o, k, v, d) {
				var $opt = $("<option>").text(v).val(k);
				if (k == d) {
					$opt.attr("selected", "true");
				}
				$opt.appendTo($o);
			}
			// 通过地图获取来的数据进行改变下拉列表
			function appendOptTo1($o, k, v, d) {
				var $opt = $("<option>").text(v).val(k);
				if ((new RegExp(v)).test(d)) {
					$opt.attr("selected", "true");
				}
				$opt.appendTo($o);
			}

			// 省下拉列表事件
			$prov.change(function() {
				$city.html("");
				$dist.html("");
				var prov_curr_val = this.options[this.selectedIndex].value;
				var prov_vurr_txt = this.options[this.selectedIndex].text;
				if (this.selectedIndex == -1){
					return;
				}
				$.each(AreaData, function(k, v) {
					for(var pcode in v){
						if (prov_curr_val == pcode) {
							if (v[pcode].citys) {
								$.each(v[pcode].citys, function(k, v) {
									for(var code in v){
										appendOptTo($city, code, v[code].name, v_city);
									}
								});
							}
						}
					}
				});
				$city.change();
			}).change();

			// 城市下拉列表事件
			$city.change(function() {
				$dist.html("");
				var prov_curr_val = $prov[0].options[$prov[0].selectedIndex].value;
				if (this.selectedIndex == -1){
					return;
				}
				var city_curr_val = this.options[this.selectedIndex].value;
				$.each(AreaData, function(k, v) {
					for(var code in v){
						if (prov_curr_val == code) {
							if (v[code].citys) {
								$.each(v[prov_curr_val].citys, function(k, v) {
									for(var code in v){
										if (city_curr_val == code) {
											$.each(v[code].citys, function(k, v) {
												for(var code in v){
													appendOptTo($dist, code, v[code], v_dist);
												}
											});
										}
									}
								});
							}
						}
					}
				});
				
			}).change();
			
		});
	}
	$.fn.extend({initArea:initArea});
})(jQuery, window, window.document);