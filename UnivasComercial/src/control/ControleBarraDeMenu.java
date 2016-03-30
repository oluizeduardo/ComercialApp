package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import view.FrameInicial;
import view.cadastro.FrameCadastroProdutos;
import view.cadastro.FrameCadastroSetor;
import view.cadastro.FrameCaixa;
import view.consulta.FrameConsultaProdutos;
import view.consulta.FrameRelatorios;


public class ControleBarraDeMenu {

	
	// Frame Inicial.
	private FrameInicial frameInicial = null;
	
	// Formulário de cadastro de produtos.
	private FrameCadastroProdutos frameCadastroProduto = null;
	
	// Formulário de consulta de produtos.
	private FrameConsultaProdutos frameConsultaProdutos = null;
	
	// Consulta de relatórios.
	private FrameRelatorios frameRelatorios = null;
	
	// Cadastro de Setores.
	private FrameCadastroSetor frameCadastroSetor = null;
	
	// Registrar Venda.
	private FrameCaixa frameVenda = null;
	
	
	
	
	/**Método construtor da classe ControlerMenubar.*/
	public ControleBarraDeMenu(FrameInicial frame) {	
		this.frameInicial = frame;
	}
	
	
	
	
	/**
	 * Exibe mensagem perguntando se o usuário deseja mesmo finalizar o programa.
	 */
	public void sair(){
		int opcao = JOptionPane.showConfirmDialog(null, "Deseja mesmo sair do aplicativo?","Sair", JOptionPane.OK_CANCEL_OPTION);
    	
    	if(opcao == 0){
    		System.exit(0);
    	}
	}
	
	
	
	
	/**ActionListener da opção Sair.*/
	public ActionListener getActionSair(){
		return new ActionListener() {
            // Fecha a tela.
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	sair();            
            }
        };
	}
	
	
	
	
	
	
	/**ActionListener do Menu Novo Produto.*/
	public ActionListener getActionNovoProduto(){
		return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {               
            	
            	if(frameCadastroProduto == null || (!frameCadastroProduto.isVisible())){
            		frameCadastroProduto = new FrameCadastroProdutos(frameInicial);
            		
            		frameInicial.getDesktopPane().add(frameCadastroProduto);
            		frameCadastroProduto.setLocation(frameInicial.getDesktopPane());
            		frameCadastroProduto.setVisible(true);
            	}
            }
        };
	}
	
	
	
	
	
	
	/**
	 * ActionListener do menu 'Sobre'. Mostra informações sobre o sistema.
	 */
	public ActionListener getActionMnSobre(){
		return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	String msg = "Sistema de Automação de Vendas e Controle Comercial\n\n"
            			+ "Univás - Sistemas de Informação - 2015\n"
            			+ "Versão 01.16\n\n"
            			+ "Software desenvolvido por:\n"
            			+ "Fabiano Lomonaco Junior e Luiz Eduardo da Costa\n\n"
            			+ "Requisitos:\n"
            			+ " - Java Runtime Environment 7\n"
            			+ " - Banco de Dados PostgreSQL\n";
            	
            	JOptionPane.showMessageDialog(null,msg, "Sobre", JOptionPane.DEFAULT_OPTION);
            }
        };
	}




	/**
	 * Quando o evento desse ActionListener é executado, exibe a tela de relatórios.
	 */
	public ActionListener getActionConsultaRelatorios() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frameRelatorios == null || (!frameRelatorios.isVisible())){
            		frameRelatorios = new FrameRelatorios();
            		
            		frameInicial.getDesktopPane().add(frameRelatorios);
            		frameRelatorios.setLocation(frameInicial.getDesktopPane());
            		frameRelatorios.setVisible(true);
            	}
			}
		};
	}




	/**
	 * Quando o evento desse ActionListener é executado, exibe a tela de consulta de Produtos.
	 */
	public ActionListener getActionConsultaProdutos() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frameConsultaProdutos == null || (!frameConsultaProdutos.isVisible())){
            		frameConsultaProdutos = new FrameConsultaProdutos();
            		
            		frameInicial.getDesktopPane().add(frameConsultaProdutos);
            		frameConsultaProdutos.setLocation(frameInicial.getDesktopPane());
            		frameConsultaProdutos.setVisible(true);
            	}
			}
		};
	}
	
	
	
	
	/**
	 * Quando o evento desse ActionListener é executado, exibe a tela de cadastro de Setor.
	 */
	public ActionListener getActionCadastroSetor() {
		
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frameCadastroSetor == null || (!frameCadastroSetor.isVisible())){
            		frameCadastroSetor = new FrameCadastroSetor();
            		
            		frameInicial.getDesktopPane().add(frameCadastroSetor);
            		frameCadastroSetor.setLocation(frameInicial.getDesktopPane());
            		frameCadastroSetor.setVisible(true);
            	}
			}
		};
	}
	
	
	
	
	/**
	 * Quando o evento desse ActionListener é executado, exibe a tela de nova venda.
	 */
	public ActionListener getActionNovaVenda() {
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(frameVenda == null || (!frameVenda.isVisible())){
					frameVenda = new FrameCaixa(frameInicial);
            		
            		frameInicial.getDesktopPane().add(frameVenda);
            		frameVenda.setLocation(frameInicial.getDesktopPane());
            		frameVenda.setVisible(true);
				}
			}
		};
	}


	
	
	
}
