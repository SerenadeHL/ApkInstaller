package com.serenade.apkinstaller

/**
 * 作者：Serenade
 * 邮箱：SerenadeHL@163.com
 * 创建时间：2019-08-26 15:41:45
 */
interface InstallPermissionCallback {
    fun onPermission(grantPermission: Boolean)
}