package org.mule.tools.cc.generator;

import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import com.thoughtworks.qdox.model.Type;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;

import java.lang.reflect.Method;

import static org.easymock.EasyMock.expect;

public class EasyMockHelper
{
   private IMocksControl interfaceControl;
   private org.easymock.classextension.IMocksControl concreteControl;
   private org.easymock.classextension.IMocksControl strictConcreteControl;
   private IMocksControl strictInterfaceControl;

   protected EasyMockHelper()
   {
      interfaceControl = EasyMock.createControl();
      concreteControl = org.easymock.classextension.EasyMock.createControl();
      strictInterfaceControl = EasyMock.createStrictControl();
      strictConcreteControl = org.easymock.classextension.EasyMock.createStrictControl();
   }

   protected void reset()
   {
      interfaceControl.reset();
      concreteControl.reset();
      strictInterfaceControl.reset();
      strictConcreteControl.reset();
   }

   protected void replay()
   {
      interfaceControl.replay();
      concreteControl.replay();
      strictInterfaceControl.replay();
      strictConcreteControl.replay();
   }

   protected void verify()
   {
      interfaceControl.verify();
      concreteControl.verify();
      strictInterfaceControl.verify();
      strictConcreteControl.verify();
   }

    public JavaClass createMockClass(JavaMethod method) {
        JavaClass javaClass = concreteControl.createMock(JavaClass.class);
        expect(javaClass.getMethods()).andReturn(new JavaMethod[] { method }).anyTimes();
        return javaClass;
    }


    public JavaClass createMockClass(JavaMethod[] methods) {
        JavaClass javaClass = concreteControl.createMock(JavaClass.class);
        expect(javaClass.getMethods()).andReturn(methods).anyTimes();
        return javaClass;
    }

    public JavaClass createMockClass(String pkg, String name, JavaMethod[] methods) {
        JavaClass javaClass = concreteControl.createMock(JavaClass.class);
        expect(javaClass.getPackage()).andReturn(pkg).anyTimes();
        expect(javaClass.getName()).andReturn(name).anyTimes();
        expect(javaClass.getMethods()).andReturn(methods).anyTimes();
        return javaClass;
    }

    public JavaMethod createMockMethod(String name, String comment, JavaParameter parameter) {
        return createMockMethod(name, comment, new JavaParameter[]{ parameter } );
    }

    public JavaMethod createMockMethod(String name, String comment, JavaParameter[] parameters) {
        return createMockMethod(name, comment, parameters, true);
    }

    public JavaMethod createMockMethod(String name, String comment, JavaParameter[] parameters, boolean pub) {
        JavaMethod method = concreteControl.createMock(JavaMethod.class);
        expect(method.getName()).andReturn(name).anyTimes();
        expect(method.getComment()).andReturn(comment).anyTimes();
        expect(method.getParameters()).andReturn(parameters).anyTimes();
        expect(method.isPublic()).andReturn(pub).anyTimes();
        return method;
    }

    public JavaParameter createMockParameter(String symbol, String typeName) {
        JavaParameter parameter = concreteControl.createMock(JavaParameter.class);
        expect(parameter.getName()).andReturn(symbol).anyTimes();
        Type type = concreteControl.createMock(Type.class);
        expect(type.getValue()).andReturn(typeName).anyTimes();
        expect(parameter.getType()).andReturn(type).anyTimes();
        return parameter;
    }

}