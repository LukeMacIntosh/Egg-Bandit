package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Character {
    public Rectangle recHB;
    Sprite sprGengar;
    Texture txrReg, txrFlip;
    float fGravity;
    public boolean isGrounded = false;
    public int nSpeed = 300, nLeniency;
    int nHei = 1080, nWid = 1920;


    public Character() {
        txrReg = new Texture("gengar.png");
        txrFlip = new Texture("gengarf.png");
        sprGengar = new Sprite(txrReg, 0, 0, 128, 128);
        sprGengar.setSize(nWid/10, nWid/10);
        nLeniency = 30;
        recHB = new Rectangle(0f, 0f, sprGengar.getWidth() - nLeniency,
                sprGengar.getHeight() - nLeniency);
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
        }
        else if (type == 2) {
            setPosition(x, recHB.y);
        }
        else if (type == 3) {
            x -= sprGengar.getWidth() - nLeniency;
            setPosition(x, recHB.y);
        }
        else if (type == 4) {
            y -= sprGengar.getHeight();
            setPosition(recHB.x, y);
        }
    }

    public void update(float delta) {
        //fGravity = gravity strength
        fGravity -= (40 * delta);
        recHB.y += fGravity;
        sprGengar.setPosition(recHB.x - (nLeniency / 2), recHB.y - (nLeniency / 2));
    }

    public void setPosition(float x, float y) {
        recHB.x = x;
        recHB.y = y;
        sprGengar.setPosition(x - (nLeniency / 2), y - (nLeniency / 2));
    }

    public void moveLeft(float delta) {
        recHB.x -= (nSpeed * delta);
        sprGengar.setPosition(recHB.x - (nLeniency / 2), recHB.y - (nLeniency / 2));
    }

    public void moveRight(float delta) {
        recHB.x += (nSpeed * delta);
        sprGengar.setPosition(recHB.x - (nLeniency / 2), recHB.y - (nLeniency / 2));
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
        fGravity = 33;
        isGrounded = false;
        nSpeed = 500; //air speed
    }
}