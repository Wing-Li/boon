package com.lyl.boon.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;

import com.lyl.boon.R;
import com.lyl.boon.ui.BaseActivity;
import com.lyl.boon.ui.fragment.JokeFragment;
import com.lyl.boon.ui.fragment.LearnFragment;
import com.lyl.boon.ui.fragment.SuperBoonFragment;
import com.lyl.boon.ui.fragment.YoungFragment;
import com.lyl.boon.utils.NetUtil;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabClickListener;

import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends BaseActivity {

    private LearnFragment learnFragment;
    private YoungFragment youngFragment;
    private JokeFragment jokeFragment;
    private SuperBoonFragment superFragment;

    private Fragment oldFragment;

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature( Window.FEATURE_ACTION_BAR );
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        ButterKnife.bind( this );

        initActionbar();
        initBottom( savedInstanceState );
        initFragmentContent( savedInstanceState );
    }

    /**
     * 设置中间内容页
     */
    private void initFragmentContent(Bundle savedInstanceState) {
        learnFragment = new LearnFragment();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add( R.id.fragment_content, learnFragment ).commit();
        }
        oldFragment = learnFragment;
    }

    /**
     * 设置底部按钮
     */
    private void initBottom(Bundle savedInstanceState) {
        mBottomBar = BottomBar.attach( this, savedInstanceState );
        // Instead of attach(), use attachShy:

        mBottomBar.noTabletGoodness();
        mBottomBar.setItems( new BottomBarTab( R.drawable.ic_phone_android_black_24dp, R.string.menu_learn ), new BottomBarTab( R.drawable
                .ic_sentiment_very_satisfied_black_24dp, R.string.menu_joke ), new BottomBarTab( R.drawable.ic_pregnant_woman_black_24dp, R.string.menu_young
        ), new BottomBarTab( R.drawable.ic_wc_black_24dp, R.string.menu_super ) );

        mBottomBar.mapColorForTab( 0, "#a8dba8" );
        mBottomBar.mapColorForTab( 1, "#ff5f2e" );
        mBottomBar.mapColorForTab( 2, "#fcbe32" );
        mBottomBar.mapColorForTab( 3, "#bd1550" );

        // Listen for tab changes
        mBottomBar.setOnTabClickListener( bottomBarListener );
    }

    /**
     * 底部按钮的点击事件
     */
    private OnTabClickListener bottomBarListener = new OnTabClickListener() {
        @Override
        public void onTabSelected(int position) {
            switch (position) {
                case 0://学习
                {
                    setActTitle( R.string.menu_learn_msg );
                    toFragment( learnFragment );
                    break;
                }
                case 1://开心
                {
                    checkNet( 1 );
                    break;
                }
                case 2: //美女
                {
                    checkNet( 2 );
                    break;
                }
                case 3: //超级福利
                {
                    checkNet( 3 );
                    break;
                }
                default:
                    break;
            }
        }

        @Override
        public void onTabReSelected(int position) {

        }
    };


    private void setActTitle(int res) {
        mActionTitle.setText( res );
    }


    private void checkNet(final int position) {
        if (NetUtil.isWifi( MainActivity.this )) {
            goFragment( position );
        } else {
            final MaterialDialog mMaterialDialog = new MaterialDialog( this );
            mMaterialDialog.setTitle( "提示" );
            mMaterialDialog.setMessage( "您当前不是WIFI状态，访问会消耗大量的流量，您确定要访问吗？" );
            mMaterialDialog.setPositiveButton( "没事儿拼了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goFragment( position );
                    mMaterialDialog.dismiss();
                }
            } );
            mMaterialDialog.setNegativeButton( "还是不看了", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMaterialDialog.dismiss();
                    showToast( "(*^__^*) 没事去学习学习吧" );
                }
            } );

            mMaterialDialog.show();
        }
    }

    private void goFragment(int position) {
        switch (position) {
            case 1://开心
            {
                setActTitle( R.string.menu_joke_msg );
                if (jokeFragment == null) {
                    jokeFragment = new JokeFragment();
                }
                toFragment( jokeFragment );
                break;
            }
            case 2: //美女
            {
                setActTitle( R.string.menu_young_msg );
                if (youngFragment == null) {
                    youngFragment = new YoungFragment();
                }
                toFragment( youngFragment );
                break;
            }
            case 3: //超级福利
            {
                setActTitle( R.string.menu_super_msg );
                if (superFragment == null) {
                    superFragment = new SuperBoonFragment();
                }
                toFragment( superFragment );
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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setCustomAnimations( android.R.anim.fade_in, android.R.anim.fade_out );
        if (!to.isAdded()) {    // 先判断是否被add过
            transaction.hide( oldFragment ).add( R.id.fragment_content, to ).commit(); // 隐藏当前的fragment，add下一个到Activity中
        } else {
            transaction.hide( oldFragment ).show( to ).commit(); // 隐藏当前的fragment，显示下一个
        }
        oldFragment = to;
    }

}
