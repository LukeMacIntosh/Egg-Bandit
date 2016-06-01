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
import com.mygdx.game.Obstacles;
import com.mygdx.game.TbMenu;
import com.mygdx.game.TbsMenu;

/**
 * Created by luke on 2016-04-20.
 */
public class ScrInstruct implements Screen, InputProcessor {
    Main main;
    TbsMenu tbsMenu;
    TbMenu tbMenu;
    Stage stage;
    SpriteBatch batch;
    BitmapFont screenName;
    Obstacles obstacles;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    float fGameworldWidth = 1920, fGameworldHeight = 1080;
    OrthographicCamera ocCam;
    Viewport viewport;

    public ScrInstruct(Main main) {  //Referencing the main class.
        this.main = main;
    }

    public void show() {
        backgroundTexture = new Texture("backgrounds/instructions.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        batch = new SpriteBatch();
        obstacles = new Obstacles();
        screenName = new BitmapFont(Gdx.files.internal("fonts/8bit.fnt"));
        //screenName.getData().setScale(2, 2);
        screenName.setColor(Color.WHITE);
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        viewport = new FillViewport(fGameworldWidth, fGameworldHeight, ocCam);
        viewport.apply();
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
        tbMenu = new TbMenu("BACK", tbsMenu);
        tbMenu.setSize(Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 4);
        tbMenu.setY(Gdx.graphics.getHeight() / 18);
        tbMenu.setX(Gdx.graphics.getWidth() / 14);
        stage.addActor(tbMenu);
        Gdx.input.setInputProcessor(stage);
        btnMenuListener();
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
        screenName.draw(batch, "INSTRUCTIONS SCREEN", 525, 1000);
        batch.end();
        stage.act();
        stage.draw();
    }

    public void btnMenuListener() {
        tbMenu.addListener(new ChangeListener() {
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                main.currentState = Main.GameState.MENU;
                main.updateState();
                obstacles.nMelons = 0;
            }
        });
    }

    @Override
    public void dispose() {
        batch.dispose();
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