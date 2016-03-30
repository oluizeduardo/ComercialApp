package view.cadastro;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import control.cadastro.ControleCadastroProduto;
import model.Campo;
import model.DAO.SetorDAO;
import model.POJO.Produto;
import model.POJO.Setor;
import view.FrameInicial;


/**
 * Frame de cadastro de produtos.
 * 
 * @author Luiz Eduardo da Costa
 * @version 14/11/15
 */
public class FrameCadastroProdutos extends FrameCadastro {

	
	private static final long serialVersionUID = 1L;

	
	
	private JTextArea areaObservacao = null;
	
	private JPanel pnObservacoes, pnAuxCampos, pnAuxCamposValores;
	
	private JScrollPane pnScroll = null;
	
	private JTextField tfCodigo, tfNome, tfPrecoCusto, tfMargemLucro,
					   tfPrecoVenda, tfFornecedor, tfEstoqMinimo, tfEmEstoque;
	
	private JComboBox<Setor> cbxSetor = null;
	
	// Objeto para padronizar a criação dos campos.
	private Campo campo = new Campo(this);
	
	// Controlador do frame.
	private ControleCadastroProduto controle = null;
	
	// A instância do frame inicial onde as telas estão sendo manipuladas.
	private FrameInicial frameInicial = null;
	
	
	
	
	
	/**
	 * Método construtor da classe FrameCadastroProdutos.
	 */
	public FrameCadastroProdutos(FrameInicial frame) {
		super("Cadastro de Produtos");
		
		this.frameInicial = frame;
		
		this.controle = new ControleCadastroProduto(this);
		configuraFrame();
	}
	
	
	

