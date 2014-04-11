package com.trex.app.TRexSoccer.ofnouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by devilo on 10/4/14.
 */
public class Splash implements Screen {

    private Sprite splash;
    private Texture splashTexture;
    private SpriteBatch spriteBatch;

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.6f, 1f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
            splash.draw(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();

        splashTexture = new Texture(Gdx.files.internal("soccer/splash.png"));
        splash = new Sprite(splashTexture);
        splash.setSize(Gdx.graphics.getWidth() , Gdx.graphics.getHeight());
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
