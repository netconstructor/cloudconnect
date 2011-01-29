/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.generator.directives;

public class InvalidTypeException extends RuntimeException
{

    public InvalidTypeException(String type)
    {
        super(String.format("Don't know how to map from Java type '%1s' to schema type", type));
    }
}
