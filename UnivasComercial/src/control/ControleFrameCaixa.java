package control;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.JOptionPane;

import model.DAO.ProdutoDAO;
import model.POJO.ItemVendido;
import model.POJO.Produto;
import view.FrameInicial;
import view.cadastro.FrameCadastroProdutos;
import view.cadastro.FrameCaixa;
import view.cadastro.FrameFinalizarVenda;
import view.consulta.FrameConsultaProdutos;


/**
 * Controlador do frame de vendas.
 */
public class ControleFrameCaixa {

	
	// O produto consultado pelo código.
	private Produto produtoConsultado = null;
	// Frame Inicial do sistema.
	private FrameInicial frameInicial = null;
	// O frame que o controlador está manipulando.
	private FrameCaixa frameCaixa = null;
	// Frame para cadastrar produtos com código não encontrado na pesquisa.
	private FrameCadastroProdutos frameCadastroProduto = null;
	// Frame para consultar produto enquanto realiza venda.
	private FrameConsultaProdutos frameConsultaProdutos = null;
	// Tela para finalizar a venda.
	private FrameFinalizarVenda frameFinalizarVenda = null;
	
	
	
	
	/**
	 * Construtor da classe ControleFrameVendas.
	 * Exige como parâmetro uma instância de FrameCaixa, que é a tela que 
	 * o controlador irá manipular.
	 * 
	 * @param inicial A instância do frame inicial que é a base do frame Caixa.
	 * @param frame A instância do frame Caixa que será controlado por esta classe.
	 */
	public ControleFrameCaixa(FrameInicial inicial, FrameCaixa frame) {
		this.frameCaixa = frame;
		this.frameInicial = inicial;
	}
	
	
	
	
	
	
	
