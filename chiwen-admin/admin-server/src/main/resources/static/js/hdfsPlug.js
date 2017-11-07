$('#js-tab a').click(function (e) {
	e.preventDefault();
	$(this).tab('show');
});
// 添加集群下拉菜单更新
var yHeadRId="";
	$('.js-yHeadR li > a').click(function() {
			yHeadRId = $(this).data('id');
			$('.js-yHeadR > button').html($(this).text()+'<i class="glyphicon glyphicon-menu-down"></i>');
			$('.js-yHeadR > button').data('id',yHeadRId);
	});
