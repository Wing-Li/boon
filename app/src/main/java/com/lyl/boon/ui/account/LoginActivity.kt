package com.lyl.boon.ui.account

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import com.lyl.boon.R
import com.lyl.boon.net.LeanCloudCallBack
import com.lyl.boon.net.LeanCloudNet
import com.lyl.boon.net.entity.UserInfoEntity
import com.lyl.boon.net.model.UserModel
import com.lyl.boon.ui.MainActivity
import com.lyl.boon.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        initListener()
    }

    private fun initListener() {
        findViewById<Button>(R.id.btn_login).setOnClickListener {
            val email = findViewById<EditText>(R.id.edt_login_email).text.trim().toString()
            val password = findViewById<EditText>(R.id.edt_login_password).text.trim().toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) return@setOnClickListener

            LeanCloudNet.signIn(email, password, object : LeanCloudCallBack<UserInfoEntity> {

                override fun onSuccess(t: UserInfoEntity) {
                    mUserModel.saveUserInfo(t)

                    val i = Intent(mContext, MainActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }

                override fun onError(code: Int, msg: String, e: Exception?) {
                    showToast(msg)
                }
            })
        }

        findViewById<Button>(R.id.btn_register).setOnClickListener {
            startActivity(Intent(mContext, RegisterActivity::class.java))
        }
    }

}