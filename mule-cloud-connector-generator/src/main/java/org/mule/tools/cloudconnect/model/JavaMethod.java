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

public interface JavaMethod extends JavaAnnotatedElement
{

    String getDescription();

    boolean hasParameters();

    List<JavaParameter> getParameters();

    boolean isOperation();

    boolean isTransformer();

    boolean isPublic();

    boolean isPropertyAccessor();

    boolean isPropertyMutator();

    boolean isConstructor();

    boolean isStatic();

    boolean isVolatile();

    JavaClass getParentClass();

    String getBeanDefinitionParserName();

    String getMessageProcessorName();

    JavaType getReturnType();

    String getExample();

    boolean returnAsProperty();
}
