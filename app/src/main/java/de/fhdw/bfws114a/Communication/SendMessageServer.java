package de.fhdw.bfws114a.Communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import de.fhdw.bfws114a.data.ChatMessage;
import de.fhdw.bfws114a.startScreen.ApplicationLogic;
import de.fhdw.bfws114a.startScreen.Gui;


public class SendMessageServer extends AsyncTask<Void, String, String> {
	private Context mContext;
	private Gui mGui;
	private String mMessage;
	private int mReceived;
	private ApplicationLogic mAppLogic;
	private static final int SERVER_PORT = 8988;
	private MacAddressList mReceivers;

	public SendMessageServer(Context context, Gui gui, ApplicationLogic applogic, String message,  MacAddressList receivers){
		this.mContext = context;
		this.mGui = gui;
		this.mMessage = message;
		this.mAppLogic = applogic;
		this.mReceivers = receivers;
	}

	@Override
	protected String doInBackground(Void... params) {

		//Display le message on the sender before sending it
		publishProgress("I'm as Server sending: "+ mMessage);
		
		//Send the message to clients
		try {
			ArrayList<InetAddress> listClients = ServerInit.clients;
			for(InetAddress addr : listClients){
				Socket socket = new Socket();
				socket.setReuseAddress(true);
				socket.bind(null);
				Log.d("Communication","Connect to client: " + addr);
				socket.connect((new InetSocketAddress(addr,SERVER_PORT)), 10000);
				Log.d("Communication", "doInBackground: connect to "+ addr +" succeeded");
				OutputStream outputStream = socket.getOutputStream();
				outputStream.write(mMessage.getBytes("UTF-8"));

			    Log.d("Communication", "doInBackground: write to "+ addr +" succeeded");
			    socket.close();
				return "success";
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Communication", "Error in der Nachricht");
		}
		
		return null;
	}

	@Override
	protected void onProgressUpdate(String... message) {
		for (String msg : message) {
			Log.d("Communication", "    Server: onProgressUpdate(): " + msg);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);

		if(result == "success"){
			//add message to db and gui (true because the standard would be the left side and the messages of the own user used to be on right side)
			ChatMessage message = new ChatMessage(true, mGui.getEditText().getText().toString());
			mAppLogic.addMessage(message);
			mGui.getEditText().setText("");
		} else {
			//communication was not successful
			mAppLogic.showErrorMessage("Das Senden war nicht erfolgreich!");
		}

	}

	/*
	@SuppressWarnings("rawtypes")
	public Boolean isActivityRunning(Class activityClass)
	{
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
	}
	*/
}