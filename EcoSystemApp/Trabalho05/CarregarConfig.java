package Trabalho05;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CarregarConfig {
	
	String[] valConfig;
	
	public String[] loadConfig(interfaceRobo roboGui, String filename){

		
		InputStream inputStream = null;

		try {
			
			valConfig = new String[10];
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream(filename);
 
			if (inputStream != null) {
				System.out.println("O Save foi carregado com sucesso");

				prop.load(inputStream);
			
			} else {
				System.out.println("A Save não existe");
				throw new FileNotFoundException("property file '" + filename + "' not found in the classpath");
			}

			valConfig[0] = prop.getProperty("NomeDoRobot");
			valConfig[1] = prop.getProperty("OffsetEsq");
			valConfig[2] = prop.getProperty("OffsetDir");
			valConfig[3] = prop.getProperty("Angulo");
			valConfig[4] = prop.getProperty("cDebug");
			valConfig[5] = prop.getProperty("Raio");
			valConfig[6] = prop.getProperty("Distancia");
			valConfig[7] = prop.getProperty("bVaguear");
			valConfig[8] = prop.getProperty("bEvitar");
			valConfig[9] = prop.getProperty("bFugir");

		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				if(inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return valConfig;
	}
	
	
	public static void main(String[] args) {

		CarregarConfig config = new CarregarConfig();
		config.loadConfig(null, "teste.properties");

		
	}

}
