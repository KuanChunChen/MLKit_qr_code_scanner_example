<p align="center">
  <img src="https://github.com/KuanChunChen/MLKit_qr_code_scanner_example/blob/master/app/src/main/res/drawable/kc_icon.png" width="100" />
</p>
<p align="center">
    <h1 align="center">Android QR code scanner example with 100% kotlin.</h1>
</p>
<p align="center">
    <em><code>► Use this example to scanner the QR code!</code></em>
</p>
<p align="center">
	<img src="https://img.shields.io/github/license/KuanChunChen/MLKit_qr_code_scanner_example?style=flat&color=0080ff" alt="license">
	<img src="https://img.shields.io/github/last-commit/KuanChunChen/MLKit_qr_code_scanner_example?style=flat&logo=git&logoColor=white&color=0080ff" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/KuanChunChen/MLKit_qr_code_scanner_example?style=flat&color=0080ff" alt="repo-top-language">
	<img src="https://img.shields.io/github/languages/count/KuanChunChen/MLKit_qr_code_scanner_example?style=flat&color=0080ff" alt="repo-language-count">
<p>
<p align="center">
		<em>Developed with the software and tools below.</em>
</p>
<p align="center">
	<img src="https://img.shields.io/badge/Kotlin-7F52FF.svg?style=flat&logo=Kotlin&logoColor=white" alt="Kotlin">
	<img src="https://img.shields.io/badge/Google-4285F4.svg?style=flat&logo=Google&logoColor=white" alt="Google">
	<img src="https://img.shields.io/badge/Android-3DDC84.svg?style=flat&logo=Android&logoColor=white" alt="Android">
	<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=flat&logo=Gradle&logoColor=white" alt="Gradle">
</p>
<hr>

##  Quick Links

