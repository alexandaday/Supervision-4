package uk.ac.cam.apd44;

public class WonState implements NotifyState{
	
	public void doOutput(String info,int info2){
		System.out.println("Well done, you guessed "+info+" in "+info2+ " guesses");
	}
	
}
