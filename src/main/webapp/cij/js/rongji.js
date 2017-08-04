var RJ = {}; // 榕基命名空间
RJ.E = {};   // 电商项目命名空间
if (typeof Cfg === 'undefined') {
	RJ.E.Cfg = {
		contextPath: '/emall',
		tempPath: '/emall/tpl'
	};
} else {
	RJ.E.Cfg = Cfg; // 项目配置信息
}

RJ.E.util = {};

RJ.E.util.createNewForm = function(action, data, isBlack, name) {
	var htm = [];
	htm.push('<form action="');
	htm.push(/^https?:\/\/.*/.test(action) ? action : RJ.E.Cfg.contextPath + action)
	htm.push('" '+ (isBlack ? 'target="_blank" ' : '') +'method="post">');
	
	if (name) {
		htm.push('<input type="hidden" value="');
		htm.push(encodeURIComponent(JSON.stringify(data) ));
		htm.push('" name="'+ (name === true ? 'data' : name) +'" />');
	} else {
		for(var prop in data) {
			htm.push('<input type="hidden" value="');
			htm.push(encodeURIComponent( data[prop] ));
			htm.push('" name="'+ prop +'" />');
		}
	}
	
	htm.push('</form>');
	
	var $form = $(htm.join(''));
	$('body').append($form);
	return $form[0];
};

RJ.E.util.createForm = function(action, data, name) {
	return RJ.E.util.createNewForm(action, data, false, name);
}

RJ.E.util.createFormNewWin = function(action, data, name) {
	return RJ.E.util.createNewForm(action, data, true, name);
}

RJ.E.util.clearForm = function ($form) { /* 把表单$form表单的值清除 */
	if (Foundation && Foundation.libs && Foundation.libs.abide) {
		
	    var self = Foundation.libs.abide;
	    $form.removeAttr(self.invalid_attr);

	    $('[' + self.invalid_attr + ']', $form).removeAttr(self.invalid_attr);
	    $('.' + self.settings.error_class, $form).not('small').removeClass(self.settings.error_class);
	    $(':input', $form).not(':button, :submit, :reset, :checkbox, :radio, [data-abide-ignore]').val('').removeAttr(self.invalid_attr).end().has(':checkbox, :radio').prop('checked', false);
	} else {
		$(':input', $form).not(':button, :submit, :reset, :checkbox, :radio').val('').has(':checkbox, :radio').prop('checked', false); // 隐藏也需要重新设置
	}
};

RJ.E.util.editFormByUrl = function ($form, url, call) { /* 根据url获取数据并填充到$form中 */
//	RJ.E.util.clearForm($form);
	$.get(url, null, function(u){
		if (typeof call === 'function') {
			call(u); // 自定义值设置
		} else {
			RJ.E.util.clearForm($form);
			RJ.E.util.fillFormValue($form, u);
		}
	}, 'json');
};

RJ.E.util.fillFormValue = function ($form, vo) { /* 填充u到$form中 */
	for(var prop in vo) {
		var field = $form.find('[name="' + prop + '"]');
		if (field && field.length > 0) {
			var type = field[0].type;
			if (type === 'checkbox' || type === 'radio') {
				var v = vo[prop];
				if (v instanceof Array) {
					var s = [];
					for(var i =0; i<v.length; i++) {
						s.push('[value="'+ v[i] +'"]');
					}
					v = s.join(", ")
				} else {
					v = '[value="'+ v +'"]';
				}
				field.filter(v).prop('checked', true);
			} else if (type !== 'file') {
				field.val(vo[prop]);
			}
		}
	}
} 

RJ.E.util.getFormDataArrays = function ($form) { /* 从表单$form中获取值，返回二维数组 [0] = 名称，[1] = 值，当为多值时值也为数组 */
	var datas = [];
	var empty = true;
	if ($form.is(':input')) {
		empty = false;
//		datas[$form.attr('name')] = $form.val();
		datas.push([$form.attr('name'), $form.val()]);
		
	} else {
		var contains = {};
		$form.find("[name]").each(function(){
			var val = $(this).val();
			if (this.type === 'checkbox' || this.type === 'radio') {
				if (this.type === 'radio' && contains[this.name]) {
					return;
				}
				contains[this.name] = true;
//				val = [];
				$form.find("[name="+ this.name +"]:checked").each(function(){
//					val.push(this.value);
					if (this.value) {
						datas.push([this.name, this.value]);
					}
				});
//				if (val.length === 1) {
//					val = val[0];
//				}
			} else if (val) {
				datas.push([this.name, val]);
			}
			empty = false;
//			datas.push([this.name, val]);
		});
	}
	
	if (empty) {
		return null;
	}
	
	return datas;
};

