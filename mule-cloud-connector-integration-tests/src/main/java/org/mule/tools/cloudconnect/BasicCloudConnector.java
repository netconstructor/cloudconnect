package org.mule.tools.cloudconnect;

import org.mule.tools.cloudconnect.annotations.Connector;
import org.mule.tools.cloudconnect.annotations.Operation;

@Connector(namespacePrefix = "basic")
public class BasicCloudConnector
{
    @Operation
    public char passthruChar(char value)
    {
        return value;
    }

    @Operation
    public String passthruString(String value)
    {
        return value;
    }

    @Operation
    public float passthruFloat(float value)
    {
        return value;
    }

    @Operation
    public boolean passthruBoolean(boolean value)
    {
        return value;
    }

    @Operation
    public int passthruInteger(int value)
    {
        return value;
    }

    @Operation
    public long passthruLong(long value)
    {
        return value;
    }

    @Operation
    public Float passthruComplexFloat(Float value)
    {
        return value;
    }

    @Operation
    public Boolean passthruComplexBoolean(Boolean value)
    {
        return value;
    }

    @Operation
    public Integer passthruComplexInteger(Integer value)
    {
        return value;
    }

    @Operation
    public Long passthruComplexLong(Long value)
    {
        return value;
    }
}
