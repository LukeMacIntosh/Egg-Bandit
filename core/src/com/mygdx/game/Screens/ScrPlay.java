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
import com.mygdx.game.Obstacle;
import com.mygdx.game.TbMenu;
import com.mygdx.game.TbsMenu;
import com.mygdx.game.Character;

/**
 * Created by luke on 2016-04-20.
 */
public class ScrPlay implements Screen, InputProcessor {
    Main main;
    TbsMenu tbsMenu;
    Stage stage;
    SpriteBatch sbChar;
    BitmapFont bmLives;
    OrthographicCamera ocCam;
    Rectangle recBDown, recBUp, recBLeft, recBRight;
    Character character;
    Obstacle obstacle;
    int picID = 1, gdxW, gdxH;
    boolean isTouchingL = false, isTouchingR = false;

    public ScrPlay(Main Main) {  //Referencing the main class.
        this.main = Main;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("1");
        if (screenX < gdxW / 2 && screenY > gdxH / 2) {
            isTouchingL = true;
            System.out.println("left");
        }
        if (screenX > gdxW / 2 && screenY > gdxH / 2) {
            isTouchingR = true;
            System.out.println("right");
        }
        if (screenY < gdxH / 2 && character.isGrounded == true) {
            character.jump();
        }
        return false;
    }

    public void show() {
        gdxW = Gdx.graphics.getWidth();
        gdxH = Gdx.graphics.getHeight();
        stage = new Stage();
        tbsMenu = new TbsMenu();
        sbChar = new SpriteBatch();
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        character = new Character();
        obstacle = new Obstacle();
        System.out.println(gdxW + "" + gdxH);
        bmLives = new BitmapFont(Gdx.files.internal("label.fnt"));
        character.setPosition(gdxW/2 - 64, 11);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(this);
        recBDown = new Rectangle(0, 0, gdxW, 10);
        recBUp = new Rectangle(0, gdxH - 10, gdxW, 10);
        recBLeft = new Rectangle(0, 0, 10, gdxH);
        recBRight = new Rectangle(gdxW - 10, 0, 10, gdxH);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sbChar.setProjectionMatrix(ocCam.combined);
        sbChar.begin();
        if (picID == 1) {
            character.draw1(sbChar);
        } else {
            character.draw2(sbChar);
        }
        obstacle.draw(sbChar);
        bmLives.draw(sbChar, "Hearts collected " + Integer.toString(obstacle.nLives), gdxW - 1200, gdxH - 50);
        sbChar.end();

        //updates
        character.update(Gdx.graphics.getDeltaTime());

        //Boundaries
        if (character.bounds(recBDown) == 1) {
            character.action(1, 0, 10);
            character.isGrounded = true;
            character.nSpeed = 300; //ground speed
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                character.jump();
            }
        } else if (character.bounds(recBUp) == 1) {
            character.action(4, 0, Gdx.graphics.getHeight() - 10);
        }

        if (character.bounds(recBLeft) == 1) {
            character.action(2, 10, 0);
        }

        if (character.bounds(recBRight) == 1) {
            character.action(3, Gdx.graphics.getWidth() - 10, 0);
        }

        //Android Controlss
        if (isTouchingL) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            picID = 2;
        }
        if (isTouchingR) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            picID = 1;
        }


        //Keyboard Controls
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            picID = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            picID = 1;
        }

        //spike hit detection, goes to gameover screen
        if (obstacle.bounds(character.recHB)) {
            main.currentState = Main.GameState.OVER;
            main.updateState();
        }
        sbChar.begin();
        sbChar.end();
        stage.act();
        stage.draw();
    }

    @Override
    public void dispose() {
        sbChar.dispose();
        bmLives.dispose();
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
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isTouchingL = false;
        isTouchingR = false;
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