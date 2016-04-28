package de.fhdw.bfws114a.dataInterface;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Techcom on 28.04.16.
 */


public class JsonParser {
    String in;
    public JsonParser(){
        in="\"Employee\":[{\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"}]}";



    }

    public void test() {
        String data = "";
        try {
            JSONObject  jsonRootObject = new JSONObject(in);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(jsonObject.optString("id").toString());
                String name = jsonObject.optString("name").toString();
                float salary = Float.parseFloat(jsonObject.optString("salary").toString());

                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n ";
            }
            System.out.println(data);
        } catch (JSONException e) {e.printStackTrace();}
    }




}
