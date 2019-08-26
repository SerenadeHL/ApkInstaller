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
    implementation 'com.android.support:support-v4:latestVersion'
}
```
### 2. 安装Apk
```kotlin
ApkInstaller.with(activity).install(apkFile)
```
