package awdal.com.hackath2on;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Albert on 21/03/2015.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private MyInterface inter;

    public ConnectedThread(BluetoothSocket socket,MyInterface inter) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        this.inter = inter;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[20];  // buffer store for the stream
        int bytes; // bytes returned from read()
        char c;
        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer, 0, 20);
                // Send the obtained bytes to the UI activity
               // mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer).sendToTarget();
                // for each byte in the buffer
                String str = new String();
                for(int i = 0; i < bytes; i++)
                {
                    // convert byte to character
                    c=(char)buffer[i];

                    // prints character
                    str += c;
                }

                inter.showMessageObtained(str);
            } catch (IOException e) {
                Log.e("connect", e.getMessage());
            }
        }
    }

    /* Call this from the main activity to send data to the remote device */
    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
        } catch (IOException e) { }
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }


}
