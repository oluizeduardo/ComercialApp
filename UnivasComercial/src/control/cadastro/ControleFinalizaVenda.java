package control.cadastro;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.DAO.FormaDePagamentoDAO;
import model.DAO.VendaDAO;
import model.POJO.FormaDePagamento;
import model.POJO.ItemVendido;
import model.POJO.Venda;
import view.FrameInicial;
import view.cadastro.FrameCaixa;
import view.cadastro.FrameFinalizarVenda;


/**
 * Controlador do frame de finalização de vendas.
 *
 */
public class ControleFinalizaVenda {

	
	
	// O frame que está sendo manipulado.
	private FrameFinalizarVenda frameFinalizaVenda = null;
	// O frame caixa para realização das vendas.
	private FrameCaixa frameCaixa = null;
	// Tela inicial do sistema.
	private FrameInicial frameInicial = null;
	
	
	
	
	/**
	 * Construtor da classe ControleFinalizaVenda.
	 * 
	 * Exige como parâmetro a instância do frame que está sendo manipulado
	 * por essa classe de controle.
	 */
	public ControleFinalizaVenda(FrameCaixa caixa, FrameFinalizarVenda frame, FrameInicial frameini) {
		this.frameCaixa = caixa;
		this.frameFinalizaVenda = frame;
		this.frameInicial = frameini;
	}
	
	
	
	
	/**
	 * Consulta no banco de dados as formas de pagamento disponível e retorna
	 * em forma de vector de String.
	 */
	public Vector<String> consultaFormasDePagamento(){
		
		Vector<String> formaPag = null;
		List<FormaDePagamento> lista = FormaDePagamentoDAO.getInstancia().consulta(FormaDePagamentoDAO.CONSULTA_TABELA_INTEIRA);
		
		if(!lista.isEmpty()){
			
			formaPag = new Vector<String>();
			
			for(int i=0; i < lista.size(); i++){
				formaPag.add(lista.get( i ).getDescricao());
			}
		}
		return formaPag;
	}
	
	
	
	
	/**
	 * Retorna o objeto KeyListener que dá ação ao botão quando 
	 * a tecla ENTER for precionada sobre ele.
	 */
	public KeyListener getKeyListenerFinalizar(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					finalizaVenda();
				}
			}
		};
	}
	
	
	
	/**
	 * Retorna o objeto KeyListener que dá ação ao botão quando 
	 * a tecla ENTER for precionada sobre ele.
	 */
	public KeyListener getKeyListenerVoltar(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
					frameFinalizaVenda.setVisible(false);
					frameCaixa.setVisible(true);
				}
			}
		};
	}

	
	
	
	/**
	 * Retorna um objeto ActionListener responsável por verificar 
	 * os eventos e exeucatar uma ação quando necessário.
	 */
	public ActionListener getActionVoltar(){
		return new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				frameFinalizaVenda.setVisible(false);
				frameCaixa.setVisible(true);
			}
		};
	}
	
	
	
	
	
	/**
	 * Retorna o objeto ActionListener que dá ação ao botão que fializa uma venda.
	 */
	public ActionListener getActionBtnFinalizar(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				finalizaVenda();		
			}
		};
	}




	/**
	 * Reúne todos os dados da tela para finalizar a venda e registrar no banco de dados.
	 * <p>
	 * Interrompe o relógio, junta todos os dados em um objeto e cadastra no banco de dados.
	 */
	protected void finalizaVenda() {
		
		// Interrompe o relógio quando a venda é finalizada.
		frameFinalizaVenda.getRelogio().interrupt();				
		
		// Pega as informações de finalização de Venda.
		Venda novaVenda = getNovaVenda();				
		
		// Cadastra e retorna o staus da execução.
		boolean cadastrou = VendaDAO.getInstancia().cadastra(novaVenda);
		
		if(cadastrou){
			JOptionPane.showMessageDialog(null, "Venda finalizada com sucesso!!");
			frameFinalizaVenda.setVisible(false);
			
			if(frameCaixa == null || (!frameCaixa.isVisible())){
				frameCaixa = new FrameCaixa(frameInicial);
        		
        		frameInicial.getDesktopPane().add(frameCaixa);
        		frameCaixa.setLocation(frameInicial.getDesktopPane());
        		frameCaixa.setVisible(true);
			}
			
			
		}else{
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro e a venda não pode ser registrada!","Erro",JOptionPane.ERROR_MESSAGE);
		}		
		
	}




	/**
	 * Retorna um novo objeto preenchido com as informações de uma nova venda.
	 * O obeto retornado por esse método servirá para cadastro na tabela Vendas
	 * no banco de dados.
	 */
	protected Venda getNovaVenda() {
		
		// Consulta o código da forma de pagamento utilizada na venda.
		FormaDePagamento formPag = new FormaDePagamento();
		formPag.setDescricao((String)frameFinalizaVenda.getcbxFormaDePagamento().getSelectedItem());
		
		// Consulta no sequence da venda qual o novo ID válido a ser aplicado.
		int codigo = VendaDAO.getInstancia().consultaProximoIDVenda();
				
		Venda novaVenda = new Venda();
		novaVenda.setCodigo(codigo);
		novaVenda.setData(frameFinalizaVenda.getTfData().getText());
		novaVenda.setFormaDePagamento(formPag);
		novaVenda.setHora(frameFinalizaVenda.getRelogio().getHoraAtualSistema());		
		novaVenda.setTotal(Double.parseDouble(frameFinalizaVenda.getTfValorFinal().getText().replace(',', '.')));
		// Pega da tabela a lista de itens vendidos.
		//novaVenda.setItensVendidos(frameCaixa.getTableModel().getLista());
		novaVenda.setItensVendidos(relacionaItensComVenda(novaVenda));
		
		return novaVenda;
	}
	
	
	
	
	
	private List<ItemVendido> relacionaItensComVenda(Venda venda){
		
		List<ItemVendido> itensVendidos = frameCaixa.getTableModel().getLista();
		List<ItemVendido> listaAtualizada = new ArrayList<ItemVendido>();
		
		for(int i=0; i < itensVendidos.size(); i++){
			
			ItemVendido it = itensVendidos.get( i );
			it.setVenda(venda);
			
			listaAtualizada.add(i , it);
		}
		
		return listaAtualizada;
	}
	
	
	
	
	
	
	
}
