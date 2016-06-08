package com.mygdx.eggbandit;

import com.badlogic.gdx.math.Rectangle;

public class Character {
    public Rectangle recHB;
    float fGravity;
    public boolean isGrounded = false;
    public int nSpeed = 300;
    int nHei = 1080, nWid = 1920;


    public Character() {
        recHB = new Rectangle(0f, 0f, nWid / 10,
                nHei / 10);
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
        if (type == 2) {
            setPosition(x, recHB.y);
        } else if (type == 3) {
            x -= recHB.getWidth();
            setPosition(x, recHB.y);
        } else if (type == 4) {
            y -= recHB.getHeight();
            setPosition(recHB.x, y);
        }
    }

    public void update(float delta) {
        //fGravity = gravity strength
        fGravity -= (45 * delta);
        recHB.y += fGravity;
    }

    public void setPosition(float x, float y) {
        recHB.x = x;
        recHB.y = y;
    }

    public void moveLeft(float delta) {
        recHB.x -= (nSpeed * delta);
    }

    public void moveRight(float delta) {
        recHB.x += (nSpeed * delta);
    }

    public void jump() {
        //add jump height
        fGravity = 36;
        isGrounded = false;
        nSpeed = 600; //air speed
    }
}