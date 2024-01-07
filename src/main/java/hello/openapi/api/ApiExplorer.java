package hello.openapi.api;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ApiExplorer {

    public void apiRequest(Model model) throws IOException, ParseException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/FoodService/getFoodKr"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=CrvjYMiSdtUj%2Fw5KUU72mwzolCzAf5yaU7%2Frz7BhEjwXIkDzECQhy%2BOjFVCSSMkLeLbmNLRJyoqI2CXF4q0A6g%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        //json 파싱
        String data=sb.toString();
        JSONParser parser=new JSONParser();

        JSONObject obj = (JSONObject) parser.parse(data);

        JSONObject getFoodKr = (JSONObject) obj.get("getFoodKr");
        JSONArray item = (JSONArray) getFoodKr.get("item");

        //item의 각 요소의 정보를 Map에 저장 후 List에 저장.

        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>();

        if (item.size() > 0) {
            for (int i = 0; i < item.size(); i++) {
                Map<String, String> map = new HashMap<String, String>();

                JSONObject element = (JSONObject)item.get(i);
                map.put("PLACE",(String)element.get("MAIN_TITLE"));
                map.put("CNTCT_TEL",(String)element.get("CNTCT_TEL"));
                map.put("HOMEPAGE_URL",(String)element.get("HOMEPAGE_URL"));
                map.put("ITEMCNTNTS",(String)element.get("ITEMCNTNTS"));

                listMap.add(map);
            }
        }

        model.addAttribute("listMap",listMap);

//        for (Map<String, String> map : listMap) {
//            for (String s : map.keySet()) {
//                System.out.println("s = " + s);
//            }
//            System.out.println();
//        }



    }

}