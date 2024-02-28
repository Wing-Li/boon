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
import com.lyl.boon.ui.MainActivity
import com.lyl.boon.ui.base.BaseActivity

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initListener()
    }

    private fun initListener() {
        findViewById<Button>(R.id.btn_register).setOnClickListener {
            val email = findViewById<EditText>(R.id.edt_register_email).text.toString().trim()
            val password = findViewById<EditText>(R.id.edt_register_password).text.toString().trim()
            val password2 =
                findViewById<EditText>(R.id.edt_register_password_confirm).text.toString().trim()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(
                    password2
                )
            ) {
                showToast(getString(R.string.toast_register_input_hint))
                return@setOnClickListener
            }

            if (password != password2) {
                showToast(getString(R.string.toast_password_confirm_hint))
                return@setOnClickListener
            }

            register(email, password)

        }
    }

    private fun register(email: String, password: String) {
        LeanCloudNet.signUp(email, password, object : LeanCloudCallBack<UserInfoEntity> {
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

}