package view.consulta;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import control.consulta.ControleConsultaProdutos;
import model.Campo;
import model.DAO.ProdutoDAO;
import model.DAO.SetorDAO;
import model.POJO.Produto;
import model.POJO.Setor;
import model.tables.ProdutoTableModel;
import view.FrameInternoBase;
import view.Tabela;


/**
 * Frame de consulta de produtos.
 *
 */
public class FrameConsultaProdutos extends FrameInternoBase {


	private static final long serialVersionUID = 1L;

	private JPanel pnFiltros = null, pnAux=null, pnTabela=null;
	
	private JLabel lbNome, lbSetor;
	
	private JButton btnPesquisar, btnRecarregar;
	// Campo onde o nome do produto deve ser informado.
	private JTextField tfNome;
	// A lista de setores para auxiliar na busca por produtos.
	private JComboBox<Setor> cbxSetores;
	// Tabela que exibirá os itens pesquisados.
	private Tabela tabela = null;
	// O painel de rolagem a ser aplicado sob a tabela.
	private JScrollPane pnRolagem = null;
	// o modelo da tabela de consulta de prosutos.
	private ProdutoTableModel modelo = new ProdutoTableModel();
	// Padroniza a construção dos campos.
	private Campo campo = new Campo(this);
	// O controlador desse frame.
	private ControleConsultaProdutos controle = null;
	// O produto selecionado na tabela de consulta.
	private Produto produtoSelecionado = null;
	
	
	
	
	
