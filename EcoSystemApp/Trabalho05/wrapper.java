package Trabalho05;

import java.util.ArrayList;

import RobotLego.RobotLego;

public class wrapper {
	
	private RobotLego robo = new RobotLego();
	private ArrayList<String> commandList = new ArrayList<String>();
	private boolean gravarp = false;
	
	public void gravarPercurso(boolean b) {
		gravarp = true;
	}
	
	public boolean OpenNXT(String nome) {
		return robo.OpenNXT(nome);
	}
	
	public void CloseNXT() {
		robo.CloseNXT();
	}
	
	public void Reta(int distancia, boolean save) {
		if(save && gravarp){
			commandList.add("Reta;"+distancia);
			System.out.println(save+", "+gravarp);
		}
		robo.Reta(distancia);
	}
	
	public void Parar(boolean b, boolean save) {
		if(save && gravarp)
			commandList.add("Parar;"+b);
		robo.Parar(b);
	}
	
	public void CurvarEsquerda(int raio, int angulo, boolean save) {
		if(save && gravarp)
			commandList.add("CurvaEsquerda;"+raio+";"+angulo);
		robo.CurvarEsquerda(raio, angulo);
	}
	
	public void CurvarDireita(int raio, int angulo, boolean save) {
		if(gravarp)
			commandList.add("CurvarDireita;"+raio+";"+angulo);
		robo.CurvarDireita(raio, angulo);
	}
	
	public void AjustarVME(int offsetEsquerdo) {
		if(gravarp)
			commandList.add("AjustarVME;"+offsetEsquerdo);
		robo.AjustarVME(offsetEsquerdo);
	}
	
	public void AjustarVMD(int offsetDireito) {
		if(gravarp)
			commandList.add("AjustarVMD;"+offsetDireito);
		robo.AjustarVMD(offsetDireito);
	}
	
	public void SetSpeed(int speed) {
		if(gravarp)
			commandList.add("SetSpeed;"+speed);
		robo.SetSpeed(50);
	}
	
	public void SetSensorTouch( int sensor) {
		robo.SetSensorTouch(sensor);
	}
	
	public int Sensor(int sensor) {
		return robo.Sensor(sensor);
	}
	
	public void SetSensorLowspeed(int speed) {
		robo.SetSensorLowspeed(speed);
	}
	
	public int SensorUS (int sensor) {
		return robo.SensorUS(sensor);
	}
	
	public void reproduzir() {
		boolean wasGravarp = gravarp;
		gravarp = false;
		System.out.println("lenght "+ commandList.size());
		for (int i = 0; i < commandList.size(); i++) {
			String[] command = commandList.get(i).split(";");
			System.out.println(command[0]);
			if(command[0].equals("Reta")) {
				robo.Reta(Integer.parseInt(command[1]));
				System.out.println(Integer.parseInt(command[1]));
			}
			
			if(command[0].equals("Parar"))
				robo.Parar(Boolean.parseBoolean(command[1]));
			
			if(command[0].equals("CurvaEsquerda"))
				robo.CurvarEsquerda(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
			
			if(command[0].equals("CurvarDireita"))
				robo.CurvarDireita(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
			
			if(command[0].equals("SetSpeed"))
				robo.SetSpeed(Integer.parseInt(command[1]));
			
		}
		gravarp = wasGravarp;
	}
	
	public void reproduzirInverso() {
		System.out.println("inverso");
		boolean wasGravarp = gravarp;
		gravarp = false;
		System.out.println("lenght " + commandList.size());
		for (int i = commandList.size() - 1; i >= 0; i--) {
			System.out.println();
			String[] command = commandList.get(i).split(";");
			System.out.println(command[0]);
			if (command[0].equals("Reta")) {
				robo.Reta(-Integer.parseInt(command[1]));
				System.out.println(Integer.parseInt(command[1]));
			}
			if (command[0].equals("Parar"))
				robo.Parar(Boolean.parseBoolean(command[1]));
			if (command[0].equals("CurvaEsquerda"))
				robo.CurvarDireita(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
			if (command[0].equals("CurvarDireita"))
				robo.CurvarEsquerda(Integer.parseInt(command[1]), Integer.parseInt(command[2]));
			if (command[0].equals("SetSpeed"))
				robo.SetSpeed(Integer.parseInt(command[1]));
		}
		robo.Parar(false);
		gravarp = wasGravarp;
	}
	
	public void apagarPercurso() {
		commandList.clear();
	}
	
}
