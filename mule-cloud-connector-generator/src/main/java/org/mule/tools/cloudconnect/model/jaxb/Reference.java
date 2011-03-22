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

package org.mule.tools.cloudconnect.model.jaxb;

import org.mule.tools.cloudconnect.model.JavaAnnotatedElement;
import org.mule.tools.cloudconnect.model.JavaMethod;
import org.mule.tools.cloudconnect.model.JavaParameter;
import org.mule.tools.cloudconnect.model.JavaType;


public final class Reference
{

    /**
     * The JAXB type being referenced. Must not be null.
     */
    public JavaType type;
    /**
     * The declaration from which annotations for the {@link #type} is read.
     * Must not be null.
     */
    public JavaAnnotatedElement annotations;

    /**
     * Creates a reference from the return type of the method
     * and annotations on the method.
     */
    public Reference(JavaMethod method)
    {
        this(method.getReturnType(), method);
    }

    /**
     * Creates a reference from the parameter type
     * and annotations on the parameter.
     */
    public Reference(JavaParameter param)
    {
        this(param.getType(), param);
    }

    /**
     * Creates a reference by providing two values independently.
     */
    public Reference(JavaType type, JavaAnnotatedElement annotations)
    {
        if (type == null || annotations == null)
        {
            throw new IllegalArgumentException();
        }
        this.type = type;
        this.annotations = annotations;
    }

    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Reference))
        {
            return false;
        }

        final Reference that = (Reference) o;

        return annotations.equals(that.annotations) && type.equals(that.type);
    }

    public int hashCode()
    {
        return 29 * type.hashCode() + annotations.hashCode();
    }
}