	/**
	 * Método construtor. 
	 * Faz a reconfiguração da classe mãe para se adaptar à consulta de Produtos.
	 */
	public FrameConsultaProdutos() {
		super("Consulta de Produtos ");
		
		this.controle = new ControleConsultaProdutos(this);
		
		// Quando a tela iniciar, deve-se preencher a tabela com todos os produtos cadastrados no banco.
		String sql = ProdutoDAO.CONSULTA_TABELA_INTEIRA + ProdutoDAO.ORDENA_CRESCENTE_POR_NOME;
		
		// A tabela é preenchida com a lista retornada da pesquisa.
		controle.preencheTabela(ProdutoDAO.getInstancia().consulta(sql));
		
		configuraFrame();
	}
	
	
	
	
	/**
	 * Configura o frame.
	 * Adiciona novos componentes.
	 */
	private void configuraFrame(){			
				
		setSize(new Dimension(super.getWidth() + 120, super.getHeight()));
		getPnBase().setSize(super.getSize());
		
		pnFiltros = new JPanel(null);
		pnFiltros.setBorder(BORDA_GRAVADA);
		pnFiltros.setBounds(10, 13, getPnBase().getWidth()-30, 70);
	//	pnFiltros.setBackground(COR_PAINEL_BACKGROUND);
		
		pnAux = new JPanel(new GridLayout(1, 4));
		pnAux.setBounds(10, 20, pnFiltros.getWidth()-180, pnFiltros.getHeight()-35);
	//	pnAux.setBackground(COR_PAINEL_BACKGROUND);
		
		pnTabela = new JPanel(new GridLayout(1, 1));
		pnTabela.setBorder(BORDA_GRAVADA);
		pnTabela.setBounds(10, 110, getPnBase().getWidth()-30, 345);
	//	pnTabela.setBackground(COR_PAINEL_BACKGROUND);

		btnPesquisar = new JButton(new ImageIcon(ICONE_PESQUISAR));
		btnPesquisar.setBounds(650, 20, 50, pnAux.getHeight());
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setCursor(HAND_CURSOR);
		btnPesquisar.addActionListener(controle.getActionBtnPesquisar());
		
		btnRecarregar = new JButton(new ImageIcon(ICONE_RECARREGAR));
		btnRecarregar.setBounds(710, 20, 50, pnAux.getHeight());
		btnRecarregar.setToolTipText("Recarregar Tabela");
		btnRecarregar.setCursor(HAND_CURSOR);
		btnRecarregar.addActionListener(controle.getActionBtnRecarregar());
		
		lbNome = new JLabel("Nome: ", JLabel.CENTER);
		lbNome.setFont(FONTE_ROTULO);
		
		lbSetor = new JLabel("Setor: ", JLabel.CENTER);
		lbSetor.setFont(FONTE_ROTULO);
		
		pnRolagem = new JScrollPane(getTabela());
		pnTabela.add(pnRolagem);
		
		pnAux.add(lbNome);
		pnAux.add(getTfNome());
		pnAux.add(lbSetor);
		pnAux.add(getCbxSetores());
		pnFiltros.add(btnPesquisar);
		pnFiltros.add(btnRecarregar);
		pnFiltros.add(pnAux);
		getPnBase().add(pnFiltros);
		getPnBase().add(pnTabela);
		
	}
	
	

	
	/**
	 * Retorna a instância da tabela já formatada.
	 */
	public JTable getTabela(){
		if(tabela == null){
			tabela = new Tabela(modelo);
	        tabela.getColumnModel().getColumn(0).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(3).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(4).setCellRenderer(modelo.centralizaCelula());
	        tabela.getColumnModel().getColumn(5).setCellRenderer(modelo.centralizaCelula());
	        
	        tabela.getColumnModel().getColumn(0).setPreferredWidth(60);//Código
	        tabela.getColumnModel().getColumn(0).setMinWidth(60);//Código
	        tabela.getColumnModel().getColumn(1).setPreferredWidth(250);//Nome
	        tabela.getColumnModel().getColumn(1).setMinWidth(200);//Nome
	        tabela.getColumnModel().getColumn(2).setPreferredWidth(250);//Setor
	        tabela.getColumnModel().getColumn(2).setMinWidth(200);//Setor
	        tabela.getColumnModel().getColumn(3).setPreferredWidth(70);//Custo
	        tabela.getColumnModel().getColumn(3).setMinWidth(65);//Custo
	        tabela.getColumnModel().getColumn(4).setPreferredWidth(70);//R$ venda
	        tabela.getColumnModel().getColumn(4).setMinWidth(65);//R$ Venda
	        tabela.getColumnModel().getColumn(5).setPreferredWidth(83);//Em estoque
	        tabela.getColumnModel().getColumn(5).setMinWidth(80);// Em Estoque  
	        tabela.getColumnModel().getColumn(6).setPreferredWidth(220);//Fornecedor
	        tabela.getColumnModel().getColumn(6).setMinWidth(220);// Fornecedor 
	        tabela.getColumnModel().getColumn(7).setPreferredWidth(500);//Observação
	        tabela.getColumnModel().getColumn(7).setMinWidth(500);// Observação
	        tabela.addKeyListener(new KeyAdapter() {
	        	@Override
	        	public void keyPressed(KeyEvent e) {
	        		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	        			setVisible(false);
	        		}
	        	}
			});
	        
	        tabela.addMouseListener(new MouseAdapter() {
	        	
	        	@Override
	        	public void mouseClicked(MouseEvent e) {
	        		if (SwingUtilities.isRightMouseButton(e)) {
						
	        			if(tabela.getSelectedRow() >= 0)
		        			getPopUpMenu().show(tabela,e.getX()+10,e.getY()+10);
						
					}else{
						int linha = tabela.getSelectedRow();
						
						setProdutoSelecionado(modelo.getElementAt(linha));
					}
	        	}
			}); 
		}
		return tabela;
	}
	
	
	
	
	/**
	 * Retorna o modelo aplicado na tabela de consulta de produtos.
	 */
	public ProdutoTableModel getTableModel(){
		return modelo;
	}
	
	
	
	/**
	 * PopupMenu exibido quando se clica com o botão direito em alguma linha da tabela.
	 */
	private JPopupMenu getPopUpMenu(){
		JPopupMenu popup = new JPopupMenu();
		JMenuItem itemExcluir = new JMenuItem("Excluir ");
		itemExcluir.addActionListener(controle.getActionExcluir());
		popup.add(itemExcluir);
		
		return popup;
	}
	
	
	
	
	/**
	 * Retorna a instância da caixa de combinação com a lista de setores.
	 */
	public JComboBox<Setor> getCbxSetores(){
		if(cbxSetores == null){
			cbxSetores = campo.createComboBoxSetores(SetorDAO.getInstancia().getVectorSetores());
			cbxSetores.setBackground(COR_CAMPO_BACKGROUD);   
		}
		return cbxSetores;
	}
	
	
	
	/**
	 * Retorna a instância do campo de texto referente ao preenchimento do nome do produto s ser consultado.
	 */
	public JTextField getTfNome(){
		if(tfNome == null){
			tfNome = campo.createTextField();
			tfNome.setBackground(COR_CAMPO_BACKGROUD);  
		}
		return tfNome;
	}
	
	
	
	/**
	 * Retorna se o campo de nome está em branco ou não.
	 */
	public boolean validaCampo(){
		return tfNome.getText().trim().isEmpty();
	}
	
	
	
	
	
	
	/**
	 * Define uma nova instância para o produto selecionado na tabela de consulta.
	 * @param novoProdutoSelecionado
	 */
	private void setProdutoSelecionado(Produto novoProdutoSelecionado){
		this.produtoSelecionado = novoProdutoSelecionado;
	}
	
	
	/**
	 * Retorna a instância do produto selecionado na tabela de consulta.
	 */
	public Produto getProdutoSelecionado(){
		return this.produtoSelecionado;
	}
	
	
}
