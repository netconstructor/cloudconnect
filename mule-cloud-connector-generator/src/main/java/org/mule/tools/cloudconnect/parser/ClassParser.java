/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.parser;

import org.mule.tools.cloudconnect.model.JavaModel;

import java.io.File;

public interface ClassParser
{

    void setLog(ClassParserLog log);

    void addSourceTree(File sourceTree);

    JavaModel parse() throws ClassParseException;
}
