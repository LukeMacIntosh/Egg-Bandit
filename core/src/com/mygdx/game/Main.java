package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.mygdx.game.Screens.ScrInstruct;
import com.mygdx.game.Screens.ScrMenu;
import com.mygdx.game.Screens.ScrPlay;
import com.mygdx.game.Screens.ScrGameover;

/**
 * Created by luke on 2016-04-20.
 */

public class Main extends Game {
    public Preferences prefsSCORE;
    ScrMenu scrMenu;
    ScrPlay scrPlay;
    ScrGameover scrGameover;
    ScrInstruct scrInstruct;

    public enum GameState {
        MENU, PLAY, OVER, INSTRUCT
    }

    public GameState currentState;

    public void updateState() {
        if (currentState == GameState.MENU) {
            setScreen(scrMenu);
        } else if (currentState == GameState.PLAY) {
            setScreen(scrPlay);
        } else if (currentState == GameState.OVER) {
            setScreen(scrGameover);
        } else if (currentState == GameState.INSTRUCT) {
            setScreen(scrInstruct);
        }
    }

    @Override
    public void create() {
        prefsSCORE = Gdx.app.getPreferences("Latest Highscore");
        scrMenu = new ScrMenu(this);
        scrPlay = new ScrPlay(this);
        scrGameover = new ScrGameover(this);
        scrInstruct = new ScrInstruct(this);
        currentState = GameState.MENU;
        updateState();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
