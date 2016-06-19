package de.fhdw.bfws114a.dataInterface;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ricardo La Valle.
 */


public class JsonParser {
    String in;
    public JsonParser(){
        in="{ \"developers\": [{ \"firstName\":\"Linus\" , \"lastName\":\"Torvalds\" }, " +
                "{ \"firstName\":\"John\" , \"lastName\":\"von Neumann\" } ]}";



    }

    public void test() {
        String data = "";
        try {
            JSONObject  jsonRootObject = new JSONObject(in);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("developers");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String firstName = jsonObject.optString("firstName").toString();
                String lastName = jsonObject.optString("lastName").toString();

                data += "Node"+i+" : \n firstName= "+ firstName  +" \n lastName= "+ lastName +" \n ";
            }
            Log.d("LOG", data);
        } catch (JSONException e) {e.printStackTrace();}
    }




}
