# react-native-uking-qrcode 

[![npm package](https://badge.fury.io/js/react-native-uking-qrcode.svg)](https://www.npmjs.com/package/react-native-uking-qrcode)
[![npm download](https://img.shields.io/npm/dm/react-native-uking-qrcode.svg?style=flat-square)](https://www.npmjs.com/package/react-native-uking-qrcode)

##Introduction

原生二维码（QRCode）和条码(BarCode)生成组件及二维码扫描(QRScaner)组件，，集成到 iOS 和 Android 。

二维码和条码生成组件和二维码扫描组(开发中)是基于 

LBXScan(IOS) GIT: https://github.com/MxABC/LBXScan.git (已放弃)

SGQRCode(IOS) GIT: https://github.com/kingsic/SGQRCode.git

BGAQRCode-Android(android) GIT: https://github.com/bingoogolapple/BGAQRCode-Android.git

##注：

IOS 版已将ZXingObjc 更新到最新

如果有不对的地方请提交到issues以便解决。

##Installation

###npm or yarn 

npm install react-native-uking-qrcode  or yarn add react-native-uking-qrcode

###IOS:

在你的项目ios目录下面新建一个Profile文件:
```
    platform :ios, '8.0'
    target '你的项目名称' do
        pod 'yoga', path: '../node_modules/react-native/ReactCommon/yoga/'    
        pod 'React', path: '../node_modules/react-native/'    
        pod 'RCTZXing', path: '../node_modules/react-native-uking-qrcode/ios/'   
    end
```
然后在ios目录下 执行 pod install
然后把ios/Pods录下 Pods.xcodeproj添加到 Libraries下

3. 如果是 iOS 10 需要在 info 中额外添加如下权限:
```
    <key>NSCameraUsageDescription</key>    
    <string>cameraDesciption</string>

    <key>NSContactsUsageDescription</key>    
    <string>contactsDesciption</string>

    <key>NSMicrophoneUsageDescription</key>    
    <string>microphoneDesciption</string>
```    


#### Android

make the following additions to the given files manually:

**android/settings.gradle**

```gradle
include ':react-native-uking-qrcode'
project(':react-native-uking-qrcode').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-uking-qrcode/android')
```

**android/app/build.gradle**

```gradle
dependencies {
   ...
   compile project(':react-native-uking-qrcode')
}
```

**MainApplication.java**

On top, where imports are:

```java
import net.muding.uking.qrcode.RCTQRCodePackage;
```

Add the `RCTQRCodePackage` class to your list of exported packages.

```java
@Override
protected List<ReactPackage> getPackages() {
    return Arrays.asList(
            new MainReactPackage(),
            new RCTQRCodePackage()
    );
}
```


##Usage

###1. 二维码 QRCode

```javascript
import {QRCode} from 'react-native-uking-qrcode';

<QRCode 
    style={{width:150,height:150}} 
    forecolor="#FF6621" 
    backcolor="#256DDD" 
    text={'by uking'} 
    logo='path' //logo local path 本地路径
    size={150} 
/>
```
###2. 条码 BarCode

```javascript
import {BarCode} from 'react-native-uking-qrcode';

<BarCode
    style={{width:300,height:100}}                    
    text='6923450657713'
    format='EAN_13'
    width={300}
    height={100}
/>
```
条码类型 format :
```
CODABAR
CODE_39
CODE_93
CODE_128
DATA_MATRIX
EAN_8
EAN_13
ITF
MAXICODE
PDF_417
QR_CODE
RSS_14
RSS_EXPANDED
UPC_A
UPC_E
UPC_EAN_EXTENSION

```
###3. 二维码扫描  QRScanner 开发中
