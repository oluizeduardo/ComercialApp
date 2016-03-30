package model.DAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.POJO.ItemVendido;
import model.POJO.Produto;
import model.POJO.Setor;
import model.database.AcoesBancoDeDados;
import model.database.SQL;



/**
 * Classe que manipula o acesso aos dados da tabela de Itens vendidos.
 * 
 * @author Luiz Eduardo da Costa
 * @version 1.0, 28/11/15
 *
 */
public class ItemVendidoDAO implements AcoesBancoDeDados<ItemVendido> {

	
	/**Nome da tabela no banco de dados.*/
    public static final String TABLE_NAME = "ITENS_VENDIDOS";
    
    /**Nome do campo identificador da tabela.*/
    public static final String ID_FIELD_NAME = "CODIGO";    
    
    /**Instrução SQL para consultar todos os registros da tabela de itens vendidos.*/
    public static final String CONSULTA_TABELA_INTEIRA = "SELECT * FROM "+TABLE_NAME;
    
    /** Instância da classe <code>ItensVendidosDAO</code>.  */
    private static ItemVendidoDAO instancia = null;
    
    /** Esse objeto faz uso dos métodos e alguns atributos protegidos da classe <code>SQL</code>. */
    private SQL objSQL = new SQL();

    
    
    
    
    
    /**
     * Retorna uma instância da classe <code>ItensVendidosDAO</code>.<p>
     * 
     * O método serve para que não seja necessário a declaração de um objeto ou 
     * a instância desta classe, basta chamar o método desejado em apenas uma linha 
     * de código. Esse método já retorna uma instância da classe <code>ItensVendidosDAO</code>, 
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
     *      ItemVendido item = ItensVendidosDAO.getInstance().consulta();
     * </pre>
     * 
     * 
     * @return Uma instância de <code>ItemVendidoDAO</code>.
     */
    public static ItemVendidoDAO getInstancia(){
        if(instancia == null){
            instancia = new ItemVendidoDAO();
        }
        return instancia;
    }
    
    
    
    
    
    
    
    /**
     * Cadastra no banco de dados um novo item Vendido.
     */
	@Override
	public boolean cadastra(ItemVendido item) {
		// Monta o script para cadastrar um novo item vendido.
		String scriptSql = "INSERT INTO itens_vendidos(codigo, cod_venda, cod_produto, quantidade)"
				+ "VALUES (DEFAULT, "+item.getVenda().getCodigo()+", "+item.getProduto().getCodigo()+", "+item.getQuantidade()+")";
		
		return objSQL.executaSQL(scriptSql);
	}

	
	
	
	
	/**
	 * Consulta os Itens Vendidos e a respectiva quantidade vendida de cada um.
	 * 
	 * A String sql exigida no parâmetro é sobrescrita dentro do método 
	 * por um script adequado. 
	 */
	@Override
	public List<ItemVendido> consulta(String sql) {

		// Lista com os itens vendidos e suas respectivas quantidades.
		List<ItemVendido> itensVendidos = new ArrayList<ItemVendido>();

		
		sql = "SELECT p.codigo \"COD_PRODUTO\", p.nome \"PRODUTO\", "
				+ "s.descricao \"SETOR\", sum(iv.quantidade) \"QTDE\" "
				+ "FROM setores s JOIN produtos p "
				+ "JOIN itens_vendidos iv "
				+ "ON iv.cod_produto = p.codigo "
				+ "ON p.setor = s.codigo "
				+ "GROUP BY p.codigo, p.nome, s.descricao ORDER BY \"QTDE\" DESC";
		
		
		try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            // Roda o loop enquanto tiver um registro no banco de dados.
            while(objSQL.resSet.next()){
            	
            	// Monta Setor.
            	Setor setor = new Setor();
            	setor.setNome(objSQL.resSet.getString("SETOR"));
            	
            	// Monta Produto            	
            	Produto produto = new Produto();
            	produto.setCodigo(objSQL.resSet.getInt("COD_PRODUTO"));
            	produto.setNome(objSQL.resSet.getString("PRODUTO"));
            	produto.setSetor(setor);
            	
            	// Monta Item vendido
            	ItemVendido item = new ItemVendido(produto);           	
            	item.setQuantidade(objSQL.resSet.getInt("QTDE"));
            	
            	// Adiciona Item na lista
            	itensVendidos.add(item);
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de Itens Vendidos!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método ItensVendidosDAO.consulta()\n"+e.getMessage());
            }
         }		
		return itensVendidos;
	}

	
	
	
	
	
	
	
	@Override
	public boolean atualiza(ItemVendido obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(int codigo) {
		// TODO Auto-generated method stub
		return false;
	}
}
