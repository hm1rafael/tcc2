package br.univali.tcc2;

public class Lib {

	private byte[] libFile;
	private String libName;
	
	public Lib(byte[] libFile, String libName) {
		this.libFile = libFile;
		this.libName = libName;
	}
	
	public byte[] getLibFile() {
		return libFile;
	}
	
	public void setLibFile(byte[] libFile) {
		this.libFile = libFile;
	}
	
	public String getLibName() {
		return libName;
	}
	
}
