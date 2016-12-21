package com.lyl.boon.main;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.lyl.boon.R;
import com.lyl.boon.framework.base.BaseActivity;
import com.lyl.boon.main.joke.JokeFragment;
import com.lyl.boon.main.learn.LearnFragment;
import com.lyl.boon.main.superboon.SuperBoonFragment;
import com.lyl.boon.main.young.YoungFragment;
import com.lyl.boon.utils.NetStatusUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {

    private LearnFragment learnFragment;
    private YoungFragment youngFragment;
    private JokeFragment jokeFragment;
    private SuperBoonFragment superFragment;

    private Fragment oldFragment;

    @Bind(R.id.bottomBar)
    BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initActionbar();
        setAppAbout();
        initBottom();
        initFragmentContent(savedInstanceState);
    }

    /**
     * 设置中间内容页
     */
    private void initFragmentContent(Bundle savedInstanceState) {
        learnFragment = new LearnFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_content, learnFragment).commit();
        }
        oldFragment = learnFragment;
    }

    /**
     * 设置底部按钮
     */
    private void initBottom() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                switch (tabId) {
                    case R.id.menu_learn: //学习
                        setActTitle(R.string.menu_learn_msg);
                        toFragment(learnFragment);
                        break;
                    case R.id.menu_joke: //开心
                        checkNet(1);
                        break;
                    case R.id.menu_young: //美女
                        checkNet(2);
                        break;
                    case R.id.menu_super: //超级福利
                        checkNet(3);
                        break;
                    default:
                        setActTitle(R.string.menu_learn_msg);
                        toFragment(learnFragment);
                        break;
                }
            }
        });
    }

    /**
     * 检查网络，并跳转
     */
    private void checkNet(final int position) {
        if (NetStatusUtil.isWifi(MainActivity.this)) {
            goFragment(position);
        } else {
            final MaterialDialog mMaterialDialog = new MaterialDialog(this);
            mMaterialDialog.setTitle("提示");
            mMaterialDialog.setMessage("您当前不是WIFI状态，访问会消耗大量的流量，您确定要访问吗？");
            mMaterialDialog.setPositiveButton("没事儿拼了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goFragment(position);
                    mMaterialDialog.dismiss();
                }
            });
            mMaterialDialog.setNegativeButton("还是不看了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    showToast("(*^__^*) 没事去读书学习吧");
                    mBottomBar.selectTabWithId(R.id.menu_learn);
                }
            });

            mMaterialDialog.show();
        }
    }

    private void goFragment(int position) {
        switch (position) {
            case 1://开心
            {
                setActTitle(R.string.menu_joke_msg);
                if (jokeFragment == null) {
                    jokeFragment = new JokeFragment();
                }
                toFragment(jokeFragment);
                break;
            }
            case 2: //美女
            {
                setActTitle(R.string.menu_young_msg);
                if (youngFragment == null) {
                    youngFragment = new YoungFragment();
                }
                toFragment(youngFragment);
                break;
            }
            case 3: //超级福利
            {
                setActTitle(R.string.menu_super_msg);
                if (superFragment == null) {
                    superFragment = new SuperBoonFragment();
                }
                toFragment(superFragment);
                break;
            }
            default:
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param to 下一个Fragment页面
     */
    private void toFragment(Fragment to) {
        if (to == oldFragment) return;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R
                .anim.fade_out);
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide(oldFragment).add(R.id.fragment_content, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide(oldFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
        }
        oldFragment = to;
    }

    private void setActTitle(int res) {
        mActionTitle.setText(getString(res));
    }

}
