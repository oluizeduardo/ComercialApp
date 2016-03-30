/*
 * "O objetivo de uma interface em Orientação a Objetos é definir um conjunto 
 * de comportamentos (métodos) que devem obrigatoriamente ser implementados pelas 
 * classes que utilizam a interface. Diferente da herança uma classe Java pode 
 * implementar n interfaces."
 */
package model.database;

import java.util.List;

/**
 * Interface onde estão reunidas as ações básicas a serem executadas
 * em um banco de dados. A classe que implementar esta interface deverá
 * reescrever os métodos e montar toda a sua estrutura. Deve ser implementada 
 * em toda classe DAO (Data Access Objetct).<p>
 * 
 * Esta não é uma interface comum, ela é uma interface genérica, onde pode ser 
 * atribuida ao seu parâmetro qualquer outra classe e, seus métodos abstratos funcionarão 
 * da mesma forma em todas as classes em que for implementada. Observando a 
 * assinatura dos métodos já é possível saber do que cada um se trata, note também
 * que após o nome da interface, entre os sinais de maior e menor está uma letra,
 * esta letra está sendo usada apenas como uma referência para o uso dos métodos.
 * Portanto, esses métodos funcionarão da mesma forma em qualquer classe, sem a
 * necessidade de ter um parâmetro específico.<p>
 * 
 * <strong>Resumindo:</strong><br>
 * Uma vez entendido o propósito de cada método, basta apenas passar como referência
 * o tipo de classe que terá seus objetos manipulados. Por exemplo: se quiser cadastrar 
 * ou atualizar um objeto do tipo <code>Usuario</code>, passe <code>Usuario</code>
 * como referência quando for implementar esta interface em uma classe, se quiser 
 * cadastrar ou atualizar funcionários, passe <code>Funcionario</code> como referência. 
 * Só lembrando que a classe que servirá de referência já deve estar criada e, não é necessário 
 * declarar um objeto quando for passar a referência na interface, basta apenas colocar o nome 
 * da classe que usuará nos parâmetros dos métodos herdados. Sendo assim, a referência <code>C</code>
 * será substituida pela classe que for passada como parâmtro na hora da implementação da interface.
 * 
 * @param <C> O nome da classe que substituirá o <code>C</code> nos métodos genéricos.
 * @author  Luiz Eduardo Costa
 * @version 1.0,  05/01/2014
 * @since   JDK 1.2
 */
public interface AcoesBancoDeDados<C> {
      
    
    /**
     * Método de cadastro, dentro dele vai todo o código de conexão e cadastro 
     * dos dados de um objeto, esse objeto será do tipo que for passado como 
     * referência na hora da implementação da interface.<p>
     * 
     * O parâmetro desse método será um objeto da classe que foi atribuida à interface
     * quando ela foi implementada. Note que seu parâmetro é um objeto do tipo <code>C</code>,
     * porém <code>C</code> não é uma classe existente no projeto, é apenas uma referência.
     * Quando uma classe existente for passada como referência, então o parâmetro do
     * método passa a ser um objeto do tipo desta nova classe referenciada.
     * 
     * @param obj <code>C</code> - Onde C é apenas uma referência de classe.
     * @return <code>true</code> ou <code>false</code>, caso tenha conseguido
     * cadastrar novo registro ou não.
     */
    boolean cadastra(C obj);
    /**
     * Método que realiza as consultas ao banco de dados, exige como parâmetro
     * uma <code>String</code> que será a instrução SQL a ser executada no banco.
     * Retorna uma lista de objetos do tipo que foi passado como referência da hora
     * da implementação da interface.
     * 
     * @param sql A <code>String</code> que será executada para fazer o cadastro.
     * @return Um objeto <code>List</code> do tipo que foi passado como referência 
     * na hora da implementação da interface.
     */
    List<C> consulta(String sql);
    /**
     * Método que realiza a atualização de um registro no banco de dados.
     * Exige como parâmetro um objeto do tipo que foi passado como referência 
     * na hora da implementação da interface. O método atualiza o registro inteiro,
     * portanto o objeto passado como parâmetro deve ter todos os seus atributos
     * preenchidos, será executa uma instrução SQL com todos os dados desse objeto.
     * 
     * @param obj <code>C</code> - Onde C é apenas uma referência de classe.
     * @return <code>true</code> caso a execução da atualização tenha ocorrido corretamente.
     * <code>false</code> caso haja algum problema na execução da atualização.
     */
    boolean atualiza(C obj);
    /**
     * Método que exclui um determinado registro no banco de dados, o registro
     * deletado será o que possuir o código passado como parâmetro para o método.
     * Executa a instrução e retorna um <code>boolean</code> com o status dessa operação.
     * 
     * @param codigo <code>int</code> O código do registro que será excluido.
     * @return <code>true</code> se a operação foi executada com sucesso, 
     * <code>false</code> se ocorreu alguma irregularidade na hora da exicução dos caomandos.
     */
    boolean exclui(int codigo);
}
