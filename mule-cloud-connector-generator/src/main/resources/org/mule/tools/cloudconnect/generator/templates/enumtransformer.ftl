package ${type.getTransformerPackage()};

import org.mule.api.transformer.DiscoverableTransformer;
import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

public class ${type.getTransformerName()} extends AbstractTransformer implements DiscoverableTransformer
{
    private int weighting = DiscoverableTransformer.DEFAULT_PRIORITY_WEIGHTING + 1;

    public ${type.getTransformerName()}()
    {
        registerSourceType(String.class);
        setReturnClass(${type.getFullyQualifiedName()}.class);
        setName("${type.getTransformerName()}");
    }

    @Override
    protected Object doTransform(Object src, String enc) throws TransformerException
    {
        return Enum.valueOf(${type.getFullyQualifiedName()}.class, (String)src);
    }

    public int getPriorityWeighting()
    {
        return weighting;
    }

    public void setPriorityWeighting(int weighting)
    {
        this.weighting = weighting;
    }
}