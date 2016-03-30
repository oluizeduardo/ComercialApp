
package view.graficos;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;



/**
 * Gráfico de linhas informando os horários de maiores vendas durante determinado dia.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 14/01/16
 */
public class RelatorioHorariosMaioresVendas extends ChartPanel {

	

	private static final long serialVersionUID = 1L;
	// Objeto que gera o gráfico.
	private static JFreeChart chart = null;


	
	
	/**
	 * Método construtor. 
	 * Gera um gráfico de linhas que informa a evolução das vendas durante um dia.
	 * 
	 * @param data A data que se deseja saber os horários de maiores vendas.
	 * @param n Vetor int com as quantidades de vendas realizadas durante o expediente.
	 */
	public RelatorioHorariosMaioresVendas(String data, int[] n) {
		super(gerarGrafico(data, n));
	}
	
	
	
	
	/**
	 * Cria o gráfico de linhas com os horários de maiores vendas durante o dia.
	 * 
	 * @param dataStr A data que se deseja saber os horários de maiores vendas.
	 * @param n Vetor int com as quantidades de vendas realizadas durante o expediente.
	 * @return Um gráfico de linhas montado com os valores informados no vetor.
	 */
	private static JFreeChart gerarGrafico(String dataStr, int[] n){
		
		// Definindo um novo gráfico.
        XYSeries series = new XYSeries ("Vendas");  
        
        series.add(7,  n[0 ]);
        series.add(8,  n[1 ]);
        series.add(9,  n[2 ]);
        series.add(10, n[3 ]);
        series.add(11, n[4 ]);
        series.add(12, n[5 ]);
        series.add(13, n[6 ]);
        series.add(14, n[7 ]);
        series.add(15, n[8 ]);
        series.add(16, n[9 ]);
        series.add(17, n[10]);
        series.add(18, n[11]);
        
        
        //CRIANDO O DATASET
        XYSeriesCollection dados = new XYSeriesCollection(series);     
        
        chart = ChartFactory.createXYLineChart("Horários de Maiores Vendas "+ dataStr, 
        												  "Horário", "Qtde. de Vendas",     
                dados,PlotOrientation.VERTICAL , true,true, false); 

        chart.clearSubtitles();// Tira a legenda.
        
        
        //-- Define uma escala básica para o gráfico.
        XYPlot plot = (XYPlot) chart.getPlot();        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();  
        rangeAxis.setRange(0, 10);    
        rangeAxis.setNegativeArrowVisible(false);
       
        return chart;
	}
	
	
	
	
	
	/**
	 * Retorna o objeto do gráfico formatado.
	 */
	public JFreeChart getChart(){
		return chart;
	}
	
	
	
	
	
	
}
