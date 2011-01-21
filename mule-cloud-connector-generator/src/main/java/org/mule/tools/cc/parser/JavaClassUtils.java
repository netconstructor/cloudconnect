/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.parser;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;

import java.util.ArrayList;
import java.util.List;

public class JavaClassUtils
{
    public static List<JavaMethod> collectSetters(JavaClass javaClass)
    {
        List<JavaMethod> collectedMethods = new ArrayList<JavaMethod>();
        JavaMethod[] methods = javaClass.getMethods();

        for( int i = 0; i < methods.length; i++ )
        {
            if (isSetterMethod(methods[i]))
            {
                collectedMethods.add(methods[i]);
            }
        }
        
        return collectedMethods;
    }

    public static List<JavaMethod> collectOperations(JavaClass javaClass)
    {
        List<JavaMethod> collectedMethods = new ArrayList<JavaMethod>();
        JavaMethod[] methods = javaClass.getMethods();

        for( int i = 0; i < methods.length; i++ )
        {
            if (!isSetterMethod(methods[i]) && !isGetterMethod(methods[i]) && methods[i].isPublic() && methods[i].getParameters().length > 0)
            {
                collectedMethods.add(methods[i]);
            }
        }

        return collectedMethods;
    }

    public static boolean isSetterMethod(JavaMethod method)
    {
        if (method.getName().startsWith("set") && (method.getParameters().length == 1))
        {
            return true;
        }
        return false;
    }

    public static boolean isGetterMethod(JavaMethod method)
    {
        if (method.getName().startsWith("get") && (method.getParameters().length == 0))
        {
            return true;
        }
        return false;
    }

    private JavaClassUtils()
    {
        // do not create instances of this class;
    }
}


