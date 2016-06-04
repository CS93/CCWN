package de.fhdw.bfws114a.Communication;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.startScreen.ApplicationLogic;
import de.fhdw.bfws114a.startScreen.Gui;

//                                     doInBackGround, onProgressUpdate, onPostExecute
public class ServerAsyncTask extends AsyncTask<Void, String, String> {

    private Context mContext;
    private Gui mGui;
    private String mMessage;
    private int mReceived;
    private ApplicationLogic mAppLogic;
    private static final int SERVER_PORT = 1234;

    public ServerAsyncTask(Context context, Gui gui, ApplicationLogic applogic, String message){
        this.mContext = context;
        this.mGui = gui;
        this.mMessage = message;
        this.mAppLogic = applogic;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result;
        try {
            publishProgress("Server: opening socket " + SERVER_PORT);
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
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
            Log.d("Communication", "Received from Client: " + result);

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
            if(!message.equals("Client: connection failed")){
                Log.d("Communication", "    Server: onPostExecute(): " + message);

                if(message.isEmpty()){
                    Log.d("Communication", "Empty Message received");
                } else {
                    // add received message to gui (false because received messages should stay on left side)
                    ChatMessage chatMessage = new ChatMessage(false, message);
                    mAppLogic.addMessage(chatMessage);
                }

            } else {
                Log.d("Communication", message);
            }

        }

    }

}
