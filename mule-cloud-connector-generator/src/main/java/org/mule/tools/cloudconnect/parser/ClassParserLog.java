package org.mule.tools.cloudconnect.parser;

public interface ClassParserLog
{
    void debug(String content);
    void error(String content);
    void warn(String content);
    void info(String content);
}
