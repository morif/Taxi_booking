package ch.crut.taxi.client;

import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**
 * Created by Alex on 24.11.2014.
 */
public class StartClient extends Thread {

    private Handler handler;
    private Socket socket;
    private DataOutputStream dataOutputStream;
    private BufferedInputStream in;
    private DataInputStream dataInputStream;
    private boolean isConnecting = true;

    public StartClient(Handler handler){
        this.handler = handler;
    }

    private void setConnecting() {
        InetAddress inetAddress = null;
        try {
            try {
                inetAddress = InetAddress.getByName("192.168.1.133");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            SocketAddress socketAddress = new InetSocketAddress(inetAddress, 1910);
            socket = new Socket();
            try {
                socket.connect(socketAddress, 5000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            in = new BufferedInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataInputStream = new DataInputStream(in);
            sendPacket("hello!!!");
            while (isConnecting) {
                if (socket == null) {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
                String str = dataInputStream.readUTF();
                android.os.Message message = handler.obtainMessage(1, str);
                handler.sendMessage(message);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(String str) {
        if (dataOutputStream == null)
            return;
        try {
            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        setConnecting();
    }


}
