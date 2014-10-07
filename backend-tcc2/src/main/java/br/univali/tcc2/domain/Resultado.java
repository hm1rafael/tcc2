package br.univali.tcc2.domain;

import tcc2.desafio.MediaMetricas;

public class Resultado {

    private boolean compiladoCorretamente;
    private boolean executadoCorretamente;
    private double tempoDeExecucao;
    private double tempoDeCompilacao;
    private int quantidadeClasses;

    private double wmcMedia;
    private double nocMedia;
    private double rfcMedia;
    private double cboMedia;
    private double ditMedia;
    private double lcomMedia;
    private double npmMedia;

    public Resultado(MediaMetricas mediaMetricas, boolean executadoCorretamente, double tempoTotalCompilacao, double tempoTotalExecucao,int quantidadeClasses) {
	this.compiladoCorretamente = true;
	this.executadoCorretamente = executadoCorretamente;
	this.tempoDeCompilacao = tempoTotalCompilacao / 1000;
	this.tempoDeExecucao = tempoTotalExecucao / 1000;
	this.quantidadeClasses = quantidadeClasses;
	try {
		this.wmcMedia = mediaMetricas.getMediaWmc();
		this.nocMedia = mediaMetricas.getMediaNoc();
		this.rfcMedia = mediaMetricas.getMediaRfc();
		this.cboMedia = mediaMetricas.getMediaCbo();
		this.ditMedia = mediaMetricas.getMediaDit();
		this.lcomMedia = mediaMetricas.getMediaLcom();
		this.npmMedia = mediaMetricas.getMediaNpm(); 
	} catch(Throwable e) {
		e.printStackTrace();
	}
    }

    public Resultado() {

    }

    public double getWmcMedia() {
	return this.wmcMedia;
    }

    public double getCboMedia() {
	return this.cboMedia;
    }

    public double getDitMedia() {
	return this.ditMedia;
    }
   
    public double getTempoDeCompilacao() {
		return tempoDeCompilacao;
	}
    
    public void setTempoDeCompilacao(long tempoDeCompilacao) {
		this.tempoDeCompilacao = tempoDeCompilacao;
	}
    
    public int getQuantidadeClasses() {
		return quantidadeClasses;
	}
    
    public void setQuantidadeClasses(int quantidadeClasses) {
		this.quantidadeClasses = quantidadeClasses;
	}
    
    public void setTempoDeExecucao(long tempoDeExecucao) {
		this.tempoDeExecucao = tempoDeExecucao;
	}
    
    public double getTempoDeExecucao() {
		return tempoDeExecucao;
	}

    public double getLcomMedia() {
	return this.lcomMedia;
    }

    public double getNocMedia() {
	return this.nocMedia;
    }

    public double getNpmMedia() {
	return this.npmMedia;
    }

    public double getRfcMedia() {
	return this.rfcMedia;
    }

    public boolean isCompiladoCorretamente() {
	return this.compiladoCorretamente;
    }
    
    public boolean isExecutadoCorretamente() {
		return executadoCorretamente;
	}
    
    public void setExecutadoCorretamente(boolean executadoCorretamente) {
		this.executadoCorretamente = executadoCorretamente;
	}



}
