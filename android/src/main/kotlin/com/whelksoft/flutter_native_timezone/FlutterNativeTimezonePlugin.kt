package com.whelksoft.flutter_native_timezone

import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.TimeZone

/** FlutterNativeTimezonePlugin */
class FlutterNativeTimezonePlugin: FlutterPlugin, MethodCallHandler {
  private lateinit var channel : MethodChannel

  // This is called when your plugin is attached to the Flutter engine.
  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(
      flutterPluginBinding.binaryMessenger,
      "flutter_native_timezone"
    )
    channel.setMethodCallHandler(this)
  }

  // This handles incoming method calls from Dart.
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "getLocalTimezone" -> {
        try {
          val tz = TimeZone.getDefault().id
          result.success(tz)
        } catch (e: Exception) {
          result.error("UNAVAILABLE", "Could not fetch timezone", null)
        }
      }
      else -> result.notImplemented()
    }
  }

  // This is called when your plugin is detached from the Flutter engine.
  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
