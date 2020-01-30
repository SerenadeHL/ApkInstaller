package com.serenade.apkinstaller

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2019-08-26 14:51:13
 */
class InstallPermissionFragment : Fragment() {
    private lateinit var mInstallPermissionCallback: InstallPermissionCallback

    companion object {
        const val REQUEST_CODE = 100

        fun newInstance() = InstallPermissionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置为 true，表示 configuration change 的时候，fragment 实例不会背重新创建
        retainInstance = true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startActivityForResult(callback: InstallPermissionCallback) {
        mInstallPermissionCallback = callback
        val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:${activity?.packageName}"))
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && ::mInstallPermissionCallback.isInitialized) {
            mInstallPermissionCallback.onPermission(resultCode == Activity.RESULT_OK)
        }
    }
}