package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import model.Constantes;




/**
 * Classe mãe da maioria dos frames internos do software.
 * <p>
 * Permite abrir, arrastar, minimizar e fechar um frame dentro do frame inicial. 
 * Todo objeto <code>FrameInterno</code> deve ser adicionado em cima de um {@link JDesktopPane},
 * que funciona como base para todo objeto <code>JInternalFrame</code>.
 * <p>
 * Implementa a interface <code>Constantes</code> que atribui à classe os valores 
 * constantes usados no projeto, padroniza algumas configurações no projeto, tais como:
 * dimensão de campos, fonte, bordar, etc.
 * 
 * @author  Luiz Eduardo da Costa
 * @version 1.0, 09/11/2015
 * @see     JDesktopPane
 */
public class FrameInternoBase extends JInternalFrame implements Constantes {

	
	private static final long serialVersionUID = 1L;
	/**
     * A dimensão do frame interno.
     */
    private Dimension   dimensao      = null;
    /**
     * Painel base onde serão adicionados os componentes do frame interno.
     */
    private JPanel       pnBase = null;
	
	
	
	
	
	public FrameInternoBase(String titulo) {
		super(titulo, //Titulo
                false,//Resizable
                true, //Closable
                true);//Maximizable 
		
		constroiFrame();
	}
	
	
	
	
	/**
     * Este método reune todas as configurações de todos os objetos desta classe.
     * São os objetos responsáveis por construir o frame e consfigurar seus coonentes internos.
     */
    private void constroiFrame(){

        dimensao = new Dimension(700, 500);
        
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setSize(dimensao);
        setMaximizable(false);// Remove o ícone de maximizar a janela.
        setIconifiable(true);// Define se a janela poderá ser minimizada ou não.
        setLocation(new FrameInicial().getDesktopPane());
        getContentPane().add(getPnBase());  
        
    }
    

    
    /**
     * Configura a localização da tela centralizando-a em seu painel base.
     * 
     * @param pnDesktop O painel base onde o <code>FrameInterno</code> foi adicionado.
     */
    public void setLocation(JDesktopPane pnDesktop){
        setLocation((pnDesktop.getWidth() - dimensao.width) / 2, 105);
    }
    
       
    
    /**
     * Retorna o <code>JPanel</code> base do frame interno.
     * @return Um objeto <code>JPanel</code>.
     */
    public JPanel getPnBase(){
        if(pnBase == null){
        	pnBase = new JPanel();
            pnBase.setLayout(null);
            pnBase.setSize(dimensao);
       //     pnBase.setBackground(COR_PAINEL_BACKGROUND);
        }
    	return pnBase;
    }
	
	
	
    
    /**
     * Define a dimensão da tela.
     */
    @Override
    public void setSize(Dimension dim) {
    	this.dimensao = dim;
    	super.setSize(dim);
    }
    
	
    
    /**
     * Retorna a dimensão da tela.
     */
    public Dimension getSize(){
    	return dimensao;
    }
	
	
    
    
    /**
     * Adiciona em um botão uma instância de MouseListener.
     * Configura o evento de entrada e saída nos botões.
     * Sempre que o mouse sobrepõe o botão, um evento diferente acontece.
     * 
     * @param btn O botão que vai receber o MouseListener.
     */
    protected void addMouseEventIO(final JButton btn){
    	btn.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseEntered(MouseEvent e) {
    			btn.setForeground(Color.red);
    		}
    		@Override
    		public void mouseExited(MouseEvent e) {
    			btn.setForeground(Color.black);
    		}
		});
    }
    
    
	
}
