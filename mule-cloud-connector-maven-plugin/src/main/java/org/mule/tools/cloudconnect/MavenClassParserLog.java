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
