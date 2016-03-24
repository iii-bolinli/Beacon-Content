package iii.org.beaconcontentdemo;

import java.util.List;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.os.Bundle;
import android.util.Log;
import iii.org.beaconcontentsdk.BatteryStatus;
import iii.org.beaconcontentsdk.BeaconContent;
import iii.org.json.push_message.Coupons;
import iii.org.json.push_message.Push_message;

public class MainActivity extends Activity {
	static String TAG = "MainActivity";
	//For Scan
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
	private BluetoothLeScanner bluetoothLeScanner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override 
    protected void onResume() {
    	super.onResume();
    	bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
		bluetoothLeScanner.startScan(mScanCallback);
	}
	
	private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
        	byte[] scanR = result.getScanRecord().getBytes();
        	
        	//過濾目標Beacon所需資訊
        	// Here is your Major value
    		int major_i = (scanR[25] & 0xff) * 0x100 + (scanR[26] & 0xff);
    		// Here is your Minor value
    		int minor_i = (scanR[27] & 0xff) * 0x100 + (scanR[28] & 0xff);
    		
            String Major = String.valueOf(major_i);
            String Minor = String.valueOf(minor_i);
            String BeaconID = Major+"-"+Minor;
            
            // 實際使用程式碼
        	String apiKey = "yourAPIKey";
        	
        	// 初始化相關物件
        	BatteryStatus batteryStatus = new BatteryStatus(); // 電量
        	Push_message jsonBody = new Push_message(); // 推播資訊
        	BeaconContent BC = new BeaconContent(""); // 主要物件

			try {
				if(BeaconID.equals("your-beacon")){ // 只鎖定特定Beacon，防止送出過多查詢
					
					// 查詢電量
	            	batteryStatus = BC.getBeaconPower(MainActivity.this, "your-beacon", result);
	            	Log.e(TAG, "製造商:"+batteryStatus.currentManufacturer);
	            	Log.e(TAG, "電量:"+batteryStatus.batterypower);
					
					// 查詢推播資訊
					jsonBody = BC.beaconContent("your-beacon", apiKey, MainActivity.this, result);
					
					// 輸出回傳資訊 - 照片網址
					List<Coupons> Coupons = jsonBody.getResult_content().getCoupons(); 
		        	for(int i = 0; i < Coupons.size() ;i++){
		        	   System.out.println("photoUrl:" + Coupons.get(i).getPhotoUrl());
		        	}
					bluetoothLeScanner.stopScan(mScanCallback); // 找到目標後即停止掃描
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.i("ScanResult - Results", sr.toString());
            }
        }
 
        @Override
        public void onScanFailed(int errorCode) {
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }
	};
}
