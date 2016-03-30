package view.cadastro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import control.cadastro.ControleCadastroSetor;
import model.Campo;
import model.POJO.Setor;


/**
 * Frame de cadastro e consulta de setores.
 * 
 * Possui um campo de texto onde é possivel inserir o nome do novo setor.
 * 
 * Possui um JList onde é possível consultar os setores já cadastrados.
 *
 */
public class FrameCadastroSetor extends FrameCadastro {


	private static final long serialVersionUID = 1L;
	
	// Campo para inserir o nome do novo setor.
	private JTextField tfDescricao;
	
	// A dimensão da tela e do painel base.
	private Dimension dim = null;
	
	// Painéis para auxilio na distribuição dos campos.
	private JPanel pnDescricao, pnLista, pnBotoes;
	
	// Lista onde será exibida o nome dos setores cadastrados.
	private JList<Setor> listaDeSetores = null;
	
	private JButton btnSair, btnSalvar;
	
	// Padroniza a criação dos campos.
	private Campo campo = new Campo(this);
	
	// Controlador do cadastro de setores.
	private ControleCadastroSetor controle = null;
	
	// Objeto Setor com os dados a ser atualizado.
	// Esse objet é montado quando o usuário clica duas vezes em algum item da lista.
	private Setor setorAtualizado = null;
	
	
	
	
	/**
	 * Construtor da classe FrameCadastroCategoria.
	 */
	public FrameCadastroSetor() {
		super("Cadastro/Consulta de Setores ");
		controle = new ControleCadastroSetor(this);
		controle.atualizaListaDeSetores();
		configuraFrame();
	}

	


