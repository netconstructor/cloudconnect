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

import org.mule.tools.cloudconnect.model.JavaClass;
import org.mule.tools.cloudconnect.model.JavaModel;
import org.mule.tools.cloudconnect.model.JavaType;

import com.thoughtworks.qdox.JavaDocBuilder;
import com.thoughtworks.qdox.parser.ParseException;

import java.io.File;
import java.util.List;

public class QDoxModel extends JavaModel
{

    private JavaDocBuilder javaDocBuilder = new JavaDocBuilder();

    public JavaType getVoidType()
    {
        return new QDoxTypeAdapter(new com.thoughtworks.qdox.model.Type("void"));
    }

    public void parse(List<File> sourceTrees)
    {
        for (File sourceTree : sourceTrees)
        {
            javaDocBuilder.addSourceTree(sourceTree);
        }

        try
        {
            if (javaDocBuilder.getClasses() == null)
            {
                throw new RuntimeException("Sources does not contain any class");
            }

            for (int i = 0; i < javaDocBuilder.getClasses().length; i++)
            {
                addClass(new QDoxClassAdapter(javaDocBuilder.getClasses()[i], this));
            }
        }
        catch (ParseException pe)
        {
            throw new RuntimeException("Cannot parse", pe);
        }
    }

    public JavaType getType(Class c)
    {
        if( c.isArray() )
        {
            String typeName = c.getComponentType() != null ? c.getComponentType().getName() : c.getName();

            return new QDoxTypeAdapter(new com.thoughtworks.qdox.model.Type(typeName, 1));
        }
        else if( c.isPrimitive() )
        {
            if( c == void.class )
                return getVoidType();

            return new QDoxTypeAdapter(new com.thoughtworks.qdox.model.Type(c.getName(), 1));
        }
        else
        {
            return new QDoxTypeAdapter(javaDocBuilder.getClassByName(c.getName()).getSuperClass());
        }
    }

    public JavaClass getClass(String name)
    {
        for( JavaClass c : this.getClasses() )
        {
            if( c.getName().equals(name) )
                return c;
        }

        return null;
    }
}
