package model.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Classe que reúne as informações de coinexão com o banco de dados.
 * 
 * Possui o método getConexao() que retorna um objeto Connection, referente
 * à conexão com o banco de dados.
 * 
 * @author Luiz Eduardo da Costa.
 */
public class Conexao {

	
	
	/**
	 * Instância da classe <code>Conexao</code>.
	 */
    public static Conexao instancia = null;
	
	
	//-- Conexão com o Postgres
	private String address = "127.0.0.1";
	private String port = "5432";
	private String nameDataBase = "Comercial";
    private String url = "jdbc:postgresql://"+address+":"+port+"/"+nameDataBase;  
    private String username = "postgres";  
    private String password = "postgres";  
    private String driver = "org.postgresql.Driver";
    //***********************************************************************
	
    
    
    public Conexao() {	}
    
    
    
    /**
     * Retorna uma instância da classe.
     * @return Uma instância da classe.
     */
    public static Conexao getInstance(){
        if(instancia == null){
            instancia = new Conexao();
        }
        return instancia;
    }
    
    
    
    
    
    
    
    /**
     * Retorna um objeto <code>Connection</code> contendo
     * a conexão com o banco de dados. Caso não encontre o
     * driver informado ou ocorra algum erro na conxão com
     * o banco de dados, a exceção é tratada.
     * 
     * @return conn <code>Connection</code>
     */
    public Connection getConexao() {
        
    	Connection conn = null;
        
        try {
        	// Carrega o driver.
			Class.forName(driver);			
			// Passa endereço, usuário e senha, retorna a conexão.
	        conn = DriverManager.getConnection(url, username ,password);
	        
		} catch (ClassNotFoundException e) {
			System.err.println("Classe não encontrada! \n"+e.getMessage());
			e.printStackTrace();
		}catch (SQLException e) {
			System.err.println("Erro de SQL!\n"+e.getMessage());
		}
        
        return conn;
    }
    
    
    
	
}
