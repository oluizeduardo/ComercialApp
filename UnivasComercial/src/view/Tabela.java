package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;

import model.Constantes;


/**
 * Padroniza a construção de tabelas no sistema.
 * <p>
 * Essa classe reúne vários detalhes que formatam uma tabela.
 * 
 * @author Luiz Eduardo da Costa
 *
 */
public class Tabela extends JTable implements Constantes{


	private static final long serialVersionUID = 1L;

	
	// O modelo a ser aplicado na tabela.
	private TableModel modelo = null;
	
	
	
	/**
	 * Construtor da classe tabela.
	 * 
	 * @param model Algum filho de TableModel para ser aplicado como modelo da tabela.
	 */
	public Tabela(TableModel model) {
		this.modelo = model;
		
		super.setModel(modelo);
		super.setRowHeight(25);// Altura das linhas.
		super.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		super.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));//Nome das colunas em negrito.
		super.setFont(FONTE_CAMPO_TEXTO);
		super.getTableHeader().setBackground(new Color(200, 200, 200));
        super.setSelectionForeground(Color.BLACK);
        super.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}
	
	
	
}
