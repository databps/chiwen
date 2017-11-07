<#--
分页查询
-->
[#macro mongo_page value form]
[#assign pageNumber=(value.currentPage)!1 pageSize=(value.maxResults)!8 totalPage=(value.totalPage)!0 totalRow=(value.totalResults)!0]
<div class="page">

  [#if totalPage == 0]
  <span>没有记录</span>
  [#else]
  <span>共${totalRow}条 / 共${totalPage}页</span>
  <div class="pagecont">
    [#assign pagingSize = 10]
    <a href="javascript:goPage('${value.postlinkPage}');" class="last">下一页</a>
  </div>
  [/#if]
</div>

<script type="text/javascript">
  function goPage(pageNo) {
    try{
      var tableForm = document.getElementById('${form}');
      document.getElementById("currentPage").value=pageNo;
      tableForm.onsubmit=null;
      tableForm.submit();
    } catch(e) {
      alert('goPage(pageNo)方法出错');
    }
  }
</script>
[/#macro]