import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.util.HashMap;

/**
 * Created by devilo on 11/4/14.
 */
public class Server {

    private JSONObject dataJson = null;
    private JSONObject dataScreenJson = null;
    private static String tempData = null;

    /*public static String tempData = "{\n" +
            "  \"t11\" : {\"LA\":51.0 , \"LO\":-61.0},\n" +
            "  \"t12\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t13\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t14\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t15\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t16\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t17\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t18\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t19\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t110\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t111\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t21\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t22\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t23\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t24\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t25\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t26\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t27\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t28\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t29\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t210\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"t211\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"TB\" : {\"LA\":41.0 , \"LO\":-81.0}\n" +
            "}";
    */

    public Server() {
        this.dataJson = new JSONObject();
        for ( int i = 1 ; i <= 11 ; i++ ) {
            JSONObject position = new JSONObject();
            position.put("la" , 41.0);
            position.put("lo" , -81.0);
            dataJson.put("t1" + i , position);
        }
        for ( int i = 1 ; i <= 11 ; i++ ) {
            JSONObject position = new JSONObject();
            position.put("la" , 41.0);
            position.put("lo" , -81.0);
            dataJson.put("t2" + i , position);
        }
        tempData = dataJson.toJSONString();


        this.dataScreenJson = new JSONObject(this.dataJson);

        try {
            //jsonObject = (JSONObject) new JSONParser().parse(tempData);

            for ( int i = 1 ; i <= 11 ; i++ ) {
                JSONObject obj = (JSONObject)this.dataScreenJson.get("t1" + i);
                double xold = (Double) obj.get("la");
                double yold = (Double) obj.get("lo");

                obj.put("la" , this.getNewX(xold));
                obj.put("lo" , this.getNewY(yold));
            }

            for ( int i = 1 ; i <= 11 ; i++ ) {
                JSONObject obj = (JSONObject)this.dataScreenJson.get("t2" + i);
                double xold = (Double) obj.get("la");
                double yold = (Double) obj.get("lo");

                obj.put("la" , this.getNewX(xold));
                obj.put("lo" , this.getNewY(yold));

            }


        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String tempMetaData = "{" +
            "  \"SBL\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"SBR\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"STL\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
            "  \"STR\" : {\"LA\":41.0 , \"LO\":-81.0},\n" +
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

    private Double getNewX ( double x ) {
        return (((x-x1) * w2)/w1) + x2;
    }

    private Double getNewY ( double y ) {
        return (((y-y1) * h2)/h1) + y2;
    }

    private Double getOldX ( double x ) {
        return (((x-x2) * w1)/w2) + x1;
    }

    private Double getOldY ( double y ) {
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
                System.out.println("Data sent : " + server.dataScreenJson.toString());
                return server.dataScreenJson.toString();
            }
        });


        // matches "POST /update/t11/UP"
        // request.params(":player") is 't11' or 't11'
        Spark.get(new Route("/update/:player/:direction") {
            @Override
            public Object handle(Request request, Response response) {
                String player = request.params(":player");
                String direction = request.params(":direction");

                JSONObject positions = (JSONObject) server.dataScreenJson.get(player);

                double oldX = (Double) positions.get("la");
                double oldY = (Double) positions.get("lo");

                if (direction.equals("up")) {
                    oldY += 1;
                }
                if (direction.equals("down")) {
                    oldY -= 1;
                }
                if (direction.equals("left")) {
                    oldX -= 1;
                }
                if (direction.equals("right")) {
                    oldX += 1;
                }

                positions.put("la", oldX);
                positions.put("lo", oldY);

                System.out.println("Update : " + server.dataScreenJson);

                return server.dataScreenJson;

            }
        });
    }

}
