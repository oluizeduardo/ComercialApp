package model;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;

import view.FrameInicial;

public class Runner {

	public static void main(String[] args) {
		
		UIManager.put("ToolTip.foreground", new ColorUIResource(Color.blue));
        UIManager.put("ToolTip.background", new ColorUIResource(Color.white));
        UIManager.put("ToolTip.font", new FontUIResource(new Font("Arial", Font.PLAIN, 15)));
        
        // Set look and feel
      /*  try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());// Nativo do S.O.
            
            // Lista de Look And Feel da biblioteca JTattoo
            // http://www.jtattoo.net/index.html
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");// Gostei muito!
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");// Gostei muito!
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");// Amarelão
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.graphite.GraphiteLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");// Botões Arredondados. Gostei!!
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
        	//UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");// Parece papel. Gostei!
        	
        	
        } catch (ClassNotFoundException | InstantiationException | 
        		IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
       */ 
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Mostra os Look & Feel do sistema operacional.
            	System.out.println(info.toString());
            	
            	if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) { }
        
         
        
        
        
        
        FrameInicial frame = new FrameInicial();
        frame.pack();
        frame.setVisible(true);
        
        
	}
	
}
