package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Character {
    Rectangle recHB;
    Sprite sprGengar;
    Texture txrReg, txrFlip;
    float fGravity;

    public Character() {
        txrReg = new Texture("gengar.png");
        txrFlip = new Texture("gengarf.png");
        sprGengar = new Sprite(txrReg, 0, 0, 128, 128);
        sprGengar.setSize(Gdx.graphics.getWidth() / 5, Gdx.graphics.getWidth() / 5);
        recHB = new Rectangle(0f, 0f, sprGengar.getWidth(), sprGengar.getHeight());
        this.setPosition(0, 0);
        fGravity = 0;
    }

    public int bounds(Rectangle r) {
        if (recHB.overlaps(r)) {
            return 1;
        }
        return -1;
    }

    public void action(int type, float x, float y) {
        if (type == 1) {
            setPosition(recHB.x, y);
            fGravity = 0;
        } else if (type == 2) {
            setPosition(x, recHB.y);
        } else if (type == 3) {
            x -= sprGengar.getWidth();
            setPosition(x, recHB.y);
        } else if (type == 4) {
            y -= sprGengar.getHeight();
            setPosition(recHB.x, y);
        }
    }

    public void update(float delta) {
        //fGravity = gravity strength
        fGravity -= (40 * delta);
        recHB.y += fGravity;
        sprGengar.setPosition(recHB.x, recHB.y);
    }

    public void setPosition(float x, float y) {
        recHB.x = x;
        recHB.y = y;
        sprGengar.setPosition(x, y);
    }

    public void moveLeft(float delta) {
        recHB.x -= (200 * delta);
        sprGengar.setPosition(recHB.x, recHB.y);
    }

    public void moveRight(float delta) {
        recHB.x += (200 * delta);
        sprGengar.setPosition(recHB.x, recHB.y);
    }

    public void draw1(SpriteBatch batch) {
        sprGengar.setTexture(txrReg);
        sprGengar.draw(batch);
    }

    public void draw2(SpriteBatch batch) {
        sprGengar.setTexture(txrFlip);
        sprGengar.draw(batch);
    }

    public void jump() {
        //add jump height
        fGravity = 15;
    }
}