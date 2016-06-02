package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Main;
import com.mygdx.game.Obstacles;
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
    BitmapFont bmMelons, bmStart;
    OrthographicCamera ocCam;
    Rectangle recBDown, recBUp, recBLeft, recBRight;
    Character character;
    Obstacles obstacles;
    Viewport viewport;
    Music mJump, mInGameSong;
    float fGameworldWidth = 1920, fGameworldHeight = 1080;
    int picID = 1, nHei = 1080, nWid = 1920, nTouchCount = 0, nCounter = 0;
    boolean isTouchingL = false, isTouchingR = false;
    public static Texture backgroundTexture;
    public static Sprite backgroundSprite;
    float fTimer, faniTimer;
    Texture txrBirds;
    TextureRegion[] trAnimFrames2;
    Animation[] araniBirds;

    public ScrPlay(Main main) {  //Referencing the main class.
        this.main = main;
    }

    public void show() {
        backgroundTexture = new Texture("backgrounds/Play.jpg");
        backgroundSprite = new Sprite(backgroundTexture);
        mJump = Gdx.audio.newMusic(Gdx.files.internal("sounds/jump.mp3"));
        mInGameSong = Gdx.audio.newMusic(Gdx.files.internal("music/ingame1v2.mp3"));
        mInGameSong.play();
        mInGameSong.setLooping(true);
        stage = new Stage();
        tbsMenu = new TbsMenu();
        sbChar = new SpriteBatch();
        ocCam = new OrthographicCamera();
        ocCam.setToOrtho(false);
        viewport = new FillViewport(fGameworldWidth, fGameworldHeight, ocCam);
        // We tried FitViewport and StretchViewport and they didn't seem to work, but Fill did !
        viewport.apply();
        ocCam.position.set(fGameworldWidth / 2, fGameworldHeight / 2, 0);
        character = new Character();
        obstacles = new Obstacles();
        bmMelons = new BitmapFont(Gdx.files.internal("fonts/8bit.fnt"));
        bmMelons.getData().setScale(3, 3);
        bmMelons.setColor(Color.RED);
        bmStart = new BitmapFont(Gdx.files.internal("fonts/8bit.fnt"));
        bmStart.getData().setScale(10, 10);
        character.setPosition(nWid / 2 - 64, 11);
        Gdx.input.setInputProcessor(stage);
        Gdx.input.setInputProcessor(this);
        recBDown = new Rectangle(0, 0, nWid, 10);
        recBUp = new Rectangle(0, nHei - 10, nWid, 10);
        recBLeft = new Rectangle(0, 0, 10, nHei);
        recBRight = new Rectangle(nWid - 10, 0, 10, nHei);
        fTimer = 3;
        obstacles.nMelons = 0;
        isTouchingL = false;
        isTouchingR = false;
        nTouchCount = 0;

        //birds animation
        araniBirds = new Animation[4];
        txrBirds = new Texture("obstacles/birdssheet.png");
        TextureRegion[][] trAnimTemp2 = TextureRegion.split(txrBirds, 128, 128);
        trAnimFrames2 = new TextureRegion[2];
        int index2 = 0;

        for (int j = 0; j < 2; j++) {
            trAnimFrames2[index2++] = trAnimTemp2[0][j];
        }
        for (int x = 0; x < 4; x++) {
            araniBirds[x] = new Animation(1f / 6f, trAnimFrames2);
        }
    }

    public void renderBackground() {
        sbChar.begin();
        backgroundSprite.draw(sbChar);
        sbChar.end();
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
        renderBackground();

        faniTimer += Gdx.graphics.getDeltaTime();
        fTimer -= Gdx.graphics.getDeltaTime();
        obstacles.fTimer2 += Gdx.graphics.getDeltaTime();

        sbChar.begin();
        if (fTimer > 0 && fTimer < 1) {
            nCounter = 1;
        } else if (fTimer > 1 && fTimer < 2) {
            nCounter = 2;
        } else if (fTimer > 2 && fTimer < 3) {
            nCounter = 3;
        } else if (fTimer < 0) {
            nCounter = -1;
        }
        if (picID == 1) {
            character.draw1(sbChar);
        } else {
            character.draw2(sbChar);
        }
        obstacles.draw(sbChar);
        bmMelons.draw(sbChar, Integer.toString(obstacles.nMelons), nWid - 200, nHei - 50);
        if (fTimer > 0 && fTimer < 3) {
            bmStart.draw(sbChar, Integer.toString(nCounter), 800, 800);
        } else {
            //don't draw
        }
        sbChar.end();

        //updates
        character.update(Gdx.graphics.getDeltaTime());

        //Boundaries
        if (character.bounds(recBDown) == 1) {
            if (fTimer > 0 && fTimer < 3) {
                character.action(1, 0, 10);
                character.isGrounded = true;
                character.nSpeed = nWid / 4;
            } else {
                character.jump();
                mJump.play();
                obstacles.isGrabable = true;
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

        //Android Controls
        if (Gdx.input.isTouched(0) && Gdx.input.getX(0) < Gdx.graphics.getWidth() / 2
                || Gdx.input.isTouched(1) && Gdx.input.getX(1) < Gdx.graphics.getWidth() / 2) {
            character.moveLeft(Gdx.graphics.getDeltaTime());
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            } else {
                picID = 2;
            }
        }
        if (Gdx.input.isTouched(0) && Gdx.input.getX(0) > Gdx.graphics.getWidth() / 2
                || Gdx.input.isTouched(1) && Gdx.input.getX(1) > Gdx.graphics.getWidth() / 2) {
            character.moveRight(Gdx.graphics.getDeltaTime());
            if (Gdx.input.isTouched(0) && Gdx.input.isTouched(1)) {
            } else {
                picID = 1;
            }
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

        //Bird hit detection, goes to game over screen
        if (obstacles.bounds(character.recHB)) {
            if (obstacles.nMelons > main.prefsSCORE.getInteger("Latest Highscore")) {
                main.prefsSCORE.putInteger("Latest Highscore", obstacles.nMelons);
            }
            // hurt sound
            main.currentState = Main.GameState.OVER;
            main.updateState();
            mInGameSong.stop();
        }
        // if statement for the Melon sound
        stage.act();
        stage.draw();
        main.prefsSCORE.flush();

        //Animate
        sbChar.begin();
        for (int i = 0; i < 4; i++) {
            sbChar.draw(araniBirds[i].getKeyFrame(faniTimer, true), obstacles.arecBird.get(i).getX() - 64,
                    obstacles.arecBird.get(i).getY() - 64);
        }
        sbChar.end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public void dispose() {
        sbChar.dispose();
        bmMelons.dispose();
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