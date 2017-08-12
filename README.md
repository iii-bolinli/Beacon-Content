## Android Beacon 推播、電量資訊查詢 SDK V2.7

## 自 2.6 版開始不支援 Eclipse 的相關檔案更新

### 詳細使用說明可以參考 [Wiki](https://github.com/iii-bolinli/Beacon-Content/wiki)

---

### V2.7 更新說明

* 修改 Beacon 偵測流程
* 調整使用方式

---

### 檔案說明

Android Studio | 說明 |
------------ | ------------
beaconcontentsdk_v2.7.aar | SDK 主體 |
beaconcontentsdk_v2.7_withLog.aar | SDK 主體 開啟 Log 記錄 |
PushMessage_demo.zip | Android Studio 範例程式 (包含使用步驟說明) |
proguard-rules.pro | SDK 混淆設定 |
proguard-rules-withLog.pro | SDK 混淆設定 |


---

### 注意事項

### 設定 API Level

#### Android Studio

應用程式的 build.gradle
```
defaultConfig {
    applicationId "tw.org.iii.pushmessage_demo"
    minSdkVersion 18
    targetSdkVersion 25
    versionCode 1
    versionName "1.0"
}
```
