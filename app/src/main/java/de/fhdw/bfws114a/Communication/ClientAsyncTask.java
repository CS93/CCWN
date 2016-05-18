package de.fhdw.bfws114a.Communication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import de.fhdw.bfws114a.startScreen.Gui;

//                                     doInBackGround, onProgressUpdate, onPostExecute
public class ClientAsyncTask extends AsyncTask<Void, String, String> {

    private Context mContext;
    private Gui mGui;
    private String mServerIp;
    private String mMessage;

    private static final int SOCKET_TIMEOUT = 5000;

    public ClientAsyncTask(Context context, Gui gui, String serverIp, String message) {
        mContext = context;
        mGui = gui;
        mServerIp = serverIp;
        this.mMessage = message;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            Socket socket = new Socket();
            int port = 8988;
            socket.bind(null);  // local address
            publishProgress("Client: connecting to " + mServerIp);
            socket.connect((new InetSocketAddress(mServerIp, port)), SOCKET_TIMEOUT);

            OutputStream oStream = socket.getOutputStream();
            //String s = new SimpleDateFormat("HHmmss").format(new Date()).toString();
            oStream.write(mMessage.getBytes("UTF-8"));

            InputStream stream = socket.getInputStream();

            byte data[] = new byte[100];
            publishProgress("Client: reading data");
            stream.read(data);
            publishProgress("Client: data read: " + new String(data));

            Log.d("Communication", "Received from Server: " +  new String(data));

            socket.close();
        } catch (IOException e) {
            Log.e("LOGTAG", e.getMessage());
            return "Client: connection failed";
        }
        return "Client: connection done";
    }

    @Override
    protected void onPreExecute() {
        Log.d("Communication", "Client: onPreExecute()");
    }

    @Override
    protected void onProgressUpdate(String... message){
        super.onProgressUpdate(message);
        for (String msg : message) {
            Log.d("Communication", "Client: onProgressUpdate(): " + msg);
        }
    }

    @Override
    protected void onPostExecute(String message) {
        if (message != null) {
            Log.d("Communication", "Client: onPostExecute(): " + message);
        }
    }

}
