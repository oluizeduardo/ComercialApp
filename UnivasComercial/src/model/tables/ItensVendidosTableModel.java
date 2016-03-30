package model.tables;

import java.util.ArrayList;
import java.util.List;

import model.POJO.ItemVendido;


/**
 * Modelo de tabela para ser aplicado na tabela de itens vendidos.
 * 
 */
public class ItensVendidosTableModel extends ModeloTabela<ItemVendido> {

	
	private static final long serialVersionUID = 1L;
	
	
	// A lista de itens vendidos.
	private ArrayList<ItemVendido> produtosComprados = new ArrayList<ItemVendido>();
	
	// Nomes das colunas da tabela.
	private static String[] colunas = {"Cod","Nome","Qtde","R$ Uni", "R$ Total"};
	
	
	
	
	
	/**
     * Método construtor da classe VendasTableModel.
     */    
    public ItensVendidosTableModel() { 
    	super(colunas);
    	setLista(produtosComprados);
    }
	

    
    
    
    /**
     * Retorna a posição que o um item com o mesmo código informado
     * ocupa na lista. Retorna 0 caso não encontre nenhum item com esse código.
     * 
     * @param codigo
     * @return
     */
    public int getIndexItemPeloCodigo(int codigo){
    	
		for(int i=0; i < this.produtosComprados.size(); i++){
			
			if(produtosComprados.get( i ).getProduto().getCodigo() == codigo){
				return i ;
			}
		}
		return -1;
    }
    
    
    
    
    
	/**
	 * Retorna o valor da posição especificada no parâmetro.
	 */
	@Override
	public Object getValueAt(int linha, int coluna) {
		
		ItemVendido item = produtosComprados.get(linha);
		
		switch (coluna) {
		case 0:
			return item.getProduto().getCodigo();
		case 1:
			return item.getProduto().getNome();
		case 2:
			return item.getQuantidade();	
		case 3:
			return String.format("%.2f", item.getProduto().getPrecoVenda());// Adapta o valor para moeda.
		case 4:
			return String.format("%.2f", item.getValorTotal());// Adapta o valor para moeda.
		default:
			throw new IndexOutOfBoundsException("Posição inexistente na tabela");
		}
	}
	

	
	
	/**
	 * Retorna a lista utilizada no modelo de tabela.
	 */
	public List<ItemVendido> getLista(){
		return produtosComprados;
	}
	
	
	

    
	/**
	 * Retorna a soma total dos itens vendidos.
	 */
	public Double getValorTotalItensVendidos(){
		Double total = 0.0;
		for(int i=0; i < produtosComprados.size(); i++){
			total += produtosComprados.get( i ).getValorTotal();
		}
		return total;
	}
	
	
	
	
}
