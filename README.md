## Android Beacon 推播、電量資訊查詢 SDK V2.6

##此版本開始不支援 Eclipse 的相關檔案更新

###詳細使用說明可以參考 [Wiki](https://github.com/iii-bolinli/Beacon-Content/wiki)

---

###V2.6 更新說明

* 新增純文字、超連結兩種版型

---

###檔案說明

Android Studio | 說明 |
------------ | ------------
beaconcontentsdk_v2.6.aar | SDK 主體 |
beaconcontentsdk_v2.6_withLog.aar | SDK 主體 開啟 Log 記錄 |
PushMessage_demo.zip | Android Studio 範例程式 (包含使用步驟說明) |
proguard-rules.pro | SDK 混淆設定 |
proguard-rules-withLog.pro | SDK 混淆設定 |


---

###注意事項

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
