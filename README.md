# Android Apk安装器
>只需要一行代码安装已有Apk

## 使用
### 1. 添加Gradle依赖
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
dependencies {
    implementation 'com.github.SerenadeHL:ApkInstaller:1.0.0'
}
```
### 2. 安装Apk
```kotlin
ApkInstaller.with(activity).install(apkFile)
```
## 注意
- 安装前开发者需自行判断、请求SD卡权限