package com.lyl.boon.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.crashreport.CrashReport;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lyl on 2016/12/21.
 */
public class AboutActivity extends BaseActivity {

    @Bind(R.id.check_update)
    TextView checkUpdate;
    @Bind(R.id.link_text)
    TextView linkText;
    @Bind(R.id.wing_li)
    TextView wingLi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        initActionbar();
        mActionTitle.setText(getString(R.string.about_title));
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
                openUri("market://details?id=com.lyl.boon");
                break;
            case R.id.wing_li:
                openUri("https://wing-li.github.io/");
                break;
        }
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
