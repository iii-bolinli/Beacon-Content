## Android Beacon 推播、電量資訊查詢 SDK

> ####檔案說明

* 主要檔案為：BeaconContentSDK、BeaconContentDemo

* BeaconContentSDK 為 SDK 本體，分為包含 Gson (master) 檔案以及不包含 Gson (No_Gson) 檔案兩種，避免使用時原專案中已經有 Gson 檔案造成衝突

* 範例程式 BeaconContentDemo 建議使用 Eclipse 開啟

> ####注意事項

* 切換伺服器方式

在初始化相關物件時，在BeaconContent的參數中輸入IP指定想要連結的伺服器，輸入空字串 "" 將預設使用測試伺服器連線

![SDK 5.png](/media/SDK 5.png)  

---

* 最低版本限制  
![SDK 2.png](/media/SDK 2.png)  

---

* 要求權限  
![SDK_3.png](/media/SDK_3.png)
