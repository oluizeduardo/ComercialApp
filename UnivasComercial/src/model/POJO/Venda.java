package model.POJO;

import java.util.List;


public class Venda {

	// O código da nova Venda. (PK da tabela Vendas)
	private int codigo = 0;
	// A lista de itens vendidos. (Registros deverão ser armazenados na tabela itens_vendidos)
	private List<ItemVendido> itensVendidos = null;
	// A data em que a venda foi realizada.
	private String data = null;
	// A hor em que a venda foi realizada.
	private String hora = null;
	// A forma de pagamento utilizada.
	private FormaDePagamento formaDePagamento = null;
	// O valor total dos itens vendidos.
	private double total = 0;
	
	
	
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
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	/**
	 * @return the formaDePagamento
	 */
	public FormaDePagamento getFormaDePagamento() {
		return formaDePagamento;
	}
	/**
	 * @param formaDePagamento the formaDePagamento to set
	 */
	public void setFormaDePagamento(FormaDePagamento formPag) {
		this.formaDePagamento = formPag;
	}
	/**
	 * @return the total
	 */
	public double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(double total) {
		this.total = total;
	}
	
	
	/**
	 * @return the itensVendidos
	 */
	public List<ItemVendido> getItensVendidos() {
		return itensVendidos;
	}
	/**
	 * @param itensVendidos the itensVendidos to set
	 */
	public void setItensVendidos(List<ItemVendido> itensVendidos) {
		this.itensVendidos = itensVendidos;
	}
	
	
	
	
	
}