RJ.E.util.getFormDataArray = function ($form) { /* 从表单$form中获取值，返回url格式数组 */
	var datas = RJ.E.util.getFormDataArrays($form);
	if ( ! datas ) {
		return null;
	}
	
	var dt = [];
	for ( var i = 0; i < datas.length; i++) {
		dt.push(datas[i][0] + "=" + datas[i][1].replace(/=/g, '%3D'))
	}
	
	return dt;
};

RJ.E.util.getFormData = function ($form, idColumn, exId) { /* 从表单$form中获取值，返回对象 */
	
	var dt = RJ.E.util.getFormDataArrays($form);
	if ( ! dt ) {
		return null;
	}
	
	var datas = {};
	for ( var i = 0; i < dt.length; i++) {
		var nv = dt[i];
		var n = datas[nv[0]];
		if (n) {
			if (n.constructor.name === 'Array') {
				n.push(nv[1]);
			} else {
				datas[nv[0]] = [n, nv[1]];
			}
			
		} else {
			datas[nv[0]] = nv[1];
		}
	}
	
	if ( !exId && idColumn && ! datas[idColumn.data] ) {
		delete datas[idColumn.data];
	}
	
	return datas;
};

RJ.E.util.bindFormVerificationByUrl = function($form, url) {
	$.get(url, function(vbs){
		RJ.E.util.bindFormVerification($form, vbs);
	},"json");
}

RJ.E.util.bindFormVerification = function($form, vbs) { /* 绑定表单校验 */ 
	for(var prop = 0 ;vbs.length > prop ;prop++) {
		var field = $form.find('[name="' + vbs[prop].fieldName + '"]');
		if(field && field[0]){//判断是否存在field,如果存在提取它的表单
				var $valid = vbs[prop].verificationRules;
				field[0].required = $valid.required; //在表单内增加required属性
				if($valid.pattern) field[0].pattern = $valid.pattern;//在表单内增加pattern属性
				if($valid.min) field[0].min = $valid.min;//在表单内增加max属性
				if($valid.max) field[0].max = $valid.max;//在表单内增加max属性
				if(field[0].type == "textarea" && $valid.max){
					field[0].maxLength = $valid.max;//在表单内增加max属性
				}
				field[0].isTrue = $valid.isTrue;
				field[0].isFalse = $valid.isFalse;
				field[0].includemax = $valid.includemax;
				field[0].includemin = $valid.includemin;
				field[0].past = $valid.past;
				field[0].future = $valid.future;
				field[0].isnull = $valid.isnull; 
				field[0].eantype = $valid.eantype; 

				field[0].regexp = new RegExp($valid.regexp);//safehtml正则 白名单
				field[0].regexp1 = new RegExp($valid.regexp1);//safehtml正则1	字母
				field[0].regexp2 = $valid.regexp2;//safehtml正则2 <>
				if(field[0].name == 'inte'){

					var value = '<input id="ids" type="text" /><br/><div class="row"><div class="large-12 columns"><label>EAN8<input type="text" id="ean2" name="ean2" data-abide-validator="eantype" /></label> <small class="error">不是有效的条形码</small></div></div>';
					document.getElementById("inte").value = value;
				}
		}
		
	}

};

RJ.E.util.verificationForm = function($form) { /* 进行表单校验 */
	var e = { type: 'submit' }; /* 模拟事件，Foundation需要的 */
	return Foundation.libs.abide.validate($form.is(':input') ? $form.get() : $form.find('input, textarea, select').get(), e, false);
};

RJ.E.util.readonlyForm = function($form) { /* 设置表单为只读 */
	var sel = 'select, [type=radio], [type=checkbox]';
	$form.find(':input')
	.not(sel).prop('readonly', true).end()
	.filter(sel).prop('disabled', true);
}


$(document).ajaxSuccess(function(event, jqXHR, ajaxOptions, data){
	if (data && data.errorId && data.errorMsg){
		alert(data.errorMsg);
	}
})
