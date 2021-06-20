import java.io.*;
import java.net.*;
import org.json.*;

public class Weather {
    public static void main(String args[]) {
        try {
            Weather w = new Weather();
            String apiResult = w.callApi(
                    "http://api.openweathermap.org/data/2.5/weather?q="+args[0]+"&appid=0ee0e8dc98a4243c29e5ff0faa5e7b17");
            // System.out.println("Weather forcast"+apiResult);
            Temp p = w.jsonProcess(apiResult);
            System.out.println("Temprature is:: " + p.getTemp());
            System.out.println("Humidity is:: " + p.getHumidity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String callApi(String urlstr) throws IOException {
        URL url = new URL(urlstr);
        String query = "";

        // make connection
        URLConnection urlc = url.openConnection();

        // use post mode
        urlc.setDoOutput(true);
        urlc.setAllowUserInteraction(false);

        // send query
        PrintStream ps = new PrintStream(urlc.getOutputStream());
        ps.print(query);
        ps.close();

        // get result
        BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String l = null;
        while ((l = br.readLine()) != null) {
            sb.append(l);
        }
        br.close();

        return sb.toString();
    }

    private Temp jsonProcess(String apiResult) {
        Temp t = new Temp();
        JSONObject obj = new JSONObject(apiResult);
        JSONObject mainobj = obj.getJSONObject("main");
        double temp = mainobj.getDouble("temp");
        int humidity = mainobj.getInt("humidity");
        t.setTemp(temp);
        t.setHumidity(humidity);
        return t;
    }
}

