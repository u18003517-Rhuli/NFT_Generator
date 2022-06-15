import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NFT_Generator {

    public static void main(String[] args) throws IOException {

        getImage();
        //Image image1 = new ImageIcon(NFT_Generator.class.getClass().getResource("Images/elephant.jpg")).getImage();
        //Image image2 = new ImageIcon(NFT_Generator.class.getClass().getResource("Images/words.png")).getImage();
        //image1.getWidth();


        //Class<? extends Class> hey = NFT_Generator.class.getClass();

        //System.out.println(hey.getResource("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/elephant.jpg"));


        //ImageIcon image1 = new ImageIcon(NFT_Generator.class.getClass().getResource("Images/elephant.jpg"));
        ImageIcon image1 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/elephant.jpg");
        ImageIcon image2 = new ImageIcon("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/resources/Images/words.png");


        ImageObserver comp = new JComponent() {
            private static final long serialVersionUID = 1L;
        };

        int w = image1.getIconWidth() + image2.getIconWidth();
        int h = Math.max(image1.getIconHeight(), image2.getIconHeight());

        BufferedImage image = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2 = image.createGraphics();

        g2.drawImage(image1.getImage(), 0, 0, comp);
        g2.drawImage(image2.getImage(), image1.getIconWidth(), 0, comp);
        g2.dispose();

        //return image;
        File outputfile = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage.jpg");
        File outputfile2 = new File("C:/Users/Rhuli/IdeaProjects/NFT_Generator/src/main/java/NFT_Image/testImage.png");
        ImageIO.write(image, "jpg", outputfile);
        ImageIO.write(image, "png", outputfile2);



    }

    static void useApi(){
        /*
        Maven dependency for JSON-simple:
            <dependency>
                <groupId>com.googlecode.json-simple</groupId>
                <artifactId>json-simple</artifactId>
                <version>1.1.1</version>
            </dependency>
         */

        try {
            //Public API:
            //https://www.metaweather.com/api/location/search/?query=<CITY>
            //https://www.metaweather.com/api/location/44418/

            URL url = new URL("https://www.metaweather.com/api/location/search/?query=London");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                //Close the scanner
                scanner.close();

                System.out.println(informationString);


                //JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

                //Get the first JSON object in the JSON array
                System.out.println(dataObject.get(0));

                JSONObject countryData = (JSONObject) dataObject.get(0);

                System.out.println(countryData.get("woeid"));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static byte[] getImage() {
        // Create the Contentful client.
        /*final CDAClient client =
                CDAClient
                        .builder()
                        .setToken("<access_token>") // required
                        .setSpace("<space_id>") // required
                        .setEnvironment("<environment_id>") // optional, defaults to `master`
                        .build();

        // Create an okhttp client for networking.
        final OkHttpClient httpClient =
                new OkHttpClient
                        .Builder()
                        .build();

        // Retrieve one asset to be used.
        final CDAAsset asset =
                client
                        .fetch(CDAAsset.class)
                        .one("<asset_id>");

        // Save it's url.
        final String url = asset.url();

        // Add the protocol to url.
        final String url = asset.urlForImageWith(https());

        final Call call =
                httpClient
                        .newCall(
                                new Request
                                        .Builder()
                                        .url(url)
                                        .build()
                        );

        // Request the image.
        final Response response = call.execute();

        // Read image in `png` format.
        byte[] bytes = response.body().bytes();
        return bytes;*/

        return null;
    }

}
