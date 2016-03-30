
package model;

import model.Constantes;
import model.POJO.Setor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

/**
 * Classe responsável por reunir os métodos que padronizarão os campos
 * do frame.
 * <p>
 * É recomendado que um objeto dessa classe seja instanciado na mesma 
 * classe onde estão sendo construido os campos a serem adicionados
 * no frame.
 * <p>
 * Para cada tipo de campo existe um método <code>create</code>.
 * Basta apenas chamar o objeto do campo, por exemplo objeto do tipo
 * <code>JTextField</code> e atribuir nele o retorno do método 
 * <code>createTextField()</code>.
 * <p>
 * <strong>Exemplo:</strong>
 * <br/>
 * <code>
 * JTextField campo1 = {@link #createTextField(String)};<br/>
 * JComboBox campo2 = {@link #createComboBoxSetores(String[])} <br/>
 * </code>
 * <p>
 * Cada método <code>create</code> implementa o método {@link #configure(JComponent)},
 * que possui os métodos básicos que configuram um tipo de campo.
 * Configura detalhes como borda, altura, largura, background, fonte, etc.
 * <p>
 * Todo tipo de campo tem a interface <code>KeyListener</code> adicionada, para
 * que, quando o botão ESC seja pressionado, a janela feche.
 * <p>
 * Implementa a interface <code>Constantes</code> que traz para a classe algumas
 * das constantes que tentam padronizar detalhes no sistema.
 * 
 * 
 * @author   Luiz Eduardo da Costa
 * @version  1.0, 07/03/15
 */
public class Campo implements Constantes{
    
    
    /**Componente sobre o qual o campo será adicionado.*/
    private JComponent janelaPai = null;
    
    
    
    /**
     * Método construtor da classe <code>Campo</code>.
     * 
     * @param janelaPai Objeto <code>JFrame</code> onde o campo será adicionado.
     */
    public Campo(JComponent janelaPai){
        this.janelaPai = janelaPai;
    }
    
    
    
    
    /**
     * Responsável pela configuração básica dos campos.
     * <p/>
     * Possui os métodos básicos que configuram um <code>Jcomponent</code>.
     * Configura detalhes básicos como borda, altura, largura, fonte, etc.
     * 
     * @param c O componente a ser modificado.
     */
    private void configure(JComponent c)
    {
        c.setBorder(BORDA_FUNDA);
        c.setBackground(Color.WHITE);
        c.setFont(FONTE_CAMPO_TEXTO);
        c.setSize(LARGURA_CAMPO, ALTURA_CAMPO);
    }
    
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JTextField</code>.
     * 
     * @return Objeto <code>JTextField</code> com formato básico.
     */
    public JTextField createTextField() 
    {
    	JTextField campo = new JTextField();
        configure(campo);
        adicionaKeyListener(campo);
        
        return campo;
    }
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JTextField</code>.
     * Campos de texto para a tela de Vendas.
     * 
     * @return Objeto <code>JTextField</code> com formato básico.
     */
    public JTextField createTextFieldVendas() 
    {
    	JTextField campo = new JTextField();
        configure(campo);
        campo.setFont(new Font(NOME_FONTE, Font.BOLD, 22));
        campo.setSize(LARGURA_CAMPO, 50);
        
        return campo;
    }
    
    
    
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JTextField</code>.
     * 
     * @return Objeto <code>JTextField</code> com formato básico.
     */
    public JTextField createTextField(int x, int y, int lar, int alt) 
    {
    	JTextField campo = new JTextField();
        configure(campo);
        adicionaKeyListener(campo);
        campo.setBounds(x, y, lar, alt);
        
        return campo;
    }
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JComboBox</code>.
     * 
     * @param lista Lista de Strings a ser aplicada na caixa de combinação.
     * @return Objeto <code>JComboBox</code> com formato básico.
     */
    public JComboBox<Setor> createComboBoxSetores(Vector<Setor> lista) 
    {
        JComboBox<Setor> campo = new JComboBox<Setor>(lista);
        configure(campo);
        adicionaKeyListener(campo);
        return campo;
    }
    
    
    
    
    

    /**
     * Carrega a configuração padrão de um <code>JComboBox</code>.
     * 
     * Essa método retorna uma instância configurada de JCombobox sem 
     * as configurações de KeyListener.
     * 
     * @param lista Lista de Strings a ser aplicada na caixa de combinação.
     * @return Objeto <code>JComboBox</code> com formato básico.
     */
    public JComboBox<String> createComboBoxVendas(Vector<String> lista) 
    {
        JComboBox<String> campo = new JComboBox<String>(lista);
        configure(campo);
        return campo;
    }
    
    
    
   
    
    
    /**
     * Carrega a configuração padrão de um <code>JComboBox</code>.
     * 
     * @param linhas O número de linhas.
     * @param colunas O número de colunas.
     * @return Objeto <code>JTextArea</code> com formato básico.
     */
    public JTextArea createTextArea(int linhas, int colunas) 
    {
        JTextArea campo = new JTextArea(linhas, colunas);
        configure(campo);
        adicionaKeyListener(campo);
        return campo;
    }
    
  
    
    
    
    
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JLabel</code>.
     * 
     * @param texto A String que será adicionada no JLabel.
     * @return O JLabel formatado para ser adicionado na tela.
     */
    public JLabel createJLabel(String texto){
    	JLabel label = new JLabel(texto);
    	label.setFont(FONTE_ROTULO);
    	return label;
    }
    
    
    
    
    
    /**
     * Carrega a configuração padrão de um <code>JLabel</code>.
     * 
     * @param texto A String que será adicionada no JLabel.
     * @param x Localização X.
     * @param y Localização Y.
     * @param largura A largura do rótulo
     * @param altura A altura do rótulo.
     * @return O JLabel formatado para ser adicionado na tela.
     */
    public JLabel createJLabel(String texto, int x, int y, int largura, int altura){
    	JLabel label = new JLabel(texto);
    	label.setFont(FONTE_ROTULO);
    	label.setBounds(x, y, largura, altura);
    	return label;
    }
    
    
    
    
    
    /**
     * Configura a janela na qual o campo será exibido.
     * @param comp 
     */
    public void setComponentePai(JComponent comp){
        this.janelaPai = comp;
    }
    
    
    /**
     * @return A janela pai do frame onde o campo se localiza.
     */
    public JComponent getJanelaPai(){
        return janelaPai;
    }
    
    
    
    /**
     * Adiciona a interface ouvinte ,code>
     * @param campo 
     */
    protected void adicionaKeyListener(final JComponent campo){
        // Adiciona a interface ouvinte KeyListener.
        campo.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                
            	destacaBorda(campo, COR_PADRAO_BORDA);
                // Some a tela caso o botão ESC seja pressinado.
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                    janelaPai.setVisible(false);
                }
            }
        });
    }
    
    
    
    /**
     * Destaca o componente na tela alterando a cor da sua borda.
     * 
     * @param campo O componente que terá a borda destacada.
     * @param cor A cor que será aplicada à borda do campo.
     */
    public void destacaBorda(JComponent campo, Color cor){
    	campo.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,cor));
        campo.requestFocus();
    }
    
    
    
    
    
    
    
    
}
