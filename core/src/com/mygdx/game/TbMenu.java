package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by luke on 2016-04-20.
 */

public class TbMenu extends TextButton {
    String sText;

    public TbMenu(String text, TextButtonStyle tbs) {
        super(text, tbs);
        sText = text;
        this.setSize(200, 80);
        this.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                System.out.println(sText);
            }
        });
    }
}