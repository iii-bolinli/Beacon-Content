package iii.org.tw.pushmessage_demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import tw.org.iii.beaconcontentsdk.BeaconContent;
import tw.org.iii.beaconcontentsdk.OpenAlarm;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class BeaconListActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    String TAG = "BeaconListActivity";
    String server_ip = "";
    String app_key = "";

    ListView listView;
    Button start_btn, stop_btn, setting_btn;
    TextView SDK_status;
    RecordListAdapter adapter;

    ArrayList<nearBeacon> nearBeacon_list = new ArrayList<>();
    ArrayList<String> listed_beacons = new ArrayList<>();

    SharedPreferences appData;
    private OpenAlarm openAlarm;
    private BeaconContent beaconContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beacon_list);

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permission = ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
            //未取得權限時，向使用者要求允許權限
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{ACCESS_FINE_LOCATION},
                        REQUEST_EXTERNAL_STORAGE
                );
            }
        }

        SharedPreferences appData = getApplication().getSharedPreferences("PushMessage", 0);
        server_ip = appData.getString("server_ip", "iiibeacon.net");
        app_key = appData.getString("app_key", "36101de29093fad767e5b1a751036bccf37f3580");

        findView();
        setListener();

        openAlarm = new OpenAlarm(BeaconListActivity.this);
        beaconContent = new BeaconContent(server_ip,app_key);

        //註冊監聽 broadcast
        registerReceiver(broadcastReceiver, new IntentFilter("beaconDetect"));

    }

    public  void findView(){
        setting_btn = (Button) findViewById(R.id.setting_btn);
        listView = (ListView) findViewById(R.id.listView);
        start_btn = (Button) findViewById(R.id.start_btn);
        stop_btn = (Button) findViewById(R.id.stop_btn);
        SDK_status = (TextView) findViewById(R.id.SDK_status);
    }

    public void setListener(){
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop_scanning();
                stop_btn.setEnabled(false);
                stop_btn.setAlpha(0.5f);
                start_btn.setEnabled(true);
                start_btn.setAlpha(1);

                Intent intent = new Intent(BeaconListActivity.this, SettingActivity.class);
                startActivityForResult(intent, 1111);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(BeaconListActivity.this, PushMessageType.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("server_ip",server_ip);
                intent.putExtra("app_key",app_key);
                intent.putExtra("beacon_id", nearBeacon_list.get(position).getBeacon_id());
                intent.putExtra("beacon_uuid", nearBeacon_list.get(position).getBeacon_uuid());
                startActivity(intent);
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start_btn.isEnabled()) {
                    start_btn.setEnabled(false);
                    start_btn.setAlpha(0.5f);
                    stop_btn.setEnabled(true);
                    stop_btn.setAlpha(1);
                }

                openAlarm.getSdkData(server_ip, app_key, 20 * 1000);
                openAlarm.startSdkService();
            }
        });

        stop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stop_btn.isEnabled()) {
                    stop_btn.setEnabled(false);
                    stop_btn.setAlpha(0.5f);
                    start_btn.setEnabled(true);
                    start_btn.setAlpha(1);
                }
                stop_scanning();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (resultCode) {
            case 1111:
                server_ip = data.getExtras().getString("server_ip");
                app_key = data.getExtras().getString("app_key");

                appData = getApplication().getSharedPreferences("PushMessage",0);
                SharedPreferences.Editor editor = appData.edit();
                editor.putString("server_ip", server_ip);
                editor.putString("app_key", app_key);
                editor.apply();
                break;
        }

    }

    public void stop_scanning() {
        openAlarm.stopSdkService();

        nearBeacon_list.clear();
        listed_beacons.clear();
        SDK_status.setText("");
        adapter = new RecordListAdapter(BeaconListActivity.this, nearBeacon_list);
        listView.setAdapter(adapter);
    }

    class nearBeacon {
        private String beacon_id;
        private String beacon_uuid;
        private String distance;
        nearBeacon(String position_id, String uuid, String distance) {
            this.beacon_id = position_id;
            this.beacon_uuid = uuid;
            this.distance = distance;
        }
        String getBeacon_id(){
            return beacon_id;
        }
        public void setBeacon_id(String beacon_id){
            this.beacon_id = beacon_id;
        }
        String getBeacon_uuid(){
            return beacon_uuid;
        }
        public void setBeacon_uuid(String beacon_uuid){
            this.beacon_uuid = beacon_uuid;
        }
        String getDistance(){
            return distance;
        }
        public void setDistance(String distance){
            this.distance = distance;
        }
    }

    private static class RecordListAdapter extends ArrayAdapter<nearBeacon> {
        // XML Layout, 通常自定Layout就會寫死在類別裡，不再透過引數傳入以免產生誤用
        private static final int mResourceId = R.layout.list_view_item;  //自定的layout
        private LayoutInflater mInflater;
        private ArrayList<nearBeacon> data;

        public RecordListAdapter(Context context, ArrayList<nearBeacon> objects) {
            super(context, mResourceId, objects);
            mInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            data = objects;
        }

        public ArrayList<nearBeacon> getData() {
            return data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(mResourceId, parent, false);
            }
            TextView beacon_id, beacon_distance, beacon_uuid;

            beacon_id = (TextView) convertView.findViewById(R.id.beacon_id);
            String id_text = "ID:"+(CharSequence) getItem(position).getBeacon_id();
            beacon_id.setText(id_text);

            beacon_uuid = (TextView) convertView.findViewById(R.id.beacon_uuid);
            String uuid_text = "UUID:"+(CharSequence) getItem(position).getBeacon_uuid();
            beacon_uuid.setText(uuid_text);

            beacon_distance = (TextView) convertView.findViewById(R.id.beacon_distance);
            String distance_text = "distance(m):"+(CharSequence) getItem(position).getDistance();
            beacon_distance.setText(distance_text);

            return convertView;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("ScanState");
            if(text.contains("Stop")){
                SDK_status.setTextColor(Color.parseColor("#000000"));
            }else{
                SDK_status.setTextColor(Color.parseColor("#e50606"));
            }
            SDK_status.setText(text);

            Bundle bundle = intent.getExtras();
            final String beacon_id = bundle.getString("id");
            final String beacon_uuid = bundle.getString("uuid");
            final String beacon_distance = String.format("%.2f", bundle.getDouble("distance"));

            //==============================================================================
            if(beacon_id!=null){
                final nearBeacon returned_beacon = new nearBeacon(beacon_id, beacon_uuid,beacon_distance);
                if (!listed_beacons.contains(beacon_id)){
                    listed_beacons.add(beacon_id);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            nearBeacon_list.add(returned_beacon);
                            adapter = new RecordListAdapter(BeaconListActivity.this, nearBeacon_list);
                            listView.setAdapter(adapter);
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int id = listed_beacons.indexOf(beacon_id);
                            View v = listView.getChildAt(id -
                                    listView.getFirstVisiblePosition());
                            if(v != null){
                                TextView distance = (TextView) v.findViewById(R.id.beacon_distance);
                                String distance_text = "distance(m):"+beacon_distance;
                                distance.setText(distance_text);
                            }
                        }
                    });
                }
            }

            //==============================================================================
        }
    };

}
