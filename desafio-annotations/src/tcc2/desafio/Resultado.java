package tcc2.desafio;


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

	    private String wmcBad;
	    private String nocBad;
	    private String ditBad;
	    private String rfcBad;
	    private String lcomBad;
	    private String cboBad;
	    private String npmBad;
	    
	    public Resultado(MediaMetricas mediaMetricas, boolean executadoCorretamente, double tempoTotalCompilacao, double tempoTotalExecucao,int quantidadeClasses) {
	    this.compiladoCorretamente = true;
	    this.executadoCorretamente = executadoCorretamente;
	    this.tempoDeCompilacao = tempoTotalCompilacao / 1000;
	   	this.tempoDeExecucao = tempoTotalExecucao / 1000;
	   	this.quantidadeClasses = quantidadeClasses;
	    this.wmcMedia = mediaMetricas.getMediaWmc();
		this.nocMedia = mediaMetricas.getMediaNoc();
		this.rfcMedia = mediaMetricas.getMediaRfc();
		this.cboMedia = mediaMetricas.getMediaCbo();
		this.ditMedia = mediaMetricas.getMediaDit();
		this.lcomMedia = mediaMetricas.getMediaLcom();
		this.npmMedia = mediaMetricas.getMediaNpm();
	    }

	    public Resultado() {

	    }

	    public double getTempoDeCompilacao() {
			return tempoDeCompilacao;
		}
	    
	    public double getTempoDeExecucao() {
			return tempoDeExecucao;
		}
	    
	    public void setDitMedia(double ditMedia) {
			this.ditMedia = ditMedia;
		}
	    
	    public void setLcomMedia(double lcomMedia) {
			this.lcomMedia = lcomMedia;
		}
	    
	    public void setNocMedia(double nocMedia) {
			this.nocMedia = nocMedia;
		}
	    
	    public void setNpmMedia(double npmMedia) {
			this.npmMedia = npmMedia;
		}
	    
	    public void setRfcMedia(double rfcMedia) {
			this.rfcMedia = rfcMedia;
		}
	    
	    public void setWmcMedia(double wmcMedia) {
			this.wmcMedia = wmcMedia;
		}
	
	    public void setCboMedia(double cboMedia) {
			this.cboMedia = cboMedia;
		}
	    
	    public void setTempoDeCompilacao(Double tempoDeCompilacao) {
			this.tempoDeCompilacao = tempoDeCompilacao;
		}
	    
	    public void setTempoDeExecucao(Double tempoDeExecucao) {
			this.tempoDeExecucao = tempoDeExecucao;
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

	    public double getLcomMedia() {
		return this.lcomMedia;
	    }

	    public double getNocMedia() {
		return this.nocMedia;
	    }
	    
	    public int getQuantidadeClasses() {
			return quantidadeClasses;
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

		public String getWmcBad() {
			return wmcBad;
		}

		public void setWmcBad(String wmcBad) {
			this.wmcBad = wmcBad;
		}

		public String getNocBad() {
			return nocBad;
		}

		public void setNocBad(String nocBad) {
			this.nocBad = nocBad;
		}

		public String getDitBad() {
			return ditBad;
		}

		public void setDitBad(String ditBad) {
			this.ditBad = ditBad;
		}

		public String getRfcBad() {
			return rfcBad;
		}

		public void setRfcBad(String rfcBad) {
			this.rfcBad = rfcBad;
		}

		public String getLcomBad() {
			return lcomBad;
		}

		public void setLcomBad(String lcomBad) {
			this.lcomBad = lcomBad;
		}

		public String getCboBad() {
			return cboBad;
		}

		public void setCboBad(String cboBad) {
			this.cboBad = cboBad;
		}

		public String getNpmBad() {
			return npmBad;
		}

		public void setNpmBad(String npmBad) {
			this.npmBad = npmBad;
		}

	}

	
