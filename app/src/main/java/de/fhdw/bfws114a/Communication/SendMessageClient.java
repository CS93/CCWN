package de.fhdw.bfws114a.Communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.fhdw.bfws114a.startScreen.ApplicationLogic;
import de.fhdw.bfws114a.startScreen.Gui;

public class SendMessageClient extends AsyncTask<Void, String, String>{
	private static final int SERVER_PORT = 8988;
	private Context mContext;
	private Gui mGui;
	private String mServerIp;
	private String mMessage;
	private ApplicationLogic mAppLogic;

	private static final int SOCKET_TIMEOUT = 5000;
	
	public SendMessageClient(Context context, Gui gui, ApplicationLogic appLogic, String serverIp, String message){
		mContext = context;
		mGui = gui;
		mServerIp = serverIp;
		this.mMessage = message;
		this.mAppLogic = appLogic;
	}
	
	@Override
	protected String doInBackground(Void... params) {

		//Display le message on the sender before sending it
		publishProgress("I'm as Client sending: "+ mMessage);
		
		//Send the message
		Socket socket = new Socket();
		try {
			socket.setReuseAddress(true);
			socket.bind(null);
			socket.connect(new InetSocketAddress(mServerIp, SERVER_PORT));
			Log.d("Communication", "doInBackground: Socket connect succeeded");
			
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(mMessage.getBytes("UTF-8"));
			
		    Log.d("Communication", "doInBackground: send message succeeded");
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (socket != null) {
		        if (socket.isConnected()) {
		            try {
		                socket.close();
		            } catch (IOException e) {
		            	e.printStackTrace();
		            }
		        }
		    }
		}
		
		return null;
	}

	@Override
	protected void onProgressUpdate(String... message) {
		super.onProgressUpdate(message);
		for (String msg : message) {
			Log.d("Communication", "    Server: onProgressUpdate(): " + msg);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//Todo: If succesful-> add to chatbubble
	}
}
