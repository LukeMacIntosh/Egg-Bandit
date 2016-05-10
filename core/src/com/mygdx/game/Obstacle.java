package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Obstacle {
    public static int nHearts = 0;
    Rectangle recHeartBox;
    Sprite sprSpike, sprHeart;
    Texture txrSpike, txrHeart;
    int nSpikeStatus, nHeartStatus, nRan, nLowRange, nHighRange;
    int nHei = 1080, nWid = 1920, nLeniency;
    float fX, fY;
    Array<Sprite> asprSpike;
    Array<Rectangle> arecSpike;
    public int nHighscore;

    public Obstacle() {
        txrHeart = new Texture("heart.png");
        sprHeart = new Sprite(txrHeart, 0, 0, 128, 128);
        sprHeart.setSize(nWid / 15, nWid / 15);
        recHeartBox = new Rectangle(0f, 0f, sprHeart.getWidth(), sprHeart.getHeight());
        recHeartBox.x = (int) Math.floor(Math.random() * (nWid - sprHeart.getWidth() + 1));
        recHeartBox.y = nHei * 1 / 4;
        sprHeart.setPosition(recHeartBox.x, recHeartBox.y);
        nLeniency = 150;

        //range for random spike y coordinates
        nLowRange = nWid / 6;
        nHighRange = nHei * 5 / 6;

        txrSpike = new Texture("spikeball.png");

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
            arecSpike.get(i).setSize(asprSpike.get(i).getWidth() - (nLeniency), asprSpike.get(i).getHeight() - (nLeniency));
        }
    }

    public void draw(SpriteBatch batch) {
        sprHeart.draw(batch);

        for (int i = 0; i < 4; i++) {
            asprSpike.get(i).draw(batch);
            asprSpike.get(i).translateX(4);
            arecSpike.get(i).setX(asprSpike.get(i).getX() + (nLeniency / 2));
            arecSpike.get(i).setY(asprSpike.get(i).getY() + (nLeniency / 2));
            if (asprSpike.get(i).getX() > nWid) {
                asprSpike.get(i).setX(-txrSpike.getWidth());
                asprSpike.get(i).setY((int) Math.floor(Math.random() * (nHighRange - nLowRange + 1) + nLowRange));
            }
        }
    }

    public boolean bounds(Rectangle r) {
        //spike collision
        if (nSpikeStatus == 0 && isRecTouch(r)) {
            //0 = not touching, -1 = touching
            System.out.println("collision - 1");
            nSpikeStatus = -1;
            //nRan = (int) Math.floor(Math.random() * (Gdx.graphics.getWidth() - sprSpike.getWidth() + 1));
            //sprSpike.setX(nRan);
            //recSpikeBox.setX(nRan);
            return true;
        } else if (!isRecTouch(r)) {
            nSpikeStatus = 0;
        }

        //heart collision
        if (nHeartStatus == 0 && recHeartBox.overlaps(r)) {
            //0 = not touching, -1 = touching
            System.out.println("collision + 1");
            nHeartStatus = -1;
            nRan = (int) Math.floor(Math.random() * (nWid - sprHeart.getWidth() + 1));
            sprHeart.setX(nRan);
            recHeartBox.setX(nRan);
            nHearts++;
        } else {
            nHeartStatus = 0;
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