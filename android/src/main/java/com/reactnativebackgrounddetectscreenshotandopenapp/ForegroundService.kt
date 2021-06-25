package com.reactnativebackgrounddetectscreenshotandopenapp

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.akexorcist.screenshotdetection.ScreenshotDetectionDelegate

class ForegroundService : Service(), ScreenshotDetectionDelegate.ScreenshotDetectionListener {
  companion object {
    const val TAG = "ForegroundService"
    const val NOTIFICATION_ID = 10
    private const val REQUEST_CODE_READ_EXTERNAL_STORAGE_PERMISSION = 3009
    const val CHANNEL_ID = "ForegroundServiceChannel"
  }

  private val binder = LocalBinder()
  private lateinit var screenshotDetectionDelegate:ScreenshotDetectionDelegate

  inner class LocalBinder : Binder() {
    // MainActivity에서 서비스 연결할 때 접근할 수 있는 메서드
    // 서비스 자신을 전달한다
    fun getService(): ForegroundService = this@ForegroundService
  }

  override fun onBind(intent: Intent?): IBinder? {
    return binder
  }

  override fun onCreate() {
    super.onCreate()

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      createNotificationChannel()
    }
  }

  // MainActivity에서 전달받은 activity를 사용해 screenshotDetectionDelegate의 인스턴스를 생성한다
  // 슼
  fun setScreenshotDetectionDelegate(activity: Activity){
    Log.d(TAG, "setScreenshotDetectionDelegate: ")
    screenshotDetectionDelegate = ScreenshotDetectionDelegate(activity, this)
    startScreenshotDetection()
  }

  fun startScreenshotDetection(){
    screenshotDetectionDelegate?.startScreenshotDetection()
  }

  fun stopScreenshotDetection(){
    screenshotDetectionDelegate?.stopScreenshotDetection()
  }


  override fun onScreenCaptured(path: String) {
    Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
    // Do something when screen was captured
  }

  override fun onScreenCapturedWithDeniedPermission() {
    Toast.makeText(this, "Please grant read external storage permission for screenshot detection", Toast.LENGTH_SHORT).show()
    // Do something when screen was captured but read external storage permission has denied
  }

  @RequiresApi(Build.VERSION_CODES.O)
  private fun createNotificationChannel() {
    val channel = NotificationChannel(CHANNEL_ID, "Foreground Service", NotificationManager.IMPORTANCE_DEFAULT)
    val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    manager?.createNotificationChannel(channel)

    val builder = NotificationCompat.Builder(this, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle("MP3")
      .setContentText("Music Playing~~")

    val notification = builder.build()
    startForeground(NOTIFICATION_ID , notification)
  }
}
