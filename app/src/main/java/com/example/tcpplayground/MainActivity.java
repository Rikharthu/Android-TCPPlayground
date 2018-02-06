package com.example.tcpplayground;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    public static final String[] HOSTS = new String[]{"192.168.1.234"};
    public static final String[] PORTS = new String[]{"4444","5555"};

    TextView ipAddressTv;
    AutoCompleteTextView hostEt;
    AutoCompleteTextView portEt;

    private NetworkThread mNetworkThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsynchronousServerSocketChannel socket;

        ipAddressTv = findViewById(R.id.ipAddressTv);
        hostEt = findViewById(R.id.hostTv);
        portEt = findViewById(R.id.portTv);

        ArrayAdapter<String> hostAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, HOSTS);
        ArrayAdapter<String> portAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, PORTS);
        hostEt.setAdapter(hostAdapter);
        portEt.setAdapter(portAdapter);

        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();
        int ipAddress = wifiInf.getIpAddress();
        String ip = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        ipAddressTv.setText(ip);


    }

    @Override
    protected void onResume() {
        super.onResume();

        mNetworkThread = new NetworkThread("192.168.1.234", 4444);
        mNetworkThread.start();
    }

    private class NetworkThread extends Thread {
        private String host;
        private int port;

        public NetworkThread(String host, int port) {
            this.host = host;
            this.port = port;
        }


        @Override
        public void run() {
            try (
                    Socket kkSocket = new Socket(host, port);
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true)
            ) {
                Random rnd = new Random();
                while (true) {
                    Log.d("MainActivity", "Sending data");
                    out.println("From Android: " + rnd.nextInt());
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
