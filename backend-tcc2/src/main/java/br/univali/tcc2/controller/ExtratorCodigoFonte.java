package br.univali.tcc2.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import br.univali.tcc2.CodigoFonte;
import br.univali.tcc2.compiler.ExtratorPackageName;

@Component
public class ExtratorCodigoFonte {

	private final ExtratorPackageName extratorPackageName = new ExtratorPackageName();
	
	public CodigoFonte carregarCodigoFonte(ZipInputStream zin) throws IOException {
		CodigoFonte codigoFonte = new CodigoFonte();
		ZipEntry entry;
		while ((entry = zin.getNextEntry()) != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int d;
			while ((d = zin.read()) != -1) baos.write(d);
			String name = entry.getName();
			if (StringUtils.endsWith(name, ".java")) carregarArquivoDeCodigoFonte(codigoFonte, baos, name);
			else if (StringUtils.endsWith(name, ".txt")) carregarArquivoEntradaOuSaida(codigoFonte, baos, name);
		}
		return codigoFonte;
	}

	private void carregarArquivoEntradaOuSaida(CodigoFonte codigoFonte,
			ByteArrayOutputStream baos, String name) {
		String arquivo = new String(baos.toByteArray());
		if (name.endsWith("input.txt")) {
			codigoFonte.setEntrada(arquivo);
		} else {
			codigoFonte.setSaida(arquivo);
		}
		
	}

	private void carregarArquivoDeCodigoFonte(CodigoFonte codigoFonte,
			ByteArrayOutputStream baos, String name) {
		byte[] arquivoCodigoFonteBytes = baos.toByteArray();
		String packageName = this.extratorPackageName.extrair(new String(arquivoCodigoFonteBytes));
		String nomeArquivo = FilenameUtils.getName(name);
		codigoFonte.adicionarArquivoDeCodigo(arquivoCodigoFonteBytes,
				packageName + nomeArquivo);
	}
	
}
