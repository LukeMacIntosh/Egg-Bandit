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
    public Sprite sprSpike, sprMelon;
    Texture txrSpike, txrMelon;
    Music mMeloncollected, mSpikehit;
    int nSpikeStatus, nMelonStatus, nRan, nLowRange, nHighRange;
    int nHei = 1080, nWid = 1920, nSpikeLen, nMelonLen;
    float fX, fY;
    public Array<Sprite> asprSpike;
    public Array<Rectangle> arecSpike;
    public boolean isGrabable = true;
    public float fTimer, fTimer2;
    Texture txrBirds;
    TextureRegion[] trAnimFrames2;
    Animation aniBirds;
   SpriteBatch sbAnim;

    public Obstacles() {
        //hitbox reduction amount
        nSpikeLen = 125;
        nMelonLen = 100;

        mMeloncollected = Gdx.audio.newMusic(Gdx.files.internal("sounds/meloncollected.wav"));
        mSpikehit = Gdx.audio.newMusic(Gdx.files.internal("sounds/spikehit.wav"));
        txrMelon = new Texture("obstacles/watermelon1.png");
        sprMelon = new Sprite(txrMelon, 0, 0, 512, 512);
        sprMelon.setSize(256, 256);
        recMelonBox = new Rectangle(0f, 0f, sprMelon.getWidth() - 200, sprMelon.getHeight() - 200);
        //recMelonBox.x = (int) Math.floor(Math.random() * (nWid - sprMelon.getWidth() + 1));
        recMelonBox.x = nWid / 2 - sprMelon.getWidth() / 2 + nMelonLen;
        recMelonBox.y = nHei * 3 / 4 + nMelonLen;
        sprMelon.setPosition(recMelonBox.x - nMelonLen, recMelonBox.y - nMelonLen);

        //range for random spike y coordinates
        nLowRange = nWid / 6;
        nHighRange = nHei * 5 / 6;

        txrSpike = new Texture("obstacles/spikeball.png");

        asprSpike = new Array<Sprite>(false, 4);
        for (int i = 0; i < 4; i++) {
            asprSpike.add(new Sprite(txrSpike, 0, 0, 128, 128));
            asprSpike.get(i).setSize(nWid / 18, nWid / 18);
            fX = i * (nWid + txrSpike.getWidth()) / 4;
            fY = (int) Math.floor(Math.random() * (nHighRange - nLowRange + 1) + nLowRange);
            asprSpike.get(i).setPosition(fX, fY);
            //nVelo = (int) Math.floor(Math.random() * 5);
        }

        arecSpike = new Array<Rectangle>(false, 4);
        for (int i = 0; i < 4; i++) {
            arecSpike.add(new Rectangle());
            arecSpike.get(i).setSize(128 - (nSpikeLen), 128 - (nSpikeLen));
        }
    }

    public void draw(SpriteBatch batch) {
        sprMelon.draw(batch);
        for (int i = 0; i < 4; i++) {
            fTimer+=Gdx.graphics.getDeltaTime();
            /*batch.draw(aniBirds.getKeyFrame(fTimer, true), arecSpike.get(i).getX(),
                    arecSpike.get(i).getY());*/

            //asprSpike.get(i).draw(batch);
            //control spike speed up to certain point
            if (nMelons < 7) {
                asprSpike.get(i).translateX((2 * nMelons) + 1);
            }
            else {
                asprSpike.get(i).translateX(14);
            }
            arecSpike.get(i).setX(asprSpike.get(i).getX() + (nSpikeLen / 2));
            arecSpike.get(i).setY(asprSpike.get(i).getY() + (nSpikeLen / 2));
            if (asprSpike.get(i).getX() > nWid) {
                asprSpike.get(i).setX(-txrSpike.getWidth());
                asprSpike.get(i).setY((int) Math.floor(Math.random() * (nHighRange - nLowRange + 1) + nLowRange));
            }
        }
    }

    public boolean bounds(Rectangle r) {
        //spike collision
        if (nSpikeStatus == 0 && isRecTouch(r)) {
            System.out.println("collision - 1");
            nSpikeStatus = -1;
            mSpikehit.play();
            return true;
        } else if (!isRecTouch(r)) {
            nSpikeStatus = 0;
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
            if (r.overlaps(arecSpike.get(i))) {
                return true;
            }
        }
        return false;
    }
}