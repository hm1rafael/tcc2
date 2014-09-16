package br.univali.tcc2;

import org.apache.bcel.classfile.JavaClass;

public class Classe {

	private JavaClass javaClass;
	private Class clazz;
	
	public Classe(JavaClass javaClass, Class clazz) {
		this.javaClass = javaClass;
		this.clazz = clazz;
	}
	
	public Classe(Class clazz) {
		this(null,clazz);
	}
	
	public Class getClazz() {
		return clazz;
	}
	
	public JavaClass getJavaClass() {
		return javaClass;
	}
	
}
