package org.mule.tools.cc.parser;

public interface ClassParserLog
{
    void debug(String content);
    void error(String content);
    void warn(String content);
    void info(String content);
}
