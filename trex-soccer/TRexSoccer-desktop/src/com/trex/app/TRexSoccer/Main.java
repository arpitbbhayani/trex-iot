package com.trex.app.TRexSoccer;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "TRexSoccer";
		cfg.width = 800;
		cfg.height = 480;


        /*TRexSoccer tRexSoccer = new TRexSoccer();
        DataThread dataThread = new DataThread();
        dataThread.addObserver(tRexSoccer);

        Thread t = new Thread(dataThread);
        t.start();

        new LwjglApplication(tRexSoccer, cfg);*/

        new LwjglApplication(new TRexSoccerControlTeam1(), cfg);


	}
}
