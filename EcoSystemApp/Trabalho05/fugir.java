package Trabalho05;

import java.util.concurrent.Semaphore;

import RobotLego.RobotLego;

public class fugir extends Thread{
	Semaphore lock = new Semaphore(0);
	int distSensor;
	int distMax = 75;
	int distMin = 0;
	boolean suspend, stop = false;
	wrapper robo;
	private static final int S_1 = RobotLego.S_1;
	boolean vagWasSuspended = false;
	vaguear v;

	fugir(wrapper robo, vaguear v) {
		this.robo = robo;
		this.v = v;
	}

	public void run() {
		robo.SetSensorLowspeed(S_1);
		int distAux;
		for (;;) {
			if(stop){
				stop = false;
				break;
			}
			if(suspend){
				try {
					lock.acquire();
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			distAux = 0;
			for(int i = 0; i <4;++i){
				distAux += robo.SensorUS(S_1);
			}
			//distSensor = distAux/5;
			distSensor = robo.SensorUS(S_1);
			if (distSensor < distMax && distSensor >= distMin) {
				if(v.isAlive()){
					vagWasSuspended = v.mySuspend();
				}
				if(distSensor <= 30) robo.SetSpeed(100);
				else if(distSensor > 30 && distSensor <50) robo.SetSpeed(85);
				else robo.SetSpeed(75);
				System.out.println("Fugir: " + distSensor);
				//robo.SetSpeed(50 - (distSensor - 2));
				robo.Reta(20, true);
				//robo.SetSpeed(50);
				robo.Parar(false, true);
				distAux = 0;
				if(v.isAlive() && vagWasSuspended){
					v.myResume();
				}
			}
			
			 try {
				Thread.sleep(distSensor * 3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}
	public boolean mySuspend() {
		if(suspend == false) {
			suspend = true;
			return true;
		}
		else return false;
	}
	public void myResume() {
		suspend = false;
		lock.release();
		
	}

	public void myStop() {
		stop = true;
	}
}
