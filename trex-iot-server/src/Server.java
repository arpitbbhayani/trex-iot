import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by devilo on 11/4/14.
 */
public class Server {

    int timeElapsed = 1;

    int t1score = 3;
    int t2score = 2;

    static int[] arrayx = {50,50,-50,-50, 10 , 20 , 50 , -20 , 30 , -10, -41 , 10};
    static int[] arrayy = {50,-50,50,-50 , -10 , -40 , 28 , -45 , 25 , -40 , 10};

    private JSONObject dataJson = null;
    private JSONObject dataScreenJson = null;

    public Server() {
        this.dataJson = new JSONObject();
        for ( int i = 1 ; i <= 11 ; i++ ) {
            dataJson.put("xt1" + i , 41.0);
            dataJson.put("yt1" + i , -81.0);
        }
        for ( int i = 1 ; i <= 11 ; i++ ) {
            dataJson.put("xt2" + i , 41.0);
            dataJson.put("yt2" + i , -81.0);
        }


        this.dataScreenJson = new JSONObject(this.dataJson);

        try {

            /*for ( int i = 1 ; i <= 11 ; i++ ) {
                double xold = (Double) dataScreenJson.get("xt1"+i);
                double yold = (Double) dataScreenJson.get("yt1"+i);

                dataScreenJson.put("xt1" + i , (int) this.getNewX(xold));
                dataScreenJson.put("yt1" + i , (int) this.getNewY(yold));
            }

            for ( int i = 1 ; i <= 11 ; i++ ) {
                double xold = (Double) dataScreenJson.get("xt2"+i);
                double yold = (Double) dataScreenJson.get("yt2"+i);

                dataScreenJson.put("xt2" + i , (int) this.getNewX(xold));
                dataScreenJson.put("yt2" + i , (int) this.getNewY(yold));

            }*/

            Random random = new Random();

            dataScreenJson.put("score" , t1score + "-" + t2score);

            for ( int i = 1 ; i <= 11 ; i++ ) {
                //double xold = (Double) dataScreenJson.get("xt1"+i);
                //double yold = (Double) dataScreenJson.get("yt1"+i);

                dataScreenJson.put("xt1" + (i) , (int) Math.abs(random.nextInt()) % 740);
                dataScreenJson.put("yt1" + (i) , (int) Math.abs(random.nextInt()) % 420);
            }

            for ( int i = 1 ; i <= 11 ; i++ ) {
                //double xold = (Double) dataScreenJson.get("xt2"+i);
                //double yold = (Double) dataScreenJson.get("yt2"+i);

                dataScreenJson.put("xt2" + (i) , (int) Math.abs(random.nextInt()) % 740);
                dataScreenJson.put("yt2" + (i) , (int) Math.abs(random.nextInt()) % 420);

            }

            dataScreenJson.put("xtb" , 400);
            dataScreenJson.put("ytb" , 240);


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String tempMetaData = "{" +
            "  \"SBL\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"SBR\" : {\"LA\":61.0 , \"LO\":-81.0},\n" +
            "  \"STL\" : {\"LA\":41.0 , \"LO\":-51.0},\n" +
            "  \"STR\" : {\"LA\":61.0 , \"LO\":-51.0},\n" +
            "  \"t1\" : {\"N\" : \"arsenal\" , \"I\" : 1},\n" +
            "  \"t2\" : {\"N\" : \"chelsea\" , \"I\" : 2}\n" +
            "}";

    double sblx = 41.0; double sbly = -81.0;
    double sbrx = 61.0; double sbry = -81.0;
    double stlx = 41.0; double stly = -51;
    double strx = 61.0; double stry = -51;

    double x1 = sblx; double y1 = sbly;
    double x2 = 40; double y2 = 10;

    double w1 = Math.abs(sbrx - sblx); double h1 = Math.abs(sbly - stly);
    double w2 = 740 - 40; double h2 = 420 - 10;

    /*
        Rectangle 1 has (x1, y1) origin and (w1, h1) for width and height, and
        Rectangle 2 has (x2, y2) origin and (w2, h2) for width and height, then

        Given point (x, y) in terms of Rectangle 1 coords, to convert it to Rectangle 2 coords:

        xNew = ((x-x1)/w1)*w2 + x2;
        yNew = ((y-y1)/h1)*h2 + y2;

     */

    private double getNewX(double x) {
        return (int) (((x-x1) * w2)/w1) + x2;
    }

    private double getNewY ( double y ) {
        return (((y-y1) * h2)/h1) + y2;
    }

    private double getOldX ( double x ) {
        return (((x-x2) * w1)/w2) + x1;
    }

    private double getOldY ( double y ) {
        return (((y-y2) * h1)/h2) + y1;
    }

    public static void main(String[] args) {

        final Server server = new Server();

        Spark.get(new Route("/meta") {

            @Override
            public Object handle(Request request, Response response) {
                return tempMetaData;
            }
        });

        Spark.get(new Route("/data") {

            @Override
            public Object handle(Request request, Response response) {
                Random random = new Random();

                if ( server.timeElapsed % 25  == 0 ) {
                    if ( server.timeElapsed % 10 == 0 )
                        server.t1score ++;
                    else
                        server.t2score ++;
                }
                server.timeElapsed ++;

                server.dataScreenJson.put( "score" , server.t1score + "-" + server.t2score);

                for ( int i = 1 ; i <= 11 ; i++ ) {

                    int xoff = arrayx[Math.abs(random.nextInt()) % arrayx.length];
                    int yoff = arrayy[Math.abs(random.nextInt()) % arrayy.length];
                    int oldx = (Integer) server.dataScreenJson.get("xt1" + i);
                    int oldy = (Integer) server.dataScreenJson.get("yt1" + i);

                    int newX = (int) oldx + xoff;
                    int newY = (int) oldy + yoff;

                    if ( i != 1 && newX >= 40 && newX <= 740 && newY >= 10 && newY <= 420 ) {
                        server.dataScreenJson.put("xt1" + (i) , newX);
                        server.dataScreenJson.put("yt1" + (i) , newY);
                    }

                    xoff = arrayx[Math.abs(random.nextInt()) % arrayx.length];
                    yoff = arrayy[Math.abs(random.nextInt()) % arrayy.length];
                    oldx = (Integer) server.dataScreenJson.get("xt2" + i);
                    oldy = (Integer) server.dataScreenJson.get("yt2" + i);

                    newX = (int) oldx + xoff;
                    newY = (int) oldy + yoff;

                    if ( newX >= 40 && newX <= 740 && newY >= 10 && newY <= 420 ) {
                        server.dataScreenJson.put("xt2" + (i) , newX);
                        server.dataScreenJson.put("yt2" + (i) , newY);
                    }
                    else {
                        server.dataScreenJson.put("xt2" + (i) , 370);
                        server.dataScreenJson.put("yt2" + (i) , 210);
                    }

                }

                int xoff = arrayx[Math.abs(random.nextInt()) % arrayx.length];
                int yoff = arrayy[Math.abs(random.nextInt()) % arrayy.length];
                int oldx = (Integer) server.dataScreenJson.get("xtb");
                int oldy = (Integer) server.dataScreenJson.get("ytb");

                int newX = (int) oldx + xoff;
                int newY = (int) oldy + yoff;

                if ( newX >= 40 && newX <= 740 && newY >= 10 && newY <= 420 ) {
                    server.dataScreenJson.put("xtb" , newX);
                    server.dataScreenJson.put("ytb" , newY);
                }
                else {
                    server.dataScreenJson.put("xtb" , 370);
                    server.dataScreenJson.put("ytb" , 210);
                }

                //System.out.println("Update : " + server.dataScreenJson);

                return server.dataScreenJson;
            }
        });


        // matches "POST /update/t11/UP"
        // request.params(":player") is 't11' or 't11'
        Spark.get(new Route("/update/:player/:direction") {
            @Override
            public Object handle(Request request, Response response) {
                String player = request.params(":player");
                String direction = request.params(":direction");

                int oldX = (Integer) server.dataScreenJson.get("x" + player);
                int oldY = (Integer) server.dataScreenJson.get("y" + player);

                if (direction.equals("up")) {
                    oldY += 5;
                }
                if (direction.equals("down")) {
                    oldY -= 5;
                }
                if (direction.equals("left")) {
                    oldX -= 5;
                }
                if (direction.equals("right")) {
                    oldX += 5;
                }

                server.dataScreenJson.put("x" + player , oldX);
                server.dataScreenJson.put("y" + player , oldY);

                //System.out.println("Update : " + server.dataScreenJson.get("xt11") + " - " + server.dataScreenJson.get("yt11"));

                /*Random random = new Random();

                for ( int i = 0 ; i < 11 ; i++ ) {

                    server.dataScreenJson.put("xt1" + (i+1) , random.nextInt() % 740);
                    server.dataScreenJson.put("yt1" + (i+1) , random.nextInt() % 420);

                    server.dataScreenJson.put("xt2" + (i+1) , random.nextInt() % 740);
                    server.dataScreenJson.put("yt2" + (i+1) , random.nextInt() % 420);

                }

                System.out.println("Update : " + server.dataScreenJson);
                */

                return server.dataScreenJson;

            }
        });
    }

}
