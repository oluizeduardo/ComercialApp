package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.POJO.Setor;
import model.database.AcoesBancoDeDados;
import model.database.SQL;



/**
 * Controla toda a manipulação de dados na tabela de Setores.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 20/11/215
 */
public class SetorDAO implements AcoesBancoDeDados<Setor> {

	
	/**Nome da tabela no banco de dados.*/
    public static final String TABLE_NAME = "SETORES";
    
    /**Nome do campo identificador da tabela.*/
    public static final String ID_FIELD_NAME = "CODIGO";
    
    /**Instrução SQL para consultar todos os registros da tabela de Setores.*/
    public static final String CONSULTA_TABELA_INTEIRA = "SELECT * FROM "+TABLE_NAME;
    
    /**Instrução SQL para ordenar a consulta em ordem alfabética da descrição do setor.*/
    public static final String ORDENA_CRESCENTE_POR_DESCRICAO = " ORDER BY descricao ASC";
    
    
    /** Instância da classe <code>SetorDAO</code>.  */
    private static SetorDAO instancia = null;
    
    /** Esse objeto faz uso dos métodos e alguns atributos protegidos da classe <code>SQL</code>. */
    private SQL objSQL = new SQL();
    
    /**
     * Guarda as instruções SQL a serem executadas.
     * As instruções SQL serão somente de propriedade desta classe.
     */
    private String scriptSql = null;
    
    
    
    

    
    
    /**
     * Retorna uma instância da classe <code>SetorDAO</code>.<p>
     * 
     * O método serve para que não seja necessário a declaração de um objeto ou 
     * a instância desta classe, basta chamar o método desejado em apenas uma linha 
     * de código. Esse método já retorna uma instância da classe <code>SetorDAO</code>, 
     * após a chamada desse método basta apenas chamar o método desejado na frente.<p>
     * 
     * A utilização deste método é conhecida como Singleton, um padrão de desenvolvimento
     * onde não necessita que o programador declare um objeto ou faça a instância de uma
     * classe, basta apenas chamar a classe, usar este método que retorna uma instância da
     * mesma classe e depois fazer a chamada do método desejado. Utiliza apenas uma linha
     * de código para essa operação.<p>
     * 
     * <strong>Exemplo:</strong><br>
     * <pre>
     *      // Vai retornar apenas o número de clientes cadastrados.
     *      Setor setor = SetorDAO.getInstance().consulta();
     * </pre>
     * 
     * 
     * @return Uma instância de <code>SetorDAO</code>.
     */
    public static SetorDAO getInstancia(){
        if(instancia == null){
            instancia = new SetorDAO();
        }
        return instancia;
    }



    
    
    
    
    /**
     * CADASTRA UM NOVO SETOR NO BANCO DE DADOS.
     */
	@Override
	public boolean cadastra(Setor obj) {
		
		// O campo código é preenchido com o valor de um sequence criado no banco de dados.
		this.scriptSql = "INSERT INTO "+TABLE_NAME+" (codigo, descricao) "
				+ "VALUES (DEFAULT, '"+obj.getNome()+"')";
       
		return (objSQL.executaSQL(scriptSql)); 
	}



	 /**
     * Realiza uma consulta de setores no banco de dados.<p>
     * 
     * A consulta realizada depende da instrução SQL passada como parâmetro,
     * o script será executado e os dados retornados da consulta serão armazenados
     * em um objeto <code>List</code>, que conterá somente instâncias da classe
     * <code>Setor</code>. Essa lista será o retorno do método, e cada registro 
     * gravado no banco de dados será um objeto em uma posição na lista.<p>
     * 
     * Roda um loop e vai adicionando à lista todos os registros encontrados pelo
     * objeto <code>ResultSet</code>.
     * 
     * @param sql Uma <code>String</code> com a instrução SQL que será usada na 
     * realização da consulta.
     * @return Uma lista de registros de Setores consultados no banco de dados.
     */
	@Override
	public List<Setor> consulta(String sql) {

		List<Setor> listaDeSetores = new ArrayList<Setor> ();
		Setor objSetor = null;
		
		
        try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            // Roda o loop enquanto tiver um registro no banco de dados.
            while(objSQL.resSet.next()){
            	
            	// Cria um novo objeto Setor.
            	objSetor = new Setor();

            	objSetor.setCodigo(objSQL.resSet.getInt("codigo"));
            	objSetor.setNome(objSQL.resSet.getString("descricao"));
            	
            	listaDeSetores.add(objSetor);
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de setores!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método SetorDAO.consulta()\n"+e.getMessage());
            }
         }
		
		return listaDeSetores;
	}



	/**
	 * Reliza a atualização de um registro no banco de dados.
	 */
	@Override
	public boolean atualiza(Setor obj) {
		
		this.scriptSql = "UPDATE "+TABLE_NAME+" SET descricao ='"+obj.getNome()+"' WHERE codigo = "+obj.getCodigo();
		
		return objSQL.executaSQL(scriptSql);
	}



	/**
	 * Exclui um setor da tabela no banco de dados.
	 */
	@Override
	public boolean exclui(int codigo) {
		this.scriptSql = "DELETE FROM "+TABLE_NAME+" WHERE codigo = "+codigo;
		
		return objSQL.executaSQL(scriptSql);
	}
    
	
	
	
	
	
	/**
	 * Retorna um Vector com os nomes dos Setores cadastrados no banco de dados.
	 * @return Vector<Setor> com os objetos Setore.
	 */
	public Vector<Setor> getVectorSetores(){
		
		List<Setor> setoresList = consulta(SetorDAO.CONSULTA_TABELA_INTEIRA + SetorDAO.ORDENA_CRESCENTE_POR_DESCRICAO);
				
		return new Vector<Setor>(setoresList);
		
	}
	
	
	
	
	/**
     * Pesquisa o código do setor apartir do seu nome.
     * @param nomeSetor
     * @return O código do setor.
     */
    public int consultaCodigoDoSetor(String nomeSetor){
    	
    	String scrip = "SELECT * FROM "+TABLE_NAME+" WHERE descricao = '"+nomeSetor+"'";
    	
    	List<Setor> listaDeSetores = consulta(scrip);
    	
    	Setor setor = listaDeSetores.get(0);
    	
    	return setor.getCodigo();
    	
    }
	
	
    
    
	/**
     * Pesquisa o nome do setor apartir do seu código.
     * @param codigo
     * @return O nome do setor.
     */
    public String consultaNome(int codigo){
    	
    	String scrip = "SELECT * FROM "+TABLE_NAME+" WHERE codigo = "+codigo;
    	
    	List<Setor> listaDeSetores = consulta(scrip);
    	
    	Setor setor = listaDeSetores.get(0);
    	
    	return setor.getNome();
    	
    }
    
	
}
