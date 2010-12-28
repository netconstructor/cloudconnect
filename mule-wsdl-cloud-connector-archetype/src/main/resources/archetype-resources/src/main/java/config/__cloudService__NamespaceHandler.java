#set($D = '$')
/*
 * ${D}Id:${D}
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package ${package}.config;

import org.mule.config.spring.handlers.AbstractPojoNamespaceHandler;
import ${package}.${cloudService}CloudConnector;

public class ${cloudService}NamespaceHandler extends AbstractPojoNamespaceHandler
{
    public void init()
    {
        registerPojo("config", ${cloudService}CloudConnector.class);

        // TODO add additional custom namespace mappings
    }
}
