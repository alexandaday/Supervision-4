package uk.ac.cam.apd44;

public class FailedState implements NotifyState{
	
	public void doOutput(String info,int info2){
		System.out.println("The word was "+info);
	}
	
}