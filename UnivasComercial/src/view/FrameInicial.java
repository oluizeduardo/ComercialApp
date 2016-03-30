package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

import model.Constantes;

/**
 * Classe que monta a tela inicial do sistema.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 6/11/2015
 */
public class FrameInicial extends JFrame implements Constantes{

	private static final long serialVersionUID = 1L;

	
	private JLabel lbComercial, lbDescricaoSistema ;
	
	/**Guarda a dimens�o da tela inicial do sistema.*/
	private Dimension dimTelaInicial = null;
	
	/**O painel de funda da �rea de trabalho do frame inicial.*/
	private JDesktopPane desktopPane = null;
	
	/**Barra de menu a ser aplicada no frame inicial.*/
	private BarraDeMenu barraDeMenu = null;
	
	
	
	/**
	 * Método construtor da classe FrameInicial.
	 * Usa o método init() para montar todos os componentes do frame.
	 */
	public FrameInicial() {
		super.setTitle("Sistema de Automação de Vendas ");
		init();
	}
	
	
	
	/**
	 * Esse método configura todos os componentes que serão adicionados no frame.
	 */
	private void init(){
		
		this.barraDeMenu = new BarraDeMenu(this);
		
		this.setDimensionFrame();
		super.setUndecorated(true);//Tira a borda do frame. Deixa o frame em outro estilo.
		super.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);  
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		super.setLocationRelativeTo(null);//Centraliza a tela.
        // Exibe uma mensagem quando clicar no X para fechar.
        super.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
               barraDeMenu.getControlerMenuBar().sair();
            }
        });
        
        
        lbComercial = new JLabel("Comercial", JLabel.CENTER);
        lbComercial.setForeground(Color.white);
        lbComercial.setFont(new Font("Times New Roman", Font.ITALIC, 150));
        lbComercial.setSize(dimTelaInicial.width, 180);
        lbComercial.setLocation(0, ((dimTelaInicial.height - 150) - lbComercial.getHeight())/2);
        
        lbDescricaoSistema = new JLabel("Univás - Sistemas de Informação");
        lbDescricaoSistema.setForeground(Color.white);
        lbDescricaoSistema.setFont(new Font("Arial", Font.PLAIN, 18));
        lbDescricaoSistema.setSize(400, 20);
        lbDescricaoSistema.setLocation(380,lbComercial.getY() + 165);
        
        getDesktopPane().add(lbComercial);
        getDesktopPane().add(lbDescricaoSistema);
        getContentPane().add(getDesktopPane());
        
        setJMenuBar(barraDeMenu);
        
	}



	
	
	/**
	 * Captura a dimens�o total da tela do usu�rio e aplica no frame.
	 */
	private void setDimensionFrame() {
		// Retorna a dimensão do monitor do usu�rio.
        this.dimTelaInicial = Toolkit.getDefaultToolkit().getScreenSize(); 
        
        // Tamanho m�nimo da tela.
        setMinimumSize(dimTelaInicial);		
	}
	
	
	
	/**
	 * Cria o painel desktop do frame inicial.
	 * @return O painel base configurado.
	 */
	public JDesktopPane getDesktopPane(){
		if(desktopPane == null){
			desktopPane = new JDesktopPane();
	        desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
	   //     desktopPane.setBackground(COR_PAINEL_BACKGROUND);
	        desktopPane.setLayout(null);
		}
        
        return desktopPane;
	}	
	
	

	
	
}
