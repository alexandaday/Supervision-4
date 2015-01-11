package uk.ac.cam.apd44;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;


//Uses State Pattern
public class Main implements TextFileLoading,NotifyState{

	private String word;
	private String wordSoFar = "";
	
	private int guessLimit = 12;
	
	private int guesses = 0;
	
	private static Scanner userIn = new Scanner(System.in);
	
	private NotifyState wonState = new WonState();
	private NotifyState failedState = new FailedState();

	private NotifyState guessed = failedState;

	public static void main(String[] args){
		Main newGame = new Main();
		try{
			newGame.loadText();
			System.out.println("Welcome to hangman, you have "+newGame.guessLimit+" guesses of either letters or whole words");
			System.out.println("Difficulty level: English Degree");
			while (true){
				newGame.newGuess();
				if ((newGame.guesses < newGame.guessLimit) && (newGame.guessed == newGame.failedState)){
					newGame.displayInfo();
				}
				else{
					break;
				}
			}
			newGame.displayEnd();
		}
		catch(FileNotFoundException exception){
			System.out.println("Cannot find words.txt");
		}
		catch(IOException exception){
			System.out.println("Error reading file");
		}
	}

	public void loadText() throws FileNotFoundException,IOException{
		FileReader myReader = new FileReader("words.txt");
		BufferedReader myBReader = new BufferedReader(myReader);
		LinkedList<String> wordList = new LinkedList<String>();

		String line;

		while ((line = myBReader.readLine()) != null){
			if (line.length() > 3){
				wordList.add(line);
			}
		}

		myBReader.close();
		Random ranGen = new Random();
		int ranInt = ranGen.nextInt(wordList.size());
		
		word = (wordList.get(ranInt)).trim();
		
		for(int index = 0;index<word.length();index++){
			wordSoFar = wordSoFar +"-";
		}
		
		return;
	}
	
	public void newGuess(){
		guesses = guesses + 1;
		String guess = userIn.nextLine();

		if (guess.equals(word)){
			guessed = wonState;
			return;
		}
		if (guess.length() != 1){
			return;
		}
		
		String newWordSoFar = "";
		
		char guessChar = guess.charAt(0);
		
		for(int index = 0;index<word.length();index++){
			if (guessChar == word.charAt(index)){
				newWordSoFar = newWordSoFar + guessChar;
			}
			else {
				newWordSoFar = newWordSoFar + wordSoFar.charAt(index);
			}
		}
		wordSoFar = newWordSoFar;
		if (wordSoFar.equals(word)){
			guessed = wonState;
		}
	}
	
	public void displayInfo(){
		System.out.println("You have "+(guessLimit-guesses)+" guesses left");
		System.out.println(wordSoFar);
	}
	
	public void displayEnd(){
		doOutput(word,guesses);
		System.out.println("Enter 'q' to quit");
		String response = userIn.nextLine();
		if (!response.equals("q")){
			Main newGame = new Main();
			newGame.startNewGame();
		}
	}
	
	public void startNewGame(){
		main(new String[0]);
	}
	
	@Override
	public void doOutput(String info,int info2){
		this.guessed.doOutput(info,info2);
	}
}

interface TextFileLoading{
	public void loadText() throws FileNotFoundException,IOException;
}

interface NotifyState{
	public void doOutput(String info, int info2);
}
