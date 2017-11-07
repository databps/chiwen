[#macro value
	list value="" listKey="" listValue="" id="" name="" 
]

[#list list as item]
	[#if item[listKey]?string==value?string]${item[listValue]}[/#if]
[/#list]

[/#macro]


<!-- 数据字典-显示  value:单值;valueList:多值，以逗号间隔-->
[#macro constant_value
   code_name="" parent_code=""	 value="" valueList=""  id="" name="" 
]

	[#list Application['constants'] as item]
		[#if item.NAME?string==code_name?string || item.PARENTCODE?string==parent_code?string]
			[#if (value?string!='' &&  item.CODE?string=value?string)]
				${item.CODEDSCR}
			[/#if]
			[#if (valueList?length)[0]]
				[#assign valueLists=valueList?split(',')/]
				[#if valueLists?seq_contains(item.CODE?string)]
					${item.CODEDSCR}
				[/#if]
			[/#if]
		[/#if]
	[/#list]

[/#macro]

<!-- 数据字典-select-->
[#macro constant_select
	code_name="" parent_code="" value=""  id="" name="" 
	class="" headerKey="" headerValue=""  headerButtom="false" required="" combobox="" onchange=""
	rule="" disabled=""
	]
<select class="select-normal"[#rt/]
[#if id!=""] id="${id}"[/#if][#rt/]
[#if name!=""] name="${name}"[/#if][#rt/]
[#if required!=""] data-rule="${required}"[/#if][#rt/]
[#if combobox=="true"] combobox="true"[/#if][#rt/]
[#if onchange!=""] onchange="${onchange}"[/#if][#rt/]
[#if rule!=""] data-rule="${rule}"[/#if][#rt/]
[#if disabled!=""] disabled="${disabled}"[/#if][#rt/]
>[#rt/]
[#if headerButtom=="false"]
[#if headerKey!="" || headerValue!=""]
	<option value="${headerKey}"[#if headerKey==value?string] selected="selected"[/#if]>请选择</option>[#t/]
[/#if]
[/#if]
	[#list Application['constants'] as item]
		[#if item.LEVEL!='1' && (item.NAME?string==code_name?string || item.PARENTCODE?string==parent_code?string)]
			<option value="${item.CODE}"[#if item.CODE?string==value?string] selected="selected"[/#if]>${item.CODEDSCR}</option>[#t/]
		[/#if]	
	[/#list]
</select>
[/#macro]

<!-- 数据字典-radio -->
[#macro constant_radio
	code_name="" parent_code="" value=""  id="" name="" 
	required="false" rule=""
	]
	[#list Application['constants'] as item]
		[#if  item.LEVEL!='1' && (item.NAME?string==code_name?string || item.PARENTCODE?string==parent_code?string)]
			[#local rkey=item.CODE]
			[#local rvalue=item.CODEDSCR]
			[#local index=item_index]
			[#local hasNext=item_has_next]
			[#include "/ftl/pony/ui/radio-item.ftl"][#t/]
		[/#if]	
	[/#list]
[/#macro]

<!-- 数据字典-checkbox  value：默认值，以逗号间隔-->
[#macro constant_checkbox
	code_name="" parent_code="" value=""  id="" name=""
	label="" noHeight="false" colspan="" width="100" help="" helpPosition="2" colon=":" hasColon="true"
	class="" style="" size="" title="" disabled="" tabindex="" accesskey="" rule=""
	]
	[#assign valueList=value?split(',')/]
	[#list Application['constants'] as item]
		[#if  item.LEVEL!='1' && (item.NAME?string==code_name?string || item.PARENTCODE?string==parent_code?string)]
			[#local rkey=item.CODE]
			[#local rvalue=item.CODEDSCR]
			[#local index=item_index]
			[#local hasNext=item_has_next]
			[#include "/ftl/pony/ui/checkboxlist-item.ftl"][#t/]
		[/#if]
	[/#list]
[/#macro]