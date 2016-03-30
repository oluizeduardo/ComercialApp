package view.graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import model.POJO.ItemVendido;





/**
 * Classe que monta o gráfico de barras que exibirá os itens mais vendidos de cada setor.
 */
public class RelatorioItensMaisVendidos extends ChartPanel {

	
	private static final long serialVersionUID = 1L;

	// O objeto que irá gerar o gráfico.
	private static JFreeChart chart = null;
	
	
	
	/**
	 * Contrutor da classe RelatorioItensMaisVendidos.
	 * 
	 * Os valores exigidos no parâmetro do método servirão para desenhar as barras do gráfico.
	 * 
	 * @param a 
	 * @param b
	 * @param c
	 */
	public RelatorioItensMaisVendidos(ItemVendido a, ItemVendido b, ItemVendido c) {
		super(gerarGrafico(a, b, c));
	}
	
	
	
	
	
	private static JFreeChart gerarGrafico(ItemVendido a, ItemVendido b, ItemVendido c){
		
		DefaultCategoryDataset result = createDataset(a, b, c);
		
		chart = ChartFactory.
				createBarChart3D("Produtos Mais Vendidos", "Itens", "Qtde. Comprada", 
						         result, PlotOrientation.VERTICAL, true, true, false);
			  
	   CategoryPlot plot = (CategoryPlot) chart.getPlot();
	   CategoryAxis xAxis = (CategoryAxis) plot.getDomainAxis();
	   xAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // Inclinação de 45graus do eixo X;
	   plot.setBackgroundAlpha(0.5f);
	   
	   return chart;
	}
	
	
	
	private static DefaultCategoryDataset createDataset(ItemVendido a, ItemVendido b, ItemVendido c) {
		
		DefaultCategoryDataset resultDataSet = new DefaultCategoryDataset();
		
		int qtde = 0;
		String nomeProduto;
		String setor;
		
		if(a != null){
			qtde = a.getQuantidade();
			nomeProduto = a.getProduto().getNome();
			setor = a.getProduto().getSetor().getNome();
			
			resultDataSet.setValue(qtde, nomeProduto, setor);
		}
		
		if(b != null){
			qtde = b.getQuantidade();
			nomeProduto = b.getProduto().getNome();
			setor = b.getProduto().getSetor().getNome();
			
			resultDataSet.setValue(qtde, nomeProduto, setor);
		}
		
		if(c != null){
			qtde = c.getQuantidade();
			nomeProduto = c.getProduto().getNome();
			setor = c.getProduto().getSetor().getNome();
			
			resultDataSet.setValue(qtde, nomeProduto, setor);
		}
	
		return resultDataSet;
	}
	
	
	
	
	public JFreeChart getChart(){
		return chart;
	}
	
	
	
	
}
