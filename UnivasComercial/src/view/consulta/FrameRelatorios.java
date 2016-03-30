package view.consulta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.toedter.calendar.JDateChooser;

import model.DAO.ItemVendidoDAO;
import model.DAO.SetorDAO;
import model.DAO.VendaDAO;
import model.POJO.ItemVendido;
import model.POJO.Setor;
import model.POJO.Venda;
import view.FrameInternoBase;
import view.graficos.RelatorioFormasDePagamento;
import view.graficos.RelatorioHorariosMaioresVendas;
import view.graficos.RelatorioItensMaisVendidos;

/**
 * Frame de emissão de relatórios.
 * 
 * Tela onde são apresentado os gráficos de relatórios.
 * 
 */
public class FrameRelatorios extends FrameInternoBase {


	private static final long serialVersionUID = 1L;
	private static final int GRAFICO_FORMAS_DE_PAGAMENTO = 0;
	private static final int GRAFICO_ITENS_MAIS_VENDIDOS = 1;
	private static final int GRAFICO_HORARIO_MAIORES_VENDAS = 2;
	

	// Gráfico com a porcentagem das formas de pagamento mais utilizadas nas vendas.
	private RelatorioFormasDePagamento graficoFormaDePagamento = null;
	
	// Gráfico dos itens masi vendidos.
	private RelatorioItensMaisVendidos graficoItensMaisVendidos = null;
	
	// Gráfico dos horários com maiores vendas.
	private RelatorioHorariosMaioresVendas graficoHorarioMaioresVendas = null;
	
	// Objeto responsável por criar um painel com abas na tela.
	private JTabbedPane painelAbas = null;
	
	// Paineis onde são adicionados os gráficos de relatórios.
	private JPanel pnRelat_1, pnRelat_2, pnRelat_3;
	
	// Painel onde componentes serão adicionados.
	private JPanel pnGraficoBarras, pnBotoes;
	
	// Lista dos setores cadastrados no banco de dados.
	private JList<Setor> listaSetores = null;
	
	// A lista de itens mais vendidos.
	private List<ItemVendido> itensMaisVendidos = null;
	
	private JButton btnExportar, btnCancelar, btnGerarGrafico;
	
	// Componente Swing que exibe um calendário.
	private JDateChooser calendario = null;
	
	// Tela para escolher o caminho e o nome para salvar uma imagem de gráfico.
	private JFileChooser chooser = null;
	
	
	
	
	
	
	/**
	 * Construtor da classe FrameRelatorio.
	 * 
	 * Preenche um vetor de int com a quantidade de formas de pagamento utilizada nas vendas.
	 */
	public FrameRelatorios() {
		super("Consulta de Relatórios ");
			
		int[] valores = VendaDAO.getInstancia().getQtdeFormasDePagamentoUtilizadas();	
		graficoFormaDePagamento = new RelatorioFormasDePagamento(valores);
		
		this.itensMaisVendidos = ItemVendidoDAO.getInstancia().consulta(null);
		graficoItensMaisVendidos = constroiGraficoDeBarras(itensMaisVendidos, null);
		
		// Consulta as vendas feitas na data desejada.
		List<Venda> vendas = VendaDAO.getInstancia().getVendasPorData(new Date());
		
		int v[] = organizaQtdeVendasPorHora(vendas);
		String strData = formataData("dd/MM/yyyy", new Date());
		
		graficoHorarioMaioresVendas = new RelatorioHorariosMaioresVendas(strData, v);
		
		configuraFrame();
	}
	
	
	
	
	/**
	 * Usa os dados de uma lista de Itens Vendidos para construir o gráfico dos itens mais vendidos.
	 * 
	 * @param lista
	 * @param setor
	 * @return Uma instância da classe que monta o gráfico.
	 */
	private RelatorioItensMaisVendidos constroiGraficoDeBarras(List<ItemVendido> lista, String setor) {
		
		// A gráfico exibirá apenas os três itens mais vendidos.
		ItemVendido[] itens = new ItemVendido[3];
		int pos = 0;// Auxiliar na posição do vetor.
		
		if(setor != null){
			for(int i=0; i < lista.size() && pos < 3; i++){
				
				// Pega um novo item da lista.
				ItemVendido iv = lista.get( i );
				
				// Verifica se ele pertence a tal setor.
				if(iv.getProduto().getSetor().getNome().equals(setor)){
					itens[ pos ] = iv;
					pos++;
				}
			}
		}
		
		return new RelatorioItensMaisVendidos(itens[0], itens[1], itens[2]);
	}



