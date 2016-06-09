package com.mygdx.eggbandit;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacles implements Screen, InputProcessor{
    public static int nNests = 0;
    Rectangle recNestBox;
    public Sprite sprNest;
    Texture txrBird, txrNest;
    Sound mNestcollected, mBirdhit;
    int nBirdStatus, nNestStatus, nRan, nLowRange, nHighRange;
    int nHei = 1080, nWid = 1920, nBirdLen, nNestLen;
    float fX, fY;
    public Array<Sprite> asprBird;
    public Array<Rectangle> arecBird;
    public boolean isGrabable = true, isAnimate = false;
    public float fTimer, fTimer2;

    public Obstacles() {
        //hitbox reduction amount
        nBirdLen = 125;
        nNestLen = 0;

        mNestcollected = Gdx.audio.newSound(Gdx.files.internal("sounds/nestcollected.mp3"));
        mBirdhit = Gdx.audio.newSound(Gdx.files.internal("sounds/birdhit.wav"));
        txrNest = new Texture("obstacles/nestsprite.png");
        sprNest = new Sprite(txrNest, 0, 0, 200, 110);
        //sprNest.setSize(256, 256);
        recNestBox = new Rectangle(0f, 0f, sprNest.getWidth(), sprNest.getHeight());
        //recNestBox.x = (int) Math.floor(Math.random() * (nWid - sprNest.getWidth() + 1));
        recNestBox.x = nWid / 2 - sprNest.getWidth() / 2 + nNestLen;
        recNestBox.y = nHei * 3 / 4 + 50;
        sprNest.setPosition(recNestBox.x - nNestLen, recNestBox.y - nNestLen);

        //range for random Bird y coordinates
        nLowRange = nWid / 6;
        nHighRange = nHei * 5 / 6;

        txrBird = new Texture("obstacles/spikeball.png");

        asprBird = new Array<Sprite>(false, 4);
        for (int i = 0; i < 4; i++) {
            asprBird.add(new Sprite(txrBird, 0, 0, 128, 128));
            asprBird.get(i).setSize(nWid / 18, nWid / 18);
            fX = i * (nWid + txrBird.getWidth()) / 4;
            fY = (int) Math.floor(Math.random() * (nHighRange - nLowRange + 1) + nLowRange);
            asprBird.get(i).setPosition(fX, fY);
            //nVelo = (int) Math.floor(Math.random() * 5);
        }

        arecBird = new Array<Rectangle>(false, 4);
        for (int i = 0; i < 4; i++) {
            arecBird.add(new Rectangle());
            arecBird.get(i).setSize(128 - (nBirdLen), 128 - (nBirdLen));
        }
    }

    public void draw(SpriteBatch batch) {
        sprNest.draw(batch);
        for (int i = 0; i < 4; i++) {
            fTimer += Gdx.graphics.getDeltaTime();
            //control Bird speed up to certain point
            if (nNests < 7) {
                asprBird.get(i).translateX((2 * nNests) + 1);
            } else {
                asprBird.get(i).translateX(14);
            }
            arecBird.get(i).setX(asprBird.get(i).getX() + (nBirdLen / 2));
            arecBird.get(i).setY(asprBird.get(i).getY() + (nBirdLen / 2));
            if (asprBird.get(i).getX() > nWid) {
                asprBird.get(i).setX(-txrBird.getWidth());
                asprBird.get(i).setY((int) Math.floor(Math.random() * (nHighRange - nLowRange + 1) + nLowRange));
            }
        }
    }

    public boolean bounds(Rectangle r) {
        //Bird collision
        if (nBirdStatus == 0 && isRecTouch(r)) {
            System.out.println("collision - 1");
            nBirdStatus = -1;
            mBirdhit.play();
            return true;
        } else if (!isRecTouch(r)) {
            nBirdStatus = 0;
        }

        //Nest collision
        if (nNestStatus == 0 && recNestBox.overlaps(r) && isGrabable == true) {
            //0 = not touching, -1 = touching
            fTimer2 = 0;
            System.out.println("collision + 1");
            isGrabable = false;
            nNestStatus = -1;
/*            nRan = (int) Math.floor(Math.random() * (nWid - sprNest.getWidth() + 1));
            sprNest.setX(nRan);
            recNestBox.setX(nRan);*/
            mNestcollected.play();
            nNests++;
            isAnimate = true;
        } else {
            nNestStatus = 0;
        }
        return false;
    }

    boolean isRecTouch(Rectangle r) {
        for (int i = 0; i < 4; i++) {
            if (r.overlaps(arecBird.get(i))) {
                return true;
            }
        }
        return false;
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

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
    public void dispose() {
        mBirdhit.dispose();
        mNestcollected.dispose();
    }
}