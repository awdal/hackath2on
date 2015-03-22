package awdal.com.hackath2on;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Albert on 21/03/2015.
 */
public class AcceptThread extends Thread {
    private final BluetoothServerSocket mmServerSocket;
    private UUID MY_UUID = UUID.fromString("4a684ec9-844d-4bdc-917e-a7b764374617");
    private ConnectedThread th = null;
    private MyInterface inter;
    public AcceptThread(BluetoothAdapter mBluetoothAdapter,MyInterface inter) {
        // Use a temporary object that is later assigned to mmServerSocket,
        // because mmServerSocket is final
        this.inter = inter;
        BluetoothServerSocket tmp = null;
        try {

            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord("Hackaton", MY_UUID);

        } catch (IOException e) { }
        mmServerSocket = tmp;
    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept(99999);
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                //manageConnectedSocket(socket);
                ConnectedThread th = new ConnectedThread(socket,inter);
                th.start();
                try {
                mmServerSocket.close();
            } catch (IOException e) { }

                break;
            }
        }
    }

    /** Will cancel the listening socket, and cause the thread to finish */
    public void cancel() {
        try {
            mmServerSocket.close();
        } catch (IOException e) { }
    }
    public ConnectedThread getSenderThread(){
        return th;
    }
}
