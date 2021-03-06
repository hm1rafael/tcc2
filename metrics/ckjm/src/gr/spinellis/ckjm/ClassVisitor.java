/*
 * $Id: \\dds\\src\\Research\\ckjm.RCS\\src\\gr\\spinellis\\ckjm\\ClassVisitor.java,v 1.21 2012/04/04 13:08:23 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package gr.spinellis.ckjm;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

import org.apache.bcel.Constants;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.Type;

/**
 * Visit a class updating its Chidamber-Kemerer metrics.
 *
 * @see ClassMetrics
 * @version $Revision: 1.21 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
public class ClassVisitor extends org.apache.bcel.classfile.EmptyVisitor {
    /** The class being visited. */
    private final JavaClass visitedClass;
    /** The class's constant pool. */
    private final ConstantPoolGen cp;
    /** The class's fully qualified name. */
    private final String myClassName;
    /** The container where metrics for all classes are stored. */
    private final ClassMetricsContainer cmap;
    /** The emtrics for the class being visited. */
    private final ClassMetrics cm;
    /* Classes encountered.
     * Its cardinality is used for calculating the CBO.
     */
    private final HashSet<String> efferentCoupledClasses = new HashSet<String>();
    /** Methods encountered.
     * Its cardinality is used for calculating the RFC.
     */
    private final HashSet<String> responseSet = new HashSet<String>();
    /** Use of fields in methods.
     * Its contents are used for calculating the LCOM.
     * We use a Tree rather than a Hash to calculate the
     * intersection in O(n) instead of O(n*n).
     */
    ArrayList<TreeSet<String>> mi = new ArrayList<TreeSet<String>>();

    public ClassVisitor(JavaClass jc, ClassMetricsContainer classMap) {
	this.visitedClass = jc;
	this.cp = new ConstantPoolGen(this.visitedClass.getConstantPool());
	this.cmap = classMap;
	this.myClassName = jc.getClassName();
	this.cm = this.cmap.getMetrics(this.myClassName);
    }

    /** Return the class's metrics container. */
    public ClassMetrics getMetrics() { return this.cm; }

    public void start() {
	visitJavaClass(this.visitedClass);
    }

    /** Calculate the class's metrics based on its elements. */
    @Override
    public void visitJavaClass(JavaClass jc) {
	String super_name   = jc.getSuperclassName();
	String package_name = jc.getPackageName();

	this.cm.setVisited();
	if (jc.isPublic()) {
	    this.cm.setPublic();
	}
	ClassMetrics pm = this.cmap.getMetrics(super_name);

	pm.incNoc();
	try {
	    this.cm.setDit(jc.getSuperClasses().length);
	} catch( ClassNotFoundException ex) {
	    System.err.println("Error obtaining all superclasses of " + jc);
	}
	registerCoupling(super_name);

	String ifs[] = jc.getInterfaceNames();
	/* Measuring decision: couple interfaces */
	for (int i = 0; i < ifs.length; i++) {
	    registerCoupling(ifs[i]);
	}

	Field[] fields = jc.getFields();
	for(int i=0; i < fields.length; i++) {
	    fields[i].accept(this);
	}

	Method[] methods = jc.getMethods();
	for(int i=0; i < methods.length; i++) {
	    methods[i].accept(this);
	}
    }

    /** Add a given class to the classes we are coupled to */
    public void registerCoupling(String className) {
	/* Measuring decision: don't couple to Java SDK */
	if ((MetricsFilter.isJdkIncluded() ||
		!ClassMetrics.isJdkClass(className)) &&
		!this.myClassName.equals(className)) {
	    this.efferentCoupledClasses.add(className);
	    this.cmap.getMetrics(className).addAfferentCoupling(this.myClassName);
	}
    }

    /* Add the type's class to the classes we are coupled to */
    public void registerCoupling(Type t) {
	registerCoupling(className(t));
    }

    /* Add a given class to the classes we are coupled to */
    void registerFieldAccess(String className, String fieldName) {
	registerCoupling(className);
	if (className.equals(this.myClassName)) {
	    this.mi.get(this.mi.size() - 1).add(fieldName);
	}
    }

    /* Add a given method to our response set */
    void registerMethodInvocation(String className, String methodName, Type[] args) {
	registerCoupling(className);
	/* Measuring decision: calls to JDK methods are included in the RFC calculation */
	incRFC(className, methodName, args);
    }

    /** Called when a field access is encountered. */
    @Override
    public void visitField(Field field) {
	registerCoupling(field.getType());
    }

    /** Called when encountering a method that should be included in the
        class's RFC. */
    private void incRFC(String className, String methodName, Type[] arguments) {
	String argumentList = Arrays.asList(arguments).toString();
	// remove [ ] chars from begin and end
	String args = argumentList.substring(1, argumentList.length() - 1);
	String signature = className + "." + methodName + "(" + args + ")";
	this.responseSet.add(signature);
    }

    /** Called when a method invocation is encountered. */
    @Override
    public void visitMethod(Method method) {
	MethodGen mg = new MethodGen(method, this.visitedClass.getClassName(), this.cp);

	Type   result_type = mg.getReturnType();
	Type[] argTypes   = mg.getArgumentTypes();

	registerCoupling(mg.getReturnType());
	for (int i = 0; i < argTypes.length; i++) {
	    registerCoupling(argTypes[i]);
	}

	String[] exceptions = mg.getExceptions();
	for (int i = 0; i < exceptions.length; i++) {
	    registerCoupling(exceptions[i]);
	}

	/* Measuring decision: A class's own methods contribute to its RFC */
	incRFC(this.myClassName, method.getName(), argTypes);

	this.cm.incWmc();
	if (Modifier.isPublic(method.getModifiers())) {
	    this.cm.incNpm();
	}
	this.mi.add(new TreeSet<String>());
	MethodVisitor factory = new MethodVisitor(mg, this);
	factory.start();
    }

    /** Return a class name associated with a type. */
    static String className(Type t) {
	String ts = t.toString();

	if (t.getType() <= Constants.T_VOID) {
	    return "java.PRIMITIVE";
	} else if(t instanceof ArrayType) {
	    ArrayType at = (ArrayType)t;
	    return className(at.getBasicType());
	} else {
	    return t.toString();
	}
    }

    /** Do final accounting at the end of the visit. */
    public void end() {
	this.cm.setCbo(this.efferentCoupledClasses.size());
	this.cm.setRfc(this.responseSet.size());
	/*
	 * Calculate LCOM  as |P| - |Q| if |P| - |Q| > 0 or 0 otherwise
	 * where
	 * P = set of all empty set intersections
	 * Q = set of all nonempty set intersections
	 */
	int lcom = 0;
	for (int i = 0; i < this.mi.size(); i++) {
	    for (int j = i + 1; j < this.mi.size(); j++) {
		/* A shallow unknown-type copy is enough */
		TreeSet<?> intersection = (TreeSet<?>)this.mi.get(i).clone();
		intersection.retainAll(this.mi.get(j));
		if (intersection.size() == 0) {
		    lcom++;
		} else {
		    lcom--;
		}
	    }
	}
	this.cm.setLcom(lcom > 0 ? lcom : 0);
    }
}
