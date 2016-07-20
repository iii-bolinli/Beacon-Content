## Android Beacon 推播、電量資訊查詢 SDK V2.2


> ###詳細使用說明可以參考 [Wiki](https://github.com/iii-bolinli/Beacon-Content/wiki).

---
> ###V2.2 更新說明

* 回傳資料中新增 Beacon 距離資料
* 新增相關錯誤訊息回傳，如藍牙不支援、藍牙未開啟、連線失敗等，取得方式與 Beacon 資料相同

e.g. 只要有不是 Beacon 資料的訊息就 Log 出來：

```java
@SuppressLint("ParcelCreator")
class ServiceResultReceiver extends ResultReceiver {
    public ServiceResultReceiver(Handler handler) {
        super(handler);
    }
    @Override
    protected void onReceiveResult(int resultCode, final Bundle resultData) {
      if (resultCode != 300){
        Log.e("msg", resultCode+":"+resultData.getString("Msg"));
      }
    }
}

```

回傳碼 | 訊息 |
------------ | ------------
201 | Device does not support Bluetooth. |
202 | Bluetooth is not enable. |
203 | get beacon list http request error. |
204 | beacon detect log update http request error. |
205 | update beacon power http request error: (msg) |
300 | `beacon data` |


---

> ###檔案說明

* 主要檔案依開發環境分為兩類：

Android Studio | 說明 |
------------ | ------------
beaconcontentsdk_v2.2.aar | SDK 主體 |
beaconcontentsdk_v2.2_withLog.aar | SDK 主體 開啟 Log 記錄 |
PushMessage_demo.zip | Android Studio 範例程式 (包含使用步驟說明) |
proguard-rules.pro | SDK 混淆設定 |

Eclipse | 說明 |
------------ | ------------
beaconcontentsdk_v2.1_20160704.jar | SDK 主體 |
beaconcontentsdk_withLog_v2.1_20160705.jar | SDK 主體 開啟 Log 記錄 |
v2.1_Eclipse_demo.zip | Eclipse 範例程式 (包含使用步驟說明)|
gson-2.2.4.jar | gson jar 檔 |

> 使用 Eclipse 需要混淆的話也可以使用 proguard-rules.pro 中的設定

---

> ###注意事項

### 切換伺服器方式

啟動服務時，Intent 的 Extra 參數中輸入 IP 或網域指定想要連結的伺服器，請務必提供

```java
String server_ip = "your_target_server"; // Both IP and domain name are acceptable.

// Step 2. start scan service and send receiver, requested data to it.
scanWithKeyReceiver = new ServiceResultReceiver(null);
Intent serviceIntent = new Intent(MainActivity.this, BeaconContentService.class);
serviceIntent.putExtra("server_ip", server_ip); // must provide
serviceIntent.putExtra("app_key", app_key); // must provide
serviceIntent.putExtra("receiver", scanWithKeyReceiver); // must provide
```
---

### 設定 API Level

###### Android Studio

應用程式的 build.gradle
```
defaultConfig {
    applicationId "tw.org.iii.newsdkdemo"
    minSdkVersion 18
    targetSdkVersion 23
    versionCode 1
    versionName "1.0"
}
```
###### Eclipse

AndroidManifest.xml
```xml
<uses-sdk
  android:minSdkVersion="18"
  android:targetSdkVersion="23" />
```
---
> ### 使用 jar 檔需套用以下設定
---

* 要求權限

```xml
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
```
---

* 引用 SDK 中的服務

AndroidManifest.xml

```xml
<service
  android:name="tw.org.iii.beaconcontentsdk.BeaconContentService"
  android:enabled="true"
  android:exported="true" />
```
