(function($, undefined) {
	"use strict";
	var pluginName = 'baseAjax';
	$[pluginName] = function(url, type, dataFormId, success, error, isSynchronization) { // isSynchronization true:同步 false:异步
		var data;
		console.log(data);
		var $form;
		try {
			if (typeof dataFormId == "string" && dataFormId.indexOf("=") == -1) {
				$form = (dataFormId.indexOf("#") == -1) ? $("#" + dataFormId).first() : $(dataFormId).first();
				data = $form.serialize();
			} else if(typeof dataFormId == "object" && !$.isPlainObject(dataFormId)) {
				try {
					$form = dataFormId;
					data = $form.serialize();
				} catch (e) {
					$form = $(dataFormId);
					data = $form.first().serialize();
				}
			}
		} catch (e) {
		}
		if(!data){
			data = dataFormId;
		}
		if(!url && $form){
			url = $form.attr("action");
		}
		$.ajax({
			async : (!isSynchronization),
			type : type == '' ? 'POST' : type,
			url : url, // 避免缓存
			data : data,
			success : success,
			error : error
		});
	};
})(jQuery);

(function($, undefined) {
	"use strict";
	var pluginName = 'addContent';
	/** 两个参数时url+divId，三个参数时url+dataFormId+divId **/
	$[pluginName] = function(url, dataFormId, divId,addContentCallBack,isSynchronization) {
		if(!divId && dataFormId){ // 两个参数时，没有表单
			divId = dataFormId;
			dataFormId = null;
		}
		if(divId && divId.indexOf("#")!=0){
			divId = "#"+divId;
		}
		var success = function(o) {
			$(divId).html(o);
			if(addContentCallBack){
				addContentCallBack();
			}
		};
		var error = function(o) {
			$(divId).html('数据载入异常');
		};
		
		if($(divId).html()== undefined || trim($(divId).html()).indexOf('<div class="content_loading">')!=0){
			$(divId).prepend('<div class="content_loading">&nbsp;</div>');// loading
		}
		$.baseAjax(url, 'POST', dataFormId, success, error,isSynchronization);
	};
})(jQuery);


(function($, undefined) {
	"use strict";
	var pluginName = 'addChart';
	/** divId:  chart_line  不带#
		三个参数时url+divId+type （没有查询条件） 
		        type：图表类型（line：曲线图   bar：柱状图    pie:饼状图）
	    四个参数  表单域id （有查询条件 ）
	    五个参数  （有查询条件  但是没有表单的时候）
	**/
	$[pluginName] = function(url,divId,type,dataFormId,data,isSynchronization) {
		var dataSubmit;
		if(dataFormId && dataFormId!='' && dataFormId!=null){
			dataSubmit = $('#'+dataFormId).serialize();
		}
		if(data){
			dataSubmit = data;
		}
		var success = function(o) {
			if(o.length>8){
				var obj=eval("("+o+")");
				var options = obj.options;
				if(obj.chartName && obj.chartName!=''){
					var chartName = obj.chartName;
					$('#'+divId+'_chartname').html(chartName);
				}
				if(obj.lineDescription && obj.lineDescription!=''){
					var description = obj.lineDescription;
					$('#'+divId+'_description').html(description);
				}
				var ctx = document.getElementById(divId).getContext("2d");
				
				if(type == 'line'){
					//封装数据值
					var datas = obj;
					new Chart(ctx).Line(datas,options);
				}else if(type == 'bar'){
					var datas = obj;
					new Chart(ctx).Bar(datas,options);
				}else if(type == 'pie'){
					var datas = obj.datasets;
					new Chart(ctx).Pie(datas,options);
				}
			}
		};
		var error = function(o) {
			$('#'+divId).html('数据载入异常');
		};
		if(trim($('#'+divId).html()).indexOf('<div class="content_loading">')!=0){
			$('#'+divId).prepend('<div class="content_loading">&nbsp;</div>');// loading
		}
		$.baseAjax(url, 'POST', dataSubmit, success, error,isSynchronization);
	};
})(jQuery);

;(function($, undefined) {
	"use strict";
	var pluginName = 'scojs_message';
	$[pluginName] = function(message, type) {
		clearTimeout($[pluginName].timeout);
		var $selector = $('#' + $[pluginName].options.id);
		if (!$selector.length) {
			$selector = $('<div/>', {id: $[pluginName].options.id}).appendTo($[pluginName].options.appendTo);
		}
		$selector.html(message);
		if (type === undefined || type == $[pluginName].TYPE_ERROR) {
			$selector.removeClass($[pluginName].options.okClass).addClass($[pluginName].options.errClass);
		} else if (type == $[pluginName].TYPE_OK) {
			$selector.removeClass($[pluginName].options.errClass).addClass($[pluginName].options.okClass);
		}
		$selector.slideDown('fast', function() {
			$[pluginName].timeout = setTimeout(function() { $selector.slideUp('fast'); }, $[pluginName].options.delay);
		});
	};
	$.extend($[pluginName], {
		options: {
			id: 'page_message'
			,okClass: 'page_mess_ok'
			,errClass: 'page_mess_error'
			,delay: 2000
			,appendTo: 'body'	// where should the modal be appended to (default to document.body). Added for unit tests, not really needed in real life.
		},

		TYPE_ERROR: 1,
		TYPE_OK: 2
	});
})(jQuery);

