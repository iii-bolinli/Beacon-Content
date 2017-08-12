package iii.org.tw.pushmessage_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tw.org.iii.beaconcontentsdk.BeaconContent;
import tw.org.iii.beaconcontentsdk.json.push_message.Push_message;

public class PushMessageType extends AppCompatActivity {

    Push_message push_message;
    tw.org.iii.beaconcontentsdk.json.push_message.Result_content Result_content;
    List<tw.org.iii.beaconcontentsdk.json.push_message.Coupons> Coupons;
    List<tw.org.iii.beaconcontentsdk.json.push_message.Products> Products;
    List<tw.org.iii.beaconcontentsdk.json.push_message.Links> Links;
    List<tw.org.iii.beaconcontentsdk.json.push_message.Texts> Texts;

    String server_ip = "";
    String app_key = "";
    String beacon_id = "";
    String beacon_uuid = "";
    Button btn_back;
    ListView listView;

    // Initializing a new String Array
    String[] types = new String[]{};
    // Create a List from String Array elements
    final List<String> type_list = new ArrayList<String>(Arrays.asList(types));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_message_type);

        Intent intent = getIntent();
        server_ip = intent.getStringExtra("server_ip");
        app_key = intent.getStringExtra("app_key");
        beacon_id = intent.getStringExtra("beacon_id");
        beacon_uuid = intent.getStringExtra("beacon_uuid");

        BeaconContent BC = new BeaconContent(server_ip, app_key);
        push_message = BC.beaconContent(beacon_uuid);
        Result_content = push_message.getResult_content();
        Log.e("receive_data_info", "getResource: "+String.valueOf(push_message.getResource()
                + "\ngetMethod: " + push_message.getMethod()
                + "\ngetError_msg: " + push_message.getError_msg()
                + "\ngetResult: " + push_message.getResult()
                + "\ngetResult_content: " + push_message.getResult_content()));

        Coupons = Result_content.getCoupons();
        Products = Result_content.getProducts();
        Links = Result_content.getLinks();
        Texts = Result_content.getTexts();

        findView();
        setListener();

        // Create an ArrayAdapter from List
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, type_list);

        // DataBind ListView with items from ArrayAdapter
        listView.setAdapter(arrayAdapter);

        if (Coupons != null && Coupons.size() !=0){
            for (int i = 0; i < Coupons.size(); i++) {
                Log.e("Item_Coupons", Coupons.get(i).getPhotoUrl());
                type_list.add(Coupons.get(i).getTemplateName()+"_Coupons_"+i);
                arrayAdapter.notifyDataSetChanged();
            }
        }
        if (Products != null && Products.size() != 0){
            for (int i = 0; i < Products.size(); i++) {
                Log.e("Item_Products", Products.get(i).getPhotoUrl());
                type_list.add(Products.get(i).getTemplateName()+"_Products_"+i);
                arrayAdapter.notifyDataSetChanged();
            }
        }
        if (Links != null && Links.size() != 0){
            for (int i = 0; i < Links.size(); i++) {
                Log.e("Item_Links", Links.get(i).getUrl());
                type_list.add(Links.get(i).getTemplateName()+"_Links_"+i);
                arrayAdapter.notifyDataSetChanged();
            }
        }
        if (Texts != null && Texts.size() != 0) {
            for (int i = 0; i < Texts.size(); i++) {
                Log.e("Item_Texts", Texts.get(i).getName()+"\n"+Texts.get(i).getTemplateDescription());
                type_list.add(Texts.get(i).getName()+"_Texts_"+i);
                arrayAdapter.notifyDataSetChanged();
            }
        }

    }

    public void findView(){
        btn_back = (Button) findViewById(R.id.btn_back);
        listView = (ListView) findViewById(R.id.listView);
    }

    public void setListener(){

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
