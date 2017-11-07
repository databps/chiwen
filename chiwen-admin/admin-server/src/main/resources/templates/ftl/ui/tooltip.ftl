<#--
tooltip
-->
[#macro tooltip  value][#if value?length gt 50]${value?substring(0,50)}...[#else]${value}[/#if][/#macro]