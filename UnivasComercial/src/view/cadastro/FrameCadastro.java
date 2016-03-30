package view.cadastro;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import view.FrameInternoBase;


/**
 * Frame interno base para as telas de cadastro.
 * @author Luiz Eduardo da Costa - 11/11/2015
 *
 */
public class FrameCadastro extends FrameInternoBase {

	

	private static final long serialVersionUID = 1L;
	

	// Paineis padrões da tela.
	private JPanel pnCampos, pnBotoes;
    
	// Botões padrões da tela de cadastro.
	private JButton btnNovo, btnSalvar, btnCaixa, btnCancelar;
	
	// Fonte padrão dos botões do frame de cadastro.
	private Font fonte = new Font("Arial", Font.BOLD, 18);
	
	// Objeto para carregar um icone nos botões.
	private Icon icone = null;

	
	
	
	/**
     * Método construtor da classe <code>FrameInternoBase</code>.
     * Contém o método {@link #constroiFrame()} responsável 
     * por montar e configurar o frame.
     * 
     * @param titulo O título que será adicionado ao topo da janela.
     */
    public FrameCadastro(String titulo){
        super(titulo); 
        reconfiguraFrame();
    }
	
	
    
    
    
    // Reconfiguração da tela base e adição de novos componentes.
    private void reconfiguraFrame(){
    	
    	setLayout(null);
    	super.getPnBase().setSize(super.getSize());

    	getPnBase().add(getPnBotoes());
    	getPnBase().add(getPnCampos());
    }
    
   
    
    
    /**
     * Retorna a instância do painel onde são adicionados os campos.
     */
    protected JPanel getPnCampos(){
    	if(pnCampos == null){
    		pnCampos = new JPanel();
        	pnCampos.setLayout(null);
        	pnCampos.setBorder(BORDA_GRAVADA);
        	pnCampos.setBounds(8, 8, 540, 452);
     //   	pnCampos.setBackground(COR_PAINEL_BACKGROUND);
    	}
    	return pnCampos;
    }
    
    
    /**
     * Retorna a instância configurada do painel de botões.
     */
    protected JPanel getPnBotoes(){
    	if(pnBotoes == null){
    		pnBotoes = new JPanel();
        	pnBotoes.setLayout(new GridLayout(4, 1, 5, 5));
        	pnBotoes.setBounds(558, 8, 125, 450);
      //  	pnBotoes.setBackground(COR_PAINEL_BACKGROUND);
        	
        	pnBotoes.add(getBtnNovo());
        	pnBotoes.add(getBtnSalvar());
        	pnBotoes.add(getBtnCaixa());
        	pnBotoes.add(getBtnCancelar());
    	}
    	return pnBotoes;
    }
    
    
    
    
    /**
     * Retorna a instância do botão Novo cadastro.
     */
    public JButton getBtnNovo(){
    	if(btnNovo == null){
    		
    		this.icone = new ImageIcon(ICONE_INCLUIR);
    		
    		btnNovo = new JButton("", icone);
    		btnNovo.setFont(fonte);
    		btnNovo.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		btnNovo.setToolTipText("Novo Registro");
    		addMouseEventIO(btnNovo);
    	}
    	return btnNovo;
    }
    
    
    
    /**
     * Retorna a instância do botão Salvar registro.
     */
    public JButton getBtnSalvar(){
    	if(btnSalvar == null){
    		this.icone = new ImageIcon(ICONE_SALVAR);
    		btnSalvar = new JButton("", icone);
    		btnSalvar.setFont(fonte);    		
    		btnSalvar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		btnSalvar.setToolTipText("Salvar");
    		addMouseEventIO(btnSalvar);
    	}
    	return btnSalvar;
    }
    
    
    
    /**
     * Retorna a instância do botão para acessar a tela do caixa.
     */
    public JButton getBtnCaixa(){
    	if(btnCaixa == null){
    		this.icone = new ImageIcon(ICONE_CAIXA);
    		btnCaixa = new JButton("", icone);
    		btnCaixa.setFont(fonte);
    		btnCaixa.setCursor(new Cursor(Cursor.HAND_CURSOR));
    		btnCaixa.setToolTipText("Caixa - Nova Venda");
    		addMouseEventIO(btnCaixa);
    	}
    	return btnCaixa;
    }
    
    
    // Cancelar tela de cadastro.
    public JButton getBtnCancelar(){
    	if(btnCancelar == null){
    		this.icone = new ImageIcon("src/resources/cancelar.png");
    		btnCancelar = new JButton("", icone);
    		btnCancelar.setFont(fonte);
    		btnCancelar.setCursor(HAND_CURSOR);
    		btnCancelar.setToolTipText("Cancelar");
    		addMouseEventIO(btnCancelar);
    		btnCancelar.addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					setVisible(false);	
				}
			});
    	}
    	return btnCancelar;
    }
    
	
    
    
  
    
    /**
	 * Retorna um objeto MaskFormatter já configurado.
	 * Necessita apenas do padrão desejado.
	 * 
	 * @param mascaraStr O padrão desejado.
	 * @return A máscara configurada.
	 */
	protected MaskFormatter getMascaraFormatada(String mascaraStr){
		MaskFormatter mask = null;
		try {
			mask = new MaskFormatter(mascaraStr);
			mask.setPlaceholderCharacter('_');
		} catch (ParseException e) {
			System.err.println("Erro na construção da máscara!");
		}
		return mask;
	}
    
    
    
    
	
}
