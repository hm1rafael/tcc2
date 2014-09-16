package br.univali.tcc2.metrics;

import gr.spinellis.ckjm.ClassMetrics;
import gr.spinellis.ckjm.ClassMetricsContainer;
import gr.spinellis.ckjm.ClassVisitor;

import org.apache.bcel.classfile.JavaClass;
import org.springframework.stereotype.Component;

@Component
public class AvaliadorMetricas {

	public ClassMetrics avaliarMetricasDaClasse(JavaClass javaClass) {
		ClassMetricsContainer classMetricsContainer = new ClassMetricsContainer();
		ClassVisitor classVisitor = new ClassVisitor(javaClass,
				classMetricsContainer);
		classVisitor.start();
		classVisitor.end();
		String className = javaClass.getClassName();
		return classMetricsContainer.getMetrics(className);
	}

}
