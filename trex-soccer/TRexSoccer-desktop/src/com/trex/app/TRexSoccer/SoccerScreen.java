package com.trex.app.TRexSoccer;

/**
 * Created by devilo on 11/4/14.
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class SoccerScreen {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "TRexSoccer";
        cfg.width = 800;
        cfg.height = 480;


        TRexSoccer tRexSoccer = new TRexSoccer();
        DataThread dataThread = new DataThread();
        dataThread.addObserver(tRexSoccer);

        Thread t = new Thread(dataThread);
        t.start();

        new LwjglApplication(tRexSoccer, cfg);


    }
}
