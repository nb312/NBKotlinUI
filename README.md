# NBKotlinUI
[![ui][uisvg]][ui]  
This util is for kotlin in android .
# Start. 
### 1. Add an URL of the repository 
In the build.gradle of the project root.Add this:
```gradle
allprojects {
    repositories {
        //others 
        maven {
            url "https://jitpack.io"
        }
       
    }
}
```
### 2. dependencies
In the `build.gradle` of the module.
```gradle
dependencies {
    implementation 'com.github.nb312:NBKotlinUI:0.0.3'
}
```
### 3. init 
If you want to use the code of this library, you must initialize the param in the onCreate of your Application  as below:
```kotlin
      KotlinUi.context = this
```
if you follow these steps above,then you can use the library as well.


[uisvg]:https://img.shields.io/badge/NBKotlinUI-v0.0.3-green.svg
[ui]: https://github.com/Blankj/AndroidUtilCode
