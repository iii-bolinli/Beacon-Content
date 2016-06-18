## Android Beacon 推播、電量資訊查詢 SDK V2.0

> ####V2.0更新說明

* 加入偵測 Beacon 功能 (SDK 自動偵測目標 APP 中的 Beacon)
* 簡化電量更新方式 (偵測 Beacon 時順便更新電量，不需任何額外設定或程式碼)
* 版本相容性維持 API Level 18 (Android 4.3)

---

> ####檔案說明

* 主要檔案依開發環境分為兩類：

1. Android Studio
  * beaconContentSDK_v2.0.aar : SDK 主體 
  * v2.0_AndroidStudio_Demo.zip : Android Studio 範例程式
  * proguard-rules.pro : SDK的混淆設定
  
2. Eclipse
由於使用 jar 檔，需要較多的支援檔案


> ####注意事項

* 切換伺服器方式

在初始化相關物件時，在BeaconContent的參數中輸入IP指定想要連結的伺服器，輸入空字串 "" 將預設使用測試伺服器連線

![SDK 5.png](/media/SDK 5.png)  
---

* SDK Version 18 

![SDK 6.png](/media/SDK 6.png)
---

* 要求權限  

