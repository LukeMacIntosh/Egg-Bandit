package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.Obstacle;
import com.mygdx.game.TbMenu;
import com.mygdx.game.TbsMenu;
import com.mygdx.game.Character;
import com.mygdx.game.Screens.ScrGameover;

/**
 * Created by luke on 2016-04-20.
 */
public class ScrPlay implements Screen, InputProcessor {
    Main main;
    TbsMenu tbsMenu;
    Stage stage;
    SpriteBatch sbChar;
    BitmapFont bmHearts;
    OrthographicCamera ocCam;
    Rectangle recBDown, recBUp, recBLeft, recBRight;
    Character character;
    Obstacle obstacle;
    Viewport viewport;
    float fGameworldWidth = 1920, fGameworldHeight = 1080;
    int picID = 1, nHei = 1080, nWid = 1920;
    boolean isTouchingL = false, isTouchingR = false;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    float fTimer;

    public ScrPlay(Main Main) {  //Referencing the main class.
        this.main = Main;
    }

    public void show() {
        backgroundTexture = new Texture("Play.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        sbChar = new SpriteBatch();
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        viewport = new FillViewport(fGameworldWidth, fGameworldHeight, ocCam); // We tried FitViewport and StretchViewport and they didnt seem to work, but Fill did !
        viewport.apply();
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
        character = new Character();
        obstacle = new Obstacle();
        bmHearts = new BitmapFont(Gdx.files.internal("label.fnt"));
        bmHearts.setColor(Color.RED);
        character.setPosition(nWid / 2 - 64, 11);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(this);
        recBDown = new Rectangle(0, 0, nWid, 10);
        recBUp = new Rectangle(0, nHei - 10, nWid, 10);
        recBLeft = new Rectangle(0, 0, 10, nHei);
        recBRight = new Rectangle(nWid - 10, 0, 10, nHei);
        fTimer = 0;
        obstacle.nHearts = 0;
        isTouchingL = false;
        isTouchingR = false;
    }

    public void renderBackground() {
        backgroundSprite.draw(sbChar);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        sbChar.setProjectionMatrix(ocCam.combined);
        ocCam.update();
        sbChar.begin();
        renderBackground();

        fTimer += Gdx.graphics.getDeltaTime();

        if (picID == 1) {
            character.draw1(sbChar);
        } else {
            character.draw2(sbChar);
        }
        obstacle.draw(sbChar);
        bmHearts.draw(sbChar, "Hearts collected: " + Integer.toString(obstacle.nHearts), nWid - 1300, nHei - 50);
        sbChar.end();

        //updates
        character.update(Gdx.graphics.getDeltaTime());

        //Boundaries
        if (character.bounds(recBDown) == 1) {
            if (fTimer < 3) {
                character.action(1, 0, 10);
                character.isGrounded = true;
                character.nSpeed = nWid / 4;
            }
            else {
                character.jump();
            }
        } else if (character.bounds(recBUp) == 1) {
            character.action(4, 0, nHei - 10);
        }

        if (character.bounds(recBLeft) == 1) {
            character.action(2, 10, 0);
        }

        if (character.bounds(recBRight) == 1) {
            character.action(3, nWid - 10, 0);
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
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("1");
        if (screenX < nWid / 2) {
            isTouchingR = false;
            isTouchingL = true;
            System.out.println("left");
        }
        if (screenX > nWid / 2) {
            isTouchingL = false;
            isTouchingR = true;
            System.out.println("right");
        }
        return false;
    }

    @Override
    public void dispose() {
        sbChar.dispose();
        bmHearts.dispose();
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
        if (screenX < nWid / 2) {
            isTouchingL = false;
            System.out.println("left stop");
        }
        if (screenX > nWid / 2) {
            isTouchingR = false;
            System.out.println("right stop");
        }
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