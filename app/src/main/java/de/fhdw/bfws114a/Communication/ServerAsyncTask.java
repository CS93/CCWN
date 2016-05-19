package de.fhdw.bfws114a.Communication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.startScreen.Gui;

//                                     doInBackGround, onProgressUpdate, onPostExecute
public class ServerAsyncTask extends AsyncTask<Void, String, String> {

    private Context mContext;
    private Gui mGui;
    private String mMessage;
    private int mReceived;

    public ServerAsyncTask(Context context, Gui gui, String message){
        this.mContext = context;
        this.mGui = gui;
        this.mMessage = message;
    }

    @Override
    protected String doInBackground(Void... params){
        String result;
        try {
            publishProgress("Server: opening socket 8988");
            ServerSocket serverSocket = new ServerSocket(8988);
            publishProgress("Server: calling accept");
            Socket client = serverSocket.accept();
            publishProgress("Server: connection accepted");

            OutputStream oStream = client.getOutputStream();
            //String s = new SimpleDateFormat("HHmmss").format(new Date()).toString();
            oStream.write(mMessage.getBytes("UTF-8"));

            byte data[] = new byte[100];
            InputStream iStream = client.getInputStream();
            iStream.read(data);
            result = new String(data);
            Log.d("Communication", "Received from Client: " +  result);

            serverSocket.close();
        } catch (IOException e) {
            Log.e("LOGTAG", e.getMessage());
            return null;
        }
        //return "Server: connection done ";
        return result;
    }

    @Override
    protected void onPreExecute(){
        Log.d("Communication", "    Server: onPreExecute()");
    }

    @Override
    protected void onProgressUpdate(String... message){
        super.onProgressUpdate(message);
        for (String msg : message) {
            Log.d("Communication", "    Server: onProgressUpdate(): " + msg);
        }
    }

    @Override
    protected void onPostExecute(String message){
        if (message != null) {
            Log.d("Communication", "    Server: onPostExecute(): " + message);
            //for test reasons
            mGui.showToast((Activity) mContext, message);
            // add received message to gui
            mGui.getChatArrayAdapter().add(new ChatMessage(false, mGui.getEditText().getText().toString()));
            mGui.getEditText().setText("");
        }

    }

}