> - [ Overview](#-overview)
> - [ Features](#-features)
> - [ Repository Structure](#-repository-structure)
> - [ Modules](#-modules)
> - [ Getting Started](#-getting-started)
>   - [ Installation](#-installation)
>   - [Running MLKit_qr_code_scanner_example](#-running-MLKit_qr_code_scanner_example)
> - [ Contributing](#-contributing)
> - [ Acknowledgments](#-acknowledgments)

---

##  Overview

<code>► Overview.</code>

Implementing an Android QR code scanner using CameraX and ML Kit. 

The scanner supports features such as auto focus, zoom, and QR code analyze. 

It ensures fast and accurate QR code reading in various lighting conditions.

---

##  Features

<code>► Features</code>

The Android QR code scanner application includes the following features:

CameraX Integration: Utilizes CameraX libraries for efficient camera operations, auto-focus, zoom, and flash control.

ML Kit Barcode Scanning: Leverages Google's ML Kit's barcode-scanning library for fast and accurate QR code reading.

Modern Android Development: Uses core-ktx for Kotlin extensions and appcompat for backward-compatible Android components.

Dependency Injection: Implements koin-android for dependency injection, promoting loose coupling and easier testing.

---

##  Repository Structure

```sh
└── MLKit_qr_code_scanner_example/
    ├── README.md
    ├── app
    │   ├── .gitignore
    │   ├── build.gradle
    │   ├── proguard-rules.pro
    │   └── src
    │       ├── androidTest
    │       │   └── java
    │       │       └── elegant
    │       ├── main
    │       │   ├── AndroidManifest.xml
    │       │   ├── java
    │       │   │   └── elegant
    │       │   └── res
    │       │       ├── drawable
    │       │       ├── drawable-v24
    │       │       ├── layout
    │       │       ├── mipmap-anydpi-v26
    │       │       ├── mipmap-hdpi
    │       │       ├── mipmap-mdpi
    │       │       ├── mipmap-xhdpi
    │       │       ├── mipmap-xxhdpi
    │       │       ├── mipmap-xxxhdpi
    │       │       ├── raw
    │       │       ├── values
    │       │       ├── values-en
    │       │       ├── values-fr
    │       │       ├── values-ja
    │       │       ├── values-ko
    │       │       ├── values-night
    │       │       ├── values-zh-rCN
    │       │       ├── values-zh-rTW
    │       │       └── xml
    │       └── test
    │           └── java
    │               └── elegant
    ├── build.gradle
    ├── gradle
    │   └── wrapper
    │       ├── gradle-wrapper.jar
    │       └── gradle-wrapper.properties
    ├── gradle.properties
    ├── gradlew
    ├── gradlew.bat
    └── settings.gradle
```

---

##  Modules

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example</summary>

| File                                                                                                                                                                             | Summary                         |
| ---                                                                                                                                                                              | ---                             |
| [Styles.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/Styles.kt)               | <code>►To make common activity can be setting when the acitivity is in the module.</code> |
| [MainActivity.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/MainActivity.kt)   | <code>► Main activity.</code> |
| [MLApplication.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/MLApplication.kt) | <code>► Main application.</code> |

</details>

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example.ui.barcode</summary>

| File                                                                                                                                                                                                                      | Summary                         |
| ---                                                                                                                                                                                                                       | ---                             |
| [BaseBarCodeActivity.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/ui/barcode/BaseBarCodeActivity.kt)                   | <code>► Base bar code activity that developer can extension it.</code> |
| [ElegantAccessCodeViewModel.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/ui/barcode/ElegantAccessCodeViewModel.kt)     | <code>► The viewmodel that implement the main qrcode logic.</code> |
| [ElegantAccessBarCodeActivity.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/ui/barcode/ElegantAccessBarCodeActivity.kt) | <code>► The activity that implement the main qrcode logic.</code> |

</details>

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example.di</summary>

| File                                                                                                                                                                        | Summary                         |
| ---                                                                                                                                                                         | ---                             |
| [AppModule.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/di/AppModule.kt) | <code>► Koin DI module to inject viewmodel.</code> |

</details>

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example.constants</summary>

| File                                                                                                                                                                                           | Summary                         |
| ---                                                                                                                                                                                            | ---                             |
| [BarCodeConstant.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/constants/BarCodeConstant.kt) | <code>► base constant defined in here.</code> |

</details>

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example.base.utils</summary>

| File                                                                                                                                                                                                                    | Summary                         |
| ---                                                                                                                                                                                                                     | ---                             |
| [ActivityViewBindingDelegate.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/utils/ActivityViewBindingDelegate.kt) | <code>► To implement viewbinding.</code> |
| [ScanCodeParser.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/utils/ScanCodeParser.kt)                           | <code>► Scan code parser.</code> |
| [PermissionUtil.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/utils/PermissionUtil.kt)                           | <code>► Permission util implement by extension.</code> |

</details>

<details open><summary>app.src.main.java.elegant.access.mlkit.qrcode.scanner.example.base.mlkit</summary>

| File                                                                                                                                                                                                                    | Summary                         |
| ---                                                                                                                                                                                                                     | ---                             |
| [BaseCameraLifecycleObserver.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/mlkit/BaseCameraLifecycleObserver.kt) | <code>►Base camera lifecycler observer to handle careraX.</code> |
| [ScannerAnalyzer.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/mlkit/ScannerAnalyzer.kt)                         | <code>► The scanner analyzer to handle careraX.</code> |
| [ScannerLifecycleObserver.kt](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example.git/blob/master/app/src/main/java/elegant/access/mlkit/qrcode/scanner/example/base/mlkit/ScannerLifecycleObserver.kt)       | <code>► Implement camera lifecycler observer to handle careraX.</code> |

</details>

---

##  Getting Started

###  Installation

1. Clone the MLKit_qr_code_scanner_example repository:

```sh
git clone https://github.com/KuanChunChen/MLKit_qr_code_scanner_example
```

2. Change to the project directory:

```sh
cd MLKit_qr_code_scanner_example
```

3. Install the dependencies:

```sh
./gradlew build
```

###  Running `MLKit_qr_code_scanner_example`

Find the output apk to install into your Android device,
or use IDE to auto install it.

##  Contributing

Contributions are welcome! Here are several ways you can contribute:

- **[Submit Pull Requests](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example/blob/main/CONTRIBUTING.md)**: Review open PRs, and submit your own PRs.
- **[Report Issues](https://github.com/KuanChunChen/MLKit_qr_code_scanner_example/issues)**: Submit bugs found or log feature requests for the `MLKit_qr_code_scanner_example` project.

<details closed>
    <summary>Contributing Guidelines</summary>

1. **Fork the Repository**: Start by forking the project repository to your github account.
2. **Clone Locally**: Clone the forked repository to your local machine using a git client.
   ```sh
   git clone https://github.com/KuanChunChen/MLKit_qr_code_scanner_example
   ```
3. **Create a New Branch**: Always work on a new branch, giving it a descriptive name.
   ```sh
   git checkout -b new-feature-x
   ```
4. **Make Your Changes**: Develop and test your changes locally.
5. **Commit Your Changes**: Commit with a clear message describing your updates.
   ```sh
   git commit -m 'Implemented new feature x.'
   ```
6. **Push to GitHub**: Push the changes to your forked repository.
   ```sh
   git push origin new-feature-x
   ```
7. **Submit a Pull Request**: Create a PR against the original project repository. Clearly describe the changes and their motivations.

Once your PR is reviewed and approved, it will be merged into the main branch.

</details>

---

##  Acknowledgments

- You can find the article that show you how to implement this project by following website:[https://elegantaccess.org/](https://elegantaccess.org/)

[**Return**](#-quick-links)

---
