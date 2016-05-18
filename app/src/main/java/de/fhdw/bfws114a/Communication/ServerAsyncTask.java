package de.fhdw.bfws114a.Communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.fhdw.bfws114a.startScreen.Gui;

//                                     doInBackGround, onProgressUpdate, onPostExecute
public class ServerAsyncTask extends AsyncTask<Void, String, String> {

    private Context mContext;
    private Gui mGui;

    public ServerAsyncTask(Context context, Gui gui){
        this.mContext = context;
        this.mGui = gui;
    }

    @Override
    protected String doInBackground(Void... params){
        try {
            publishProgress("Server: opening socket 8988");
            ServerSocket serverSocket = new ServerSocket(8988);
            publishProgress("Server: calling accept");
            Socket client = serverSocket.accept();
            publishProgress("Server: connection accepted");

            OutputStream stream = client.getOutputStream();
            String s = new SimpleDateFormat("HHmmss").format(new Date()).toString();
            stream.write(s.getBytes("UTF-8"));

            serverSocket.close();
        } catch (IOException e) {
            Log.e("LOGTAG", e.getMessage());
            return null;
        }
        return "Server: connection done";
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
        }

    }

}
