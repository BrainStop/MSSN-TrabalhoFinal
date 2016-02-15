package Trabalho05;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class GravarConfig{
	interfaceRobo roboGui;
	String filename;
	OutputStream output;

	public GravarConfig(interfaceRobo roboGui) {
		this.roboGui = roboGui;
	}
	public void run(){
		Properties prop = new Properties();
		OutputStream output = null;		
			
		try{
			output = new FileOutputStream("bin//"+roboGui.nome+".properties");
				
			//set properties value
			prop.setProperty("NomeDoRobot", roboGui.nome);
			prop.setProperty("OffsetEsq", String.valueOf(roboGui.offsetEsquerdo));
			prop.setProperty("OffsetDir", String.valueOf(roboGui.offsetDireito));
			prop.setProperty("Raio", String.valueOf(roboGui.raio));
			prop.setProperty("Angulo", String.valueOf(roboGui.angulo));
			prop.setProperty("Distancia", String.valueOf(roboGui.distancia));
			prop.setProperty("bVaguear", String.valueOf(roboGui.radioVaguear.isSelected()));
			prop.setProperty("bEvitar", String.valueOf(roboGui.radioEvitar.isSelected()));
			prop.setProperty("bFugir", String.valueOf(roboGui.radioFugir.isSelected()));
			prop.setProperty("cDebug", String.valueOf(roboGui.isDebugAtivo));			
				
			//save properties
			prop.store(output, null);
				
		}catch(IOException io){
			io.printStackTrace();
			
		}finally{
			if(output != null){
				try{
					output.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}	
}

