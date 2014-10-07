package tcc2.desafio;

import gr.spinellis.ckjm.ClassMetrics;


public class MediaMetricas {

    private ClassMetrics classMetrics;

    private final double quantidadeClasses;

    public MediaMetricas(double qc) {
	this.quantidadeClasses = qc;
    }

    public void addMetris(ClassMetrics classMetrics) {
	if (this.classMetrics == null) {
	    this.classMetrics = classMetrics;
	} else {
	    this.classMetrics.setWmc(this.classMetrics.getWmc() + classMetrics.getWmc());
	    this.classMetrics.setNoc(this.classMetrics.getNoc() + classMetrics.getNoc());
	    this.classMetrics.setRfc(this.classMetrics.getRfc() + classMetrics.getRfc());
	    this.classMetrics.setCbo(this.classMetrics.getCbo() + classMetrics.getCbo());
	    this.classMetrics.setDit(this.classMetrics.getDit() + classMetrics.getDit());
	    this.classMetrics.setLcom(this.classMetrics.getLcom() + classMetrics.getLcom());
	    this.classMetrics.setNpm(this.classMetrics.getNpm() + classMetrics.getNpm());
	}
    }

    public double getMediaWmc() {
	return getMedia(this.classMetrics.getWmc());
    }

    public double getMediaNoc() {
	return getMedia(this.classMetrics.getNoc());
    }

    public double getMediaRfc() {
	return getMedia(this.classMetrics.getRfc());
    }

    public double getMediaCbo() {
	return getMedia(this.classMetrics.getCbo());
    }

    public double getMediaDit() {
	return getMedia(this.classMetrics.getDit());
    }

    public double getMediaLcom() {
	return getMedia(this.classMetrics.getLcom());
    }

    public double getMediaNpm() {
	return getMedia(this.classMetrics.getNpm());
    }

    private double getMedia(int i) {
	return i / this.quantidadeClasses;
    }

}
