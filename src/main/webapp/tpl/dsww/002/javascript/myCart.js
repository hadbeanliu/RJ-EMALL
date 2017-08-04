
window.onload = function () {
   
    var $form = $('#cartForm'),                          // 购物车表单
		$selectInputs = $form.find('.check'),            // 所有勾选框
		$checkAll = $form.find('.check-all'),            // 全选框
		$merchandise = $form.find('.merchandise'),       // 店铺
		$checkShop = $merchandise.find('.check-shop'),   // 店铺的全选框
		$totalBar = $form.find('.totalbar'),             // 悬浮条
		selectedTotal = $form.find('#selectedTotal')[0], // 已选商品数目容器
		priceTotal = $form.find('#priceTotal')[0],       // 总计
		deleteAll = $form.find('#deleteAll')[0],         // 删除全部按钮
		tr = $merchandise.find('tbody tr'),              // 商品列表
		targetTop = $totalBar[0].offsetTop,              // totalbar到body的偏移量
		targetWidth = $totalBar.width();                 // totalbar的宽度
	
	 

    // 更新总数和总价格，已选浮层
    function getTotal() {
		var selected = 0,
		    selectedT = 0,
			price = 0,
			priceT = 0,
			HTMLstr = '',
			tr = $merchandise.find('tbody tr');
		
		for (var i = 0, len = tr.length; i < len; i++) {
			if (tr[i].getElementsByTagName('input')[0].checked) {
				tr[i].className = 'on';
				selected = parseInt(tr[i].getElementsByTagName('input')[1].value);
				selectedT += selected;
				price = parseFloat(tr[i].cells[4].children[1].innerHTML);
				priceT += selected * price;
			}
			else {
				tr[i].className = '';
			}
		}
	
		selectedTotal.innerHTML = selectedT;
		priceTotal.innerHTML = priceT.toFixed(2);
	}

    // 点击选择框
    for(var i = 0; i < $selectInputs.length; i++ ){
        $selectInputs[i].onclick = function () {
		
		    //如果是全选，则把所有的选择框选中或取消全中
            if (this.className.indexOf('check-all') >= 0) { 
                for (var j = 0; j < $selectInputs.length; j++) {
                    $selectInputs[j].checked = this.checked;
                }
            }
			
			//如果是全选店铺，则把该店铺的所有选择框选中
			if (this.className.indexOf('check-shop') >= 0) { 
			    var $merCheck = $(this).parents('.merchandise').find('.check');
                for (var j = 0; j < $merCheck.length; j++) {
                    $merCheck[j].checked = this.checked;
                }
            }
			
			//只要有一个未勾选，则取消全选框的选中状态
            if (!this.checked) { 
                for (var i = 0; i < $checkAll.length; i++) {
                    $checkAll[i].checked = false;
                }
				if (this.className.indexOf('check-all') < 0){
					$(this).parents('.merchandise').find('.check-shop')[0].checked = false;
				}
            }
			
			//选完更新总计
            getTotal();
        }
    }

    //为每行元素添加事件
    for (var i = 0; i < tr.length; i++) {
		var value = '',
		    countInput = '';
		$(tr[i]).on('click','.reduce',function(e){
    		  countInput = this.parentElement.children[1];
			  value = parseInt(countInput.value);
			 
			 if (value > 1) {
				countInput.value = value - 1;
			}
			getTotal();
		})
		.on('click','.add',function(e){
			countInput = this.parentElement.children[1];
			value = parseInt(countInput.value);
			countInput.value = value + 1;
			getTotal();
		})
		.on('click','.delete',function(e){
			var merList = $(this).parents('.merchandise-list'),
			    $delTr = $(this).parents('tr');
			
			$('#warning .warning-confirm').on('click',function(e){
				setTimeout(function(){
					$delTr.remove();
					tr = $merchandise.find('tbody tr');
					if(merList[0].childElementCount == 0){
						merList.parents('.merchandise').remove();
					}
					
					targetTop = $totalBar[0].offsetTop;
					magellan();
					getTotal();
				},300);
			});
			
		});
		
        // 给数目输入框绑定keyup事件
        tr[i].getElementsByTagName('input')[1].onkeyup = function () {
            var val = parseInt(this.value);
            if (isNaN(val) || val <= 0) {
                val = 1;
            }
            if (this.value != val) {
                this.value = val;
            }
            getSubtotal(this.parentNode.parentNode); //更新小计
            getTotal(); //更新总数
        }
    }

    // 点击全部删除
    deleteAll.onclick = function () {
        if (selectedTotal.innerHTML != 0) {
			$('#warning .warning-confirm').on('click',function(e){
				setTimeout(function(){
					for (var i = 0; i < tr.length; i++) {
                    // 如果被选中，就删除相应的行
                    if (tr[i].getElementsByTagName('input')[0].checked) {
                         
						var hasTr = tr[i].parentNode;
						tr[i].remove();
						tr.splice(i,1);
						if(hasTr.childElementCount == 0){
							$(hasTr).parent().remove();
						}
						i--;
						
						//重新获取元素到头部的距离
						targetTop = $totalBar[0].offsetTop;
						magellan();
                    }
                }
				getTotal(); 
			    },300);
			});
        } else {
            alert('请选择商品！');
        }
    }
		
	magellan();
	
	$(window).on('scroll',function(e){
	    magellan();
	});
	 
	//悬浮totalbar条
	function magellan(){
		var screenviewHeight = $(window).height(),
		   scrollHeight = $(document).scrollTop();
		   
		if(targetTop - screenviewHeight - scrollHeight >= 0){
		   $totalBar.css({'position':'fixed','bottom':'-0.625rem','width':targetWidth});
		}else{
		   $totalBar.css({'position':'static','bottom':'0','width':'auto'});
		}
	  }

    // 默认全选
    $checkAll[0].checked = true;
    $checkAll[0].onclick();
}