	/**
	 * Configura o frame e adicinoa novos campos.
	 */
	private void configuraFrame() {
		
		dim = new Dimension(400, 400);
		setSize(dim);
		getPnBase().setSize(dim);
		getPnCampos().setSize(new Dimension(375, 348));
		getPnCampos().setLayout(null);
		
		
		getPnBase().remove(super.getPnBotoes());// Remove o painel de botões.
		getPnCampos().add(getPnDescricao());
		getPnCampos().add(getPnLista());
		getPnCampos().add(getPnButtons());
	}
	
	
	
	
	/**
	 * Retorna o painel onde será adicionado o campo para descriçãodo setor.
	 */
	private JPanel getPnDescricao(){
		if(pnDescricao == null){
			
			JLabel lbNome = campo.createJLabel("Nome");
			lbNome.setHorizontalAlignment(JLabel.CENTER);
			
			pnDescricao = new JPanel(new GridLayout(1, 2));
			pnDescricao.setBounds(5, 20, 340, ALTURA_CAMPO);
			//pnDescricao.setBackground(COR_PAINEL_BACKGROUND);
			pnDescricao.add(lbNome);
			pnDescricao.add(getTfDescricao());
		}
		return pnDescricao;
	}
	
	
	

	
	/**
	 * Retorna o painel onde será adicionado o JList.
	 */
	private JPanel getPnLista(){
		if(pnLista == null){
			pnLista = new JPanel(new GridLayout(1, 1));
			pnLista.setBounds(20, 65, 335, 220);
			JScrollPane scroll = new JScrollPane(getLista());
			pnLista.add(scroll);
		}
		return pnLista;
	}
	
	
	
	
	/**
	 * Retorna o painel onde são adicionados os botões.
	 */
	protected JPanel getPnButtons(){
		if(pnBotoes == null){
			pnBotoes = new JPanel(new FlowLayout());
			pnBotoes.setBounds(20, 300, 335, ALTURA_CAMPO+10);
		//	pnBotoes.setBackground(COR_PAINEL_BACKGROUND);
			pnBotoes.add(getBotaoCancelar());
			pnBotoes.add(getBotaoSalvar());
		}
		return pnBotoes;
	}
	
	
	/**
	 * Retorna o campo de texto onde o nome do setor deve ser informado.
	 */
	private JTextField getTfDescricao(){
		if(tfDescricao == null){
			tfDescricao = campo.createTextField(100, 5, LARGURA_CAMPO, ALTURA_CAMPO);
			tfDescricao.setBackground(new Color(255, 250, 220));
			tfDescricao.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER){
						controle.cadastraNovoSetor();
					}
				}
			});
		}
		return tfDescricao;
	}
	
	
	
	
	/**
	 * Retorna a lista de Setores.
	 */
	public JList<Setor> getLista(){
		if(listaDeSetores == null){
			listaDeSetores = new JList<Setor>();
			listaDeSetores.setBorder(BORDA_FUNDA);
			listaDeSetores.setFont(FONTE_CAMPO_TEXTO);
			listaDeSetores.addKeyListener(getKeyListenerLista());
			listaDeSetores.addMouseListener(getMouseListenerLista());
		}
		return listaDeSetores;
	}
	
	
	

	/**
	 * Retorna a instância do botão cancelar.
	 */
	private JButton getBotaoCancelar(){
		if(btnSair == null){
			btnSair = new JButton("Sair");
			btnSair.setCursor(HAND_CURSOR);
			btnSair.setFocusable(false);// Tira o foco da lista.
			super.addMouseEventIO(btnSair);
    		btnSair.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);	
				}
			});
		}
		return btnSair;
	}
	
	
	/**
	 * Retorna a instância do botão salvar.
	 */
	private JButton getBotaoSalvar(){
		if(btnSalvar == null){
			btnSalvar = new JButton("Salvar");
			btnSalvar.setCursor(HAND_CURSOR);
			btnSalvar.setFocusable(false);// Tira o foco da lista.
			super.addMouseEventIO(btnSalvar);
			btnSalvar.addActionListener(controle.getActionCadastrarSetor());
		}
		return btnSalvar;
	}
	
	
	
	
	
	/**
	 * Realiza a validação do campo de cadastro de setores.
	 * 
	 * @return verdadeiro ou falseo caso o campo esteja preenchido ou não.
	 */
	public boolean validaCampos(){
		// Retorna true se não estiver em branco.
		return (!tfDescricao.getText().isEmpty());
	}
	
	
	
	
	/**
	 * Retorna a descrição do novo setor informado na tela de cadastro.
	 */
	public Setor getSetor(){
		Setor setor = new Setor();
		setor.setNome(tfDescricao.getText().toUpperCase());
		return setor;
	}
	
	
	
	/**
	 * Limpa o campo do frame de cadastro.
	 */
	public void limpaTela(){
		tfDescricao.setText("");
	}
	
	
	
	/**
	 * Atualiza os dados da lista de setores.
	 * @param novaLista
	 */
	public void atualizarLista(List<Setor> novaLista){
		getLista().setListData(new Vector<Setor>(novaLista));
		
	}
	
	
	
	
	/**
	 * Trata o processo de edição e atualização de um registro de setor.
	 */
	private void trataEdicao() {
		// Captura o registro selecionado na lista.
		this.setorAtualizado = listaDeSetores.getSelectedValue();
		String novoNome = null;
		
		do{
			// Pede para o usuário entrar com o novo nome.
			novoNome = JOptionPane.showInputDialog(null, 
					"Novo nome para este setor:", setorAtualizado.getNome());
		}while(novoNome != null && novoNome.equals(""));
		
		// Se o usuário confirmar, procegue com a atualização.
		if(novoNome != null){
			// Pega o novo nome e converte para maiusculo.
			setorAtualizado.setNome(novoNome.toUpperCase());
			// Atualiza o registro do setor.
			controle.atualizaRegistro(setorAtualizado);
		}
	}
	
	
	
	
	
	/**
	 * Trata o processo de exclusão de um registro de setor.
	 */
	private void trataExclusao(){
		// Captura o objeto selecionado na lista.
    	Setor setor = listaDeSetores.getSelectedValue();
    	
    	if(setor != null){
    		// Confirma se o usuário deseja mesmo excluir.
            int opcao = JOptionPane.showConfirmDialog(null, 
            		"Excluir Setor "+setor.getNome()+"?",
            		"Excluir Setor", JOptionPane.OK_CANCEL_OPTION);
            
            if(opcao == 0)
            	controle.excluiRegistro(setor.getCodigo());
    	}
	}
	
	
	
	
	
	
	
	/**
	 * Retorna a instância de um  JPopupMenu.
	 */
	public JPopupMenu getPopupMenu(){
		JPopupMenu popup = new JPopupMenu();
		
		JMenuItem itemEditar = new JMenuItem("Editar");
		JMenuItem itemExcluir = new JMenuItem("Excluir");
		
		itemEditar.addActionListener(getActionEditar());
		itemExcluir.addActionListener(getActionExcluir());
		
		popup.add(itemEditar);
		popup.add(itemExcluir);
		
		return popup;
	}
	
	
	
	
	/**
	 * Retorna a ação da opção de Editar um setor.
	 */
	private ActionListener getActionEditar(){
		return new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				trataEdicao();
			}
		};
	}
	
	
	
	
	
	/**
	 * Retorna a ação da opção de Excluir um setor.
	 */
	private ActionListener getActionExcluir(){
        
        return new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	trataExclusao(); 
            }
        };        
	}
	
	
	
	
	/**
	 * Retorna as ações de teclado a ser aplicado na lista.
	 * <p>
	 * O objeto resultante dessa função deve ser adicionado no JList do frame.
	 */
	private KeyListener getKeyListenerLista(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE){
					
					trataExclusao(); 
					
				}else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
					setVisible(false); 
				}
			}
		};
	}
	
	
	
	/**
	 * Retorna um objeto MouseListener formatado responsável
	 * por verificar as ações do mouse e executar alguma função.
	 */
	private MouseListener getMouseListenerLista(){
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
											
					trataEdicao();
				}
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// Clique com o botão direito do mouse.
				if (SwingUtilities.isRightMouseButton(e)) {
					
					getPopupMenu().show(getLista(), e.getX()+5, e.getY()+15); 
					
				}
			}
		};
	}
	
	
	
	
}
