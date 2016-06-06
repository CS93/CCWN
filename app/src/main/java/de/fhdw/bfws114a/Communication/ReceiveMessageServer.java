package de.fhdw.bfws114a.Communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.startScreen.ApplicationLogic;
import de.fhdw.bfws114a.startScreen.Gui;


public class ReceiveMessageServer extends AsyncTask<Void, String, String> {
	private Context mContext;
	private Gui mGui;
	private String mMessage;
	private int mReceived;
	private ApplicationLogic mAppLogic;
	private ServerSocket serverSocket;

	public ReceiveMessageServer(Context context, Gui gui, ApplicationLogic applogic) {
		this.mContext = context;
		this.mGui = gui;
		this.mAppLogic = applogic;
	}

	@Override
	protected String doInBackground(Void... params) {
		try {
			serverSocket = new ServerSocket(8988);
			while (true) {
				Socket clientSocket = serverSocket.accept();

				InputStream inputStream = clientSocket.getInputStream();
				byte data[] = new byte[100];
				inputStream.read(data);
				mMessage = new String(data);
				//ObjectInputStream objectIS = new ObjectInputStream(inputStream);
				//mMessage = (String) objectIS.readObject();

				//Add the InetAdress of the sender to the message
				//InetAddress senderAddr = clientSocket.getInetAddress();
				clientSocket.close();
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
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.onCancelled();
	}

	@Override
	protected void onProgressUpdate(String... message) {
		super.onProgressUpdate(message);
		for (String msg : message) {
			Log.d("Communication", "    Server: I received this message from Client " + msg);
			ChatMessage receivedMessage = new ChatMessage(false, msg);
			mAppLogic.addMessage(receivedMessage);
		}

		// Here could we send a message back to notify that we received the message
		// new SendMessageServer(mContext, false).executeOnExecutor(THREAD_POOL_EXECUTOR, values);
	}
	
}
