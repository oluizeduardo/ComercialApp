package view.cadastro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import control.ControleFrameCaixa;
import model.Campo;
import model.POJO.ItemVendido;
import model.tables.ItensVendidosTableModel;
import view.FrameInicial;
import view.FrameInternoBase;
import view.Tabela;


/**
 * Frame para gerar uma nova venda.
 */
public class FrameCaixa extends FrameInternoBase {


	private static final long serialVersionUID = 1L;

	private Campo campo = new Campo(this);
	
	private JPanel pnCampos, pnTabela, pnDescricao, pnBotoes;
	
	private JLabel lbDescricao, lbTotal;
	
	private JButton btnCancelar, btnPesquisar, btnFinalizar, btnAdicionar;
	
	private JScrollPane pnRolagem = null;
	// Modelo de tabela. 
	private ItensVendidosTableModel modelo = new ItensVendidosTableModel();
	
	private Tabela tabela;
	
	private JTextField tfCodigo, tfQtde, tfPrecoUnitario, tfPrecoTotal;
	
	private ControleFrameCaixa controle = null;
	// Instância do frame inicial que está sendo executado.
	private FrameInicial frameInicial = null;
	
	
	
	
	
	/**
	 * Construtor da classe Framevenda.
	 */
	public FrameCaixa(FrameInicial frame) {
		super(" Caixa - Nova Venda ");
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.frameInicial = frame;
		
		controle = new ControleFrameCaixa(frameInicial, this);
		configuraFrame();
		
		super.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				cancelar();
			}
		});
		
	}
	
	
	
	
	/**
	 * Configura o frame e adiciona novos componentes na tela.
	 */
	private void configuraFrame(){
		
		setSize(new Dimension(super.getWidth() + 40, super.getHeight()));
		getPnBase().setSize(super.getSize());
		addKeyListener(controle.getKeyListenerTabela());
		
		pnCampos = new JPanel(new GridLayout(9, 1, 0, 10));
		//pnCampos.setBackground(COR_PAINEL_BACKGROUND);
		pnCampos.setBounds(560, 30, 150, 330);
		pnCampos.add(campo.createJLabel("Código"));
		pnCampos.add(getTfCodigo());
		pnCampos.add(campo.createJLabel("Qtde"));
		pnCampos.add(getTfQuantidade());
		pnCampos.add(campo.createJLabel("Preço Unit. (R$)"));
		pnCampos.add(getTfPrecoUnitario());
		pnCampos.add(campo.createJLabel("Preço Total (R$)"));
		pnCampos.add(getTfPrecoTotal());		
		
		
		pnDescricao = new JPanel(null);
		pnDescricao.setBounds(15, 15, 520, 75);
		pnDescricao.setBorder(BorderFactory.createRaisedBevelBorder());
	//	pnDescricao.setBackground(COR_PAINEL_BACKGROUND);
		
		pnTabela = new JPanel(new GridLayout(1, 1));
		pnTabela.setBounds(15, 103, pnDescricao.getWidth(), 295);
		pnTabela.setBorder(BorderFactory.createRaisedBevelBorder());
	//	pnTabela.setBackground(COR_PAINEL_BACKGROUND);
		
		pnBotoes = new JPanel(new GridLayout(1, 2, 5, 0));
		pnBotoes.setBounds(15, 405, pnTabela.getWidth(), 55	);
		pnBotoes.add(getBtnCancelar());
		pnBotoes.add(getBtnPesquisar());
		pnBotoes.add(getBtnFinalizar());
	//	pnBotoes.setBackground(COR_PAINEL_BACKGROUND);

		pnRolagem = new JScrollPane(getTabela());
		pnTabela.add(pnRolagem);
		pnDescricao.add(getLbDescricao());
		getPnBase().add(pnTabela);
		getPnBase().add(pnDescricao);
		getPnBase().add(pnBotoes);
		getPnBase().add(getBtnAdicionar());
		getPnBase().add(pnCampos);
		getPnBase().add(getLbTotal());
		
	}
	
	
	
	
	/**
	 * Retorna a instância do rótulo que informa o valor do total dos itens adicionados na tabela.
	 */
	public JLabel getLbTotal() {
		if(lbTotal == null){
			lbTotal = campo.createJLabel("00,00", 540, 408, 170, 50);
			lbTotal.setFont(new Font(NOME_FONTE, Font.BOLD, 35));
			lbTotal.setHorizontalAlignment(JLabel.RIGHT);
			lbTotal.setForeground(Color.red);
		}
		return lbTotal;
	}




	/**
	 * Retorna a instância do botão de adicionar novo item na lista de produtos vendidos.
	 */
	public JButton getBtnAdicionar(){
		if(btnAdicionar == null){
			btnAdicionar = new JButton("Adicionar");
			btnAdicionar.setCursor(HAND_CURSOR);
			btnAdicionar.setBounds(560, 347, 150, 50);
			btnAdicionar.setFont(new Font(NOME_FONTE, Font.ITALIC + Font.BOLD, 18));
			super.addMouseEventIO(btnAdicionar);
			btnAdicionar.setToolTipText("Adicionar item à lista de vendas");
			btnAdicionar.addActionListener(controle.getActionAdicionar());
			btnAdicionar.addKeyListener(controle.getKeyListenerAdicionar());
		}
		return btnAdicionar;
	}
	
	
	
	/**
	 * Retorna a instância do botão para cancelar a venda.
	 */
	private JButton getBtnCancelar(){
		if(btnCancelar == null){
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setCursor(HAND_CURSOR);
			btnCancelar.setFocusable(false);
			btnCancelar.setFont(new Font(NOME_FONTE, Font.ITALIC + Font.BOLD, 18));
			super.addMouseEventIO(btnCancelar);
			btnCancelar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelar();
				}
			});
		}
		return btnCancelar;
	}
	
		
	
	
	
	/**
	 * Retorna a instância do botão para abrir a tela de pesquisa de produtos.
	 */
	private JButton getBtnPesquisar(){
		if(btnPesquisar == null){
			btnPesquisar = new JButton("Pesquisar (F1)");
			btnPesquisar.setCursor(HAND_CURSOR);
			btnPesquisar.setFocusable(false);// Retira o foco do botão.
			btnPesquisar.setFont(new Font(NOME_FONTE, Font.ITALIC + Font.BOLD, 18));
			super.addMouseEventIO(btnPesquisar);
			btnPesquisar.addActionListener(controle.getActionPesquisar(frameInicial));
		}
		return btnPesquisar;
	}
	
	
	
	
	
	/**
	 * Retorna a instância do botão de finalizar as vendas.
	 */
	public JButton getBtnFinalizar(){
		if(btnFinalizar == null){
			btnFinalizar = new JButton("Finalizar (F2)");
			btnFinalizar.setCursor(HAND_CURSOR);
			btnFinalizar.setFocusable(false);// Retira o foco do botão.
			btnFinalizar.setFont(new Font(NOME_FONTE, Font.ITALIC + Font.BOLD, 18));
			super.addMouseEventIO(btnFinalizar);
			btnFinalizar.setEnabled(false);
			btnFinalizar.addActionListener(controle.getActionFinalizar(frameInicial));		
		}
		return btnFinalizar;
	}
	
	
	
	
	/**
	 * Retorna a instância da tabela já formatada.
	 */
	public JTable getTabela(){
		
		if(tabela == null){
			tabela = new Tabela(modelo);
	        tabela.getColumnModel().getColumn(0).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(2).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(3).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(4).setCellRenderer(modelo.centralizaCelula());
	        
	        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);//Código
	        tabela.getColumnModel().getColumn(0).setResizable(false);//Código
	        tabela.getColumnModel().getColumn(1).setPreferredWidth(250);//Nome
	        tabela.getColumnModel().getColumn(1).setMinWidth(200);//Nome
	        tabela.getColumnModel().getColumn(2).setPreferredWidth(60);//Qtde
	        tabela.getColumnModel().getColumn(2).setMinWidth(60);//Qtde	        
	        tabela.getColumnModel().getColumn(3).setPreferredWidth(65);//R$ Unidade
	        tabela.getColumnModel().getColumn(3).setMinWidth(60);// R$ Unidade	
	        
	        tabela.addKeyListener(controle.getKeyListenerTabela());
	        tabela.addMouseListener(new MouseAdapter() {
	        	
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		if (SwingUtilities.isRightMouseButton(e)) {
						
	        			if(tabela.getSelectedRow() >= 0)
		        			getPopUpMenu().show(tabela,e.getX()+10,e.getY()+10);					
					}
	        	}
			});
		}
		return tabela;
	}
	
	
	
	
	/**
	 * PopupMenu exibido quando se clica com o botão direito em alguma linha da tabela.
	 */
	private JPopupMenu getPopUpMenu(){
		JPopupMenu popup = new JPopupMenu();
		JMenuItem itemExcluir = new JMenuItem("Excluir ");
		itemExcluir.addActionListener(controle.getActionPopUpExcluir());
		popup.add(itemExcluir);
		
		return popup;
	}
	
	
	
	
	
	/**
	 * Retorna a instância referente ao campo de inserção de código do produto.
	 */
	public JTextField getTfCodigo(){
		if(tfCodigo == null){
			tfCodigo = campo.createTextFieldVendas();
			tfCodigo.setHorizontalAlignment(JTextField.CENTER);
			tfCodigo.setToolTipText("<html>Informe o código e aperte ENTER <br>para ver a descrição do item.<html>");
			tfCodigo.addKeyListener(controle.getActionCampoCodigo());
		}
		return tfCodigo;
	}
	
	
	
	
	/**
	 * Retorna a instância referente ao campo onde é informado o preço unitário do produto pesquisado.
	 */
	public JTextField getTfPrecoUnitario(){
		if(tfPrecoUnitario == null){
			tfPrecoUnitario = campo.createTextFieldVendas();
			tfPrecoUnitario.setHorizontalAlignment(JTextField.CENTER);
			tfPrecoUnitario.setEditable(false);
		}
		return tfPrecoUnitario;
	}
	
	
	
	
	/**
	 * Retorna a instância referente ao campo onde é informado a quantidade do produto pesquisado.
	 */
	public JTextField getTfQuantidade(){
		if(tfQtde == null){
			tfQtde = campo.createTextFieldVendas();
			tfQtde.setHorizontalAlignment(JTextField.CENTER);
			tfQtde.setToolTipText("<html>Informe a quantidade e aperte ENTER <br>para atualizar o preço total.<html>");
			tfQtde.addKeyListener(controle.getActionCampoQuantidade());
		}
		return tfQtde;
	}
	
	
	
	
	
	/**
	 * Retorna a instância referente ao campo onde é totalizado o preço total do item desejado.
	 */
	public JTextField getTfPrecoTotal(){
		if(tfPrecoTotal == null){
			tfPrecoTotal = campo.createTextFieldVendas();
			tfPrecoTotal.setHorizontalAlignment(JTextField.CENTER);
			tfPrecoTotal.setEditable(false);
		}
		return tfPrecoTotal;
	}
	
	
	
	
	
	
	/**
	 * Retorna o rótulo da descrição do produto.
	 */
	public JLabel getLbDescricao(){
		if(lbDescricao == null){
			lbDescricao = new JLabel("Descrição do Produto");
			lbDescricao.setFont(new Font(NOME_FONTE, Font.ITALIC + Font.BOLD, 30));
			lbDescricao.setBounds(5, 5,pnDescricao.getWidth()-10, 65);
		}
		return lbDescricao;
	}
	
	
	
	
	
	/**
	 * Exibe mensagem perguntando se o usuário deseja mesmo cancelar a tela de venda.
	 */
	public void cancelar(){
		int opcao = JOptionPane.showConfirmDialog(null, "Ao fechar esta janela a venda será interrompida.\n\n"
				+ "Deseja continuar?","Cancelar Venda", JOptionPane.OK_CANCEL_OPTION);
    	
    	if(opcao == 0){
    		setVisible(false);
    	}
	}
	
	
	
	
	/**
	 * Retorna o modelo aplicado na tabela.
	 */
	public ItensVendidosTableModel getTableModel(){
		return modelo;
	}
	
	
	
	/**
	 * Retorna uma instância preenchida de ItemVendido.
	 */
	public ItemVendido getNovoItemVendido(){
		int quantidade = Integer.parseInt(tfQtde.getText());
		double valorTotal = Double.parseDouble(tfPrecoTotal.getText().replace(',', '.'));
		
		ItemVendido novoitem = new ItemVendido(null);
		novoitem.setProduto(null);
		novoitem.setQuantidade(quantidade);
		novoitem.setValorTotal(valorTotal);
		
		return novoitem;
	}
	
	
	
	
	
}
