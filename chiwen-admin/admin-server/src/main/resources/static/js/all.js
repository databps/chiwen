$(function(){
  // 关闭菜单
      $(document).on("click", function(){
          $(window.parent.document).find('.onemenu').slideUp();
          $(window.parent.document).find('.dropMenu li > a').siblings('ul').find('li').eq(0).find('ul').show();
      });
      //注意：下面的代码是放在iframe引用的子页面中调用
      $(window.parent.document).find("#mainframe").load(function () {
          var main = $(window.parent.document).find("#mainframe");
          var thisheight = $(document).height() + 30;
          main.height(thisheight);
      });
});