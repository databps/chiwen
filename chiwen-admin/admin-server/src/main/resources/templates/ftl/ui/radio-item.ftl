<label class="radio-inline"><input type="radio"[#rt/]
 value="${rkey}" [#if index==0 && rule!=""] data-rule=${rule} [/#if] [#rt/]
[#if onclick!=""] onclick=${onclick+'(\''+params+rkey+'\')'}[/#if] [#rt/]
[#if (rkey?string=="" && (!value?? || value?string=="")) || (value?? && value?string!="" && value?string==rkey?string)] checked="checked"[/#if][#rt/]
 name="${name}"
/>${rvalue}</label>[#if hasNext] [/#if]