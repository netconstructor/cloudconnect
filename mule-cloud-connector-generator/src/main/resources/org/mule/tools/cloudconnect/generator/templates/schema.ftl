<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsd:schema xmlns="${class.getNamespaceUri()}"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:mule="http://www.mulesoft.org/schema/mule/core"
            xmlns:schemadoc="http://www.mulesoft.org/schema/mule/schemadoc"
            xmlns:beans="http://www.springframework.org/schema/beans"
            targetNamespace="${class.getNamespaceUri()}"
            elementFormDefault="qualified"
            attributeFormDefault="unqualified">

    <xsd:import namespace="http://www.w3.org/XML/1998/namespace"/>
    <xsd:import namespace="http://www.springframework.org/schema/beans"
                schemaLocation="http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"/>
    <xsd:import namespace="http://www.mulesoft.org/schema/mule/core"
                schemaLocation="http://www.mulesoft.org/schema/mule/core/${class.getMuleVersion()}/mule.xsd"/>
    <xsd:import namespace="http://www.mulesoft.org/schema/mule/schemadoc"
                schemaLocation="http://www.mulesoft.org/schema/mule/schemadoc/${class.getMuleVersion()}/mule-schemadoc.xsd"/>

    <xsd:annotation>
        <xsd:documentation>
            This schema was auto-generated. Do not edit.
        </xsd:documentation>
    </xsd:annotation>

    <#if class.hasProperties()>
    <!-- Configuration -->
    <xsd:element name="config" type="configType" substitutionGroup="mule:abstract-extension"/>
    <xsd:complexType name="configType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractExtensionType">
            <#if class.getFactory()?has_content>
            <#list class.getFactory().getProperties() as property>
                <#if property.isEnum()>
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="${property.getEnumName()}Enum">
                <#else>
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="<@typeMap>${property.getType()}</@typeMap>">
                </#if>
                    <#if property.getDescription()?has_content>
                    <xsd:annotation>
                        <xsd:documentation><![CDATA[
                            ${property.getDescription()}
                        ]]></xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
            </#list>
            <#else>
            <#list class.getProperties() as property>
                <#if property.isEnum()>
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="${property.getEnumName()}Enum">
                <#else>
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="<@typeMap>${property.getType()}</@typeMap>">
                </#if>
                    <#if property.getDescription()?has_content>
                    <xsd:annotation>
                        <xsd:documentation><![CDATA[
                            ${property.getDescription()}
                        ]]></xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
            </#list>
            </#if>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    </#if>
    <!-- Operations -->
    <#list class.getMethods() as method>
    <#if method.isOperation()>
    <xsd:element name="<@splitCamelCase>${method.getName()}</@splitCamelCase>" type="${method.getName()}Type" substitutionGroup="mule:abstract-message-processor">
        <#if method.getDescription()?has_content>
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                ${method.getDescription()}
            ]]></xsd:documentation>
        </xsd:annotation>
        </#if>
    </xsd:element>
    <xsd:complexType name="${method.getName()}Type">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <#list method.getParameters() as parameter>
                <#if parameter.isEnum()>
                <xsd:attribute name="${parameter.getName()}" type="${parameter.getEnumName()}Enum">
                <#else>
                <xsd:attribute name="${parameter.getName()}" type="<@typeMap>${parameter.getType()}</@typeMap>">
                </#if>
                    <#if parameter.getDescription()?has_content>
                    <xsd:annotation>
                        <xsd:documentation><![CDATA[
                            ${parameter.getDescription()}
                        ]]></xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
                </#list>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    </#if>
    </#list>

    <!-- Enums -->
    <#list class.getEnums() as enum>
    <xsd:simpleType name="${enum.getName()}Enum">
        <xsd:restriction base="xsd:string">
            <#list enum.getValues() as enumValue>
            <xsd:enumeration value="${enumValue}" />
            </#list>
        </xsd:restriction>
    </xsd:simpleType>

    </#list>
</xsd:schema>
