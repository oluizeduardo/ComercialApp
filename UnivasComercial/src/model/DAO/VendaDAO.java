package model.DAO;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.POJO.FormaDePagamento;
import model.POJO.ItemVendido;
import model.POJO.Venda;
import model.database.AcoesBancoDeDados;
import model.database.SQL;


/**
 * Classe que manipula toda a interação do programa com os dados na tabela de Vendas no banco de dados.
 * 
 * @author Luiz Eduardo da Costa
 *
 */
public class VendaDAO implements AcoesBancoDeDados<Venda> {

	
	/**Nome da tabela no banco de dados.*/
    public static final String TABLE_NAME = "VENDAS";
    
    /**Nome do campo identificador da tabela.*/
    public static final String ID_FIELD_NAME = "CODIGO";    
    
    /**Instrução SQL para consultar todos os registros da tabela de Vendas.*/
    public static final String CONSULTA_TABELA_INTEIRA = "SELECT * FROM "+TABLE_NAME;
    
    /** Instância da classe <code>VendaDAO</code>.  */
    private static VendaDAO instancia = null;
    
    /** Esse objeto faz uso dos métodos e alguns atributos protegidos da classe <code>SQL</code>. */
    private SQL objSQL = new SQL();
    

    
    
    /**
     * Retorna uma instância da classe <code>VendaDAO</code>.<p>
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
     *      Venda venda = VendaDAO.getInstance().consulta();
     * </pre>
     * 
     * 
     * @return Uma instância de <code>VendaDAO</code>.
     */
    public static VendaDAO getInstancia(){
        if(instancia == null){
            instancia = new VendaDAO();
        }
        return instancia;
    }
    
	
	
	
	/**
	 * Cadastra uma nova Venda no banco de dados.
	 */
	@Override
	public boolean cadastra(Venda obj) {

		// Pega a forma de pagamento escolhida.
		String descFormPag = obj.getFormaDePagamento().getDescricao();
		
		// Usa o nome da forma de pagamento para consultar o respectivo código.
		int codFormPag = FormaDePagamentoDAO.getInstancia().consultaCodigoFormaDePagamento(descFormPag);
		
		// Script para rodar na tabela Vendas.
		String scriptSql = "INSERT INTO vendas(codigo, data, hora, total, forma_pagamento)"
				+ "VALUES (DEFAULT, '"+obj.getData()+"','"+obj.getHora()+"', "+obj.getTotal()+","+codFormPag+")";
		
		// Primeiro cadastra a venda.
		if(objSQL.executaSQL(scriptSql)){
			
			// Retorna a lista de itens vendidos.
			List<ItemVendido> listaDeItens = obj.getItensVendidos();
			
			for(int i=0; i < listaDeItens.size(); i++){
				
				// Pega um novo item da lista.
				ItemVendido item = listaDeItens.get( i );
				
				ItemVendidoDAO.getInstancia().cadastra(item);
			}
			return true;
		}
		return false;
	}

	
	
	
	
	
	
	
	
