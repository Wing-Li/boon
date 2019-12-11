package com.lyl.boon.net

import cn.leancloud.AVUser
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import com.lyl.boon.net.entity.FavoriteEntity
import com.lyl.boon.net.entity.UserInfoEntity


object LeanCloudNet {

    fun signUp(email: String, password: String, callBack: LeanCloudCallBack<UserInfoEntity>) {
        // 创建实例
        val user = AVUser()
        user.username = email
        user.email = email
        user.password = password

        user.signUpInBackground().subscribe(object : Observer<AVUser> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: AVUser) {
                callBack.onSuccess(UserInfoEntity(t.objectId, t.email, t.sessionToken))
            }

            override fun onError(e: Throwable) {
                callBack.onError(502, "注册失败", null)
            }
        })
    }

    fun signIn(email: String, password: String, callBack: LeanCloudCallBack<UserInfoEntity>) {
        AVUser.loginByEmail(email, password).subscribe(object : Observer<AVUser> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: AVUser) {
                callBack.onSuccess(UserInfoEntity(t.objectId, t.email, t.sessionToken))
            }

            override fun onError(e: Throwable) {
                callBack.onError(501, "登录错误", null)
            }
        })
    }

    fun logOut() {
        AVUser.logOut()
    }

    fun getCurrentUser(): AVUser? {
        return AVUser.getCurrentUser()
    }

    fun isLogin(): Boolean {
        return getCurrentUser() != null
    }

    fun saveFavorite(title: String, author: String, url: String) {
        val obj = AVObject("Favorite")
        obj.put("title", title)
        obj.put("author", author)
        obj.put("url", url)
        obj.save()
    }

    fun deleteFavorite(objId: String) {
        val obj = AVObject.createWithoutData("Favorite", objId)
        obj.delete()
    }

    fun fetchFavorite(callBack: LeanCloudCallBack<List<FavoriteEntity>>) {
        val obj = AVQuery<AVObject>("Favorite")
        obj.findInBackground().subscribe(object : Observer<List<AVObject>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: List<AVObject>) {
                val data = t.map {
                    FavoriteEntity(
                            it.objectId,
                            it.getString("title"),
                            it.getString("author"),
                            it.getString("url"),
                            it.createdAt
                    )
                }
                callBack.onSuccess(data)
            }

            override fun onError(e: Throwable) {
                callBack.onError(501, "收藏夹获取失败", null)
            }
        })
    }

}

interface LeanCloudCallBack<T> {

    fun onSuccess(t: T)

    fun onError(code: Int, msg: String, e: Exception?)
}
