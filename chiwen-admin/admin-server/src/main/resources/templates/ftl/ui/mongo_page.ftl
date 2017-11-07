<#--
分页查询
-->
[#macro mongo_page value form]
[#assign pageNumber=(value.currentPage)!1 pageSize=(value.maxResults)!8 totalPage=(value.totalPage)!0 totalRow=(value.totalResults)!0]
<div class="page">

  [#if totalPage == 0]
  <span>没有记录</span>
  [#else]
  <span>共${totalRow}条</span>
  <ul class="pagination pagination-sm">
    [#assign pagingSize = 10]
    <!-- 首页 -->
    <li class="previous"><a href="javascript:goPage('${value.firstPage}');" class="first">首页</a></li>

    <!-- 上一页 -->
    [#if value.currentPage!=value.firstPage]
    <li><a href="javascript:goPage('${value.prelinkPage}');" class="first">上一页</a></li>
    [/#if]

    <!-- 不能全部显示 -->
    [#if (totalPage > pagingSize)]
    [#assign startPage = pageNumber+1 - (pagingSize / 2) ? floor]
    [#if (startPage < 1)]
    [#assign startPage = 1]
    [/#if]

    [#assign endPage = startPage + pagingSize - 1]

    [#if (endPage > totalPage)]
    [#assign endPage = totalPage startPage = totalPage - pagingSize + 1]
    [/#if]
    [#else]
    [#assign startPage = 1 endPage = totalPage]
    [/#if]

    <!-- ... -->
    [#if (totalPage > pagingSize && startPage != 1)]
    <li><a href="javascript:void(0)">...</a></li>
    [/#if]

    [#list startPage..endPage as i]
    [#if pageNumber == i]
    <!-- 当前页 -->
    <li class="active"><a href="javascript:void(0)" >${i}</a></li>
    [#else]
    <li><a href="javascript:goPage('${i}');">${i}</a></li>
    [/#if]

    [/#list]

    <!-- ... -->
    [#if (totalPage > pagingSize && endPage != totalPage)]
    <li><a href="javascript:void(0)">...</a></li>
    [/#if]

    <!-- 下一页 -->
    [#if value.currentPage!=value.lastPage]
    <li><a href="javascript:goPage('${value.postlinkPage}');" class="last">下一页</a></li>
    [/#if]

    <!-- 尾页 -->
    <li class="next"><a href="javascript:goPage('${value.lastPage}');" class="last-my">尾页</a></li>
  </ul>
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