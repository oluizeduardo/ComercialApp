
package model.database;

import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Contém os métodos que tratam de conexões com o banco de dados, objetos que
 * preparam a instrução que será executada, método de execução de instruções SQL. 
 * <p> 
 * É aberta uma nova conexão com o banco de dados, a instrução SQL recebida 
 * pelos métodos é então enviada e executada no banco de dados, a resposta
 * obtida do banco é o retono que o método dará.
 * 
 * @author  Luiz Eduardo Costa
 * @version 1.0, 04/01/2014
 * @see     model.database.Conexao
 */
public class SQL {
 
    /**
     * O script SQL que será executado no banco de dados.
     */
    private String           sqlScript = null;
    /**
     * Usado para montar uma nova conexão com o banco de dados.
     */
    public Connection        conn      = null;
    /**
     * Prepara uma instrução pré-compilada que será executada no banco de dados. 
     */
    public PreparedStatement preStm    = null;
    /**
     * Usado para consultas no banco de dados.
     */
    public ResultSet         resSet    = null;
   
    
    
    
    /**
     * Construtor da classe <code>SQL</code>.
     */
    public SQL(){};
    

    
    
    /**
     * Abre uma nova conexão com o banco de dados.
     * <p>
     * Abre uma conexão com um objeto <code>Connection</code>,
     * <p>
     * É aberto nesse método o objetos que permitirá um fluxo de dados 
     * entre o software e o banco de dados.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     * 
     * @see Conexao#getConexao() 
     */
    public void abreConexao(){
        this.conn = Conexao.getInstance().getConexao();  
        System.out.println("\nConexão aberta com o Banco de Dados");
    }
    

    
    /**
     * Abre uma nova conexão com o banco de dados e executa um script SQL. 
     * Exige como parâmetro uma <code>String</code> com o código SQL a ser executado.
     * <p>
     * A <code>String</code> com a instrução SQL exigida no parâmetro do método, 
     * e então preparada, pré-compilada para ser executada no banco de dados, 
     * logo após o objeto <code>PreparedStatement</code> executa essa instrução.
     * 
     * @param sql A instrução SQL que será executada no banco de dados.
     * @return <code>true</code> se a instrução for executada com sucesso, 
     * <code>false</code> se houver qualquer falha na execução da instrução SQL.
     */
    public boolean executaSQL(String sql){  
        
        // Variável começa com false, recebe true se a instrução for executada com sucesso.
        boolean statusDaExecucao = false;
        
        try {
            // Passa o comando SQL para a variável da classe.
            this.sqlScript = sql;
            // Abre uma nova conexão com o Banco de dados.
            abreConexao();
            // Deixa a instrução SQL pré-compilada, pronta para ser executada.
            this.preStm = conn.prepareStatement(sql);
            // Executa a instrução SQL e retona o status da execução.
            statusDaExecucao = !preStm.execute(); 
            
        } catch (SQLException e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Erro ao executar instrução SQL!\n\n"+e.getMessage(),
                    "Erro",JOptionPane.ERROR_MESSAGE);
            
            System.err.println("\nErro ao executar SQL - SQL.executaSQL()\n"
                    + ""+e.getMessage());
            statusDaExecucao = false;//se houve algum erro na execução do SQL, retorna false
        }finally{
            try {
                // Fecha as conexãoes com o banco de dados.
                this.fechaConexoes();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Houve um erro de conexão com o Banco de Dados!","Erro",JOptionPane.ERROR_MESSAGE);
                System.err.println("\nErro ao fechar conexões. - SQL.executaSQL()\n"+e.getMessage());
            }
        }
      
        return statusDaExecucao;
    }

    
    
    /**
     * Fecha as conexões abertas com o banco de dados.
     * @throws SQLException 
     */
    public void fechaConexoes() throws SQLException{
        if(conn   != null) conn  .close();
        /* Se tentar fechar o objeto PreparedStatement usando o driver do
         Firebirds, um erro é encontrado, não sei o porquê. Más, qundo se usa
         o drive do Postgres, esse erro não é encontrado. No Firebirds dá erro,
         no Postgre não.*/
        if(preStm != null) preStm.close();
        if(resSet != null) resSet.close();
        System.out.println("Conexão encerrada com o Banco de Dados\n");
    }
    
    
    /**
     * Retorna o último comando SQL passado para o objeto da classe.
     * 
     * @return O último script SQL executado.
     */
    public String getSQLScript(){
        return sqlScript;
    }
    
    
    
}
