<label class="checkbox-inline" title=""><input type="checkbox"[#rt/]
 value="${rkey}" view-name="${rvalue}" [#if index==0 &&rule!=""] data-rule=${rule}[/#if] name="${name}"[#rt/]
[#if valueList?seq_contains(rkey)] checked="checked"[/#if][#rt/]
/>${rvalue}</label>[#if hasNext] [/#if]