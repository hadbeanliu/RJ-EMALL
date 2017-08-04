//author = liu

$(document).foundation({
	abide : {
		timeout : 10,//响应时间
		validators : {
			lengths : function(el, required, parent) {
				var b = true;
				if (el.min != "") {
					b = el.value.length >= el.min;
					if (!b) {
						return b;
					}
				}
				if (el.max != "") {
					b = el.max >= el.value.length;
					if (!b) {
						return b;
					}
				}
				if(el.required && el.value.length == 0) return false;
				if(el.pattern != undefined){//如果有正则，也需要组合进行判断
					if(el.value.match(el.pattern) == null) return false;
				}
				return true;
			},
			isTrue : function(el, required, parent) {
				if (el.isTrue == undefined)
					return false;
				return el.value == 'true' || el.value == 'True';
				// var value = Boolean(el.value);
				// if(el.isTrue == undefined) return false;
				// if(value == false) return false;
				// return typeof value == "boolean";
			},
			isFalse : function(el, required, parent) {
				if (el.isFalse == undefined)
					return false;
				return el.value == 'false' || el.value == 'False';
				// var value = Boolean(el.value);
				// if(el.isFalse == undefined) return false;
				// if(value == true) return false;
				// return typeof value == "boolean";
			},
			includemin : function(el, required, parent) {
				if (el.includemin == undefined)
					return false;
				if (el.includemin == false)
					return el.value.length > el.min;
			},
			includemax : function(el, required, parent) {
				if (el.includemax == undefined)
					return false;
				if (el.includemax == false)
					return el.max > el.value.length;
			},
			past : function(el, required, parent) {
				if (el.past) {
					var date = new Date();
					var mydate = new Date(el.value.replace(/\-/g, "\/"));
					console.info(date + "--" + mydate);
					if (mydate)
						return date > mydate;
				}
				return false;
			},
			future : function(el, required, parent) {
				if (el.future) {
					var date = new Date();
					var mydate = new Date(el.value.replace(/\-/g, "\/"));
					if (mydate)
						return mydate > date;
				}
				return false;
			},
//			notblank : function(el, required, parent) {
//				if (el.notblank) {
//					if (el.value.length == 0)
//						return false;
//					else
//						return true;
//				}
//
//				return false;
//			},
//			notempty : function(el, required, parent) {
//				if (el.notempty) {
//					if (el.value.length == 0)
//						return false;
//				}
//				return false;
//			},
//			notnull : function(el, required, parent) {
//				if (el.notnull) {
//					if (el.value.length == 0)
//						return false;
//				}
//				return false;
//			},
			isnull : function(el, required, parent) {
				if (el.isnull == undefined)
					return false;
				if (el.isnull)
					return el.value.length == 0;
			},
			eantype : function(el, required, parent) {
				if(el.eantype == undefined) return false;
				if(el.eantype == 'EAN13'){
					if(el.value.match(el.pattern) == null) return false;//是否匹配正则
					if(el.value.length != 13) return false;
					var odd = 0,even = 0;//偶数位和，奇数位和
					var arrays;//偶数位数组，奇数位数组
					var value13 = parseInt(el.value);
					var value12 = el.value.substring(0,12);
					var valueof3 = parseInt(el.value.substring(0,3));
					var valueof13 = parseInt(el.value.substr(12,1));
					
					if(valueof3 > 699) return false;
					if(690 > valueof3) return false;
					
					//计算
					arrays = value12.split("");
					for ( var i = 0; i < arrays.length; i++) {
						if(parseInt(arrays[i]) % 2 == 0) odd += parseInt(arrays[i]);
						else even += parseInt(arrays[i]);
					}
					var i = (10 - ((odd + even * 3) % 10)) % 10;
					if(i == valueof13) return true;
				}else if(el.eantype == 'EAN8'){
					if(el.value.match(el.pattern) == null) return false;//是否匹配正则
					if(el.value.length != 8) return false;
					var odd = 0,even = 0;//偶数位和，奇数位和
					var arrays;//偶数位数组，奇数位数组
					var value8 = parseInt(el.value);
					var value7 = el.value.substring(0,7);
					var valueof3 = parseInt(el.value.substring(0,3));
					var valueof8 = parseInt(el.value.substr(7,1));
					
					if(valueof3 > 699) return false;
					if(690 > valueof3) return false;
					
					//计算
					arrays = value7.split("");
					for ( var i = 0; i < arrays.length; i++) {
						if(parseInt(arrays[i]) % 2 == 0) odd += parseInt(arrays[i]);
						else even += parseInt(arrays[i]);
					}
					var i = (10 - ((odd + even * 3) % 10)) % 10;
					if(i == valueof8) return true;
				}
				return false;
			},
			safehtml : function(el, required, parent) {
				if(el.regexp == undefined)	return false;  
				var value = el.value;
				var dd=value.replace(/<[a-zA-Z0-9\u4e00-\u9fa5 .!#$%&'*,:+\/=?^_`{|}~\"-]+>/g," "); 
				var strs = dd.split(" ");
				var re = "";
				for ( var i = 0; i < strs.length; i++) {//排除<>以外的内容
					if(i == 0) {
						re = value.replace(strs[i],"");
					}
					else {
						re = re.replace(strs[i],"");
					}
					
				}
				var contains = re.split("<");
				contains = contains.toString().split(">"); 
				for ( var i = 0; i < contains.length; i++) {
					if(contains[i].length != 0){contains[i] = contains[i].trim().replace(/[,]*/g,"");}
				}//得到除标签<>的数组
				//console.info("re="+contains);
				
				var str1 = "",//首位有/
					str2 = "",//末尾有/
					str3 = "";//没有/
				for ( var i = 0; i < contains.length; i++) {
					if(contains[i] == "") continue;
					var index = contains[i].indexOf("/");
					var endindex = contains[i].lastIndexOf("/");
					if(index == 0){
						str1 += contains[i] + ",";
					}
					if(endindex == contains[i].length - 1){
						str2 += contains[i] + ",";
					}
					if(index == -1){
						str3 += contains[i] + ",";
					}
					if(index == 0){
						if(endindex == 0){
							//return false;
						}
					}
				}


				str3 = str3.split(",");
				var str4 = "",//不含/，含空格
					str5 = "";//不含/，不含空格
				for ( var i = 0; i < str3.length; i++) {
					var index = str3[i].search(/ /);
					if(index > 0){
						str4 += str3[i] + ",";
					}else{
						str5 += str3[i] + ",";
					}
				}

				str2 = str2.split(",");
				var str6 = "",//末尾有/,带空格
					str7 = "";//末尾有/,不带空格
				for ( var i = 0; i < str2.length; i++) {
					var index = str2[i].search(/ /);
					if(index > 0){
						str6 += str2[i] + ",";
					}else{
						str7 += str2[i] + ",";
					}
				}
				
				var array1 = str1.split(","),//首位有/
					array2 = str6.split(","),//末尾有/,带空格
					array3 = str7.split(","),//末尾有/,不带空格
					array4 = str4.split(","),//不含/，含空格
					array5 = str5.split(",");//不含/，不含空格
				//test 1 4 5
				var str1="",str2="";
				if(method2(array1,el.regexp,el.regexp1) == "-1") return false;
				else str1 = method2(array1,el.regexp,el.regexp1);
				if(method2(array2,el.regexp,el.regexp1) == "-1") return false;
				if(method2(array3,el.regexp,el.regexp1) == "-1") return false;
				if(method1(array4,el.regexp,el.regexp1) == "-1") return false;
				str2 = method1(array4,el.regexp,el.regexp1);
				if(method1(array5,el.regexp,el.regexp1) == "-1") return false;
				str2 += method1(array5,el.regexp,el.regexp1);
				//console.info(str1);
				//console.info(str2);
				if(str1 == ""){
					if(str2 == "") return true;
				}
				if(str1 != ""){
					if(str2 != ""){
						var str1 = str1.split(",");
						var str2 = str2.split(",");
						for ( var i = 0; i < str1.length; i++) {
							if(str1.length == str2.length){
								if(str1[i] == str2[str2.length - 2 - i]){
									if(i == str1.length -2) return true;
								}else{
									return false;
								}
							}else{
								return false;
							}
								
						}
						return false;
					}
				}
				return false;
			},
		}
	}
});


function method1(array,regexp1,regexp2){//没有/时
	var str = "";
	for ( var i = 0; i < array.length; i++) {
		array[i] = array[i].split(" ")[0];
		if(array[i] != ""){
			if(regexp1.test(array[i])){ 
				if(regexp2.test(array[i])){
					str += array[i]+",";
				}else return "-1";
			}else return "-1";
		}
	}
	return str;
}
function method2(array,regexp1,regexp2){//有/时
	var str = "";
	for ( var i = 0; i < array.length; i++) {
		array[i] = array[i].replace("/","");
		array[i] = array[i].split(" ")[0];
		if(array[i] != ""){
			if(regexp1.test(array[i])){ 
				if(regexp2.test(array[i])){
					str += array[i]+",";
				}else return "-1";
			}else return "-1";
		}
	}
	return str;
}