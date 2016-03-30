package view.cadastro;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import control.cadastro.ControleFinalizaVenda;
import model.Campo;
import model.Relogio;
import view.FrameInicial;



/**
 * Frame que finaliza uma venda. Tela de pagamento.
 * 
 */
public class FrameFinalizarVenda extends FrameCadastro {

	
	private static final long serialVersionUID = 1L;
	// Fonte dos campos da tela de finalizar vendas.
	private static final Font FONTE_CAMPOS_FINALIZAR_VENDA = new Font(NOME_FONTE, Font.BOLD, 30);
	
	// Valor total da venda realizada.
	private Double valorTotalVenda = 00.00;
	// Padroniza a construção dos campos.
	private Campo campo = new Campo(this);
	
	private JTextField tfHora, tfData, tfValorFinal, tfValorRecebido, tfTroco;
	// LIsta com as formas de pagamento disponíveis.
	private JComboBox<String> cbxFormaPagamento = null;
	// Painel auxiliar dos campos.
	private JPanel pnAuxCampos = null;
	// Painel auxiliar para colocar os botões.
	private JPanel pnBotoes = null;
	// Painel auxiliar para colocar os campos caso a forma de pagamento seja em dinheiro.
	private JPanel pnAuxCamposDinheiro = null;
	// Botões do frame.
	private JButton btnVoltar, btnFinalizar;
	// Rótulos para adicionar textos na tela.
	private JLabel lbData, lbHora, lbFormaPagamento, lbTotal, lbRecebido, lbTroco;
	// O controlador desta classe.
	private ControleFinalizaVenda controle = null;
	// Objeto responsável por obter a data e hora do sistema.
	private Relogio objRelogio = null;
	
