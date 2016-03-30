package model.tables;

import java.util.ArrayList;

import model.POJO.Produto;




/**
 * Define um modelo padrão para ser aplicado na tabela de consulta de produtos.
 * 
 * @author Luiz Eduardo da Costa
 * @version 14/11/2015
 *
 */
public class ProdutoTableModel extends ModeloTabela<Produto> {

	
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Produto> produtos = new ArrayList<Produto>();
	
	// Nomes das colunas da tabela.
	private static String[] colunas = {"Cod","Descrição","Setor","R$ Custo", "R$ Venda", "Em estoque", "Fornecedor","Observação"};
	
	
	
	/**
     * Método construtor da classe ProdutoTableModel.
     * 
     * Passa para a superclasse a instância da lista que está sendo 
     * usada nesta classe para o preenchimento da tabela.
     */    
    public ProdutoTableModel() { 
    	super(colunas);
    	setLista(produtos);
    }
	
	
	
	

	
	@Override
	public Object getValueAt(int linha, int coluna) {
		
		Produto prod = produtos.get(linha);
		
		switch (coluna) {
			case 0:
				return prod.getCodigo();
			case 1:
				return prod.getNome();
			case 2:
				return prod.getSetor();	
			case 3:
				return String.format("%.2f", prod.getPrecoCusto());// Adapta o valor para moeda.
			case 4:
				return String.format("%.2f", prod.getPrecoVenda());// Adapta o valor para moeda.
			case 5:
				return prod.getEmEstoque();
			case 6:
				return prod.getFornecedor();
			case 7:
				return prod.getObservacao();
			default:
				throw new IndexOutOfBoundsException("Posição inexistente na tabela");
		}
	}
	
	
	
	
	
	
}
