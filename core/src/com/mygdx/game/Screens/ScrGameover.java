package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.Obstacle;
import com.mygdx.game.TbMenu;
import com.mygdx.game.TbsMenu;

/**
 * Created by luke on 2016-04-20.
 */
public class ScrGameover implements Screen, InputProcessor {
    Main main;
    TbsMenu tbsMenu;
    TbMenu tbMenu, tbPlay;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName, bmScore, bmHighscore;
    Obstacle obstacle;
    int nHei = 1080, nWid = 1920;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    float fGameworldWidth = 1920, fGameworldHeight = 1080;
    OrthographicCamera ocCam;
    Viewport viewport;

    public ScrGameover(Main Main) {  //Referencing the main class.
        this.main = Main;
    }

    public void show() {
        backgroundTexture = new Texture("gameover.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        obstacle = new Obstacle();
        screenName = new BitmapFont(Gdx.files.internal("label.fnt"));
        screenName.getData().setScale(2, 2);
        screenName.setColor(Color.BLACK);
        bmScore = new BitmapFont(Gdx.files.internal("label.fnt"));
        bmHighscore = new BitmapFont(Gdx.files.internal("label.fnt"));
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        viewport = new FillViewport(fGameworldWidth, fGameworldHeight, ocCam);
        viewport.apply();
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
        tbMenu = new TbMenu("MENU", tbsMenu);
        tbPlay = new TbMenu("RETRY", tbsMenu);
        tbMenu.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 4);
        tbMenu.setY(Gdx.graphics.getHeight()/9);
        tbMenu.setX(Gdx.graphics.getWidth()/2);
        tbPlay.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 4);
        tbPlay.setY(Gdx.graphics.getHeight()/9);
        tbPlay.setX(Gdx.graphics.getWidth()/6);
        stage.addActor(tbMenu);
        stage.addActor(tbPlay);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
        btnPlayListener();
    }

    public void renderBackground() {
        backgroundSprite.draw(batch);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1); //black background.
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ocCam.update();
        batch.setProjectionMatrix(ocCam.combined);
        batch.begin();
        renderBackground();
        screenName.draw(batch, "GAMEOVER", 525, 1000);
        bmScore.draw(batch, "SCORE: " + Integer.toString(obstacle.nHearts), 800, 750);
        bmHighscore.draw(batch, "HIGH-SCORE: " + "999", 625, 500);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.currentState = Main.GameState.MENU;
                main.updateState();
                obstacle.nHearts = 0;
            }
        });
    }

    public void btnPlayListener() {
        tbPlay.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.currentState = Main.GameState.PLAY;
                main.updateState();
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
        bmScore.dispose();
        bmHighscore.dispose();
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