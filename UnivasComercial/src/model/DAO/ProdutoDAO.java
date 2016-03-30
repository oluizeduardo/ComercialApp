package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.POJO.Produto;
import model.POJO.Setor;
import model.database.AcoesBancoDeDados;
import model.database.SQL;


/**
 * Classe de manipulação dos dados da tabela de Produtos.
 */
public class ProdutoDAO implements AcoesBancoDeDados<Produto> {

	
	/**Nome da tabela no banco de dados.*/
    public static final String TABLE_NAME = "PRODUTOS";
    
    /**Nome do campo identificador da tabela.*/
    public static final String ID_FIELD_NAME = "CODIGO";    
    
    /**Instrução SQL para consultar todos os registros da tabela de Produtos.*/
    public static final String CONSULTA_TABELA_INTEIRA = "SELECT * FROM "+TABLE_NAME;
    
    /**Instrução SQL para ordenar a consulta em ordem alfabética do nome do Produto.*/
    public static final String ORDENA_CRESCENTE_POR_NOME = " ORDER BY nome ASC";
    
    
    
    /** Instância da classe <code>ProdutoDAO</code>.  */
    private static ProdutoDAO instancia = null;
    
    /** Esse objeto faz uso dos métodos e alguns atributos protegidos da classe <code>SQL</code>. */
    private SQL objSQL = new SQL();
    
    /**
     * Guarda as instruções SQL a serem executadas.
     * As instruções SQL serão somente de propriedade desta classe.
     */
    private String scriptSql = null;

    
    
    
    
    
    /**
     * Retorna uma instância da classe <code>ProdutoDAO</code>.<p>
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
     *      Produto prod = ProdutoDAO.getInstance().consulta();
     * </pre>
     * 
     * 
     * @return Uma instância de <code>ProdutoDAO</code>.
     */
    public static ProdutoDAO getInstancia(){
        if(instancia == null){
            instancia = new ProdutoDAO();
        }
        return instancia;
    }
    
    
    

    /**
     * Cadastra os dados de um novo objeto <code>Produto</code> 
     * no banco de dados
     * 
     * @param obj Objeto <code>Produto</code> que terá seus dados 
     * cadastrados no banco de dados.
     * 
     * @return Verdadeiro ou falso com o status da execução do cadastro.
     */
	@Override
	public boolean cadastra(Produto obj) {
		
		this.scriptSql = "INSERT INTO "+TABLE_NAME+" (codigo, nome, setor, preco_custo, "
				+ "margem_lucro, preco_venda, fornecedor, estoque_minimo, em_estoque, observacao)"
				+ "VALUES ("+obj.getCodigo()+", '"+obj.getNome()+"', '"+obj.getSetor().getCodigo()+"',"
				+ ""+obj.getPrecoCusto()+", "+obj.getMargemLucro()+", "+obj.getPrecoVenda()+","
				+ "'"+obj.getFornecedor()+"', "+obj.getEstoqueMinimo()+", "+obj.getEmEstoque()+","
				+ "'"+obj.getObservacao()+"')";

		
		//Se a instrução SQL for executada corretamente, então exibe a mensagem de sucesso.
        //Se houver qualquer erro, ele cairá nos exceptions do método.       
        if(objSQL.executaSQL(scriptSql)){
            return true;
        }else{
            return false;
        } 
	}
    
    



	
	 /**
     * Realiza uma consulta de produtos no banco de dados.<p>
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
     * @return Uma lista de registros de Produtos consultados no banco de dados.
     */
	@Override
	public List<Produto> consulta(String sql) {
		
		List<Produto> listaDeProdutos = new ArrayList<Produto> ();
		Produto objProduto = null;
		
		
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
            	objProduto = new Produto();
            	
            	Setor setor = new Setor();
            	setor.setCodigo(objSQL.resSet.getInt("setor"));
            	setor.setNome(SetorDAO.getInstancia().consultaNome(setor.getCodigo()));
            	
            	objProduto.setSetor(setor);
            	objProduto.setCodigo(objSQL.resSet.getInt("codigo"));
            	objProduto.setNome(objSQL.resSet.getString("nome"));
            	objProduto.setPrecoCusto(objSQL.resSet.getDouble("preco_custo"));
            	objProduto.setMargemLucro(objSQL.resSet.getDouble("margem_lucro"));
            	objProduto.setPrecoVenda(objSQL.resSet.getDouble("preco_venda"));
            	objProduto.setFornecedor(objSQL.resSet.getString("fornecedor"));	
            	objProduto.setEstoqueMinimo(objSQL.resSet.getInt("estoque_minimo"));
            	objProduto.setEmEstoque(objSQL.resSet.getInt("em_estoque"));
            	objProduto.setObservacao(objSQL.resSet.getString("observacao"));
            	
            	listaDeProdutos.add(objProduto);
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de produtos!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método ProdutoDAO.consulta()\n"+e.getMessage());
            }
         }
		
		return listaDeProdutos;
	}

	
	
	
	
	/**
     * Pesquisa o nome do produto apartir do seu código.
     * @param codigo
     * @return O nome do produto.
     */
    public String consultaNome(int codigo){
    	
    	String scrip = "SELECT * FROM "+TABLE_NAME+" WHERE codigo = "+codigo;
    	
    	List<Produto> listaDeProdutos = consulta(scrip);
    	
    	Produto prod = listaDeProdutos.get(0);
    	
    	return prod.getNome();
    	
    }
	
	
	
	
	
	
	
	
	@Override
	public boolean atualiza(Produto obj) {
		return false;
		
	}

	
	
	/**
     * Exclui do banco de dados o registro que contenha o código de identificador 
     * especificado no parâmetro do método.
     * 
     * @param codigo Código do registro a ser excluido.
     * @return <code>true ou false</code>, se a instrução foi executada com sucesso ou não.
     */
    @Override
    public boolean exclui(int codigo) {
        
        this.scriptSql = "DELETE FROM "+TABLE_NAME+" WHERE "+ID_FIELD_NAME+" = "+codigo; 
        
        return objSQL.executaSQL(scriptSql);
    }
	
	
    
    
    
   
	
	
}
