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

import japa.parser.ast.body.Parameter;

public class ParameterJavaMethodParameter implements JavaMethodParameter
{
    private Parameter parameter;

    public ParameterJavaMethodParameter(Parameter param)
    {
        super();
        parameter = param;
    }

    public String getName()
    {
        return parameter.getId().getName();
    }

    public String getType()
    {
        return parameter.getType().toString();
    }
}
