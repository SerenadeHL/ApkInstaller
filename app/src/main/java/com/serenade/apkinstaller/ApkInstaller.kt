package com.serenade.apkinstaller

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.FragmentActivity
import android.support.v4.content.FileProvider
import java.io.File

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2019-08-26 14:40:33
 */
class ApkInstaller private constructor(private val mActivity: FragmentActivity) {
    private var mFragment: InstallPermissionFragment? = null

    private var mAuthority: String = "${mActivity.packageName}.fileProvider"

    companion object {
        private const val INTENT_TYPE = "application/vnd.android.package-archive"
        private const val TAG = "InstallPermissionFragment"

        fun with(activity: FragmentActivity): ApkInstaller {
            return ApkInstaller(activity)
        }
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//只有8.0才初始化
            mFragment = getInstallPermissionFragment()
        }
    }

    private fun getInstallPermissionFragment(): InstallPermissionFragment {
        var installPermissionFragment = findInstallPermissionFragment()
        if (installPermissionFragment == null) {
            installPermissionFragment = InstallPermissionFragment.newInstance()
            val fragmentManager = mActivity.supportFragmentManager
            fragmentManager
                .beginTransaction()
                .add(installPermissionFragment, TAG)
                .commitAllowingStateLoss()
            fragmentManager.executePendingTransactions()
        }
        return installPermissionFragment
    }

    private fun findInstallPermissionFragment(): InstallPermissionFragment? {
        return mActivity.supportFragmentManager?.findFragmentByTag(TAG) as? InstallPermissionFragment
    }

    fun install(apk: String) {
        install(File(apk))
    }

    fun install(apk: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val installAllowed = mActivity.packageManager.canRequestPackageInstalls()
            if (installAllowed) {
                installApkCompat(apk)
            } else {
                mFragment?.startActivityForResult(object : InstallPermissionCallback {
                    override fun onPermission(grantPermission: Boolean) {
                        if (grantPermission) {
                            //用户授权
                            install(apk)
                        } else {
                            //用户取消开启权限
                            //TODO 回调
                        }
                    }
                })
            }
        } else {
            installApkCompat(apk)
        }
    }

    private fun installApkCompat(apk: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            val contentUri = FileProvider.getUriForFile(mActivity, mAuthority, apk)
            intent.setDataAndType(contentUri, INTENT_TYPE)
        } else {
            intent.setDataAndType(Uri.fromFile(apk), INTENT_TYPE)
        }
        mActivity.startActivity(intent)
    }
}