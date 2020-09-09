# BharatScanner - Document Scanner for Android
An android app to support document scanning via BharatScanner, written entirely in Kotlin.

### Getting Started

- Download & Install Android Studio - [from here](https://developer.android.com/studio/)
- Clone the project
- Import the project in Android Studio
- Go to Build > Make Project
- Connect a physical device
- Go to Run > Run 'app'

### Supported Platforms
-----------------------
```
Works on Android 5.0+ (API level 21+) and on Java 8+.
```

### Let's scan the images!
---------------------------
```kotlin
private fun callBharatScannerIntent(){
    val bharatScannerIntent = Intent()
    bharatScannerIntent.setClassName("com.kickhead.camscanner", "com.scanlibrary.CameraActivity");
    bharatScannerIntent.putExtra("external",true)
    startActivityForResult(bharatScannerIntent, 200)
}
```

```kotlin
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
      super.onActivityResult(requestCode, resultCode, data)

      if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
          if (data?.extras != null && data.extras!!.containsKey(Constants.SCANNED_IMAGE_LIST)) {
              val images: MutableList<String?>? = data.extras!!.get(Constants.SCANNED_IMAGE_LIST) as MutableList<String?>
              addItemsToGridView(images)
          }
      }
}
```
