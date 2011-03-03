<#list enums as enum>
<@uncapitalize>${enum.getTransformerName()}</@uncapitalize>=${enum.getTransformerPackage()}.${enum.getTransformerName()}
</#list>