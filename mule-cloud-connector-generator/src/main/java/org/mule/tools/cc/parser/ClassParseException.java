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

public class ClassParseException extends Exception
{

    public ClassParseException(String message)
    {
        super(message);
    }

    public ClassParseException(String message, Throwable e)
    {
        super(message, e);
    }
}