	/**
	 * Configura o frame e adiciona novos componentes.
	 */
	private void configuraFrame(){
		
		setSize(new Dimension(super.getWidth() + 100, super.getHeight() + 50));
		getPnBase().setSize(super.getSize());

		getPnBase().setLayout(new BorderLayout());
		getPnBase().add(getPnBotoes(), BorderLayout.SOUTH);
		getPnBase().add(getPnAbas(), BorderLayout.CENTER);
		
		super.setResizable(true);// Permite o redimensionamento.
		super.setMaximizable(true);// Aparece o ícone de maximizar.
	}
	
	
	
	
	/**
	 * Painel onde serão adicionados os botões.
	 */
	private JPanel getPnBotoes(){
		if(pnBotoes == null){
			pnBotoes = new JPanel(new FlowLayout());
			pnBotoes.setPreferredSize(new Dimension(200, 40));
			
			pnBotoes.add(getBtnCancelar());
			pnBotoes.add(getBtnExportarGrafico());
			
		}
		return pnBotoes;
	}
	


	
	/**
	 * Retorna a instância configurada do botão Cancelar.
	 * Quando precionado deve cancelar a exibição da tela atual.
	 */
	private JButton getBtnCancelar(){
		if(btnCancelar == null){
			btnCancelar = new JButton("Cancelar");
			btnCancelar.setCursor(HAND_CURSOR);
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
	 * Retorna um objeto configurado de botão.
	 * Esse botão é responsável por exportar o gráfico exibido em imagem.
	 * Esse método monta o botão e configura sua sequência de ações. 
	 */
	private JButton getBtnExportarGrafico(){
		if(btnExportar == null){			
			btnExportar = new JButton("Exportar Gráfico");
			btnExportar.setCursor(HAND_CURSOR);
			addMouseEventIO(btnExportar);
			btnExportar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Objeto do gráfico que será convertido para imagem.
					JFreeChart grafico = null;
					
					// O nome sugerido ao arquivo.
					String fileName = "grafico_";
					
					// Verifica qual aba está selecionada no momento.
					switch (painelAbas.getSelectedIndex()) {
						
						case GRAFICO_FORMAS_DE_PAGAMENTO:
							fileName += "formasDePagamento";
							grafico = graficoFormaDePagamento.getChart();
							break;	
							
						case GRAFICO_ITENS_MAIS_VENDIDOS:
							fileName += "ProdutosMaisVendidos";
							grafico = graficoItensMaisVendidos.getChart();
							break;
							
						case GRAFICO_HORARIO_MAIORES_VENDAS:
							fileName += "HorarioMaioresVendas";
							// Usa a data selecionada para completar o nome do arquivo.
							fileName += formataData("dd-MM-yyyy", calendario.getDate());
							grafico = graficoHorarioMaioresVendas.getChart();
							break;
					}					

					// Chama o File Chooser com um nome de arquivo sugerido.
					int retorno = getFileChooser(fileName).showSaveDialog(FrameRelatorios.this);
					
					if(retorno == JFileChooser.APPROVE_OPTION) {
					   
						String endereco = chooser.getSelectedFile().getAbsolutePath();
						
						try {
							salvarGrafico(grafico, endereco);
							
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(FrameRelatorios.this, e1.getMessage());
						}
					}	
				}
			});
		}
		return btnExportar;
	}
	
	
	
	
	
	/**
	 * Retorna a data no formato desejado.
	 * 
	 * @param formato
	 * @param data
	 */
	private String formataData(String formato, Date data){
		if(data == null){
			data = new Date();
		}
		return new SimpleDateFormat(formato).format(data);
	}
	
	
	
	
	
	
	/**
	 * Método monta um objeto JFileChooser.
	 * 
	 * O parâmetro é o nome desejado para renomear o arquivo de imagem,
	 * Essa String será exibida no campo de segestão de nome do arquivo.
	 * 
	 * @param fileName
	 * @return Objeto JFileChooser configurado para salvar uma imagem.
	 */
	private JFileChooser getFileChooser(String fileName){
		this.chooser = new JFileChooser("\\");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDialogTitle("Salvar gráfico");
		chooser.setSelectedFile(new File(fileName));
		return chooser;
	}
	
	
	
	
	
	
	/**
	 * Configura o painel com abas.
	 * @return O painel configurado com as abas de relatório.
	 */
	private JTabbedPane getPnAbas(){
		if(painelAbas == null){
			painelAbas = new JTabbedPane();
			painelAbas.addTab(" Formas de Pagamento ", getPnRelat_1());
			painelAbas.addTab(" Produtos Mais Vendidos ", getPnRelat_2());
			painelAbas.addTab(" Horários de Maiores Vendas ", getPnRelat_3());
			// Adiciona ouvinte de teclado para fechar janela no 'ESC'
			painelAbas.addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyPressed(KeyEvent e) {
	                if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
	                    setVisible(false);
	                }        
	            }
	        });
		}
		return painelAbas;
	}
	
	
	
	
	//*************** SOBRE ESSES PAINEIS SERÃO ADICIONADOS OS GRÁFICOS ***************
	
	
	private JPanel getPnRelat_1(){
		if(pnRelat_1 == null){
			pnRelat_1 = new JPanel(new BorderLayout());
	//		pnRelat_1.setBackground(COR_PAINEL_BACKGROUND);
			pnRelat_1.add(graficoFormaDePagamento);
		}
		return pnRelat_1;
	}
	
	
	
	private JPanel getPnRelat_2(){
		if(pnRelat_2 == null){

			JScrollPane pnrolagem = new JScrollPane(getListaSetores());
			JPanel pnLista = new JPanel(new GridLayout(1, 1));
			pnLista.add(pnrolagem);
			pnLista.setMinimumSize(new Dimension(120, getHeight()));
			
			pnGraficoBarras = new JPanel(new BorderLayout());
			pnGraficoBarras.add(graficoItensMaisVendidos);
			pnGraficoBarras.setMinimumSize(new Dimension(getWidth()-300, getHeight()));
			
			JSplitPane pnDividido = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnGraficoBarras, pnLista);
			
			pnDividido.setResizeWeight(0.5);
			pnDividido.setOneTouchExpandable(false);
			pnDividido.setContinuousLayout(true);
			
			pnRelat_2 = new JPanel(new BorderLayout());
		//	pnRelat_2.setBackground(COR_PAINEL_BACKGROUND);
			pnRelat_2.add(pnDividido, BorderLayout.CENTER);
			
		}
		return pnRelat_2;
	}
	
	
	
	
	/**
	 * Painel que mostrará o relatório em gráfico de linhas das horas no dia que houveram mais vendas.
	 * @return
	 */
	private JPanel getPnRelat_3(){
		if(pnRelat_3 == null){
			pnRelat_3 = new JPanel(new BorderLayout());
		//	pnRelat_3.setBackground(COR_PAINEL_BACKGROUND);
			
			this.calendario = new JDateChooser();
			calendario.setPreferredSize(new Dimension(130, 40));
			calendario.setFont(new Font(NOME_FONTE, Font.PLAIN, 15));
					
			JPanel pnCalendario = new JPanel(new FlowLayout());
			pnCalendario.setPreferredSize(new Dimension(130, 100));
			
			pnCalendario.add(calendario);
			pnCalendario.add(getBtnGerarGrafico());
			
			graficoHorarioMaioresVendas.setPreferredSize(new Dimension(500, 400));
			
			pnRelat_3.add(graficoHorarioMaioresVendas, BorderLayout.CENTER);
			pnRelat_3.add(pnCalendario, BorderLayout.EAST);
			
		}
		return pnRelat_3;
	}
	
	
	
	
	
	/**
	 * Retorna o objeto configurado do botão que exporta o gráfico para arquivo de imagem.
	 */
	private JButton getBtnGerarGrafico(){
		if(btnGerarGrafico == null){
			this.btnGerarGrafico = new JButton("Gerar Gráfico");
			addMouseEventIO(btnGerarGrafico);
			btnGerarGrafico.setCursor(HAND_CURSOR);
			btnGerarGrafico.addActionListener(new ActionListener() {			
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Pega a data selecionada no calendário.
					Date data = calendario.getDate();
					
					// Entra aqui caso o usuário clicar para gerar um gráfico sem ter escolhido a data.
					if(data == null){
						data = new Date();// Data de hoje.
					}
						
					// Consulta as vendas feitas na data desejada.
					List<Venda> listVendas = VendaDAO.getInstancia().getVendasPorData(data);
					
					// Remove a imagem do gráfico anterior e atualiza o painel.
					pnRelat_3.remove(graficoHorarioMaioresVendas);
					pnRelat_3.revalidate();

					// Prepara a data em formato String para aplicar no título do gráfico.
					String strData = formataData("dd/MM/yyyy", data);
					
					// Organiza as quantidades de vendas por hora.
					int qtdVendas[] = organizaQtdeVendasPorHora(listVendas);
					
					// Nova instância do gráfico.
					graficoHorarioMaioresVendas = new RelatorioHorariosMaioresVendas(strData, qtdVendas);
					// Adiciona o novo gráfico no painel.
					pnRelat_3.add(graficoHorarioMaioresVendas, BorderLayout.CENTER);
					
					
					
					
					
					
					
					
					
				}
			});
			
		}
		return btnGerarGrafico;
	}
	
	
	
	
	
	
	/**
	 * Com base na lista de vendas realizadas, calcula quantas vendas foram feitas
	 * em cada hora durante o horário de expediente.
	 * 
	 * @param vendasRealizadas A lista de vendas realizadas.
	 * @return Um vetor int com o número de vendas feitas em cada hora do dia.
	 */
	private int[] organizaQtdeVendasPorHora(List<Venda> vendasRealizadas) {
		
		int vendasPorHora[] = {0,0,0,0,0,0,0,0,0,0,0,0};
		
		for(int i=0; i < vendasRealizadas.size(); i++){
			
			// Pega um novo registro de vendas.
			Venda v = vendasRealizadas.get( i );
			
			// Quebra a String para saber qual hora foi feita a venda.
			int hora = Integer.parseInt(v.getHora().substring(0, 2));
			
			// Soma a quantidade de vendas para cada hora.
			switch (hora) {
			case 7:
				vendasPorHora[ 0 ]++;
				break;
			case 8:
				vendasPorHora[ 1 ]++;							
				break;
			case 9:
				vendasPorHora[ 2 ]++;
				break;
			case 10:
				vendasPorHora[ 3 ]++;							
				break;
			case 11:
				vendasPorHora[ 4 ]++;
				break;
			case 12:
				vendasPorHora[ 5 ]++;							
				break;
			case 13:
				vendasPorHora[ 6 ]++;
				break;
			case 14:
				vendasPorHora[ 7 ]++;							
				break;
			case 15:
				vendasPorHora[ 8 ]++;
				break;
			case 16:
				vendasPorHora[ 9 ]++;							
				break;	
			case 17:
				vendasPorHora[ 10 ]++;
				break;
			case 18:
				vendasPorHora[ 11 ]++;							
				break;	
			}
		}
		return vendasPorHora;
	}




	/**
	 * Retorna a instância da lisat de setores cadastrados no banco de dados.
	 */
	private JList<Setor> getListaSetores(){
		if(listaSetores == null){
			listaSetores = new JList<Setor>();
			listaSetores.setFocusable(false);// Tira o foco da lista.
			
			// Consulta no banco de dados a lista de setores.
			List<Setor> lista = SetorDAO.getInstancia().consulta(SetorDAO.CONSULTA_TABELA_INTEIRA);
			
			// Atualiza os dados do JList.
			atualizarLista(lista);		
						
			listaSetores.addMouseListener(new MouseAdapter() {
				
				@Override
				public void mousePressed(MouseEvent e) {
					
					// Pega o nome do setor selecionado na lista.
					String setor = listaSetores.getSelectedValue().getNome();
					
					pnGraficoBarras.remove(graficoItensMaisVendidos);
					pnGraficoBarras.repaint();
					
					graficoItensMaisVendidos = constroiGraficoDeBarras(itensMaisVendidos, setor);
					pnGraficoBarras.add(graficoItensMaisVendidos);
					pnGraficoBarras.setVisible(false);
					pnGraficoBarras.setVisible(true);
				}
			});
		}
		return listaSetores;
	}
	
	
	
	
	/**
	 * Atualiza os dados da lista de setores.
	 * @param novaLista
	 */
	public void atualizarLista(List<Setor> novaLista){
		getListaSetores().setListData(new Vector<Setor>(novaLista));
		
	}
	
	
	
	
	
	/**
	 * Salva o gráfico em um arquivo .jpeg, no endereço especificado no parâmetro.
	 * 
	 * @param chart
	 * @param endereco
	 * @throws FileNotFoundException
	 */
	public void salvarGrafico(JFreeChart chart, String endereco) throws FileNotFoundException{	
		
		try {
			ChartUtilities.saveChartAsJPEG(new java.io.File(endereco+".jpeg"), chart, 600, 450);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
	
	
}
