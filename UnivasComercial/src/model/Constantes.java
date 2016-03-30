package model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

public interface Constantes {

	/**Cor de background do frame interno.*/
    //public static final Color COR_INTERNO_BACKGROUND = new Color(191, 203, 235);
	static final Color COR_PAINEL_BACKGROUND = new Color(135	,206,	235);
	/**A cor que será aplicada no Backgroud dos campos.*/
	static final Color COR_CAMPO_BACKGROUD = new Color(255, 250, 220);
    /**A cor que será usada para destacar alguns componentes.*/
    static final Color COR_DE_DESTAQUE_CAMPOS = Color.RED; 
    /**A cor padr�o das bordas dos componentes dos frames.*/
    static final Color COR_PADRAO_BORDA   = Color.BLACK;
    /**Constante que cria a forma de uma borda funda nos componente.*/
    static final Border BORDA_FUNDA       = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,null,new Color(0, 0, 0));
    /**Constante que cria a forma de uma borda gravada no componente.*/
    static final Border BORDA_GRAVADA     = BorderFactory.createEtchedBorder();
    /**Constante que padroniza a altura do campos de texto, caixas de combinação, rótulos, etc.*/
    static final int    ALTURA_CAMPO      = 25;
    /**Constante que padroniza a largura dos objetos <code>JTextField</code> e <code>JFormatedTextField</code>.*/
    static final int    LARGURA_CAMPO     = 120;
    /**Constante que padroniza a largura dos objetos <code>JLabel</code>.*/
    static final int    LARGURA_ROTULO     = 70;
    /**Nome da fonte que será utilizada no software.*/
    static final String NOME_FONTE = "Comic Sans";
    /**Constante que padroniza a fonte dos objetos <code>JTextField</code>.*/
    static final Font   FONTE_CAMPO_TEXTO = new Font(NOME_FONTE, Font.PLAIN, 14);
    /**Constante que padroniza a fonte aplicadas nos objetos <code>JLabel</code>.*/
    static final Font   FONTE_ROTULO      = new Font(NOME_FONTE, Font.BOLD, 18);
    /**Cursor de mão (Hand Cursor).*/
    static final Cursor HAND_CURSOR       = new Cursor(Cursor.HAND_CURSOR);    
    /**Caminho do icone Voltar.*/
    static final String ICONE_VOLTAR = "src/resources/voltar.png";
    /**Caminho do icone Caixa.*/
    static final String ICONE_CAIXA    = "src/resources/caixa.png";
    /**Caminho do icone Incluir.*/
    static final String ICONE_INCLUIR = "src/resources/incluir.png";
    /**Caminho do icone Pesquisar.*/
    static final String ICONE_PESQUISAR = "src/resources/pesquisar.png";
    /**Caminho do icone Recarregar.*/
    static final String ICONE_RECARREGAR= "src/resources/recarregar.png";
    /**Caminho do icone Salvar.*/
    static final String ICONE_SALVAR = "src/resources/salvar.png";
    /**Caminho do icone Finalizar.*/
    static final String ICONE_FINALIZAR = "src/resources/finalizar.png";
    
    
    
    
    
    
}
