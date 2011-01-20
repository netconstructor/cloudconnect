<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<xsd:schema xmlns="http://www.mulesoft.org/schema/mule/${namespaceIdentifierSuffix}"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema"
            xmlns:mule="http://www.mulesoft.org/schema/mule/core"
            xmlns:schemadoc="http://www.mulesoft.org/schema/mule/schemadoc"
            xmlns:beans="http://www.springframework.org/schema/beans"
            targetNamespace="http://www.mulesoft.org/schema/mule/${namespaceIdentifierSuffix}"
            elementFormDefault="qualified"
            attributeFormDefault="unqualified">

    <xsd:import namespace="http://www.w3.org/XML/1998/namespace"/>
    <xsd:import namespace="http://www.springframework.org/schema/beans"
                schemaLocation="http://www.springframework.org/schema/beans/spring-beans-3.0.xsd"/>
    <xsd:import namespace="http://www.mulesoft.org/schema/mule/core"
                schemaLocation="http://www.mulesoft.org/schema/mule/core/${schemaVersion}/mule.xsd"/>
    <xsd:import namespace="http://www.mulesoft.org/schema/mule/schemadoc"
                schemaLocation="http://www.mulesoft.org/schema/mule/schemadoc/${schemaVersion}/mule-schemadoc.xsd"/>

    <xsd:annotation>
        <xsd:documentation>
            This schema was auto-generated. Do not edit.
        </xsd:documentation>
    </xsd:annotation>

    <#if hasSetters>
    <!-- Configuration -->
    <xsd:element name="config" type="configType" substitutionGroup="mule:abstract-extension"/>
    <xsd:complexType name="configType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractExtensionType">
            <#list setters as setter>
                <xsd:attribute name="<@uncapitalize>${setter.getName().substring(3)}</@uncapitalize>" type="<@typeMap>${setter.getParameters()[0].getType().getValue()}</@typeMap>">
                    <#if setter.getComment()?has_content>
                    <xsd:annotation>
                        <xsd:documentation>
                            ${setter.getComment()}
                        </xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
            </#list>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    </#if>
    <!-- Operations -->
    <#list operations as operation>
    <xsd:element name="<@splitCamelCase>${operation.getName()}</@splitCamelCase>" type="${operation.getName()}Type" substitutionGroup="mule:abstract-message-processor">
        <#if operation.getComment()?has_content>
        <xsd:annotation>
            <xsd:documentation>
                ${operation.getComment()}
            </xsd:documentation>
        </xsd:annotation>
        </#if>
    </xsd:element>
    <xsd:complexType name="${operation.getName()}Type">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <#list operation.getParameters() as parameter>
                <xsd:attribute name="${parameter.getName()}" type="<@typeMap>${parameter.getType().getValue()}</@typeMap>">
                    <#if operation.getTagsByName("param")?has_content>
                    <xsd:annotation>
                        <xsd:documentation>
                            <#list operation.getTagsByName("param") as doclet>
                            <#if doclet.getParameters()[0] == parameter.getName()>
                            ${doclet.getValue().substring(doclet.getValue().indexOf(' '))}
                            </#if>
                            </#list>
                        </xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
                </#list>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    </#list>
</xsd:schema>
