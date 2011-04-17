/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mule.tools.cloudconnect.model;

import java.util.List;
import java.util.Set;

public interface JavaClass extends JavaAnnotatedElement
{

    String getPackage();

    String getDescription();

    List<JavaProperty> getProperties();

    boolean hasProperties();

    List<JavaMethod> getMethods();

    List<JavaField> getFields();

    Set<JavaType> getEnums();

    Set<JavaType> getXmlTypes();

    boolean isConnector();

    String getNamespacePrefix();

    String getNamespaceUri();

    String getMuleVersion();

    JavaClass getFactory();

    JavaModel getParentModel();

    String getFullyQualifiedName();

    String getNamespaceHandlerName();

    String getNamespaceHandlerPackage();

    boolean isXmlType();

    JavaClass getSuperClass();

    boolean isInterface();

    boolean isInnerClass();

    boolean isFinal();

    boolean isAbstract();

    boolean isEnum();

    boolean hasDefaultConstructor();

    JavaMethod getMethodBySignature(String name, List<JavaParameter> types);

    boolean hasOAuth();

    String getOAuthAuthorizationUrl();

    String getOAuthAccessTokenUrl();

    JavaProperty getOAuthClientIdProperty();

    JavaProperty getOAuthClientSecretProperty();

    JavaProperty getOAuthScopeProperty();

    JavaProperty getOAuthRedirectUriProperty();

    JavaProperty getOAuthAccessTokenProperty();
}
