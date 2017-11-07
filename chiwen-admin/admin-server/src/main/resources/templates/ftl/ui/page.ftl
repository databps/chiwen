<#--
分页查询
-->
[#macro page value form]
 [#assign pageNumber=(value.number)!1 pageSize=(value.size)!8 totalPage=(value.totalPages)!0 totalRow=(value.totalElements)!0]
<div class="page">

  [#if totalPage == 0]
    <span>没有记录</span>
  [#else]
  <span>共${totalRow}条 / 共${totalPage}页</span>
  <div class="pagecont">
      [#assign pagingSize = 7]
      <!-- 首页 -->
      [#if (totalPage > pagingSize)]
        [#if pageNumber == 0]
        [#else]
    <a href="javascript:goPage('0');" class="first">首页</a>
        [/#if]
      [/#if]

      <!-- 上一页 -->
      [#if pageNumber == 0]
      [#else]
    <a href="javascript:goPage('${pageNumber-1}');" class="first">上一页</a>
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
    <a href="javascript:void(0)">...</a>
      [/#if]

      [#list startPage..endPage as i]
        [#if pageNumber+1 == i]
          <!-- 当前页 -->
    <a href="javascript:void(0)" class="on">${i}</a>
        [#else]
    <a href="javascript:goPage('${i-1}');">${i}</a>
        [/#if]

      [/#list]

      <!-- ... -->
      [#if (totalPage > pagingSize && endPage != totalPage)]
        <a href="javascript:void(0)">...</a>
      [/#if]

      <!-- 下一页 -->
      [#if pageNumber == totalPage-1]
      [#else]
    <a href="javascript:goPage('${pageNumber+1}');" class="last">下一页</a>
      [/#if]

      <!-- 尾页 -->
      [#if (totalPage-1 > pagingSize)]
        [#if pageNumber == totalPage-1]
        [#else]
    <a href="javascript:goPage('${value.totalPages-1}');" class="last-my">尾页</a>
        [/#if]
      [/#if]
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