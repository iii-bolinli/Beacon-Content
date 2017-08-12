package iii.org.tw.pushmessage_demo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SettingActivity extends AppCompatActivity {

    TextView current_key_content;
    EditText server_ip, app_key;
    Button setting_confirm, setting_cancel;
    Spinner appKeySpinner;
    String getAppKey = "";
    String getServerIp = "";
    final String[] appKeyList = {"36101de29093fad767e5b1a751036bccf37f3580"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        findView();
        setListener();

        SharedPreferences appData = getApplication().getSharedPreferences("PushMessage", 0);
        getAppKey = appData.getString("app_key", "36101de29093fad767e5b1a751036bccf37f3580");
        getServerIp = appData.getString("server_ip", "iiibeacon.net");
        current_key_content.setText(getAppKey);
        server_ip.setText(getServerIp);

        ArrayAdapter<String> appKeyAdapter = new ArrayAdapter<>(SettingActivity.this,
                android.R.layout.simple_spinner_dropdown_item, appKeyList);
        appKeySpinner.setAdapter(appKeyAdapter);
        appKeySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                app_key.setText(appKeyList[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                app_key.setText(appKeyList[0]);
            }
        });

    }

    private void findView() {
        current_key_content = (TextView) findViewById(R.id.current_key_content);
        server_ip = (EditText) findViewById(R.id.server_ip);
        app_key = (EditText) findViewById(R.id.app_key);
        setting_confirm = (Button) findViewById(R.id.setting_confirm);
        setting_cancel = (Button) findViewById(R.id.setting_cancel);
        appKeySpinner = (Spinner)findViewById(R.id.appKeySpinner);
    }

    private void setListener() {
        setting_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bundle_server_ip = server_ip.getText().toString();
                String bundle_app_key = app_key.getText().toString();

                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                bundle.putString("server_ip", bundle_server_ip);
                bundle.putString("app_key", bundle_app_key);
                intent.putExtras(bundle);
                setResult(1111, intent);
                SettingActivity.this.finish();
            }
        });

        setting_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
