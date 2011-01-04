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

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.util.ArrayList;
import java.util.List;

public class TypeDeclarationJavaClass implements JavaClass
{
    private String packageName;
    private TypeDeclaration typeDeclaration;

    public TypeDeclarationJavaClass(TypeDeclaration typeDecl, String pkgName)
    {
        super();
        typeDeclaration = typeDecl;
        packageName = pkgName;
    }

    public List<JavaMethod> getMethods()
    {
        MethodCollector collector = new MethodCollector();
        typeDeclaration.accept(collector, null);
        return collector.javaMethods();
    }

    public String getPackage()
    {
        return packageName;
    }

    public String getName()
    {
        return typeDeclaration.getName();
    }

    private static class MethodCollector extends VoidVisitorAdapter<Object>
    {
        private List<JavaMethod> methods = new ArrayList<JavaMethod>();

        public MethodCollector()
        {
            super();
        }

        public List<JavaMethod> javaMethods()
        {
            return methods;
        }

        @Override
        public void visit(MethodDeclaration declaration, Object arg)
        {
            methods.add(new MethodDeclarationJavaMethod(declaration));
        }
    }
}
