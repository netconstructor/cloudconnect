/*
* $Id$
* --------------------------------------------------------------------------------------
* Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
*
* The software in this package is published under the terms of the CPAL v1.0
* license, a copy of which has been included with this distribution in the
* LICENSE.txt file.
*/

/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect.generator;

import java.util.HashMap;
import java.util.Map;

public class SchemaGenerator extends AbstractTemplateGenerator
{

    private static final String SCHEMA_TEMPLATE = "schema.ftl";

    @Override
    protected Map<String, Object> createModel()
    {
        Map<String, Object> root = new HashMap<String, Object>();
        root.put("class", getJavaClass());
        return root;
    }

    @Override
    protected String getTemplate()
    {
        return SCHEMA_TEMPLATE;
    }
}
