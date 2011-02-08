/**
 * Mule Cloud Connector Development Kit
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.tools.cloudconnect;

import org.mule.tools.cloudconnect.parser.ClassParserLog;

import org.apache.maven.plugin.logging.Log;

public class MavenClassParserLog implements ClassParserLog
{
    private Log log;

    public MavenClassParserLog(Log log) {
        this.log = log;
    }

    public void debug(String content)
    {
        log.debug(content);
    }

    public void error(String content)
    {
        log.error(content);
    }

    public void warn(String content)
    {
        log.warn(content);
    }

    public void info(String content)
    {
        log.info(content);
    }
}
