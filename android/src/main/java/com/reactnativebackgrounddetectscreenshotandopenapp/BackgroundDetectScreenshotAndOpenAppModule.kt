//feature
package com.reactnativebackgrounddetectscreenshotandopenapp

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.Callback

import android.content.Context
import android.content.Intent
import android.app.Notification
import android.app.NotificationManager


@SuppressWarnings("WeakerAccess")
class BackgroundDetectScreenshotAndOpenAppModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {


    private val reactContext = reactContext

    override fun getName(): String {
        return "BackgroundDetectScreenshotAndOpenApp"
    }

    // Example method
    // See https://reactnative.dev/docs/native-modules-android


  inital fun
   @ReactMethod
   fun multiply(a: Int, b: Int, promise: Promise) {

     promise.resolve(a * b)

   }


}

