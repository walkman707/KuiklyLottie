# kLottieView
本组件是基于 [Kuikly 扩展原生View](https://kuikly.tds.qq.com/DevGuide/expand-native-ui.html) 对 airbnb 的开源组件 [lottie](http://airbnb.io/lottiee) 的跨端封装，适用于`Kuikly`工程中使用。

支持平台：
- Android
- iOS
- ohosArm64（鸿蒙）

## 1.简介
kLottieView 是对 airbnb 的开源组件 [lottie](http://airbnb.io/lottiee) 的 [Kuikly扩展View](https://kuikly.tds.qq.com/DevGuide/expand-native-ui.html) 封装，支持播放  Adobe After Effects 的导出动画。

## 2.API文档

| API               | 分类  | 描述                                       |
|-------------------|-----|------------------------------------------|
| src               | 属性  | lottie资源本地asset路径，String类型               |
| loop              | 属性  | lottie资源加载成功后，是否无限循环播放，Boolean类型，默认 true |
| autoPlay          | 属性  | lottie资源加载成功后，是否开始自动播放，Boolean类型，默认 true |
| play              | 方法  | 开始（从头）播放                                 |
| pause             | 方法  | 暂停播放                                     |
| resume            | 方法  | 继续播放                                     |
| progress          | 方法  | 修改播放进度，参数类型为 Float，取值范围[0, 1]            |
| onAnimationUpdate | 事件  | 监听播放进度回调，参数类型为 Float，取值范围[0, 1]          |



## 3.快速入门及代码示例
### 3.1 跨端侧
1. 指定仓库

   mavenCentral()
2. 添加kLottieView依赖

   参考shared模块： 在shared/build.gradle.kts中增加安卓和iOS构建对kLottieView依赖
```kotlin
...
val commonMain by getting {
   dependencies {
      implementation("com.tencent.kuikly-open:core:${Version.getKuiklyVersion()}")
      implementation("com.tencent.kuikly-open:core-annotations:${Version.getKuiklyVersion()}")
      implementation("io.github.walkman707:kLottieView:1.0.0")
   }
}
...
```
   在shared/build.ohos.gradle.kts中增加Ohos构建对kLottieView依赖
```kotlin
...
val commonMain by getting {
   dependencies {
      implementation("com.tencent.kuikly-open:core:${Version.getKuiklyOhosVersion()}")
      implementation("com.tencent.kuikly-open:core-annotations:${Version.getKuiklyOhosVersion()}")
      implementation("io.github.walkman707:kLottieView:1.0.0-ohos")
   }
}
...
```
3. 代码示例

```kotlin
...
LottieView {
   attr {
      size(96f, 96f)
      src("common/Juggling_Monkey.json")
      autoPlay(true)
   }
}
...
```

### 3.2 安卓侧
1. 指定仓库：

   mavenCentral()
2. 添加依赖

```kotlin
dependencies {
   implementation("io.github.walkman707:kLottieViewAndroid:1.0.0")  // 发布产物测试
   implementation("com.airbnb.android:lottie:6.4.0")
}
```
3. 注册kLottieViewAndroid到Kuikly中

```kotlin
   override fun registerExternalRenderView(kuiklyRenderExport: IKuiklyRenderExport) {
   super.registerExternalRenderView(kuiklyRenderExport)
   with(kuiklyRenderExport) {
      renderViewExport(AndroidLottieView.COMPONENT_NAME, { context ->
         AndroidLottieView(context)
      })
   }
}
```

### 3.3 iOS侧
1. 添加依赖

```script
pod 'kLottieViewIOS'
```
2. 注册kLottieViewIOS到Kuikly中

   iOS侧的kuikly组件是通过运行时暴露给Kuikly侧，因此无需手动注册

### 3.4 Ohos侧
1. 添加依赖

   在需要entry模块，添加
```script
   "@walkman707/kLottieViewOhos": '1.0.0'
```

2. 注册kLottieViewOhos到Kuikly中
```ts
export class KuiklyViewDelegate extends IKuiklyViewDelegate {
  getCustomRenderViewCreatorRegisterMap(): Map<string, KRRenderViewExportCreator> {
    const map: Map<string, KRRenderViewExportCreator> = new Map();
    map.set(KRMyView.VIEW_NAME, () => new KRMyView());
    map.set(KTLottieViewImpl.VIEW_NAME, () => new KTLottieViewImpl)
    return map;
  }
```

## 开发指引
### 工程说明
1. 本工程通过Kuikly模板工程创建。在模板工程基础上，将Kuikly扩展组件所需要的跨端模块、各Native View模块按独立模块组织，发布独立产物，供业务共享集成。
2. 本工程kLottieView模块，采用Ohos单独配置编译链方案。即，安卓/iOS制品编译链，通过kLottiView/build.gradle.kts构建，Ohos制品通过kLottieView/build.ohos.gradle.kts构建。

### 工程目录结构
```shell
.
├── LICENSE
├── README.md
├── androidApp # kLottieView 使用示例
├── build.gradle.kts # Android/iOS根构建脚本
├── build.ohos.gradle.kts # Ohos根构建脚本
├── buildSrc
├── gradle
├── gradle.properties
├── gradlew
├── gradlew.bat
├── h5App
├── iosApp # kLottieView 使用示例
├── kLottieView  # kLottieView跨端Kuikly侧实现
├── kLottieViewAndroid # Android侧原生kLottieViewAndroid，暴露给Kuikly侧
├── kLottieViewIOS # iOS侧原生kLottieViewIOS，暴露给Kuikly侧
├── kLottieViewIOS.podspec
├── kLottieViewOhos # Ohos侧原生kLottieViewOhos，暴露给Kuikly侧
├── local.properties
├── miniApp
├── ohosApp # kLottieView 使用示例
├── package.json
├── settings.gradle.kts
├── settings.ohos.gradle.kts
└── shared # Kuikly跨端侧使用kLottieView Demo示例

```
### 版本发布
本组件已发布到各平台标准仓库。如果开发者需要发布到自己私仓，可参考本节指引发布。 
#### kLottieView模块
##### Android/iOS跨端产物发布
1. 配置发布仓库：

   修改build.gradle.kts发布仓库配置
```script
publishing {
...
            mavenLocal()
            // 发布到build/repo目录
//            maven {
//                url = uri(layout.buildDirectory.dir("repo"))
//            }
        }
...
}
```
2. 配置 maven 发布所需参数环境变量

   参考KotlinbuildVar.kt
```kotlin
fun MavenPublication.signPublicationIfKeyPresent(project: Project) {
   val keyId = getSensitiveProperty(project, "signing_keyId")
   val secretKey = getSensitiveProperty(project, "signing_secretKey")
   val password = getSensitiveProperty(project, "signing_password")
   println("signPublicationIfKeyPresent keyId:  $keyId , password: $password , secreckey: $secretKey")
   if (!secretKey.isNullOrBlank()) {
      project.extensions.configure<SigningExtension>("signing") {
         useInMemoryPgpKeys(keyId, secretKey, password)
         sign(this@signPublicationIfKeyPresent)
      }
   }
}
```
3. 配置发布版本：

   MavenConfig.VERSION
4. 运行kLottieView模块的 publish任务

##### Ohos跨端产物发布
1. 配置发布仓库

   修改build.ohos.gradle.kts发布仓库配置
2. 配置 maven 发布所需参数环境变量(同上一步)
3. 配置发布版本：MavenConfig.VERSION_OHOS

   注意，Ohos跨端产物必须独立版本号，如本工程是在版本号后增加-ohos标识。如果不独立版本号，在运行上一步安卓/iOS跨端产物发布后，再运行Ohos发布脚本，则分两次发布会引起maven元数据混乱。
4. 运行发布脚本

   ./gradlew -c settings.ohos.gradle.kts :kLottieView:publish

#### kLottieViewAndroid模块发布
1. 参考kLottieViewAndroid模块，在模块目录下，新建publish.gradle发布脚本
2. build.gradle.kts，引用该发布脚本

   apply(from = "./publish.gradle")
3. 配置发布仓库
```script
publishing {
...
   repositories {
   mavenLocal()
   //        maven {
   //          url = layout.buildDirectory.dir("repo")
   //        }
   }
...
}
```
4. 配置发布版本和发布制品

   version = MavenConfig.VERSION
   MavenConfig.ANDROID_PUBLISH_ARTIFACT_ID
   artifact("$buildDir/outputs/aar/kLottieViewAndroid-release.aar")
5. 运行kLottieViewAndroid模块的 publish 任务

#### kLottieViewIOS模块发布
1. 配置发布版本

   修改kLottieViewIOS.podspec spec.version版本
2. 运行发布脚本

   pod trunk push kLottieViewIOS.podspec

#### kLottieViewOhos模块发布
1. 配置发布版本

   修改kLottieViewOhos模块下oh-package.json5的version字段
2. 构建kLottieViewOhos Har包

   选中kLottieViewOhos模块，在DevEco IDE的Build菜单下，选择Make Module 'kLottieViewOhos'
3. 运行发布脚本

   ohpm publish <HAR包路径>(默认build/default/outputs/default/kLottieViewOhos.har)