	private Font fonteBotoes = new Font(NOME_FONTE, Font.BOLD + Font.ITALIC, 18);
	private Font fonteLabels = new Font(NOME_FONTE, Font.BOLD + Font.ITALIC, 28);
	
	
	
	
	/**
	 * Controlador da classe FrameFinalizaVenda.
	 * 
	 * @param caixa A instância do Frame caixa que estava sendo utilizado na venda.
	 * @param inicial A instância do frame inicial do sistema.
	 * @param valorTotal O valor total da venda realizada.
	 * 
	 */
	public FrameFinalizarVenda(FrameCaixa caixa, FrameInicial inicial, Double valorTotal) {
		super("Finalizar Venda - Pagamento");
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		controle = new ControleFinalizaVenda(caixa, this, inicial);
		
		this.valorTotalVenda = valorTotal;
		configuraFrame();
	}

	
	
	
	/**
	 * Configura o frame e adiciona novos campos.
	 */
	private void configuraFrame(){

		Dimension dim = new Dimension(500, 500);
		setSize(dim);
		getPnBase().setSize(dim);
		getPnCampos().setSize(new Dimension(475, 448));
		getPnCampos().setLayout(null);		
		super.addInternalFrameListener(new InternalFrameAdapter() {
			@Override
			public void internalFrameClosing(InternalFrameEvent e) {
				int opcao = JOptionPane.showConfirmDialog(null, 
						"Você irá interromper a venda sem finalizá-la!\n\n"
						+ "Deseja continuar?", "Alerta", JOptionPane.OK_CANCEL_OPTION);
				if(opcao == 0)
					setVisible(false);
			}
		});
		
		lbData = campo.createJLabel("Data ");
		lbData.setFont(fonteLabels);
		lbData.setHorizontalAlignment(JLabel.CENTER);
		
		lbHora = campo.createJLabel("Hora ");
		lbHora.setFont(fonteLabels);
		lbHora.setHorizontalAlignment(JLabel.CENTER);
		
		lbTotal = campo.createJLabel("Total ");
		lbTotal.setFont(fonteLabels);
		lbTotal.setHorizontalAlignment(JLabel.CENTER);
		
		lbFormaPagamento = campo.createJLabel("Pagamento ");
		lbFormaPagamento.setFont(fonteLabels);
		lbFormaPagamento.setHorizontalAlignment(JLabel.CENTER);
		
		lbRecebido = campo.createJLabel("Recebido ");
		lbRecebido.setFont(fonteBotoes);
		lbRecebido.setHorizontalAlignment(JLabel.CENTER);
		
		lbTroco = campo.createJLabel("Troco ");
		lbTroco.setFont(fonteBotoes);
		lbTroco.setHorizontalAlignment(JLabel.CENTER);
		
		pnAuxCampos = new JPanel(new GridLayout(4, 2, 0, 10));
		pnAuxCampos.setBounds(20, 20, getPnCampos().getWidth() - 50, getPnCampos().getHeight() - 220);
		pnAuxCampos.add(lbData);
		pnAuxCampos.add(getTfData());
		pnAuxCampos.add(lbHora);
		pnAuxCampos.add(getTfHora());
		pnAuxCampos.add(lbTotal);
		pnAuxCampos.add(getTfValorFinal());
		pnAuxCampos.add(lbFormaPagamento);
		pnAuxCampos.add(getcbxFormaDePagamento());
		//pnAuxCampos.setBackground(COR_PAINEL_BACKGROUND);
		
		objRelogio = new Relogio(getTfHora());
		objRelogio.start();// Inicia marcação da hora no campo.
		getTfData().setText(objRelogio.getDataAtualSistema());
		
		pnBotoes = new JPanel(new GridLayout(1, 2, 15, 0));
		pnBotoes.setBounds(20, 345, pnAuxCampos.getWidth(), 80);
	//	pnBotoes.setBackground(COR_PAINEL_BACKGROUND);
		
		pnAuxCamposDinheiro = new JPanel(new GridLayout(1, 4, 15, 15));
		pnAuxCamposDinheiro.setBounds(20, 265, pnAuxCampos.getWidth(), 45);
	//	pnAuxCamposDinheiro.setBackground(COR_PAINEL_BACKGROUND);
		pnAuxCamposDinheiro.add(lbRecebido);
		pnAuxCamposDinheiro.add(getTfDinheiroRecebido());
		pnAuxCamposDinheiro.add(lbTroco);
		pnAuxCamposDinheiro.add(getTfTroco());
		
		btnVoltar = new JButton("   Voltar", new ImageIcon(ICONE_VOLTAR));
		btnVoltar.setSize(100, 40);
		btnVoltar.setCursor(HAND_CURSOR);
		btnVoltar.setFont(fonteBotoes);
		btnVoltar.setToolTipText("Voltar à tela de Vendas");
		super.addMouseEventIO(btnVoltar);
		btnVoltar.addActionListener(controle.getActionVoltar());
		btnVoltar.addKeyListener(controle.getKeyListenerVoltar());
		
		pnBotoes.add(btnVoltar);
		pnBotoes.add(getBtnFinalizar());
		
		super.getPnCampos().add(pnAuxCampos);
		super.getPnCampos().add(pnAuxCamposDinheiro);
		super.getPnCampos().add(pnBotoes);
		
	}
	
	
	
	
	
	
	public JButton getBtnFinalizar(){
		if(btnFinalizar == null){
			btnFinalizar = new JButton("Finalizar ", new ImageIcon(ICONE_FINALIZAR));
			btnFinalizar.setSize(100, 40);
			btnFinalizar.setFont(fonteBotoes);
			btnFinalizar.setToolTipText("Finalizar Venda");
			btnFinalizar.setCursor(HAND_CURSOR);
			super.addMouseEventIO(btnFinalizar);
			btnFinalizar.addActionListener(controle.getActionBtnFinalizar());
			btnFinalizar.addKeyListener(controle.getKeyListenerFinalizar());
		}
		return btnFinalizar;
	}
	
	
	
	
	
	
	
