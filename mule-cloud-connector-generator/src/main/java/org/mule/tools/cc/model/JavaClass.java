/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cc.model;

import java.util.List;

public interface JavaClass {
    public String getName();
    public String getPackage();
    public String getDescription();
    public List<JavaProperty> getProperties();
    public boolean hasProperties();
    public List<JavaMethod> getOperations();
}
