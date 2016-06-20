package de.fhdw.bfws114a.Communication;
/**
 * Created by Carsten Schlender.
 */

import android.util.Log;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClientInit extends Thread{
	private static final int SERVER_PORT = 1234;
	private InetAddress mServerAddr;
	
	public ClientInit(InetAddress serverAddr){
		mServerAddr = serverAddr;
	}
	
	@Override
	public void run() {
		Socket socket = new Socket();
		Log.d("Communication", "Client Init started");
		try {
			synchronized (this){
				wait(5000); //so that Server Init thread is started
			}
			socket.bind(null);
			socket.connect(new InetSocketAddress(mServerAddr, SERVER_PORT));
			socket.close();
		} catch (ConnectException e){
			e.printStackTrace();
			run();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
