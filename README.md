## Android Beacon 推播、電量資訊查詢 SDK V2.1

> ###V2.1更新說明

* 偵測紀錄

> ###V2.0更新說明

* 加入偵測 Beacon 功能 (SDK 自動偵測目標 APP 中的 Beacon)
* 簡化電量更新方式 (偵測 Beacon 時順便更新電量，不需任何額外設定或程式碼)
* 版本相容性維持 API Level 18 (Android 4.3)

---

> ###檔案說明

* 主要檔案依開發環境分為兩類：

Android Studio | 說明 |
------------ | ------------
beaconContentSDK_v2.1.aar | SDK 主體 |
v2.0_AndroidStudio_Demo.zip | Android Studio 範例程式 (包含使用步驟說明)|
proguard-rules.pro | SDK 混淆設定 |

Eclipse | 說明 |
------------ | ------------
beaconContentSDK_v2.0.jar | SDK 主體 |
v2.0_Eclipse_demo.zip | Eclipse 範例程式 (包含使用步驟說明)|
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