	/**
	 * Configura o frame a adiciona novos componentes.
	 */
	private void configuraFrame(){
		
	
		//*************************** ÁREA DE TEXTO ****************************
        areaObservacao = campo.createTextArea(5, 45);
        areaObservacao.setLocation(0, 0);
        areaObservacao.setLineWrap(true);
		
        //****************************** PAINEIS *******************************
        pnAuxCampos = new JPanel(new GridLayout(8, 1));
        pnAuxCampos.setBounds(20, 10, 300, 260);
      //  pnAuxCampos.setBackground(COR_PAINEL_BACKGROUND);
        
        pnAuxCamposValores = new JPanel(new GridLayout(10, 1, 0, 5));
        pnAuxCamposValores.setBounds(370, 10, 120, 275);
       // pnAuxCamposValores.setBackground(COR_PAINEL_BACKGROUND);
        
        pnObservacoes = new JPanel(new FlowLayout());
        pnObservacoes.setBounds(10, 300, 520, 145);
        pnObservacoes.setBorder(BorderFactory.createTitledBorder(BORDA_GRAVADA, "Observações"));
      //  pnObservacoes.setBackground(COR_PAINEL_BACKGROUND);
        
        pnScroll = new JScrollPane(areaObservacao);
        pnScroll.setBorder(BORDA_FUNDA);
        pnScroll.setLocation(0, 0);
        pnScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        pnScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        
        
        //*************************** CAMPOS DE TEXTO ****************************
                   
        tfFornecedor = campo.createTextField();
        tfPrecoCusto = campo.createTextField();
        tfPrecoCusto.setHorizontalAlignment(JTextField.CENTER);
        tfMargemLucro = campo.createTextField();
        tfMargemLucro.setHorizontalAlignment(JTextField.CENTER);
        tfMargemLucro.setToolTipText("<HTML>Defina a porcentagem de lucro desejada <br>"
        		+ "e precione ENTER para atualizar o valor da venda."
        		+ "<br><br><strong><font color='red'>INSIRA SOMENTE NÚMEROS!</font></strong></HTML>");      
        tfMargemLucro.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER){
        			tfPrecoVenda.setText(""+String.format("%.2f", montaPrecoVenda()));
        		}
        	}
		});
        
        tfPrecoVenda = campo.createTextField();
        tfPrecoVenda.setText("00,00");
        tfPrecoVenda.setEditable(false);
        tfPrecoVenda.setHorizontalAlignment(JTextField.CENTER);
        tfEstoqMinimo = campo.createTextField();
        tfEstoqMinimo.setHorizontalAlignment(JTextField.CENTER);
        tfEmEstoque = campo.createTextField();
        tfEmEstoque.setHorizontalAlignment(JTextField.CENTER);

        
		pnAuxCampos.add(campo.createJLabel("Código"));
		pnAuxCampos.add(getTfCodigo());
		pnAuxCampos.add(campo.createJLabel("Nome"));
		pnAuxCampos.add(getTfNome());
		pnAuxCampos.add(campo.createJLabel("Setor"));
		pnAuxCampos.add(cbxSetor);
		pnAuxCampos.add(campo.createJLabel("Fornecedor"));
		pnAuxCampos.add(tfFornecedor);
		
		pnAuxCamposValores.add(campo.createJLabel("Custo (R$)"));
		pnAuxCamposValores.add(tfPrecoCusto);
		pnAuxCamposValores.add(campo.createJLabel("Lucro (%)"));
		pnAuxCamposValores.add(tfMargemLucro);
		pnAuxCamposValores.add(campo.createJLabel("Venda (R$)"));
		pnAuxCamposValores.add(tfPrecoVenda);
		pnAuxCamposValores.add(campo.createJLabel("Est. Mínimo"));
		pnAuxCamposValores.add(tfEstoqMinimo);
		pnAuxCamposValores.add(campo.createJLabel("Em Estoque"));
		pnAuxCamposValores.add(tfEmEstoque);
		
		getBtnSalvar().addActionListener(controle.getActionCadastrarProduto());
		getBtnNovo().addActionListener(controle.getActionNovoCadastro());// Limpa tela para novo cadastro.
		getBtnCaixa().addActionListener(controle.getActionAbrirTelaCaixa(frameInicial));
		
		getPnCampos().add(pnAuxCampos);
		getPnCampos().add(pnAuxCamposValores);
        pnObservacoes.add(pnScroll);
        getPnCampos().add(pnObservacoes);
		
	}
	
	
	
	/**
	 * Retorna a instância do campo de nome do novo produto.
	 */
	public JTextField getTfNome() {
		if(tfNome == null){
			tfNome = campo.createTextField();
	        cbxSetor = campo.createComboBoxSetores(SetorDAO.getInstancia().getVectorSetores());
	        cbxSetor.addItemListener(new ItemListener() {	
				@Override
				public void itemStateChanged(ItemEvent e) {
					campo.destacaBorda(cbxSetor, COR_PADRAO_BORDA);
				}
			});
		}
		return tfNome;
	}




	/**
	 * Retorna a instância do campo de código do novo produto.
	 */
	public JTextField getTfCodigo() {
		if(tfCodigo == null){
			tfCodigo = campo.createTextField();
	        tfCodigo.setHorizontalAlignment(JTextField.CENTER);
	        tfCodigo.setFont(FONTE_ROTULO);
	        tfCodigo.setToolTipText("Insira somente números");
		}
		return tfCodigo;
	}




	/**
	 * Monta o preço da venda baseado no preço de custo e na margem de lucro desejada.
	 * @return O valor adaptado.
	 */
	private Double montaPrecoVenda(){
		
		if(!tfPrecoCusto.getText().isEmpty() && !tfMargemLucro.getText().isEmpty()){
			Double valorCusto = Double.parseDouble(tfPrecoCusto.getText().trim().replace(',', '.'));
			Double margemLucro = Double.parseDouble(tfMargemLucro.getText().trim().replace(',', '.'));

			return (valorCusto + (valorCusto * (margemLucro / 100)));
		}else{
			return 00.00;
		}
	}
	
	
	
	
	
	
	/**
	 * Retorna um novo objeto Produto com dados informado pelo usuário 
	 * na tela de cadastro.
	 */
	public Produto getProduto(){
		
		Setor setor = (Setor) cbxSetor.getSelectedItem();
		
		Produto prod = new Produto();
		prod.setCodigo(Integer.parseInt(tfCodigo.getText()));
		prod.setCodigo(Integer.parseInt(tfCodigo.getText()));
		prod.setNome(tfNome.getText().toUpperCase());
		prod.setSetor(setor);
		prod.setFornecedor(tfFornecedor.getText().toUpperCase());
		prod.setPrecoCusto(Double.parseDouble(tfPrecoCusto.getText().trim().replace(',', '.')));
		prod.setMargemLucro(Double.parseDouble(tfMargemLucro.getText().trim().replace(',', '.')));
		prod.setPrecoVenda(Double.parseDouble(tfPrecoVenda.getText().trim().replace(',', '.')));
		prod.setEstoqueMinimo(Integer.parseInt(tfEstoqMinimo.getText()));
		prod.setEmEstoque(Integer.parseInt(tfEmEstoque.getText()));
		prod.setObservacao(areaObservacao.getText());
		
		return prod;
	}
	
	
	
	
	
	/**
	 * Limpa os campos da tela para um novo cadastro.
	 */
	public void limpaTela(){
		tfCodigo.setText("");
		tfNome.setText("");
		cbxSetor.setSelectedItem("Selecione");
		tfFornecedor.setText("");
		tfPrecoCusto.setText("");
		tfMargemLucro.setText("");
		tfPrecoVenda.setText("00,00");
		tfEstoqMinimo.setText("");
		tfEmEstoque.setText("");
		areaObservacao.setText("");
	}
	
	
	
	
	
	
	
	
	/**
	 * Realiza a validação dos campos do frame antes de seguir com o cadastro.
	 * @return Verdadeiro ou falso sobre a validação dos campos.
	 */
	public boolean validaCampos(){
			
			try {
				
				int codigo = Integer.parseInt(tfCodigo.getText());

				if(tfNome.getText().isEmpty()){
					campo.destacaBorda(tfNome, COR_DE_DESTAQUE_CAMPOS);
					return false;
				}else{
					if(cbxSetor.getSelectedItem().equals("")){
						campo.destacaBorda(cbxSetor, COR_DE_DESTAQUE_CAMPOS);
						return false;
					}else{
						if(tfFornecedor.getText().isEmpty()){
							campo.destacaBorda(tfFornecedor, COR_DE_DESTAQUE_CAMPOS);
							return false;
						}else{
							
							try {
								// Tenta converter o preço de custo.
								Double precoCusto = Double.parseDouble(tfPrecoCusto.getText().replace(',', '.'));

								try {
									// Tenta converter o valor da margem de lucro.
									Double margemLucro = Double.parseDouble(tfMargemLucro.getText().replace(',', '.'));
									
									if(tfPrecoVenda.getText().isEmpty()){
										campo.destacaBorda(tfPrecoVenda, COR_DE_DESTAQUE_CAMPOS);
										return false;
									}else{
										
										try {
											// Tenta converter o valor do estoque mínimo.
											int estoqueMinimo = Integer.parseInt(tfEstoqMinimo.getText());
											
											
											try {
												// Tenta converter o valor do estoque mínimo.
												int emEstoque = Integer.parseInt(tfEmEstoque.getText());
												
												return true;
												
											} catch (NumberFormatException e) {
												campo.destacaBorda(tfEmEstoque, COR_DE_DESTAQUE_CAMPOS);
												return false;
											}											
										} catch (NumberFormatException e) {
											campo.destacaBorda(tfEstoqMinimo, COR_DE_DESTAQUE_CAMPOS);
											return false;
										}
									}
									
								} catch (NumberFormatException e) {
									campo.destacaBorda(tfMargemLucro, COR_DE_DESTAQUE_CAMPOS);
									return false;
								}
							} catch (NumberFormatException e) {
								campo.destacaBorda(tfPrecoCusto, COR_DE_DESTAQUE_CAMPOS);
								return false;
							}
						}
					}
				}
			} catch (NumberFormatException e) {
				campo.destacaBorda(tfCodigo, COR_DE_DESTAQUE_CAMPOS);
				return false;
			}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
