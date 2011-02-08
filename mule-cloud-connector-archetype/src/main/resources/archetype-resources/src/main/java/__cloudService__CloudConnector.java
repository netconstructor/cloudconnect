/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

 */

#set($D='$')
package ${package};

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix="${cloudServiceLower}", muleVersion="${D}{mule.version}")
public class ${cloudService}CloudConnector
{
    /*
     * The following is a sample operation
     */
    @Operation
    public void myOperation()
    {
    }
}
