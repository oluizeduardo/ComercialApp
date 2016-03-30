package control.consulta;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import model.DAO.ProdutoDAO;
import model.POJO.Produto;
import model.POJO.Setor;
import view.consulta.FrameConsultaProdutos;



/**
 * Controlador do frame de consulta de produtos.
 * <p>
 * Possui um atributo do tipo FrameConsultaProdutos, onde o objeto consegue
 * buscar os métodos particulares da sua classe.
 *
 * @author Luiz Eduardo da Costa
 */
public class ControleConsultaProdutos {

	
	// O frame que está sendo manipulado.
	private FrameConsultaProdutos frameConsulta = null;
	
	
	
	/**
	 * Método construtor da classe ControleConsultaProdutos.
	 * 
	 * @param frame A tela que estará sendo manipulada.
	 */
	public ControleConsultaProdutos(FrameConsultaProdutos frame) {
		this.frameConsulta = frame;
		
	}
	
	
	
	
	/**
	 * Preenche a tabela com os registros da lista informada no parâmetro.
	 */
	public void preencheTabela(List<Produto> listaDeProdutos){

		if(listaDeProdutos != null ){			

			// Remove todas as linhas da tabela para colocar os registros da lista.
			// Isso deve ser feito senão aparecerá linhas duplicadas na tabela.
			frameConsulta.getTableModel().removeAll();
			
			for(int i=0; i < listaDeProdutos.size(); i++){
				
				frameConsulta.getTableModel().add(listaDeProdutos.get( i ));
			}
		}
	}
	
	
	
	
	/**
	 * Ação aplicada ao PopupMenu referente à exclusão de um registro da tabela de consulta de produtos.
	 */
	public ActionListener getActionExcluir(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Produto prod = frameConsulta.getProdutoSelecionado();
				
				int opcao = JOptionPane.showConfirmDialog(frameConsulta, 
						"Deseja excluir permanentemente esse produto?", 
						"Excluir Produto", JOptionPane.OK_CANCEL_OPTION);
				
				if(opcao == 0){
					if(ProdutoDAO.getInstancia().exclui(prod.getCodigo())){
						
						int linha = frameConsulta.getTabela().getSelectedRow();
						
						frameConsulta.getTableModel().removeRow(linha);
					}
				}
			}
		};
	}
	
	
	
	
	/**
	 * Ação aplicada no botão de recarregar os registros da tabela de produtos.
	 */
	public ActionListener getActionBtnRecarregar(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// LImpa o campo nome.
				frameConsulta.getTfNome().setText("");
				
				int linhas = frameConsulta.getTabela().getRowCount();
				
				if(linhas > 0){
					frameConsulta.getTableModel().removeAll();	
				}

				String sql = ProdutoDAO.CONSULTA_TABELA_INTEIRA+ProdutoDAO.ORDENA_CRESCENTE_POR_NOME;
				
				preencheTabela(ProdutoDAO.getInstancia().consulta(sql));
			}
		};
	}
	
	
	
	
	/**
	 * Ação aplicada ao botão de pesquisar Produto.
	 */
	public ActionListener getActionBtnPesquisar(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					
				String sql = null;
				int codSetor = ( (Setor) frameConsulta.getCbxSetores().getSelectedItem()).getCodigo();
				
				if(frameConsulta.getTfNome().getText().isEmpty()){
					sql = ProdutoDAO.CONSULTA_TABELA_INTEIRA+" WHERE setor = "+codSetor;
				}else{
					
					// Captura o nome do produto informado pelo usuário.
					String nomeProduto = frameConsulta.getTfNome().getText().toUpperCase();
					
					sql = ProdutoDAO.CONSULTA_TABELA_INTEIRA + 
							" WHERE nome LIKE '"+nomeProduto+"%' AND setor = "+codSetor+" "
									+ ""+ProdutoDAO.ORDENA_CRESCENTE_POR_NOME;
				}
	
				// Realiza a consulta que retorna os resultados para uma lista.
				List<Produto> lista = ProdutoDAO.getInstancia().consulta(sql);
				
				// Se a lista estiver vazia significa que nenhum registro foi encontrado na pesquisa.
				if(lista == null || lista.size() < 1){

					JOptionPane.showMessageDialog(frameConsulta, "Nenhum registro encontrado!","Consulta de Produto", JOptionPane.WARNING_MESSAGE);
					
				}else{
					
					// Preenche a tabela com todos os registros encontrados na pesquisa.
					preencheTabela(lista);
				}
			}
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
