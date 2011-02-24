<#list enums as enum>
<@uncapitalize>${enum.getTransformerName()}</@uncapitalize>=${packageName}.${enum.getTransformerName()}
</#list>