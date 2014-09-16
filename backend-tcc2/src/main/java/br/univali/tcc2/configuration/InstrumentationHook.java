package br.univali.tcc2.configuration;

import java.lang.instrument.Instrumentation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class InstrumentationHook {

	private static Instrumentation instrumentation;
	
	public static void premain(String agentArgs, Instrumentation inst) {
		instrumentation = inst;
	}
	
	public static List<Class> getLoadedClasses(ClassLoader loader) {
		Class clKlass = loader.getClass();
	    System.out.println("Classloader: " + clKlass.getCanonicalName());
	    while (clKlass != java.lang.ClassLoader.class) {
	        clKlass = clKlass.getSuperclass();
	    }
	    try {
	        java.lang.reflect.Field fldClasses = clKlass
	                .getDeclaredField("classes");
	        fldClasses.setAccessible(true);
	        Vector<Class> classes = (Vector) fldClasses.get(loader);
	        for (Iterator iter = classes.iterator(); iter.hasNext();) {
	            System.out.println("   Loaded " + iter.next());
	        }
	        List<Class> clazzs = new ArrayList<Class>();
	        for (Class clazz : classes) {
				clazzs.add(clazz);
			}
	        return clazzs;
	    } catch (SecurityException e) {
	        e.printStackTrace();
	    } catch (IllegalArgumentException e) {
	        e.printStackTrace();
	    } catch (NoSuchFieldException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    }
		return null;
	}
	
}
