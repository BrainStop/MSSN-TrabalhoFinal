package Trabalho05;

//import RobotLego.RobotLego;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class vaguear extends Thread{
	
	Semaphore lock = new Semaphore(0);
//	protected RobotLego robo = new RobotLego();
	private wrapper robo = new wrapper();
	Random aletorio = new Random();
	boolean suspend, stop = false;
	
	public vaguear(wrapper robo) {
		this.robo = robo;
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
	
	public void run() {
		int selector = aletorio.nextInt(3);
		int aux = -1;
		for(;;) {
			if(stop){
				stop = false;
				break;
			}
			if(suspend) {
				try {
					lock.acquire();
					continue;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			while(selector == aux) {
				selector = aletorio.nextInt(3);
			}
			aux = selector;
			System.out.println(selector);
			switch(selector) {
			case 0:
				robo.Reta(30, true);
				robo.Parar(false, true);
				break;
			
			case 1:
				robo.CurvarEsquerda(30, 45, true);
				robo.Parar(false, true);
				break;
			
			case 2:
				robo.CurvarDireita(30, 45, true);
				robo.Parar(false, true);
				break;
			}
			
			try {
				Thread.sleep(2500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
