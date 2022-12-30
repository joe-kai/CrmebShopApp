package com.joekay.module_main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.window.Dialog
import com.joekay.base.ActivityManager
import com.joekay.base.adapter.FragmentPagerAdapter
import com.joekay.base.ext.showToast
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.dialog.UpdateApkDialog
import com.joekay.module_base.login.LoginInterceptCoroutinesManager
import com.joekay.module_base.login.interceptor.LoginInterceptChain
import com.joekay.module_base.login.interceptor.NextIntercept
import com.joekay.module_base.utils.DoubleClickHelper
import com.joekay.module_base.utils.RouterUtils
import com.joekay.module_main.databinding.ActivityMainBinding
import com.joekay.network.liveData.observeLoading
import com.joekay.resource.R
import com.joekay.resource.RouterPath
import com.therouter.TheRouter
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.act_main)
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel by viewModels<MainViewModel>()

    companion object {
        private const val INTENT_KEY_IN_FRAGMENT_INDEX: String = "fragmentIndex"
        private const val INTENT_KEY_IN_FRAGMENT_CLASS: String = "fragmentClass"
    }

    private var pagerAdapter: FragmentPagerAdapter<BaseFragment<*>>? = null

    override fun initObserve() {
        viewModel.getUpdateApk()
        viewModel.updateApk.observeLoading(this, false) {
            onSuccess {
                if (GlobalUtil.appVersionCode < it.versionCode) {
                    UpdateApkDialog.Builder(mActivity!!)
                        //版本名
                        .setVersionName(it.versionName)
                        // 是否强制更新
                        .setForceUpdate(it.force)
                        // 更新日志
                        .setUpdateLog(it.updateMessage)
                        // 下载 URL
                        .setDownloadUrl("${it.updateUrl}${it.fileName}")
                        .show()
                }
            }
        }
        pagerAdapter = FragmentPagerAdapter<BaseFragment<*>>(this).apply {
            addFragment(TheRouter.build(RouterPath.frag_home).createFragment<BaseFragment<*>>()!!)
            addFragment(
                TheRouter.build(RouterPath.frag_category).createFragment<BaseFragment<*>>()!!
            )
            addFragment(
                TheRouter.build(RouterPath.frag_fullVideo).createFragment<BaseFragment<*>>()!!
            )
            addFragment(TheRouter.build(RouterPath.frag_cart).createFragment<BaseFragment<*>>()!!)
            addFragment(TheRouter.build(RouterPath.frag_mine).createFragment<BaseFragment<*>>()!!)
        }

    }

    @SuppressLint("ResourceAsColor")
    override fun initBinding() {
        mBinding.bottomNav.apply {
            itemIconTintList = null
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_video ->
                        setBackgroundColor(getResColor(R.color.black))
                    else ->
                        setBackgroundColor(getResColor(R.color.white))
                }
                when (item.itemId) {
                    R.id.nav_home -> {
                        switchFragment(0)
                    }
                    R.id.nav_category -> {
                        switchFragment(1)
                    }
                    R.id.nav_video -> {
                        switchFragment(2)
                    }
                    R.id.nav_cart -> {
                        //LoginInterceptCoroutinesManager.get()
                        //    .checkLogin({
                        //        RouterUtils.goToLogin()
                        //    }, {
                        //        switchFragment(3)
                        //    })
                        LoginInterceptChain.addInterceptor(NextIntercept {
                            switchFragment(3)
                        }).process()
                    }
                    R.id.nav_mine -> {
                        LoginInterceptCoroutinesManager.get()
                            .checkLogin({
                                RouterUtils.goToLogin()
                            }, {
                                switchFragment(4)
                            })
                    }
                }
                return@setOnItemSelectedListener true
            }
        }
        mBinding.mainPager.adapter = pagerAdapter
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        pagerAdapter?.let {
            switchFragment(it.getFragmentIndex(getBundleSerializable(INTENT_KEY_IN_FRAGMENT_CLASS)))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mBinding.mainPager.let {
            // 保存当前 Fragment 索引位置
            outState.putInt(INTENT_KEY_IN_FRAGMENT_INDEX, it.currentItem)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // 恢复当前 Fragment 索引位置
        switchFragment(savedInstanceState.getInt(INTENT_KEY_IN_FRAGMENT_INDEX))
    }

    private fun switchFragment(fragmentIndex: Int) {
        if (fragmentIndex == -1) {
            return
        }
        when (fragmentIndex) {
            0, 1, 2, 3, 4 -> {
                mBinding.mainPager.currentItem = fragmentIndex
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            getString(R.string.home_exit_hint).showToast()
            return
        }
        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false)
        postDelayed({
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities()
        }, 300)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.mainPager.adapter = null
    }
}