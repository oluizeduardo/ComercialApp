package view.graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;


/**
 * Classe que monta o gráfico que exibirá a porcentagem das formas de pagamento
 * utilizadas nas compras dos clientes.
 */
public class RelatorioFormasDePagamento extends ChartPanel {

	
	private static final long serialVersionUID = 1L;

	// O objeto que irá gerar o gráfico.
	private static JFreeChart chart = null;
	
	
	
	/**
	 * Monta o gráfico com os dados do vetor recebido.
	 * 
	 * O vetor deve conter a quantidade de vezes que cada forma de pagamento
	 * foi utlilizada pelo cliente na hora da compra.
	 * 
	 * @param v
	 */
	public RelatorioFormasDePagamento(int [] v) {
		super(gerarGrafico(v[0], v[1], v[2], v[3]));
	}
	
	
	
	private static JFreeChart gerarGrafico(int cheq, int cart, int din, int valAli){
		
		DefaultPieDataset pieDataSet = new DefaultPieDataset();
		pieDataSet.setValue("CHEQUE", cheq);
		pieDataSet.setValue("CARTÃO", cart);
		pieDataSet.setValue("DINHEIRO", din); 
		pieDataSet.setValue("VALE ALIMENTAÇÃO", valAli);
		
		String nome = "Formas de Pagamento Utilizadas nas Vendas";
		chart = ChartFactory.createPieChart(nome, pieDataSet, true, true, false);
		
		return chart;
	}
	
	
	
	
	
	public JFreeChart getChart(){
		return chart;
	}
	
	
	
	
	
	
}