	/**
	 * Executa uma atividade diferente dependendo da tela de atalho precionada.
	 * Exige como parâmetro o objeto KeyEvent que está sendo usado como objeto
	 * ouvinte do componente.
	 * @param e KeyEvent
	 */
	private void verificaTeclasDeAtalhos(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_F1){
			abreTelaConsultaDeProduto();
			
		}else if(e.getKeyCode() == KeyEvent.VK_F2){
			if(frameCaixa.getBtnFinalizar().isEnabled())
				abreTelaFinalizarVenda();
		}
	}
	
	
	
	
	
	/**
	 * Retorna um objeto KeyListener configurado para aplicar na tabela de itens vendidos.
	 * 
	 * @return KeyListener configurado.
	 */
	public KeyListener getKeyListenerTabela(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				// Deleta a linha selecionada quando apertar DELETE.
				if(e.getKeyCode() == KeyEvent.VK_DELETE){
					
					removeLinhaSelecionada();
					
				}else{
					verificaTeclasDeAtalhos(e);
				}
			}
		};
	}
	
	
	
	
	
	/**
	 * Retorna o ActionListener a ser adicionado no campo de código.
	 */
	public KeyListener getActionCampoCodigo(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
					if(!frameCaixa.getTfCodigo().getText().isEmpty()){
						
						// Limpa os campos quando entrar com novo código.
						frameCaixa.getTfQuantidade().setText("");
						frameCaixa.getTfPrecoTotal().setText("");
						
						try {
							
							int codigo = Integer.parseInt(frameCaixa.getTfCodigo().getText());
							
							produtoConsultado = consultaProdutoPeloCodigo(codigo);
							
							if(produtoConsultado != null){
																
								String num = String.format("%.2f", produtoConsultado.getPrecoVenda());  
								
								frameCaixa.getLbDescricao().setText(produtoConsultado.getNome());
								frameCaixa.getTfPrecoUnitario().setText(num);
								
								// Passa o foco para o campo quantidade.
								frameCaixa.getTfQuantidade().grabFocus();
							}else{
								
								// Emite um beep de alerta.
								Toolkit.getDefaultToolkit().beep();
								// Mostra mensagem de item inexistente.
								int opcao = JOptionPane.showConfirmDialog(null, 
									"Nenhum produto localizado com esse código!\n\n"
									+ "Deseja cadastrar um novo Produto?",
									"Produto Inexistente",JOptionPane.OK_CANCEL_OPTION);
								System.out.println("OPcao: "+opcao);
								
								if(opcao == 0){
									
									// Limpa o campo código na tela do caixa.
									frameCaixa.getTfCodigo().setText("");
									
									// Verifica se o frame já não está visível.
									if(frameCadastroProduto == null || (!frameCadastroProduto.isVisible())){
					            		
										// Exibe tela de cadastro de produtos.
										frameCadastroProduto = new FrameCadastroProdutos(frameInicial);
					            		
					            		frameInicial.getDesktopPane().add(frameCadastroProduto);
					            		frameCadastroProduto.setLocation(frameInicial.getDesktopPane());
					            		frameCadastroProduto.setVisible(true);
					            		frameCadastroProduto.getTfCodigo().setText(""+codigo);
					            		frameCadastroProduto.getTfNome().grabFocus();// Coloca o foco no campo Nome.
									}
								}
								
							}
							
						} catch (NumberFormatException ex) {
							frameCaixa.getTfCodigo().setText("");
						}
					}					
				}else {
					verificaTeclasDeAtalhos(e);
				}
			}
		};
	}
	
	
	
	
	
	
	/**
	 * Retorna o KeyListener a ser adicionado no campo de quantidade.
	 * Um evento é executado somente quando a tecla ENTER for precionada
	 * no momento em que o cursor entiver no campo 'Quantidade'.
	 */
	public KeyListener getActionCampoQuantidade(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == KeyEvent.VK_ENTER){				
					try {
						
						Double precoUnitario = Double.parseDouble(frameCaixa.getTfPrecoUnitario().getText().replace(',', '.'));
						precoUnitario = Double.valueOf(precoUnitario);
						
						int quantidade = Integer.parseInt(frameCaixa.getTfQuantidade().getText());
						
						String num = String.format("%.2f", (precoUnitario * quantidade));  
						
						frameCaixa.getTfPrecoTotal().setText(num);
						
						// Coloca o foco em cima do botão Adicionar.
						frameCaixa.getBtnAdicionar().grabFocus();
												
					} catch (NumberFormatException ex) {
						frameCaixa.getTfPrecoUnitario().setText("");
						frameCaixa.getTfQuantidade().setText("");
						frameCaixa.getTfPrecoTotal().setText("00,00");
					}
				}else {
					verificaTeclasDeAtalhos(e);
				}
			}
		};
	}
		
		
	
	
	/**
	 * Retorna o ActionListener do botão Adicionar.
	 */
	public ActionListener getActionAdicionar(){
		return new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				// Captura todos os dados inseridos pelos usuário e adiciona registro na tabela.
				adicionaItemNaTabela();
			}
		};
	}
	
	
	
	
	/**
	 * Retorna o KeyListener do botão Adicionar.
	 */
	public KeyListener getKeyListenerAdicionar(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					adicionaItemNaTabela();
				}
			}
		};
	}
	
	
	
	
	/**
	 * Realiza a validação dos campos principais e adiciona 
	 * o tem pesquisado na tabela de itens vendidos.
	 * 
	 * Captura todos os dados inseridos pelos usuário e adiciona registro na tabela.
	 */
	private void adicionaItemNaTabela() {
		
		// Verifica se o campo quantidade está em branco.
		if(frameCaixa.getTfQuantidade().getText().isEmpty()){
			
			// Coloca o cursor no campo quantidade.
			frameCaixa.getTfQuantidade().grabFocus();
			
		}else{
			
			// Preenche um novo item Vendido.
			ItemVendido novoitem = frameCaixa.getNovoItemVendido();
			novoitem.setProduto(produtoConsultado);
			
			// Verifica se já existe na lista algum item vendido com o código informado.
			int indiceItemExistente = frameCaixa.getTableModel().getIndexItemPeloCodigo(produtoConsultado.getCodigo());
			
			// Se o indice for um valor menor que zero, significa que não tem o item na lista.
			if(indiceItemExistente < 0){
				frameCaixa.getTableModel().add(novoitem);
			}else{
				
				ItemVendido iv = frameCaixa.getTableModel().getLista().remove(indiceItemExistente);
				novoitem.setQuantidade(iv.getQuantidade() + novoitem.getQuantidade());
				novoitem.setValorTotal( (novoitem.getProduto().getPrecoVenda() * novoitem.getQuantidade()) );
				
				frameCaixa.getTableModel().add(indiceItemExistente, novoitem);
			}
			
			atualizaValorTotal();
			
			// Verifica quantos itens tem na lista para ativar o botão de finalizar.
			// Impede que finalize a venda sem ter comprado nenhum produto.
			int qtdeItensLista = frameCaixa.getTableModel().getRowCount();
			if(qtdeItensLista > 0)
				frameCaixa.getBtnFinalizar().setEnabled(true);
			
			
			// Limpa os campos para nova pesquisa.
			frameCaixa.getLbDescricao().setText("");
			frameCaixa.getTfCodigo().setText("");
			frameCaixa.getTfQuantidade().setText("");
			frameCaixa.getTfPrecoUnitario().setText("");
			frameCaixa.getTfPrecoTotal().setText("");
			frameCaixa.getTfCodigo().grabFocus();// Volta o foco para o campo código.
		}
	}




	/**
	 * Retorna o ActionListener do botão Pesquisar.
	 * 
	 * Exige como parâmetro a instância de um FrameInicial.
	 */
	public ActionListener getActionPesquisar(final FrameInicial frameInicial){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				abreTelaConsultaDeProduto();
			}
		};
	}
	
	

	
	
	
	/**
	 * Exibe a tela de consulta de produtos, caso a mesma já não esteja sendo exibida.
	 */
	private void abreTelaConsultaDeProduto(){
		if(frameConsultaProdutos == null || (!frameConsultaProdutos.isVisible())){
			frameConsultaProdutos = new FrameConsultaProdutos();
    		
    		frameInicial.getDesktopPane().add(frameConsultaProdutos);
    		frameConsultaProdutos.setLocation(frameInicial.getDesktopPane());
    		frameConsultaProdutos.setVisible(true);
		}
	}
	
	
	
	/**
	 * Exibe a tela de fialização de venda, caso a mesma já não esteja sendo exibida.
	 */
	private void abreTelaFinalizarVenda(){
		if(frameFinalizarVenda == null || (!frameFinalizarVenda.isVisible())){		
			
			frameCaixa.setVisible(false);					
			
			Double totalGeral = frameCaixa.getTableModel().getValorTotalItensVendidos();
			
			frameFinalizarVenda = new FrameFinalizarVenda(frameCaixa, frameInicial, totalGeral);
    		
    		frameInicial.getDesktopPane().add(frameFinalizarVenda);
    		frameFinalizarVenda.setLocation(frameInicial.getDesktopPane());
    		frameFinalizarVenda.setVisible(true);
    		frameFinalizarVenda.getBtnFinalizar().grabFocus();
		}
	}
	
	
	
	
	
	/**
	 * Retorna o ActionListener do botão Finalizar Venda.
	 * 
	 * Exige como parâmetro a instância de um FrameInicial.
	 */
	public ActionListener getActionFinalizar(final FrameInicial frameInicial){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				abreTelaFinalizarVenda();
			}
		};
	}
	
	
	
	
	
	
	
	/**
	 * Retorna o objeto Produto pesquisado no banco de dados co o código informado.
	 * @param codigo
	 */
	private Produto consultaProdutoPeloCodigo(int codigo){
		
		String sql = "SELECT * FROM produtos WHERE codigo = "+codigo;
		
		List<Produto> lista = ProdutoDAO.getInstancia().consulta(sql);
		
		// Se não for objeto nulo e nem estar vazia.
		if(lista != null && !lista.isEmpty())
			return lista.get(0);
		else
			return null;
	}
	
	
	
	
	/**
	 * Retorna a ação a ser aplicada no PopUpMenu que exclui um item da lista 
	 * dos produtos vendidos.
	 */
	public ActionListener getActionPopUpExcluir(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				removeLinhaSelecionada();
			}
		};
	}
	
	
	
	/**
	 * Verifica a linha selecinada e a remove da tabela.
	 */
	private void removeLinhaSelecionada(){
		// Captura o número da linha selecionada na tabela.
		int linhaSelecionada = frameCaixa.getTabela().getSelectedRow();	
		
		if(linhaSelecionada >= 0){
			// Remove o item da lista.
			frameCaixa.getTableModel().removeRow(linhaSelecionada);
			
			atualizaValorTotal();
		}
	}
	
	
	
	
	/**
	 * Atualiza o valor total dos itens comprados.
	 * Soma o valor de todos os itens da lista e imprime o resultado em um label.
	 */
	private void atualizaValorTotal(){
		// Atualiza o label com o total geral dos itens vendidos.
		Double totalGeral = frameCaixa.getTableModel().getValorTotalItensVendidos();

		// Se excluir todos os itens da lista, impede que finalize a venda.
		if(totalGeral == 0){
			frameCaixa.getBtnFinalizar().setEnabled(false);
			frameCaixa.getLbTotal().setText("00,00");
		}else{
			frameCaixa.getLbTotal().setText(String.format("%.2f", totalGeral));
		}
	}
	
	
	
	
	
	
	
	
	
	
}
