## Android Beacon 推播、電量資訊查詢 SDK V2.0

> ###V2.0更新說明

* 加入偵測 Beacon 功能 (SDK 自動偵測目標 APP 中的 Beacon)
* 簡化電量更新方式 (偵測 Beacon 時順便更新電量，不需任何額外設定或程式碼)
* 版本相容性維持 API Level 18 (Android 4.3)

---

> ###檔案說明

* 主要檔案依開發環境分為兩類：

Android Studio | 說明 |
------------ | ------------
beaconContentSDK_v2.0.aar | SDK 主體 |
v2.0_AndroidStudio_Demo.zip | Android Studio 範例程式 (包含使用步驟說明)|
proguard-rules.pro | SDK 混淆設定 |

Eclipse | 說明 |
------------ | ------------
 | SDK 主體 |
 | 範例程式 |



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
```
```
---

* 要求權限

