package model.POJO;

public class Produto {

	
	private int codigo = 0;
	private String nome = null;
	private Setor setor = null;
	private double precoCusto = 0f;
	private double margemLucro = 0f;
	private double precoVenda = 0f;
	private String fornecedor = null;
	private int estoqueMinimo = 0;
	private int emEstoque = 0;
	private String observacao = null;
	
	
	
	public Produto() {}


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


	/**
	 * @return the setor
	 */
	public Setor getSetor() {
		return setor;
	}


	/**
	 * @param setor the setor to set
	 */
	public void setSetor(Setor setor) {
		this.setor = setor;
	}


	/**
	 * @return the precoCusto
	 */
	public double getPrecoCusto() {
		return precoCusto;
	}


	/**
	 * @param precoCusto the precoCusto to set
	 */
	public void setPrecoCusto(double precoCusto) {
		this.precoCusto = precoCusto;
	}


	/**
	 * @return the margemLucro
	 */
	public double getMargemLucro() {
		return margemLucro;
	}


	/**
	 * @param margemLucro the margemLucro to set
	 */
	public void setMargemLucro(double margemLucro) {
		this.margemLucro = margemLucro;
	}


	/**
	 * @return the precoVenda
	 */
	public double getPrecoVenda() {
		return precoVenda;
	}


	/**
	 * @param precoVenda the precoVenda to set
	 */
	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}


	/**
	 * @return the fornecedor
	 */
	public String getFornecedor() {
		return fornecedor;
	}


	/**
	 * @param fornecedor the fornecedor to set
	 */
	public void setFornecedor(String fornecedor) {
		this.fornecedor = fornecedor;
	}


	/**
	 * @return the estoqueMinimo
	 */
	public int getEstoqueMinimo() {
		return estoqueMinimo;
	}


	/**
	 * @param estoqueMinimo the estoqueMinimo to set
	 */
	public void setEstoqueMinimo(int estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}


	/**
	 * @return the emEstoque
	 */
	public int getEmEstoque() {
		return emEstoque;
	}


	/**
	 * @param emEstoque the emEstoque to set
	 */
	public void setEmEstoque(int emEstoque) {
		this.emEstoque = emEstoque;
	}
	
	/**
	 * @return the observation of the product.
	 */
	public String getObservacao() {
		return observacao;
	}


	/**
	 * @param newObs the new String about observation to set.
	 */
	public void setObservacao(String newObs) {
		this.observacao = newObs;
	}
	
	
	
	
	@Override
	public String toString() {
		return getNome();
	}
	
	
	
}
