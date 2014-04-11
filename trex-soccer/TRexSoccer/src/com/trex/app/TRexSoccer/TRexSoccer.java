package com.trex.app.TRexSoccer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Logger;

public class TRexSoccer implements ApplicationListener, Observer {

    OrthographicCamera camera;
    SpriteBatch spriteBatch;

    Texture groundTexture;
    Texture playerTeam1 , playerTeam2;
    Texture logoTeam1 , logoTeam2;

    Array<Rectangle> team1 , team2;

    long lastUpdateTime;

    int[] arrayx = { 1 , 1 , -1 , -1 };
    int[] arrayy = { 1 , -1 , 1 , -1 };

    BitmapFont font = null;

    JsonValue metaData;
    static JsonValue playerData;

    String team1Name;
    String team2Name;

    @Override
    public void create() {

        DataThread dataThread = new DataThread();
        dataThread.addObserver(this);
        Thread t = new Thread(dataThread);

        t.start();

        spriteBatch = new SpriteBatch();

        team1 = new Array<Rectangle>();
        team2 = new Array<Rectangle>();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        groundTexture = new Texture(Gdx.files.internal("soccer/ground.png"));

        font = new BitmapFont(Gdx.files.internal("fonts/calibri.fnt"));

        // Loading Images
        playerTeam1 = new Texture(Gdx.files.internal("soccer/playerteam1.png"));
        playerTeam2 = new Texture(Gdx.files.internal("soccer/playerteam2.png"));

        try {
            this.metaData = DataThread.getMetaData();
            team1Name = this.metaData.get("t1").getString("N");
            team2Name = this.metaData.get("t2").getString("N");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        try {
            this.playerData = DataThread.getData();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Asserting meta data is present.

        logoTeam1 = new Texture(Gdx.files.internal("soccer/arsenal_icon.png"));
        logoTeam2 = new Texture(Gdx.files.internal("soccer/chelsea_icon.png"));

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
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

            spriteBatch.draw(groundTexture , 0 , 0);
            spriteBatch.draw(logoTeam1 , 320 , 440);
            spriteBatch.draw(logoTeam2 , 450 , 440);

            font.draw(spriteBatch, "3-2", 380, 470);

            for(Rectangle player: team1) {
                spriteBatch.draw(playerTeam1, player.x, player.y);
            }
            for(Rectangle player: team2) {
                spriteBatch.draw(playerTeam2, player.x, player.y);
            }

        spriteBatch.end();

        updatePositions();

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


    private synchronized void updatePositions() {
        lastUpdateTime = TimeUtils.nanoTime();
        int i = 1;

        for(Rectangle player: team1) {
            player.x = this.playerData.get("t1" + i).getInt("la");
            player.y = this.playerData.get("t1" + i).getInt("lo");

            i++;
        }

        i = 1;
        for(Rectangle player: team2) {
            player.x = this.playerData.get("t2" + i).getInt("la");
            player.y = this.playerData.get("t2" + i).getInt("lo");
            i++;
        }
    }

    private void spawnTeams() {

        for ( int i = 1 ; i <= 11 ; i++ ) {
            Rectangle player = new Rectangle();
            player.width = 8;
            player.height = 8;
            player.x = this.playerData.get("t1" + i).getInt("la");
            player.y = this.playerData.get("t1" + i).getInt("lo");

            team1.add(player);
        }

        for ( int i = 1 ; i <= 11 ; i++ ) {
            Rectangle player = new Rectangle();
            player.width = 8;
            player.height = 8;
            player.x = this.playerData.get("t2" + i).getInt("la");
            player.y = this.playerData.get("t2" + i).getInt("lo");
            team2.add(player);
        }

    }

    @Override
    public synchronized void update(Observable o, Object data) {

        this.playerData = (JsonValue) data;
        //System.out.println("New player data : " + this.playerData);


    }

}
