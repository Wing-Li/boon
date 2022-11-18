package com.lyl.boon.net

import android.text.TextUtils
import cn.leancloud.AVException
import cn.leancloud.AVUser
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import cn.leancloud.AVObject
import cn.leancloud.AVQuery
import cn.leancloud.types.AVNull
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
                signIn(email, password, null)
            }

            override fun onError(e: Throwable) {
                callBack.onError(502, "注册失败", null)
            }
        })
    }

    fun signIn(email: String, password: String, callBack: LeanCloudCallBack<UserInfoEntity>?) {
        AVUser.loginByEmail(email, password).subscribe(object : Observer<AVUser> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
            }

            override fun onNext(t: AVUser) {
                callBack?.onSuccess(UserInfoEntity(t.objectId, t.email, t.sessionToken))
            }

            override fun onError(e: Throwable) {
                if (e is AVException) {
                    if (e.code == 210) {
                        callBack?.onError(501, "用户名或密码错误", null)
                    } else {
                        callBack?.onError(501, e.message.toString(), null)
                    }
                } else {
                    callBack?.onError(501, "登录错误", null)
                }
            }
        })
    }

    fun logOut() {
        AVUser.logOut()
    }

    fun getCurrentUser(): AVUser? {
        return AVUser.getCurrentUser()
    }

    const val DB_FAVORITE = "Favorite"
    const val DB_FAVORITE_TITLE = "title"
    const val DB_FAVORITE_AUTHOR = "author"
    const val DB_FAVORITE_URL = "url"
    const val DB_FAVORITE_USER_ID = "userId"

    fun saveFavorite(title: String, author: String, url: String) {
        getCurrentUser()?.let {
            val obj = AVObject(DB_FAVORITE)
            obj.put(DB_FAVORITE_TITLE, title)
            obj.put(DB_FAVORITE_AUTHOR, author)
            obj.put(DB_FAVORITE_URL, url)
            obj.put(DB_FAVORITE_USER_ID, it.objectId)
            obj.saveInBackground().subscribe(object : Observer<AVObject> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: AVObject) {
                }

                override fun onError(e: Throwable) {
                }
            })
        }
    }

    fun deleteFavorite(title: String, author: String, url: String) {
        getCurrentUser()?.let {
            val obj = AVQuery<AVObject>(DB_FAVORITE)
            obj.whereEqualTo(DB_FAVORITE_TITLE, title)
            obj.whereEqualTo(DB_FAVORITE_AUTHOR, author)
            obj.whereEqualTo(DB_FAVORITE_URL, url)
            obj.whereEqualTo(DB_FAVORITE_USER_ID, it.objectId)
            obj.firstInBackground.subscribe(object : Observer<AVObject> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: AVObject) {
                    val objId = t["objectId"] as? String
                    if (!TextUtils.isEmpty(objId)) {
                        val avObject = AVObject.createWithoutData(DB_FAVORITE, objId)
                        avObject.deleteInBackground().subscribe(object : Observer<AVNull> {
                            override fun onComplete() {
                            }

                            override fun onSubscribe(d: Disposable) {
                            }

                            override fun onNext(t: AVNull) {
                            }

                            override fun onError(e: Throwable) {
                            }

                        })
                    }
                }

                override fun onError(e: Throwable) {
                }
            })
        }
    }

    fun isFavorite(title: String, author: String, url: String, callBack: LeanCloudCallBack<Boolean>) {
        getCurrentUser()?.let {
            val obj = AVQuery<AVObject>(DB_FAVORITE)
            obj.whereEqualTo(DB_FAVORITE_TITLE, title)
            obj.whereEqualTo(DB_FAVORITE_AUTHOR, author)
            obj.whereEqualTo(DB_FAVORITE_URL, url)
            obj.whereEqualTo(DB_FAVORITE_USER_ID, it.objectId)
            obj.countInBackground().subscribe(object : Observer<Int> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: Int) {
                    callBack.onSuccess(t > 0)
                }

                override fun onError(e: Throwable) {
                    callBack.onError(502, "", null)
                }
            })
        }
    }

    fun fetchFavorite(callBack: LeanCloudCallBack<List<FavoriteEntity>>) {
        getCurrentUser()?.let {
            val obj = AVQuery<AVObject>(DB_FAVORITE)
            obj.whereEqualTo(DB_FAVORITE_USER_ID, it.objectId)
            obj.findInBackground().subscribe(object : Observer<List<AVObject>> {
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: List<AVObject>) {
                    val data = t.map {
                        FavoriteEntity(
                            it.objectId,
                            it.getString(DB_FAVORITE_TITLE),
                            it.getString(DB_FAVORITE_AUTHOR),
                            it.getString(DB_FAVORITE_URL),
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

}

interface LeanCloudCallBack<T> {

    fun onSuccess(t: T)

    fun onError(code: Int, msg: String, e: Exception?)
}
