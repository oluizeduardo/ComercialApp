package model.POJO;


/**
 * Classe que monta um item que poderá ser vendido.
 *
 */
public class ItemVendido {

	private Produto produto = null;
	private int quantidade = 0;
	private Double valorTotal = 0.0;
	private Venda venda = new Venda();
	
	
	public ItemVendido(Produto prod) {
		this.produto = prod;
	}


	/**
	 * @return the produto
	 */
	public Produto getProduto() {
		return produto;
	}


	/**
	 * @param produto the produto to set
	 */
	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	/**
	 * @return the quantidade
	 */
	public int getQuantidade() {
		return quantidade;
	}


	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(int quantidade) {
		
		this.quantidade = quantidade;
		
		if(produto != null){
			this.valorTotal = (this.quantidade * this.produto.getPrecoVenda());
		}
	}


	/**
	 * @return the valorTotal
	 */
	public Double getValorTotal() {
		return valorTotal;
	}


	/**
	 * @param valorTotal the valorTotal to set
	 */
	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	/**
	 * Retorna a descrição do objeto.
	 */
	@Override
	public String toString() {
		return "DESC: "+this.produto.getNome()+"  QTDE: "+this.quantidade;
	}


	/**
	 * @return the venda
	 */
	public Venda getVenda() {
		return venda;
	}


	/**
	 * @param venda the venda to set
	 */
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	
	
	
	
}
