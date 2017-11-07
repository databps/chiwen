// 添加集群下拉菜单更新
var databackup="";
	$('.js-databackup li > a').click(function() {
			databackup = $(this).data('id');
			$('.js-databackup > button').html($(this).text()+' <i class="glyphicon glyphicon-menu-down"></i>');
			$('.js-databackup > button').data('id',databackup);
});
// 时间插件---开始
    $("#datetimeStart").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        startDate:new Date()
    }).on("click",function(){
        $("#datetimeStart").datetimepicker("setEndDate",$("#datetimeEnd").val());
    });
    $("#datetimeEnd").datetimepicker({
        format: 'yyyy-mm-dd',
        minView:'month',
        language: 'zh-CN',
        autoclose:true,
        startDate:new Date()
    }).on("click",function(){
        $("#datetimeEnd").datetimepicker("setStartDate",$("#datetimeStart").val());
    });
// 时间插件 ----结束