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
                <xsd:attribute name="name" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Give a name to this configuration so it can be later referenced by config-ref.
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
            <#if class.getFactory()?has_content>
            <#list class.getFactory().getProperties() as property>
                <xsd:attribute name="<@uncapitalize>${property.getElementName()}</@uncapitalize>" type="${property.getType().getXmlType(true)}" use="<#if property.isOptional()>optional<#else>required</#if>"<#if property.hasDefaultValue()> default="${property.getDefaultValue()}"</#if>>
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
                <#if property.isConfigurable()>
                <xsd:attribute name="<@uncapitalize>${property.getElementName()}</@uncapitalize>" type="${property.getType().getXmlType(true)}" use="<#if property.isOptional()>optional<#else>required</#if>"<#if property.hasDefaultValue()> default="${property.getDefaultValue()}"</#if>>
                    <#if property.getDescription()?has_content>
                    <xsd:annotation>
                        <xsd:documentation><![CDATA[
                            ${property.getDescription()}
                        ]]></xsd:documentation>
                    </xsd:annotation>
                    </#if>
                </xsd:attribute>
                </#if>
            </#list>
            </#if>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    <!-- OAuth Operations -->
    <#if class.hasOAuth()>
    <xsd:element name="request-authorization" type="requestAuthorizationType" substitutionGroup="mule:abstract-message-processor">
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                Request OAuth authorization. This operation will set http.status and Location properties to redirect the user to the
                authorization server.
            ]]></xsd:documentation>
        </xsd:annotation>
    </xsd:element>
    <xsd:complexType name="requestAuthorizationType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <xsd:all>
                </xsd:all>
                <xsd:attribute name="config-ref" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Specify which configuration to use for this invocation
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    <xsd:element name="has-been-authorized" type="hasBeenAuthorizedType" substitutionGroup="mule:abstract-message-processor">
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                Verifies if this connector has already been authorized. If it has not, then call request-authorization.
                This operation does not modifies the payload of the message, instead it adds an "authorized" outbound
                property.
            ]]></xsd:documentation>
        </xsd:annotation>
    </xsd:element>
    <xsd:complexType name="hasBeenAuthorizedType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <xsd:all>
                </xsd:all>
                <xsd:attribute name="config-ref" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Specify which configuration to use for this invocation
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    <xsd:element name="request-access-token" type="requestAccessTokenType" substitutionGroup="mule:abstract-message-processor">
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                Request an access token using the authorization code.
            ]]></xsd:documentation>
        </xsd:annotation>
    </xsd:element>
    <xsd:complexType name="requestAccessTokenType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <xsd:all>
                </xsd:all>
                <xsd:attribute name="config-ref" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Specify which configuration to use for this invocation
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>

    <xsd:element name="set-authorization-code" type="setAuthorizationCodeType" substitutionGroup="mule:abstract-message-processor">
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                Sets the autorization code received in the OAuth redirect URI.
            ]]></xsd:documentation>
        </xsd:annotation>
    </xsd:element>
    <xsd:complexType name="setAuthorizationCodeType">
        <xsd:complexContent>
            <xsd:extension base="mule:abstractInterceptingMessageProcessorType">
                <xsd:all>
                </xsd:all>

                <xsd:attribute name="config-ref" use="optional" type="xsd:string">
                    <xsd:annotation>
                        <xsd:documentation>
                            Specify which configuration to use for this invocation
                        </xsd:documentation>
                    </xsd:annotation>
                </xsd:attribute>
                <xsd:attribute name="code" type="xsd:string" use="required" >
                </xsd:attribute>
            </xsd:extension>
        </xsd:complexContent>
    </xsd:complexType>
    </#if>

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
                    <xsd:element name="<@uncapitalize>${parameter.getElementName()}</@uncapitalize>"<#if parameter.isOptional()> minOccurs="0"</#if>>
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
                    <xsd:element name="<@uncapitalize>${parameter.getElementName()}</@uncapitalize>" <#if parameter.isOptional()> minOccurs="0"</#if>>
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

    <!-- Transformers -->
    <#list class.getMethods() as method>
    <#if method.isTransformer()>
    <xsd:element name="<@splitCamelCase>${method.getElementName()}</@splitCamelCase>" type="mule:abstractTransformerType"
                 substitutionGroup="mule:abstract-transformer">
        <#if method.getDescription()?has_content>
        <xsd:annotation>
            <xsd:documentation><![CDATA[
                ${method.getDescription()}
            ]]></xsd:documentation>
        </xsd:annotation>
        </#if>
    </xsd:element>
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

    <!-- XML Types -->
    <#list class.getXmlTypes() as xmlType>
    ${xmlType.getJavaClass().getXmlComplexType()}

    </#list>
</xsd:schema>
