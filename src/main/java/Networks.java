import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.image.BufferedImage;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class Networks {

    ArrayList<BufferedImage> imageLists;

    Networks(){

    }

    public ArrayList<BufferedImage> getImageLists() {
        return imageLists;
    }

    public void setImageLists(ArrayList<BufferedImage> imageLists) {
        this.imageLists = imageLists;
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
