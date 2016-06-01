package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Created by luke on 2016-04-20.
 */

public class TbsMenu extends TextButton.TextButtonStyle {
    Skin skin = new Skin();
    TextureAtlas buttonAtlas;

    public TbsMenu() {
        BitmapFont font = new BitmapFont(Gdx.files.internal("fonts/8bit.fnt"));
        font.getData().setScale(2, 2);
        skin.add("fonts/8bit.fnt", font);
        buttonAtlas = new TextureAtlas(Gdx.files.internal("button/MenuButton.pack"));
        skin.addRegions(buttonAtlas);
        this.up = skin.getDrawable("MenuButtonUp");
        this.down = skin.getDrawable("MenuButtonDown");
        this.checked = skin.getDrawable("MenuButtonUp");
        this.font = skin.getFont("fonts/8bit.fnt");
    }
}