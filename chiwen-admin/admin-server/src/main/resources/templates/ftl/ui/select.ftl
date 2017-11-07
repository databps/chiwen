<#--
<select><option></option></select>
-->
[#macro select
	list value="" listKey="" listValue="" listDeep=""
	id="" name="" class="" headerKey="" headerValue=""  headerButtom="false" required="" combobox="" onchange=""
	rule="" disabled="" readonly="" onchange=""
	]
<select [#rt/]
[#if id!=""] id="${id}"[/#if][#rt/]
[#if name!=""] name="${name}"[/#if][#rt/]
[#if required!=""] data-rule="${required}"[/#if][#rt/]
[#if combobox=="true"] combobox="true"[/#if][#rt/]
[#if onchange!=""] onchange="${onchange}"[/#if][#rt/]
[#if rule!=""] data-rule="${rule}"[/#if][#rt/]
[#if disabled!=""] disabled="${disabled}"[/#if][#rt/]
[#if readonly!=""] readonly="${readonly}"[/#if][#rt/]
[#if onchange!=""] onchange="${onchange}"[/#if][#rt/]
>[#rt/]
[#if headerButtom=="false"]
[#if headerKey!="" || headerValue!=""]
	<option value="${headerKey}"[#if headerKey==value?string] selected="selected"[/#if]>${headerValue}</option>[#t/]
[/#if]
[/#if]
[#if list?is_sequence]
	[#if listKey!="" && listValue!=""]
		[#if listDeep!="" && list?size gt 0][#local origDeep=list[0][listDeep]+1/][/#if]
		[#list list as item]
			<option value="${item[listKey]}"[#if item[listKey]?string==value?string] selected="selected"[/#if]>[#if listDeep!="" && item[listDeep] gte origDeep][#list origDeep..item[listDeep] as i]&nbsp;&nbsp;[/#list]>[/#if]${item[listValue]!}</option>[#t/]
		[/#list]
	[#else]
		[#list list as item]
			<option value="${item}"[#if item==value] selected="selected"[/#if]>${item}</option>[#t/]
		[/#list]
	[/#if]
[#else]
	[#list list?keys as key]
		<option value="${key}"[#if key==value?string] selected="selected"[/#if]>${list[key]}</option>[#t/]
	[/#list]
[/#if]
</select>
[/#macro]
