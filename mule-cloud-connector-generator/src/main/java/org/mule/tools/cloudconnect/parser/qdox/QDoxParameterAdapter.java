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

package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.AbstractJavaElement;
import org.mule.tools.cloudconnect.model.JavaParameter;

import com.thoughtworks.qdox.model.DocletTag;

public class QDoxParameterAdapter extends AbstractJavaElement implements JavaParameter
{

    private com.thoughtworks.qdox.model.JavaParameter javaParameter;

    public QDoxParameterAdapter(com.thoughtworks.qdox.model.JavaParameter javaParameter)
    {
        this.javaParameter = javaParameter;
    }

    public String getName()
    {
        return javaParameter.getName();
    }

    public String getType()
    {
        return javaParameter.getType().getValue();
    }

    public boolean isEnum()
    {
        return javaParameter.getType().getJavaClass().isEnum();
    }

    public String getEnumName()
    {
        return javaParameter.getType().getJavaClass().getName();
    }

    public String getDescription()
    {
        if (javaParameter.getParentMethod() != null)
        {
            DocletTag[] doclets = javaParameter.getParentMethod().getTagsByName("param");

            for (int i = 0; i < doclets.length; i++)
            {
                if (doclets[i].getParameters()[0].equals(javaParameter.getName()))
                {
                    if (doclets[i].getValue().indexOf(' ') != -1)
                    {
                        return doclets[i].getValue().substring(doclets[i].getValue().indexOf(' '));
                    }
                }
            }
        }

        return null;
    }
}
