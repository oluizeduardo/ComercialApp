package control.cadastro;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.DAO.ProdutoDAO;
import model.POJO.Produto;
import view.FrameInicial;
import view.cadastro.FrameCadastroProdutos;
import view.cadastro.FrameCaixa;


/**
 * Classe que controla o frame de cadastro de produtos.
 *
 */
public class ControleCadastroProduto {

	
	/**Frame onde os dados estão sendo manipulados.*/
	private FrameCadastroProdutos frameCadastro = null;
	
	
	
	
	/**
	 * Construtopr da classe ControleCadastroProduto, exige como parâmetro
	 * um objeto FrameCadastroProdutos que é a tela onde os dados estão sendo manipulados.
	 * @param frame
	 */
	public ControleCadastroProduto(FrameCadastroProdutos frame) {
		this.frameCadastro = frame;
	}
	
	
	
	
	
	/**
	 * Evento que dá ação ao botão de cadastro de novo produto.
	 * @return ActionListener do botão Salvar.
	 */
	public ActionListener getActionCadastrarProduto(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(frameCadastro.validaCampos()){
					Produto novoProduto = frameCadastro.getProduto();
					
					boolean cadastrou = ProdutoDAO.getInstancia().cadastra(novoProduto);
					
					if(cadastrou){
						JOptionPane.showMessageDialog(frameCadastro, "Cadastro realizado com sucesso!!");
						frameCadastro.limpaTela();
					}
				}else{
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(frameCadastro, "Preencha os campos corretamente!","Alerta", JOptionPane.WARNING_MESSAGE);
				}
			}
		};
	}
	
	
	
	/**
	 * Evento que dá ação ao botão Novo Cadastro.
	 * @return ActionListener do botão Novo Cadastro.
	 */
	public ActionListener getActionNovoCadastro(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frameCadastro.limpaTela();				
			}
		};
	}
	
	
	
	
	
	/**
	 * Evento que dá ação ao botão que acessa a tela do caixa para realização de uma nova venda.
	 * 
	 * @param frameInicial
	 * @return ActionListener do botão Caixa.
	 */
	public ActionListener getActionAbrirTelaCaixa(final FrameInicial frameInicial){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				frameCadastro.setVisible(false);
				
				FrameCaixa frameCaixa = new FrameCaixa(frameInicial);
        		
        		frameInicial.getDesktopPane().add(frameCaixa);
        		frameCaixa.setLocation(frameInicial.getDesktopPane());
        		frameCaixa.setVisible(true);	
			}
		};
	}
	
	
	
	

	
	
}
