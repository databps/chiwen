$(function(){
	// 菜单点击事件
    $('.dropMenu a').click(function() {
        if ($(this).hasClass('disabled')) {
            // 展开菜单
            var id = $(this).data('id');
            $('.dropMenu a.nt-item').addClass('hidden');
            $('.dropMenu a.nt-item[data-pid='+id+']').removeClass('hidden');
            if ($('.dropMenu a.nt-item.active[data-pid='+id+']').length === 0) {
                $('.dropMenu a.nt-item[data-pid='+id+']').first().trigger('click');
            }
        } else {
            // 打开页面
            $(this).addClass('active').siblings('a').removeClass('active');
            var url = $(this).data('url');
            $('.js-mainframe').attr('src',url);
        }
    });
    // 初始化打开第一个
    $('.dropMenu a:first').trigger('click');
    // 下拉菜单
	$(document).on('click','.dropMenu li > a',function(e){
		$(this).siblings('ul').find('li').eq(0).find('ul').show();
		$(this).parents('li').siblings('li').find('.onemenu').slideUp();
		$(this).siblings('ul').slideToggle();
    	e.stopPropagation();
	});
	// 关闭菜单
	$(document).on("click", function(){
        $(".onemenu").slideUp();
        $('.dropMenu li > a').siblings('ul').find('li').eq(0).find('ul').show();
    });
    // 关闭菜单
	$(document).on("click",'.twomenu a', function(){
        $(".onemenu").slideUp();
        $('.dropMenu li > a').siblings('ul').find('li').eq(0).find('ul').show();
    });
});

var admin = {
  checkbox:function(name, checked) {
    $("input[type=checkbox][name=" + name + "]").each(function() {
      $(this).prop('checked',checked);
    });
  },
  getTableForm:function() {
    return document.getElementById('queryPage');
  },
  chkIds: function(name) {
    var ids=[];
    $("input[type=checkbox][name=" + name + "]").each(function() {
      if($(this).prop('checked')){
        ids.push($(this).val());
      }
    });
    return ids;
  },
  checkedCount : function(name) {
    var batchChecks = document.getElementsByName(name);
    var count = 0;
    for (var i = 0;i < batchChecks.length; i++) {
      if (batchChecks[i].checked) {
        count++;
      }
    }
    return count;
  },
  optDelete:function(iid,name){
    if(admin.checkedCount(name)<=0) {
      alert("您还没有选择");
      return;
    }
    if(!confirm("确定要删除?")) {
      return;
    }
    var tableForm = admin.getTableForm();
    $("input[name=ids]").val(admin.chkIds(name));
    tableForm.action=iid;
    tableForm.onsubmit=null;
    tableForm.submit();
  },
  submit:function(){
    var tableForm = admin.getTableForm();
    $("input[name=page]").val("0");
    tableForm.onsubmit=null;
    tableForm.submit();
  }
};