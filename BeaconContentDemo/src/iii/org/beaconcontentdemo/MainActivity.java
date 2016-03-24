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
        	
        	//�L�o�ؼ�Beacon�һݸ�T
        	// Here is your Major value
    		int major_i = (scanR[25] & 0xff) * 0x100 + (scanR[26] & 0xff);
    		// Here is your Minor value
    		int minor_i = (scanR[27] & 0xff) * 0x100 + (scanR[28] & 0xff);
    		
            String Major = String.valueOf(major_i);
            String Minor = String.valueOf(minor_i);
            String BeaconID = Major+"-"+Minor;
            
            // ��ڨϥε{���X
        	String apiKey = "yourAPIKey";
        	
        	// ��l�Ƭ�������
        	BatteryStatus batteryStatus = new BatteryStatus(); // �q�q
        	Push_message jsonBody = new Push_message(); // ������T
        	BeaconContent BC = new BeaconContent(""); // �D�n����

			try {
				if(BeaconID.equals("your-beacon")){ // �u��w�S�wBeacon�A����e�X�L�h�d��
					
					// �d�߹q�q
	            	batteryStatus = BC.getBeaconPower(MainActivity.this, "your-beacon", result);
	            	Log.e(TAG, "�s�y��:"+batteryStatus.currentManufacturer);
	            	Log.e(TAG, "�q�q:"+batteryStatus.batterypower);
					
					// �d�߱�����T
					jsonBody = BC.beaconContent("your-beacon", apiKey, MainActivity.this, result);
					
					// ��X�^�Ǹ�T - �Ӥ����}
					List<Coupons> Coupons = jsonBody.getResult_content().getCoupons(); 
		        	for(int i = 0; i < Coupons.size() ;i++){
		        	   System.out.println("photoUrl:" + Coupons.get(i).getPhotoUrl());
		        	}
					bluetoothLeScanner.stopScan(mScanCallback); // ���ؼЫ�Y����y
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
