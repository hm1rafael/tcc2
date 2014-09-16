package tcc2.portal.domain;

public enum Dificuldade {

    FACIL(30), MEDIO(60), DIFICIL(90);
    
    private Dificuldade (Integer pontuacao) {
    	this.pontuacao = pontuacao;
    }
    
    private Integer pontuacao;
    
    public Integer getPontuacao() {
		return pontuacao;
	}
    
}
