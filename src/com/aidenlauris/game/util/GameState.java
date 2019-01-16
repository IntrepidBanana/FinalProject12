package com.aidenlauris.game.util;

public class GameState {
	
	public enum State{
		MAINMENU, PLAY, SETTINGS, DEATHMENU
	}
	
	private State myState = State.PLAY;
	
	
	public State getCurrentState(){
		return myState;	
	}
	
	public void changeState(State state){
		myState = state;
	}
	
	
	
	
	
	
}
