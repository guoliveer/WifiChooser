package sme.com.br.wifichooser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiConfiguration;

import java.util.List;
import android.net.wifi.ScanResult;

public class WifiActivity extends AppCompatActivity {

    WifiManipulado wifi = new WifiManipulado();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);


    }


    public void onClick(View view){
        WifiManager wifimanager=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wc = new WifiConfiguration();
        wc.status = WifiConfiguration.Status.ENABLED;
        TextView result = (TextView) findViewById(R.id.value);
        TextView Mostrando = (TextView) findViewById(R.id.WifiLevel);
        List<ScanResult> results = wifi.getConections(this);
        String SSID="";
        int Level=-200;
        String Crypto="";
        String PassWord="";
        if(results!=null){

            StringBuilder info = new StringBuilder();

            for(ScanResult connection: results){

                result.setText(info.append(connection.SSID).append("\n").append("MAC: ").append(connection.BSSID).append("\n").append("Crypto: ").append(connection.capabilities).append("\n").append("Level: " ).append(String.valueOf(connection.level)).append("\n\n\n"));
                if(connection.level>Level)
                {
                    SSID=connection.SSID;
                    Level=connection.level;
                    Crypto=connection.capabilities;
                }

            }
            Mostrando.setText(""+SSID+" Level "+Level);

            wc.SSID ="\""+SSID+"\"";

            wc.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
            wc.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            wc.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            wc.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            if (PassWord=="")
            {

            }
                wc.preSharedKey="\""+PassWord+"\"";
                wifimanager.disconnect();
                int netId = wifimanager.addNetwork(wc);
                wifimanager.enableNetwork(netId, true);
                wifimanager.reconnect();

            // / connect to and enable the connection

        } else {
            result.setText("Sorry, no connections");
        }

    }

}
