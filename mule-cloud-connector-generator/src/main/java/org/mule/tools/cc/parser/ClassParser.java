package org.mule.tools.cc.parser;

import org.mule.tools.cc.model.JavaClass;

import java.io.InputStream;

public interface ClassParser {
    JavaClass parse(InputStream input) throws ClassParseException;
}
