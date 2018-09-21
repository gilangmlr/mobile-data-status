package id.ac.ui.cs.mobileprogramming.gilanggumilar.mobiledatastatus;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {
    PhoneStateListener psl;
    TelephonyManager tm;
    String dataStateStr;
    String netTypeStr;
    String dataActivityStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        dataStateStr = getDataStateStr(tm.getDataState());
        netTypeStr = getNetworkTypeStr(tm.getNetworkType());
        dataActivityStr = getDataActivityStr(tm.getDataActivity());
        updateUI();

        phoneStateSetup();
    }

    private void phoneStateSetup(){
        psl = new PhoneStateListener() {
            @Override
            public void onDataConnectionStateChanged(int state, int netType){
                dataStateStr = getDataStateStr(state);
                netTypeStr = getNetworkTypeStr(netType);
                updateUI();
            }

            @Override
            public void onDataActivity(int direction){
                dataActivityStr = getDataActivityStr(direction);
                updateUI();
            }
        };

        try {
            tm.listen(psl, PhoneStateListener.LISTEN_DATA_CONNECTION_STATE
                    | PhoneStateListener.LISTEN_DATA_ACTIVITY);
        } catch(Exception e) {

        }
    }

    private void phoneStateTeardown() {
        try {
            if(psl != null) { tm.listen(psl, PhoneStateListener.LISTEN_NONE); }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        phoneStateTeardown();
    }

    private void updateUI() {
        TextView tvState = findViewById(R.id.dataConnectionStateValue);
        tvState.setText(dataStateStr);

        TextView tvType = findViewById(R.id.networkTypeValue);
        tvType.setText(netTypeStr);

        TextView tvActivity = findViewById(R.id.dataActivityValue);
        tvActivity.setText(dataActivityStr);
    }

    private String getDataStateStr(int dataState) {
        String dataStateStr = getString(R.string.unknown);
        switch(dataState) {
            case 0: dataStateStr = "Disconnected"; break;
            case 1: dataStateStr = "Connecting"; break;
            case 2: dataStateStr = "Connected"; break;
            case 3: dataStateStr = "Suspended"; break;
        }
        return dataStateStr;
    }

    private String getNetworkTypeStr(int netType) {
        String netTypeStr = getString(R.string.unknown);
        switch(netType) {
            case 0: netTypeStr = getString(R.string.unknown); break;
            case 1: netTypeStr = "GPRS"; break;
            case 2: netTypeStr = "EDGE"; break;
            case 3: netTypeStr = "UMTS"; break;
            case 4: netTypeStr = "CDMA"; break;
            case 5: netTypeStr = "EVDO_0"; break;
            case 6: netTypeStr = "EVDO_A"; break;
            case 7: netTypeStr = "1xRTT"; break;
            case 8: netTypeStr = "HSDPA"; break;
            case 9: netTypeStr = "HSUPA"; break;
            case 10: netTypeStr = "HSPA"; break;
            case 11: netTypeStr = "iDen"; break;
            case 12: netTypeStr = "EVDO_B"; break;
            case 13: netTypeStr = "LTE"; break;
            case 14: netTypeStr = "eHRPD"; break;
            case 15: netTypeStr = "HSPA+"; break;
        }
        return netTypeStr;
    }

    private String getDataActivityStr(int dataState) {
        String dataActivityStr = getString(R.string.unknown);
        switch(dataState) {
            case 0: dataActivityStr = "None"; break;
            case 1: dataActivityStr = "In"; break;
            case 2: dataActivityStr = "Out"; break;
            case 3: dataActivityStr = "InOut"; break;
        }
        return dataActivityStr;
    }
}