	/**
	 * Retorna um vetor de int contendo a quantidade de vezes que cada 
	 * forma de pagamento foi utilizada pelo cliente nas compras.
	 */
	public int[] getQtdeFormasDePagamentoUtilizadas(){
		
		String sql;
		int [] qtdeFormasPagUtilizadas = new int[4];
		
		sql = "SELECT COUNT(*) FROM vendas WHERE forma_pagamento  = 1";
		qtdeFormasPagUtilizadas[ 0 ] = consultaQtdeFormasDePagamentoUtilizada(sql);
		
		sql = "SELECT COUNT(*) FROM vendas WHERE forma_pagamento  = 2";
		qtdeFormasPagUtilizadas[ 1 ] = consultaQtdeFormasDePagamentoUtilizada(sql);
		
		sql = "SELECT COUNT(*) FROM vendas WHERE forma_pagamento  = 3";
		qtdeFormasPagUtilizadas[ 2 ] = consultaQtdeFormasDePagamentoUtilizada(sql);
		
		sql = "SELECT COUNT(*) FROM vendas WHERE forma_pagamento  = 4";
		qtdeFormasPagUtilizadas[ 3 ] = consultaQtdeFormasDePagamentoUtilizada(sql);
		
		return qtdeFormasPagUtilizadas;
	}
	
	
	
	
	/**
	 * Consulta a quantidade de vezes que uma determinada pesquisa no banco aparece.
	 */
	private int consultaQtdeFormasDePagamentoUtilizada(String sql){
		
		int qtde = 0;
		
		try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            // Roda o loop enquanto tiver um registro no banco de dados.
            while(objSQL.resSet.next()){
            	
            	qtde = objSQL.resSet.getInt("count");
            
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, 
        			"Erro ao consultar a quantidade de Formas de Pagamento utilizas!", 
        			"Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método "
                		+ "VendaDAO.consultaQtdeFormasDePagamentoUtilizada()\n"+e.getMessage());
            }
         }
		return qtde;
	}
	
	
	
	
	/**
	 * Consulta o próximo ID válido para o cadastro de uma nova venda.
	 */
	public int consultaProximoIDVenda(){
		
		int novoID = 0;
		String sql = "SELECT last_value from sequence_vendas";
		
		try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            while(objSQL.resSet.next()){
            	
            	novoID = objSQL.resSet.getInt("last_value");
            
            }                      
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, 
        			"Erro ao gerar novo ID da Venda!", 
        			"Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método "
                		+ "VendaDAO.consultaProximoIDVenda()\n"+e.getMessage());
            }
        }
		return (novoID + 1);
	}
	
	
	
	
	
	
	/**
	 * Consulta todas as vendas de determinada data.
	 * 
	 * @param data 
	 * @return Um List de objetos Venda.
	 */
	public List<Venda>getVendasPorData(Date data){
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		String strdata = formato.format(data);
		return consulta(CONSULTA_TABELA_INTEIRA + " WHERE data = '"+strdata+"'");
	}
	
	
	
	
	
	
	/**
	 * Consulta as vendas registradas no banco de dados.
	 * 
	 * Exige como parâmetro uma String com a instrução SQL desejada para executar a consulta.
	 */
	@Override
	public List<Venda> consulta(String sql) {
		
		List<Venda> listaDeVendas = new ArrayList<Venda> ();
		Venda objVenda = null;	
		
        try {
            //Abre uma nova conexão com o banco de dados.
            objSQL.abreConexao();
            // Deixa a String com o scipt sql pré-compilada.
            objSQL.preStm = objSQL.conn.prepareStatement(sql);
            //Abre o objeto ResultSet para fazer uma consulta.
            objSQL.resSet = objSQL.preStm.executeQuery();

            // Roda o loop enquanto tiver um registro no banco de dados.
            while(objSQL.resSet.next()){
            	
            	// Cria um novo objeto Venda.
            	objVenda = new Venda();

            	// Pega somente o código da forma de pagamento.
            	FormaDePagamento formPag = new FormaDePagamento();
            	formPag.setCodigo(objSQL.resSet.getInt("forma_pagamento"));
            	
            	objVenda.setCodigo(objSQL.resSet.getInt("codigo"));
            	objVenda.setData(objSQL.resSet.getString("data"));
            	objVenda.setHora(objSQL.resSet.getString("hora"));
            	objVenda.setTotal(objSQL.resSet.getDouble("total"));
            	objVenda.setFormaDePagamento(formPag);
            	
            	listaDeVendas.add(objVenda);
            }
        } catch (SQLException e) {
           
        	JOptionPane.showMessageDialog(null, "Erro ao consultar lista de Vendas!", "Erro", JOptionPane.ERROR_MESSAGE);
        	
        }finally{
            try {
                objSQL.fechaConexoes();
            } catch (SQLException e) {
                System.out.println("\nErro ao fechar conexões no método VendaDAO.consulta()\n"+e.getMessage());
            }
         }	
		return listaDeVendas;
	}

	
	
	
	
	@Override
	public boolean atualiza(Venda obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean exclui(int codigo) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
	
	
	
}
