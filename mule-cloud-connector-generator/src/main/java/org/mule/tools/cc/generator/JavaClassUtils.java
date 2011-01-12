/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cc.generator;

import java.util.ArrayList;
import java.util.List;

public class JavaClassUtils
{
    public static List<JavaMethod> collectSetters(JavaClass javaClass)
    {
        List<JavaMethod> collectedMethods = new ArrayList<JavaMethod>();

        for (JavaMethod method : javaClass.getMethods())
        {
            if (isSetterMethod(method))
            {
                collectedMethods.add(method);
            }
        }
        
        return collectedMethods;
    }

    public static boolean isSetterMethod(JavaMethod method)
    {
        if (method.getName().startsWith("set") && (method.getParameters().size() == 1))
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


