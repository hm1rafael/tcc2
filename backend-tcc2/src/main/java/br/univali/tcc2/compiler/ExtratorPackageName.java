package br.univali.tcc2.compiler;

import java.util.StringTokenizer;

public class ExtratorPackageName {

    public String extrair(String code) {
	StringTokenizer tokenizer = new StringTokenizer(code);
	String nextToken = tokenizer.nextToken();
	if (!"package".equals(nextToken)) {
	    return "";
	}
	String packageName = tokenizer.nextToken();
	String packageNameSemPontos = packageName.replace(".", "/");
	return packageNameSemPontos.replace(";", "/");
    }


}
