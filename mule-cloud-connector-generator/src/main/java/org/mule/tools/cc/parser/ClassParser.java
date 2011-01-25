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

import org.mule.tools.cc.model.JavaClass;

import java.io.File;
import java.io.InputStream;

public interface ClassParser
{
    void setLog(ClassParserLog log);

    void addSourceTree(File sourceTree);

    JavaClass parse(String sourceFile) throws ClassParseException;
}
