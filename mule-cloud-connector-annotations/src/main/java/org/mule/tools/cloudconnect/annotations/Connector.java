/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.tools.cloudconnect.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation will declare a method inside a cloud connector as accessible via a flow
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface Connector
{

    String namespacePrefix();

    String namespaceUri() default EMPTY_STRING;

    Class<?> factory() default NO_FACTORY.class;

    String muleVersion() default MULE_VERSION;

    public static final String EMPTY_STRING = "";

    public static final String MULE_VERSION = "3.1";

    static final class NO_FACTORY { };
}
