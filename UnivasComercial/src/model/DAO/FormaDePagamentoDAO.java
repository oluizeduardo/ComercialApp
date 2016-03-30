package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.POJO.FormaDePagamento;
import model.database.AcoesBancoDeDados;
import model.database.SQL;


/**
 * Responsável pelo acesso aos dados da tabela de Forma de Pagamento no banco de dados.
 *
 */
public class FormaDePagamentoDAO implements AcoesBancoDeDados<FormaDePagamento> {

	
	/**Nome da tabela no banco de dados.*/
    public static final String TABLE_NAME = "FORMA_PAGAMENTO";
    
    /**Nome do campo identificador da tabela.*/
    public static final String ID_FIELD_NAME = "CODIGO";    
    
    /**Instrução SQL para consultar todos os registros da tabela de Produtos.*/
    public static final String CONSULTA_TABELA_INTEIRA = "SELECT * FROM "+TABLE_NAME;
    
    /**Instrução SQL para ordenar a consulta em ordem alfabética de descrição.*/
    public static final String ORDENA_CRESCENTE_POR_NOME = " ORDER BY descricao ASC";
    
    
    
    /** Instância da classe <code>FormaDePagamentoDAO</code>.  */
    private static FormaDePagamentoDAO instancia = null;
    
    /** Esse objeto faz uso dos métodos e alguns atributos protegidos da classe <code>SQL</code>. */
    private SQL objSQL = new SQL();
	
	
	
	
    
    
    /**
     * Retorna uma instância da classe <code>FormaDePagamentoDAO</code>.<p>
     * 
     * O método serve para que não seja necessário a declaração de um objeto ou 
     * a instância desta classe, basta chamar o método desejado em apenas uma linha 
     * de código. Esse método já retorna uma instância da classe <code>ProdutoDAO</code>, 
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
     *      FormaDePagamento prod = FormaDePagamentoDAO.getInstance().consulta();
     * </pre>
     * 
     * 
     * @return Uma instância de <code>FormaDePagamentoDAO</code>.
     */
    public static FormaDePagamentoDAO getInstancia(){
        if(instancia == null){
            instancia = new FormaDePagamentoDAO();
        }
        return instancia;
    }
	
    
    
    
	
	
	
	
	@Override
	public boolean cadastra(FormaDePagamento obj) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	/**
     * Realiza uma consulta das formas de pagamento no banco de dados.<p>
     * 
     * A consulta realizada depende da instrução SQL passada como parâmetro,
     * o script será executado e os dados retornados da consulta serão armazenados
     * em um objeto <code>List</code>, que conterá somente instâncias da classe
     * <code>Produto</code>. Essa lista será o retorno do método, e cada registro 
     * gravado no banco de dados será um objeto em uma posição na lista.<p>
     * 
     * Roda um loop e vai adicionando à lista todos os registros encontrados pelo
     * objeto <code>ResultSet</code>.
     * 
     * @param sql Uma <code>String</code> com a instrução SQL que será usada na 
     * realização da consulta.
     * @return Uma lista de registros de FormaDePagamento consultados no banco de dados.
     */
	@Override
	public List<FormaDePagamento> consulta(String sql) {
		
		List<FormaDePagamento> formasDePagamento = new ArrayList<FormaDePagamento> ();
		FormaDePagamento forma = null;
		
		
        try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            // Roda o loop enquanto tiver um registro no banco de dados.
            while(objSQL.resSet.next()){
            	
            	forma = new FormaDePagamento();
            	          
            	forma.setCodigo(objSQL.resSet.getInt("codigo"));
            	forma.setDescricao(objSQL.resSet.getString("descricao"));
            	            	
            	formasDePagamento.add(forma);
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de Formas de Pagamento!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método FormasDePagamentoDAO.consulta()\n"+e.getMessage());
            }
         }
		
		return formasDePagamento;
	}

	
	
	
	
	@Override
	public boolean atualiza(FormaDePagamento obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(int codigo) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	

	/**
     * Pesquisa o código da forma de pagamento apartir do seu nome.
     * @param formaPagamento
     * @return O código da forma de Pagamento.
     */
    public int consultaCodigoFormaDePagamento(String formaPagamento){
    	
    	String scrip = "SELECT * FROM "+TABLE_NAME+" WHERE descricao = '"+formaPagamento+"'";
    	
    	List<FormaDePagamento> formasDePagamento = consulta(scrip);
    	
    	FormaDePagamento formPag = formasDePagamento.get(0);
    	
    	return formPag.getCodigo();  	
    }
	
	
	
}
