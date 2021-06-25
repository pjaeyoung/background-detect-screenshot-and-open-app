package com.reactnativebackgrounddetectscreenshotandopenapp

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.facebook.react.ReactActivity
import com.reactnativebackgrounddetectscreenshotandopenapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


  companion object {
    private val TAG = MainActivity::class.java.simpleName
  }

  private lateinit var binding: ActivityMainBinding
  private lateinit var foregroundService:ForegroundService
  private var bounded = false
  private val mActivity = this
  private val connection = object : ServiceConnection {

    override fun onServiceConnected(className: ComponentName, service: IBinder) {
      val binder = service as ForegroundService.LocalBinder
      foregroundService = binder.getService()
      bounded = true
      // 서비스 연결 시점에 mService가 초기화되었음을 보장하는 순간에
      // ScreenshotDetectionDelegate를 호출할 때 필요한 activity를
      // Service에 전달함
      foregroundService.setScreenshotDetectionDelegate(mActivity)
    }

    override fun onServiceDisconnected(arg0: ComponentName) {
      bounded = false
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      startForegroundService(Intent(this@MainActivity, ForegroundService::class.java))
    } else {
      startService(Intent(this@MainActivity, ForegroundService::class.java))
    }


    binding.btnStopService.setOnClickListener {
      stopService((Intent(this@MainActivity, ForegroundService::class.java)))
    }
  }

  override fun onStart() {
    super.onStart()
    // Bind to LocalService
    Intent(this, ForegroundService::class.java).also { intent ->
      bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }
  }



}

