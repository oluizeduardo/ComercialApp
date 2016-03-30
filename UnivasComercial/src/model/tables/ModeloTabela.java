package model.tables;

import java.util.ArrayList;

import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


/**
 * Essa classe é um modelo padrão para ser aplicado nas tabelas.
 * Cada frame que tiver uma tabela diferente, deve fazer o seu próprio TableModel.
 * Porém, todos as classes personalizadas de Tablemodel devem herdar desta classe.
 * 
 * @author Luiz Eduardo da Costa
 * @version 16/11/15
 * @param <C> Parâmetro deixa a classe mais genérica para o uso de listas.
 */
public class ModeloTabela<C> extends AbstractTableModel {


	private static final long serialVersionUID = 1L;


	// Renderizador de celulas.
	private DefaultTableCellRenderer rendererCell = null;
	
	// A lista com os dados a serem preenchidos na tabela.
	private ArrayList<C> lista = new ArrayList<C>();
	
	// Nomes das colunas da tabela.
	private String[] colunas = null;
	
	
	
	/**
	 * Configura as colunas da tabela.
	 * @param col
	 */
	public ModeloTabela(String[] col) {
		this.colunas = col;
	}
	
	
	
    /**
     * Configura o objeto <code>DefaultTableCellRenderer</code> para que, 
     * quando aplicado em uma tabela, centralize seu conteúdo.
     * 
     * @return Objeto <code>DefaultTableCellRenderer</code> 
     * configurado para centralização de conteúdos da tabela.
     */
    public TableCellRenderer centralizaCelula(){
        
    	rendererCell = new DefaultTableCellRenderer();
    	rendererCell.setHorizontalAlignment(SwingConstants.CENTER); 
        
        return rendererCell;       
    }



    /**
     * Retorna o número de coluna na tabela.
     */
	@Override
	public int getColumnCount() {
		return colunas.length;
	}



	/**
	 * Retorna o número de linhas na tabela.
	 */
	@Override
	public int getRowCount() {
		return lista.size();
	}



	// Cada classe personalizada de TableModel deve implementar esse método.
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return null;
	}
	
	
	
	/**
	 * Retorna o nome da coluna no indice especificado.
	 */
	@Override
	public String getColumnName(int column) {
		return colunas[column];
	}

	
	
	/**
	 * Retorna o tipo de dados contido nas células.
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	
	
	/**
	 * Bloqueia a edição de todas as colunas.
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	
	
	/**
	 * Adiciona um novo objeto na lista.
	 * @param a
	 */
	public void add(C obj){
		lista.add(obj);
		fireTableDataChanged();
	}
	
	
	
	
	/**
	 * Adiciona um novo objeto na posição desejada da lista.
	 * 
	 * @param pos
	 * @param obj
	 */
	public void add(int pos, C obj){
		lista.add(pos, obj);
		fireTableDataChanged();
	}
	
	
	
	
	
	/**
	 * Configura uma nova lista para o modelo de tabela.
	 * @param novaLista
	 */
	public void setLista(ArrayList<C> novaLista){
		this.lista = novaLista;
	}
	
		
	
	
	
	/**
	 * Remove uma linha da lista.
	 * @param linha
	 */
	public void removeRow(int linha){
	    this.lista.remove(linha);
	    fireTableRowsDeleted(linha, linha);
	}
	
	
	
	
	/**
	 * Remove todas as linhas da lista.
	 * @param linha
	 */
	public void removeAll(){
		
		int totalLista = lista.size();
		int index = lista.size() - 1;
		
		for(int inicio=0 ; index >= inicio; index--){
			this.lista.remove(index);
		}
		
		fireTableRowsDeleted(0, totalLista);
	}
	
	
	
	

	/**
	 * Retorna o elemento na linha desejada.
	 * @param linha
	 * @return Objeto C
	 */
	public C getElementAt(int linha) {
		return lista.get(linha);
	}
	
	
	/**
	 * Imprime no console as informações da lista.
	 */
	public void imprimeLista() {
		int finalLista = lista.size();
		for(int i=0; i < finalLista; i++){
			System.out.println(lista.get( i ).toString());
		}
	}
	
	
}
