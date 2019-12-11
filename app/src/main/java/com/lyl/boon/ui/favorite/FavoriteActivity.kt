package com.lyl.boon.ui.favorite

import android.os.Bundle
import com.lyl.boon.R
import com.lyl.boon.ui.base.BaseActivity

class FavoriteActivity : BaseActivity() {

    var mFragment: FavoriteFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorite)

        initViews()
    }

    private fun initViews() {
        mFragment = FavoriteFragment()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.replace(R.id.fragment_content, mFragment!!)
        beginTransaction.commit()
    }

}