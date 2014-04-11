package com.trex.app.TRexSoccer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by devilo on 11/4/14.
 */
public class TRexSoccerControlTeam1 implements ApplicationListener {

    private static final String USER_AGENT = "TRexIOT/1.0";

    OrthographicCamera camera;
    SpriteBatch spriteBatch;

    Texture groundTexture;
    Texture upImage , downImage, leftImage, rightImage;

    Rectangle up , down, left, right;

    Vector3 touchPos;

    @Override
    public void create() {


        touchPos = new Vector3();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        spriteBatch = new SpriteBatch();

        groundTexture = new Texture(Gdx.files.internal("soccer/ground.png"));
        upImage = new Texture(Gdx.files.internal("soccer/up.png"));
        downImage = new Texture(Gdx.files.internal("soccer/down.png"));
        leftImage = new Texture(Gdx.files.internal("soccer/left.png"));
        rightImage = new Texture(Gdx.files.internal("soccer/right.png"));


        up = new Rectangle();
        up.x = 270;
        up.y = 200;
        up.width = 256;
        up.height = 256;

        down = new Rectangle();
        down.x = 270;
        down.y = 0;
        down.width = 256;
        down.height = 256;

        left = new Rectangle();
        left.x = 0;
        left.y = 100;
        left.width = 256;
        left.height = 256;

        right = new Rectangle();
        right.x = 550;
        right.y = 100;
        right.width = 256;
        right.height = 256;

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();
            spriteBatch.draw(groundTexture , 0 , 0);
            spriteBatch.draw(upImage , up.x , up.y);
            spriteBatch.draw(downImage , down.x , down.y);
            spriteBatch.draw(leftImage , left.x , left.y);
            spriteBatch.draw(rightImage , right.x , right.y);
        spriteBatch.end();

        if(Gdx.input.isTouched()) {
            camera.unproject(touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (left.contains(touchPos.x, touchPos.y)){
                //Move your player to the left!
                try {
                    sendPost("LEFT");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (right.contains(touchPos.x, touchPos.y)){
                //Move your player to the right!
                try {
                    sendPost("right");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (up.contains(touchPos.x, touchPos.y)){
                //Move your player to the up!
                try {
                    sendPost("up");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if (down.contains(touchPos.x, touchPos.y)){
                //Move your player to the down!
                try {
                    sendPost("down");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
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


    private void sendPost(String direction) throws Exception {

        String urlString = "http://192.168.1.8:4567/update/t11/" + direction;

        URL url = new URL(urlString);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'GET' request to URL : " + url);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //System.out.println("Updated : " + response);



    }


}
