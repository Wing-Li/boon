package com.lyl.boon.net.model

import android.content.Context
import android.text.TextUtils
import com.google.gson.Gson
import com.lyl.boon.net.entity.UserInfoEntity
import com.lyl.boon.utils.SPUtils

class UserModel(var mContext: Context) {

    fun saveUserInfo(user: UserInfoEntity) {
        SPUtils.put(mContext, "userInfo", Gson().toJson(user))
    }

    fun getUserInfo(): UserInfoEntity? {
        val userJson = SPUtils.get(mContext, "userInfo", "") as? String
        if (!TextUtils.isEmpty(userJson)) {
            return Gson().fromJson(userJson, UserInfoEntity::class.java)
        }
        return null
    }

}