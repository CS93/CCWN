package de.fhdw.bfws114a.Communication;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.fhdw.bfws114a.startScreen.ApplicationLogic;
import de.fhdw.bfws114a.startScreen.Gui;

public class ReceiveMessageClient extends AsyncTask<Void, String, String> {
	private static final int SERVER_PORT = 8988;
	private Context mContext;
	private Gui mGui;
	private String mServerIp;
	private String mMessage;
	private ApplicationLogic mAppLogic;
	private ServerSocket socket;
	private static final int SOCKET_TIMEOUT = 5000;

	public ReceiveMessageClient(Context context, Gui gui, ApplicationLogic applogic, String serverIp){
		mContext = context;
		mGui = gui;
		mServerIp = serverIp;
		this.mAppLogic = applogic;
	}
	
	@Override
	protected String doInBackground(Void... params) {
		try {
			socket = new ServerSocket(SERVER_PORT);
			while(true){
				Socket destinationSocket = socket.accept();
				
				InputStream inputStream = destinationSocket.getInputStream();
				BufferedInputStream buffer = new BufferedInputStream(inputStream);
				byte data[] = new byte[100];
				inputStream.read(data);
				mMessage = new String(data);

				
				destinationSocket.close();
				publishProgress(mMessage);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return null;
	}

	@Override
	protected void onCancelled() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onCancelled();
	}

	@Override
	protected void onProgressUpdate(String... message) {
		super.onProgressUpdate(message);
		for (String msg : message) {
			Log.d("Communication", "    Client: I received this message from Server " + msg);
		}

		//// TODO: add received Message to chatbubble

		// Here could we send a message back to notify that we received the message
		// new SendMessageServer(mContext, false).executeOnExecutor(THREAD_POOL_EXECUTOR, values);
	}

}
