# HP Finance Detector

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

Android application to detect credit/finance status on used phones before purchasing.

## 📱 Screenshots

<div align="center">

### Main Interface
<img src="screenshots/main-screen.jpg" width="300" alt="Finance Destroyer Main Screen">

### Scanning Process  
<img src="screenshots/scan-process.jpg" width="300" alt="Real-time Scanning">

### Detection Results
<img src="screenshots/scan-results.jpg" width="300" alt="Finance Apps Detected">

</div>

*Professional terminal-style interface with real-time threat detection*

## 🚀 Features

- **📱 Finance App Detection** - Scan for installed credit applications
- **🔍 IMEI Status Check** - Verify against blacklist databases  
- **🔧 System Analysis** - Detect traces of monitoring applications
- **🎵 Audio Feedback** - Sound alerts for detection results
- **🗑️ App Removal** - Uninstall detected finance applications (requires root)

## 📋 Requirements

- Android 5.0 (API level 21) or higher
- USB debugging enabled on target device
- Root access (optional, for advanced features)

## 🛠️ Installation

### From Source

1. Clone the repository:
```bash
git clone https://github.com/bojourx-tos/finance-detector.git
cd finance-detector
```

2. Build the project:
```bash
./gradlew assembleDebug
```

3. Install the APK:
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

### Quick Setup

1. Enable USB debugging on target device
2. Install APK on detector device
3. Grant USB and application access permissions
4. Connect devices and start scanning

## 🎯 Usage

1. **Connect Target Device**: Connect the phone to be scanned via USB with USB debugging enabled
2. **Launch App**: Open HP Finance Detector on your detector device
3. **Start Scan**: Press "MULAI SCAN" button
4. **Review Results**: Check detection results and risk assessment

## 📱 Detected Finance Applications

- Akulaku
- Kredivo
- Home Credit  
- Indodana
- EasyCash
- CashCash
- And more...

## 🏗️ Project Structure

```
app/src/main/java/com/hpdetector/finance/
├── MainActivity.java          # Main application entry
├── ScanActivity.java         # Scanning functionality
├── SimpleDetector.java       # Core detection logic
├── UninstallActivity.java    # App removal features
├── UninstallManager.java     # Uninstall management
├── RootUtils.java           # Root access utilities
└── EnhancedSoundManager.java # Audio feedback system
```

## 🔧 Development

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

### Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 🛣️ Roadmap

- [ ] Remote lock app detection
- [ ] Online IMEI blacklist database
- [ ] File system trace scanning
- [ ] Export scan reports
- [ ] Batch scanning mode
- [ ] Multi-language support
- [ ] Cloud-based detection updates

## 🤝 Contributing

Contributions are welcome! Please read our [Contributing Guidelines](CONTRIBUTING.md) before submitting pull requests.

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## ⚠️ Disclaimer

This application is intended for educational and consumer protection purposes only. Users are responsible for ensuring compliance with applicable laws and regulations in their jurisdiction. Use responsibly and ethically.

## 🔒 Security

For security concerns, please review our [Security Policy](SECURITY.md).

## 📞 Support

If you encounter any issues or have questions:

1. Check existing [Issues](https://github.com/bojourx-tos/finance-detector/issue)
2. Create a new issue with detailed information
3. Follow the issue template for faster resolution

---

**⭐ Star this repository if you find it helpful!**
