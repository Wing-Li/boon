package com.lyl.boon.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lyl.boon.R;

/**
 * Wing_Li
 * 2016/3/30.
 */
public class BaseActivity extends AppCompatActivity {

    // 界面顶部Bar
    public Toolbar mToolbar;

    public TextView mActionTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 初始化actionbar
     */
    protected void initActionbar() {
        mToolbar = findViewById(R.id.toolbar);
        mActionTitle = findViewById(R.id.actionbar_title);

        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        setTitleAnims();
    }

    private void setTitleAnims() {
//        mActionTitle.setFactory(new ViewSwitcher.ViewFactory() {
//            @Override
//            public View makeView() {
//                final TextView textView = new TextView(BaseActivity.this);
//                textView.setSingleLine(true);
//                textView.setTextAppearance(BaseActivity.this, R.style.action_title_style);
//                textView.setGravity(Gravity.CENTER);
//                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//                textView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        textView.setSelected(true);
//                    }
//                }, 1738);
//                return textView;
//            }
//        });
//        mActionTitle.setInAnimation(this, android.R.anim.fade_in);
//        mActionTitle.setOutAnimation(this, android.R.anim.fade_out);
    }

    public void setBackIcon() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setAppAbout() {
//        mActionBack.setVisibility(View.GONE);
//        mActionRightImg.setVisibility(View.VISIBLE);
//        mActionRightImg.setImageResource(R.drawable.ic_info_outline_black_24dp);
//        mActionRightImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(BaseActivity.this, AboutActivity.class));
//            }
//        });
    }

    public void setShareIcon(final String shareTitle, final String shareContent) {
//        mActionRightImg.setVisibility(View.VISIBLE);
//        mActionRightImg.setImageResource(R.drawable.ic_share_black_24dp);
//        mActionRightImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                shareContent(shareTitle, shareContent);
//            }
//        });
    }

    /**
     * 设置导航栏的标题
     */
    public void setActTitle(int res) {
        if (mActionTitle != null){
            mActionTitle.setText(getString(res));
        }
    }

    public void shareContent(String shareTitle, String text) {
        if (TextUtils.isEmpty(text)) {
            showToast(R.string.share_err);
        } else {
            share(shareTitle, text);
        }
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int res) {
        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void share(String shareTitle, String str) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, shareTitle);
        intent.putExtra(Intent.EXTRA_TEXT, str);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, shareTitle));
    }

}
