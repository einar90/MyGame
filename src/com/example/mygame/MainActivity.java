package com.example.mygame;

import android.os.Bundle;
import android.app.Activity;
import sheep.game.Game;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// Create the game.
	Game game = new Game(this, null);
	// Push the main state.
	game.pushState(new GameState(game.getResources()));
	// View the game.
	setContentView(game);
	}

    
    
}
