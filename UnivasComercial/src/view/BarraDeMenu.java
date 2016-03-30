package view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import control.ControleBarraDeMenu;



/**
 * Esta classe contém os atributos e métodos para a montagem de uma barra de menu.
 * 
 * @author   Luiz Eduardo da Costa
 * @version  1.0, 06/11/2015
 * @see      JMenuBar
 */
public class BarraDeMenu extends JMenuBar{


	private static final long serialVersionUID = 1L;

	// Menus da barra de menu.
	private JMenu mnSistema, mnProduto, mnCaixa, mnGerar, mnAjuda;
	
	// Objetos itens de menu.
	private JMenuItem imnSair, imnCadProdutos, imnConsultaProduto, imnNovaVenda, 
					  imnRelatorios, imnSobre, imnSetor;
	
	// Controlador da barra de menu.
	private ControleBarraDeMenu controler = null;
	
	
	
	
	/**
	 * M�todo construtor da Barra de Menu.
	 * 
	 * Exige como parâmetro o frame sobre o qual o barra de menu ser� colocada.
	 * @param frame
	 */
	public BarraDeMenu(FrameInicial frame) {
		
		this.controler = new ControleBarraDeMenu(frame);
		init();
	}
	
	
	
	/**
	 * Constrói os objetos da barra de menu.
	 */
	private void init(){
		mnSistema   = new JMenu("Sistema ");	
		mnProduto   = new JMenu("Produto ");
		mnCaixa    = new JMenu("Caixa ");
		mnGerar     = new JMenu("Gerar");
		mnAjuda     = new JMenu("Ajuda ");
		
        imnSair = new JMenuItem("Sair");        
        imnSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        
        imnCadProdutos = new JMenuItem("Cadastrar ");
        imnCadProdutos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, InputEvent.CTRL_MASK));
        
        imnConsultaProduto = new JMenuItem("Consultar ");
        imnConsultaProduto.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, InputEvent.CTRL_MASK));
        
        imnSetor = new JMenuItem("Setor ");
        
        imnSobre = new JMenuItem("Sobre");
        imnSobre.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        
        imnNovaVenda = new JMenuItem("Nova Venda");
        imnNovaVenda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, InputEvent.CTRL_MASK));
        
        imnRelatorios = new JMenuItem("Relatórios");
        imnRelatorios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.CTRL_MASK));

        mnSistema.add(imnSair);
        mnProduto.add(imnCadProdutos);
        mnProduto.add(imnConsultaProduto);
        mnProduto.add(imnSetor);
        mnCaixa.add(imnNovaVenda);
        mnGerar.add(imnRelatorios);        
        mnAjuda.add(imnSobre);
        
        add(mnSistema);
        add(mnProduto);
        add(mnCaixa);
        add(mnGerar);
        add(mnAjuda);
        
        addActionsInMenu();
	}
	
	
	
	
	
	/**
	 * Adiciona a interface ouvinte ActionListener nos itens de menu.
	 * 
	 * As ações de cada item de menu são desenvolvidas na classe de controle 
	 * da Barra de menu (ControlerMenubar) e depois chamada através do objeto controler.
	 */
	private void addActionsInMenu(){
		imnSair.addActionListener(controler.getActionSair());
		imnCadProdutos.addActionListener(controler.getActionNovoProduto());
		imnSobre.addActionListener(controler.getActionMnSobre());
		imnRelatorios.addActionListener(controler.getActionConsultaRelatorios());
		imnConsultaProduto.addActionListener(controler.getActionConsultaProdutos());
		imnSetor.addActionListener(controler.getActionCadastroSetor());
		imnNovaVenda.addActionListener(controler.getActionNovaVenda());
	}
	
	
	
	/**Retorna o controlador da barra de menu.*/
	public ControleBarraDeMenu getControlerMenuBar(){
		return controler;
	}
	
	
	
}
