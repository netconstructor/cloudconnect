/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.parser.qdox;

import org.mule.tools.cloudconnect.model.JavaAnnotation;

import com.thoughtworks.qdox.model.Annotation;

public class QDoxAnnotationAdapter implements JavaAnnotation
{

    private Annotation annotation;

    public QDoxAnnotationAdapter(Annotation annotation)
    {
        this.annotation = annotation;
    }

    public String getType()
    {
        return annotation.getType().getValue();
    }
}
