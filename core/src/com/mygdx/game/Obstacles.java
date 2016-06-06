package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacles {
    public static int nMelons = 0;
    Rectangle recMelonBox;
    public Sprite sprMelon;
    Texture txrBird, txrMelon;
    Music mMeloncollected, mBirdhit;
    int nBirdStatus, nMelonStatus, nRan, nLowRange, nHighRange;
    int nHei = 1080, nWid = 1920, nBirdLen, nMelonLen;
    float fX, fY;
    public Array<Sprite> asprBird;
    public Array<Rectangle> arecBird;
    public boolean isGrabable = true;
    public float fTimer, fTimer2;

    public Obstacles() {
        //hitbox reduction amount
        nBirdLen = 125;
        nMelonLen = 100;

        mMeloncollected = Gdx.audio.newMusic(Gdx.files.internal("sounds/meloncollected.wav"));
        mBirdhit = Gdx.audio.newMusic(Gdx.files.internal("sounds/birdhit.wav"));
        txrMelon = new Texture("obstacles/watermelon1.png");
        sprMelon = new Sprite(txrMelon, 0, 0, 512, 512);
        sprMelon.setSize(256, 256);
        recMelonBox = new Rectangle(0f, 0f, sprMelon.getWidth() - 200, sprMelon.getHeight() - 200);
        //recMelonBox.x = (int) Math.floor(Math.random() * (nWid - sprMelon.getWidth() + 1));
        recMelonBox.x = nWid / 2 - sprMelon.getWidth() / 2 + nMelonLen;
        recMelonBox.y = nHei * 3 / 4 + nMelonLen;
        sprMelon.setPosition(recMelonBox.x - nMelonLen, recMelonBox.y - nMelonLen);

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
        sprMelon.draw(batch);
        for (int i = 0; i < 4; i++) {
            fTimer+=Gdx.graphics.getDeltaTime();
            //control Bird speed up to certain point
            if (nMelons < 7) {
                asprBird.get(i).translateX((2 * nMelons) + 1);
            }
            else {
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

        //Melon collision
        if (nMelonStatus == 0 && recMelonBox.overlaps(r) && isGrabable == true) {
            //0 = not touching, -1 = touching
            fTimer2 = 0;
            System.out.println("collision + 1");
            isGrabable = false;
            nMelonStatus = -1;
/*            nRan = (int) Math.floor(Math.random() * (nWid - sprMelon.getWidth() + 1));
            sprMelon.setX(nRan);
            recMelonBox.setX(nRan);*/
            mMeloncollected.play();
            nMelons++;
        } else {
            nMelonStatus = 0;
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
}