	/**
	 * Retorna a instância do campo referente ao troco a ser dado ao cliente.
	 */
	private JTextField getTfTroco() {
		if(tfTroco == null){
			tfTroco = campo.createTextFieldVendas();
			tfTroco.setEditable(false);
			tfTroco.setHorizontalAlignment(JTextField.CENTER);
			tfTroco.setFont(new Font(NOME_FONTE, Font.BOLD, 20));
		}
		return tfTroco;
	}



	
	/**
	 * Retorna a instância do campo referente ao valor recebido do cliente.
	 */
	private JTextField getTfDinheiroRecebido() {
		if(tfValorRecebido == null){
			tfValorRecebido = campo.createTextFieldVendas();
			tfValorRecebido.setHorizontalAlignment(JTextField.CENTER);
			tfValorRecebido.setFont(new Font(NOME_FONTE, Font.BOLD, 20));
			tfValorRecebido.addKeyListener(getActionCampoDinheiroRecebido());
		}
		return tfValorRecebido;
	}




	/**
	 * Retorna a instância do combobox referente à lista de formas de pagamento.
	 * 
	 * @param formaPagamento
	 */
	public JComboBox<String> getcbxFormaDePagamento() {
		if(cbxFormaPagamento == null){
			cbxFormaPagamento = campo.createComboBoxVendas(controle.consultaFormasDePagamento());
			cbxFormaPagamento.setSelectedItem("DINHEIRO");
			cbxFormaPagamento.setFont(new Font(NOME_FONTE, Font.BOLD, 20));
			cbxFormaPagamento.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if(cbxFormaPagamento.getSelectedItem().equals("DINHEIRO")){
						tfValorRecebido.setText("");
						tfTroco.setText("");
						pnAuxCamposDinheiro.setVisible(true);
					}else{
						pnAuxCamposDinheiro.setVisible(false);
					}
				}
			});
		}
		return cbxFormaPagamento;
	}




	/**
	 * Retorna a instância do campo referente a data atual.
	 */
	public JTextField getTfData(){
		if(tfData == null){
			tfData = campo.createTextFieldVendas();
			tfData.setEditable(false);
			tfData.setFont(FONTE_CAMPOS_FINALIZAR_VENDA);
			tfData.setHorizontalAlignment(JTextField.CENTER);
		}
		return tfData;
	}
	
	
	
	
	/**
	 * Retorna a instância do campo referente a hora atual.
	 */
	public JTextField getTfHora(){
		if(tfHora == null){
			tfHora = campo.createTextFieldVendas();
			tfHora.setEditable(false);
			tfHora.setFont(FONTE_CAMPOS_FINALIZAR_VENDA);
			tfHora.setHorizontalAlignment(JTextField.CENTER);
		}
		return tfHora;
	}
	
	
	
	
	
	/**
	 * Retorna a instância do campo referente ao valor final da venda.
	 */
	public JTextField getTfValorFinal(){
		if(tfValorFinal == null){
			
			String total = String.format("%.2f", valorTotalVenda);  
			
			tfValorFinal = campo.createTextFieldVendas();
			tfValorFinal.setEditable(false);
			tfValorFinal.setText(total);
			tfValorFinal.setFont(FONTE_CAMPOS_FINALIZAR_VENDA);
			tfValorFinal.setHorizontalAlignment(JTextField.CENTER);
			tfValorFinal.setForeground(COR_DE_DESTAQUE_CAMPOS);
		}
		return tfValorFinal;
	}
	
	
	
	
	/**
	 * Retorna o ActionListener a ser adicionado no campo de Dinheiro recebido do cliente.
	 */
	public KeyListener getActionCampoDinheiroRecebido(){
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					
					try {
						
						Double dinheiroRecebido = Double.parseDouble(tfValorRecebido.getText().replace(',', '.'));
						
						if(dinheiroRecebido < valorTotalVenda){
							JOptionPane.showMessageDialog(null, "O dinheiro recebido é insuficiente!!");
							tfValorRecebido.setText("");
							tfTroco.setText("");
							
						}else{
							Double troco = dinheiroRecebido - valorTotalVenda;
							
							tfTroco.setText(String.format("%.2f", troco));
						}
					} catch (NumberFormatException ex) {
						// Se informar um valor errado, limpa os dois campos.
						tfValorRecebido.setText("");
						tfTroco.setText("");
					}
				}
			}
		};
	}
	
	
	
	/**
	 * A Thread do relógio que fica sempre atualizando.
	 * @return um Thread.
	 */
	public Relogio getRelogio(){
		return objRelogio;
	}
	

	
}
