package control.cadastro;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;

import model.DAO.SetorDAO;
import model.POJO.Setor;
import view.cadastro.FrameCadastroSetor;


/**
 * Classe que controla o cadastro, consulta, atualização e exclusão dos setores.
 */
public class ControleCadastroSetor {

	
	
	/**O frame que está sendo utilizado para o cadastro de setores.*/
	private FrameCadastroSetor frameCadSetores = null;
	
	
	
	
	/**
	 * Contrutor da classe ControleCadastroSetor.
	 * Exige cmo parâmetro a instância do frame que esta sendo utilizado.
	 * 
	 * @param frame instância do FrameCadastroSetor.
	 */
	public ControleCadastroSetor(FrameCadastroSetor frame) {
		this.frameCadSetores = frame;
	}
	
	
	
	
	/**
	 * Evento que dá ação ao botão de cadastro de novo setor.
	 * @return ActionListener do botão Salvar.
	 */
	public ActionListener getActionCadastrarSetor(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				cadastraNovoSetor();
			}
		};
	}
	
	

	
	
	
	/**
	 * Realiza a validação do campo.
	 * Captura a descrição informada pelo usuário e a cadastra no bancod e dados.
	 * Caso haja um erro na execução, um alerta é exibido na tela.
	 */
	public void cadastraNovoSetor(){
		if(frameCadSetores.validaCampos()){
			Setor novoSetor = frameCadSetores.getSetor();
			
			boolean cadastrou = SetorDAO.getInstancia().cadastra(novoSetor);
			
			if(cadastrou){
				JOptionPane.showMessageDialog(frameCadSetores, "Cadastro realizado com sucesso!!");
				atualizaListaDeSetores();
				frameCadSetores.limpaTela();
			}
		}else{
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(frameCadSetores, 
					"Preencha corretamente o campo!", "Cadastro de Setor", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	
	
	/**
	 * Consulta a tabela de setores no banco de dados e atualiza a lista na tela.
	 */
	public void atualizaListaDeSetores(){
		String sql = SetorDAO.CONSULTA_TABELA_INTEIRA + SetorDAO.ORDENA_CRESCENTE_POR_DESCRICAO;
		
		List<Setor> listaDeSetores = SetorDAO.getInstancia().consulta(sql);
		
		frameCadSetores.atualizarLista(listaDeSetores);
	}




	/**
	 * Atualiza um registro da lista de setores.
	 * @param set O registro a ser editado.
	 */
	public void atualizaRegistro(Setor set) {
		boolean atualizou = SetorDAO.getInstancia().atualiza(set);
		
		if(atualizou){
			atualizaListaDeSetores();
			JOptionPane.showMessageDialog(frameCadSetores, "Atualizado com sucesso!");
		}else{
			JOptionPane.showMessageDialog(frameCadSetores, "Erro na atualização!");
		}
	}
	
	
	
	
	/**
	 * Exclui um registro da lista de Setores.
	 * @param codigo 
	 */
	public void excluiRegistro(int codigo){
		boolean excluiu = SetorDAO.getInstancia().exclui(codigo);
		
		if(excluiu){
			atualizaListaDeSetores();
		}
	}
	
	
	
	
	
}
