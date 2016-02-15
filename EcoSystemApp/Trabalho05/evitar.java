package Trabalho05;

import java.util.concurrent.Semaphore;
import RobotLego.RobotLego;

public class evitar extends Thread {
	Semaphore lock = new Semaphore(0);
	private final int S_2 = RobotLego.S_2;
//	protected RobotLego robo = new RobotLego();
	private wrapper robo = new wrapper();
	vaguear vag;
	fugir f;
	boolean vagWasSuspended, fugWasSuspended = false;
	boolean suspend, stop = false;

	evitar(wrapper robo, vaguear v, fugir f) {
		this.robo = robo;
		this.vag = v;
		this.f = f;
	}

	public void run() {
		robo.SetSensorTouch( S_2 );
		int s,num = 0;
		for(;;) {
			if(stop){
				stop = false;
				break;
			}
			if(suspend){
				try {
					lock.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			s = robo.Sensor(S_2);
				
			if (s == 1) {
				System.out.println("Bateu " + num);
				++num;
				if(vag.isAlive()){
					vagWasSuspended = vag.mySuspend();
				}
				if(f.isAlive()){
					fugWasSuspended = f.mySuspend();
				}
				robo.Parar(true, false);
				System.out.println("PAROU!");
				robo.Reta(-20, false);
				robo.Parar(false, false);
				robo.CurvarEsquerda(0, 90, false);
				robo.Parar(false, false);
				if(vag.isAlive() && vagWasSuspended){
					vag.myResume();
				}
				if(f.isAlive() && fugWasSuspended){
					f.myResume();
				}
			}
			
			try {
				Thread.sleep(200);
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
