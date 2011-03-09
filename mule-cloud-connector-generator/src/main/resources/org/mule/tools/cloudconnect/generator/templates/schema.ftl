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

    <!-- Configuration -->
    <xsd:element name="config" type="configType" substitutionGroup="mule:abstract-extension"/>
    <xsd:complexType name="configType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractExtensionType">
                <xsd:attribute name="name" use="optional" type="mule:substitutableName">
                    <xsd:annotation>
                        <xsd:documentation>
                            Give a name to this configuration so it can be later referenced by config-ref.
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
            <#if class.getFactory()?has_content>
            <#list class.getFactory().getProperties() as property>
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="${property.getType().getXmlType(true)}" use="required">
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
                <xsd:attribute name="<@uncapitalize>${property.getName()}</@uncapitalize>" type="${property.getType().getXmlType(true)}">
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

    <!-- Operations -->
    <#list class.getMethods() as method>
    <#if method.isOperation()>
    <xsd:element name="<@splitCamelCase>${method.getElementName()}</@splitCamelCase>" type="${method.getElementName()}Type" substitutionGroup="mule:abstract-message-processor">
        <#if method.getDescription()?has_content>
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                ${method.getDescription()}
            ]]></xsd:documentation>
        </xsd:annotation>
        </#if>
    </xsd:element>
    <xsd:complexType name="${method.getElementName()}Type">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <xsd:all>
                    <#list method.getParameters() as parameter>
                    <#if parameter.getType().isArray() || parameter.getType().isList()>
                    <xsd:element name="<@uncapitalize>${parameter.getElementName()}</@uncapitalize>">
                        <xsd:complexType>
                            <xsd:sequence>
                                <xsd:element name="<@singularize>${parameter.getElementName()}</@singularize>" minOccurs="0" maxOccurs="unbounded"
                                             <#if parameter.getType().isList()>
                                             type="${parameter.getType().getTypeArguments().get(0).getXmlType(false)}"/>
                                             <#else>
                                             type="${parameter.getType().getXmlType(false)}"/>
                                             </#if>
                            </xsd:sequence>
                        </xsd:complexType>
                    </xsd:element>
                    <#elseif parameter.getType().isMap()>
                    <xsd:element name="<@uncapitalize>${parameter.getElementName()}</@uncapitalize>">
                        <xsd:complexType>
                            <xsd:sequence>
                                <xsd:element name="<@singularize>${parameter.getElementName()}</@singularize>" minOccurs="0" maxOccurs="unbounded">
                                    <xsd:complexType>
                                        <xsd:attribute name="key" type="${parameter.getType().getTypeArguments().get(0).getXmlType(false)}"/>
                                        <xsd:attribute name="value" type="${parameter.getType().getTypeArguments().get(1).getXmlType(false)}"/>
                                    </xsd:complexType>
                                </xsd:element>
                            </xsd:sequence>
                        </xsd:complexType>
                    </xsd:element>
                    </#if>
                    </#list>
                </xsd:all>

                <xsd:attribute name="config-ref" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Specify which configuration to use for this invocation
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
                <#list method.getParameters() as parameter>
                <#if !parameter.getType().isArray() && !parameter.getType().isList() && !parameter.getType().isMap()>
                <xsd:attribute name="<@uncapitalize>${parameter.getElementName()}</@uncapitalize>" type="${parameter.getType().getXmlType(false)}" <#if !parameter.isOptional()>use="required" </#if><#if parameter.hasDefaultValue()>default="${parameter.getDefaultValue()}"</#if>>
                    <#if parameter.getDescription()?has_content>
                    <xsd:annotation>
                        <xsd:documentation><![CDATA[
                            ${parameter.getDescription()}
                        ]]></xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
                </#if>
                </#list>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    </#if>
    </#list>

    <!-- Enums -->
    <#list class.getEnums() as enum>
    <xsd:simpleType name="${enum.getXmlType(false)}">
        <xsd:union>
            <xsd:simpleType>
                <xsd:restriction base="xsd:string">
                    <#list enum.getValues() as enumValue>
                    <xsd:enumeration value="${enumValue}" />
                    </#list>
                </xsd:restriction>
            </xsd:simpleType>
            <xsd:simpleType>
                <xsd:restriction base="xsd:string">
                    <xsd:pattern value="\#\[[^\]]+\]"/>
                </xsd:restriction>
            </xsd:simpleType>
        </xsd:union>
    </xsd:simpleType>

    </#list>
</xsd:schema>
