package model;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JTextField;


/**
 * 
 * @author Luiz Eduardo da Costa
 * @version 11/01/2016
 */
public class Relogio extends Thread {

	
	
	private static final String formatoHora = "HH:mm:ss";
	private static final String formatoData = "dd/MM/yyyy";
	
	private GregorianCalendar calendario = null;
	private SimpleDateFormat formato = null;       
	private String horaAtual = null;
	private JTextField campoRelogio = null;
	
	
	
	public Relogio(JTextField campo) {
		this.campoRelogio = campo;
	}
	
	
	
	
	@Override
	public void run() {
		while(true){
			this.calendario = new GregorianCalendar();
			this.formato = new SimpleDateFormat(formatoHora);          
			this.horaAtual = formato.format(calendario.getTime());
			campoRelogio.setText(horaAtual);
		}
	}
	
	
	
	
	/**
	 * Retorna a hora atual do sistema, já formatada no padrão de um relógio comum.
	 */
	public String getHoraAtualSistema(){
		this.calendario = new GregorianCalendar();
		this.formato = new SimpleDateFormat(formatoHora);        
		return formato.format(calendario.getTime());
	}
	
	

	/**
	 * Retorna a data atual, já formatada, do sistema.
	 */
	public String getDataAtualSistema(){
		this.calendario = new GregorianCalendar();
		this.formato = new SimpleDateFormat(formatoData);       
		return formato.format(calendario.getTime());
	}
	
	
}
