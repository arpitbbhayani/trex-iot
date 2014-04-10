package com.trex.app.TRexSoccer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class TRexSoccer implements ApplicationListener {

    OrthographicCamera camera;
    SpriteBatch spriteBatch;

    Texture playerTeam1 , playerTeam2;
    Array<Rectangle> team1 , team2;

    @Override
    public void create() {

        // Loading Images
        playerTeam1 = new Texture(Gdx.files.internal("soccer/playerteam1.png"));
        playerTeam1 = new Texture(Gdx.files.internal("soccer/playerteam2.png"));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        spriteBatch = new SpriteBatch();

        spawnTeams();

    }

    @Override
    public void dispose() {
        playerTeam1.dispose();
        playerTeam2.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.6f, 1f, 0.6f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
            for(Rectangle player: team1) {
                spriteBatch.draw(playerTeam1, player.x, player.y);
            }
            for(Rectangle player: team2) {
                spriteBatch.draw(playerTeam2, player.x, player.y);
            }
        spriteBatch.end();


        /*if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();


        Iterator<Rectangle> iter = raindrops.iterator();
        while(iter.hasNext()) {
            Rectangle raindrop = iter.next();
            raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
            if(raindrop.y + 64 < 0) iter.remove();

            if(raindrop.overlaps(bucket)) {
                dropSound.play();
                iter.remove();
            }

        }*/

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


    private void spawnTeams() {

        for ( int i = 0 ; i < 11 ; i++ ) {
            Rectangle player = new Rectangle();
            player.x = MathUtils.random(0, 800 - 64);
            player.y = MathUtils.random(0, 480 - 64);
            player.width = 8;
            player.height = 8;
            team1.add(player);
        }

        for ( int i = 0 ; i < 11 ; i++ ) {
            Rectangle player = new Rectangle();
            player.x = MathUtils.random(0, 800 - 64);
            player.y = MathUtils.random(0, 480 - 64);
            player.width = 8;
            player.height = 8;
            team2.add(player);
        }

    }
}
