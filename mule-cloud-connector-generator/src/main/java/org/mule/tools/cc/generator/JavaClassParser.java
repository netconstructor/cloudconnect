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

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.io.InputStream;
import java.util.List;

public class JavaClassParser
{
    private CompilationUnit compilationUnit;

    public JavaClass parse(InputStream input)
    {
        parseCompilationUnit(input);

        TypeDeclaration typeDecl = typeDeclarationFromCompilationUnit();
        String packageName = packageNameFromCompilationUnit();
        return new TypeDeclarationJavaClass(typeDecl, packageName);
    }

    private void parseCompilationUnit(InputStream input)
    {
        try
        {
            compilationUnit = JavaParser.parse(input, "UTF-8");
        }
        catch (ParseException pe)
        {
            throw new IllegalArgumentException(pe);
        }
    }

    private TypeDeclaration typeDeclarationFromCompilationUnit()
    {
        List<TypeDeclaration> typeDeclarations = compilationUnit.getTypes();
        if (typeDeclarations == null)
        {
            throw new IllegalArgumentException("Source file does not contain a Java class");
        }

        if (typeDeclarations.size() > 1)
        {
            throw new IllegalArgumentException("Source file contains more than one Java class");
        }

        return typeDeclarations.get(0);
    }

    private String packageNameFromCompilationUnit()
    {
        PackageDeclaration packageDeclaration = compilationUnit.getPackage();
        if (packageDeclaration == null)
        {
            throw new IllegalArgumentException("Source file does not declare a package. Default package is unsupported.");
        }
        return packageDeclaration.getName().toString();
    }
}
