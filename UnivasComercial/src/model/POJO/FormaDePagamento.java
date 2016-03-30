package model.POJO;


/**
 * Define um padrão de classe para as formas de pagamento disponíveis na hora de efetuar a venda.
 * 
 * @version 1.0, 24/11/15
 */
public class FormaDePagamento {

	
	private int codigo = 0;
	private String descricao = null;
	
	
	public FormaDePagamento() {}
	

	/**
	 * Exige como parâmetro um código e a descrição da nova forma de pagamento.
	 */
	public FormaDePagamento(int cod, String desc) {
		this.codigo = cod;
		this.descricao = desc;
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
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}


	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	
	
	
	
	
	
}
