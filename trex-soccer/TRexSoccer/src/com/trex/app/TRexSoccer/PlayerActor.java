package com.trex.app.TRexSoccer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by devilo on 11/4/14.
 */
public class PlayerActor extends Actor {

    Texture texture = null;
    float actorX = 0, actorY = 0;
    public boolean started = false;

    public PlayerActor() {
        texture = new Texture(Gdx.files.internal("soccer/playerteam1.png"));

        setBounds(actorX,actorY,texture.getWidth(),texture.getHeight());
        addListener(new InputListener(){
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                ((PlayerActor)event.getTarget()).started = true;
                return true;
            }
        });

    }

    @Override
    public void draw(Batch batch, float alpha){
        batch.draw(texture,actorX,actorY);
    }

    @Override
    public void act(float delta){
        if(started){
            actorX += 5;
        }
    }

}
