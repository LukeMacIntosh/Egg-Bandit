package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.Main;
import com.mygdx.game.TbMenu;
import com.mygdx.game.TbsMenu;
import com.mygdx.game.Character;

/**
 * Created by luke on 2016-04-20.
 */
public class ScrPlay implements Screen, InputProcessor {
    Main main;
    TbsMenu tbsMenu;
    TbMenu tbMenu, tbGameover;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    OrthographicCamera ocCam;
    Rectangle recBDown, recBUp, recBLeft, recBRight;
    SpriteBatch bChar;
    Character character;
    int picID = 1;

    public ScrPlay(Main Main) {  //Referencing the main class.
        this.main = Main;
    }

    public void show() {
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        bChar = new SpriteBatch();
        character = new Character();
        screenName = new BitmapFont(Gdx.files.internal("label.fnt"));
        tbMenu = new TbMenu("BACK", tbsMenu);
        tbGameover = new TbMenu("GAMEOVER", tbsMenu);
        tbMenu.setY(825);
        tbMenu.setX(0);
        tbGameover.setY(825);
        tbGameover.setX(1200);
        character.setPosition(200, 100);
        stage.addActor(tbMenu);
        stage.addActor(tbGameover);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnGameoverListener();
    }

    public void btnGameoverListener() {
        tbGameover.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.currentState = Main.GameState.OVER;
                main.updateState();
            }
        });
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.currentState = Main.GameState.MENU;
                main.updateState();
            }
        });
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        bChar.setProjectionMatrix(ocCam.combined);
        bChar.begin();
        if (picID == 1) {
            character.draw1(bChar);
        } else {
            character.draw2(bChar);
        }
        bChar.end();
        recBDown = new Rectangle(0, 0, Gdx.graphics.getWidth(), 10);
        recBUp = new Rectangle(0, Gdx.graphics.getHeight() - 10, Gdx.graphics.getWidth(), 10);
        recBLeft = new Rectangle(0, 0, 10, Gdx.graphics.getHeight());
        recBRight = new Rectangle(Gdx.graphics.getWidth() - 10, 0, 10, Gdx.graphics.getHeight());
        //Updates
        character.update(Gdx.graphics.getDeltaTime());
        //Boundaries
        if (character.bounds(recBDown) == 1) {
            character.action(1, 0, 10);
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                character.jump();
            }
        }
        if (character.bounds(recBUp) == 1) {
            character.action(4, 0, Gdx.graphics.getHeight() - 10);
        }

        if (character.bounds(recBLeft) == 1) {
            character.action(2, 10, 0);
        }

        if (character.bounds(recBRight) == 1) {
            character.action(3, Gdx.graphics.getWidth() - 10, 0);
        }

        //Controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            picID = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            picID = 1;
        }
        batch.begin();
        screenName.draw(batch, "This is the PLAY screen", 400, 700);
        batch.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        bChar.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}