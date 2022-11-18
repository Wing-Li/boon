package com.lyl.boon.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lyl.boon.R;
import com.lyl.boon.net.LeanCloudNet;
import com.lyl.boon.net.entity.UserInfoEntity;
import com.lyl.boon.net.model.UserModel;
import com.lyl.boon.ui.account.LoginActivity;
import com.lyl.boon.ui.base.BaseActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.leancloud.AVUser;

/**
 * Created by lyl on 2016/12/21.
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.user_layout)
    LinearLayout userLayout;
    @Bind(R.id.user_name)
    TextView userName;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.check_update)
    LinearLayout checkUpdate;
    @Bind(R.id.link_text)
    LinearLayout linkText;
    @Bind(R.id.wing_li)
    TextView wingLi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        initActionbar();
        setBackIcon();

        initView();
        mActionTitle.setText(getString(R.string.about_title));
    }

    private void initView() {
        UserInfoEntity userModel = new UserModel(mContext).getUserInfo();

        if (userModel != null) {
            userLayout.setVisibility(View.VISIBLE);
            userName.setText(userModel.getEmail());
            btnLogin.setText(R.string.user_exit);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userLogout();
                }
            });

        } else {
            userLayout.setVisibility(View.GONE);
            btnLogin.setText(R.string.user_login);
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
            });
        }
    }

    @OnClick({R.id.check_update, R.id.link_text, R.id.wing_li})
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.check_update:
                /**
                 * @param isManual  用户手动点击检查，非用户点击操作请传false
                 * @param isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
                 */
                Beta.checkUpgrade(true, false);
                break;

            case R.id.link_text:
                openUri("https://github.com/Wing-Li/boon");
                break;

            case R.id.wing_li:
                openUri("https://wing-li.github.io/");
                break;
        }
    }

    private void userLogout() {
        LeanCloudNet.INSTANCE.logOut();

        new UserModel(mContext).saveUserInfo(null);

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void openUri(String uri) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            CrashReport.postCatchedException(e);
        }
    }

}
