package model.POJO;

public class Setor {

	
	private int codigo =0;
	private String nome = null;
	
	
	
	
	/**
	 * Construtor da classe Setor.
	 */
	 public Setor() {}
	 
	 
	 /**
	  * Construtor da classe Setor.
	  * 
	  * @param cod
	  * @param nome
	  */
	 public Setor(int cod, String nome){
		 this.codigo = cod;
		 this.nome = nome;
	 }


	/**
	 * @return the codigo
	 */
	public int getCodigo() {
		return codigo;
	}


	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	 
	 
	
	@Override
	public String toString() {
		return " "+nome;
	}
	
	 
	 
	
}
