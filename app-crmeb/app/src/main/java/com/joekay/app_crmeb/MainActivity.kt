package com.joekay.app_crmeb

import android.content.Intent
import android.os.Bundle
import com.joekay.app_crmeb.databinding.ActivityMainBinding
import com.joekay.base.ActivityManager
import com.joekay.base.adapter.FragmentPagerAdapter
import com.joekay.base.dialog.AppUpdateDialog
import com.joekay.base.ktx.showToast
import com.joekay.base.utils.GlobalUtil
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.utils.DoubleClickHelper
import com.joekay.module_cart.CartFragment
import com.joekay.module_category.CategoryFragment
import com.joekay.module_home.HomeFragment
import com.joekay.module_mine.MineFragment
import com.joekay.resource.R.id.*
import com.joekay.resource.R.string.*

class MainActivity : BaseActivity<ActivityMainBinding>() {

    companion object {
        private const val INTENT_KEY_IN_FRAGMENT_INDEX: String = "fragmentIndex"
        private const val INTENT_KEY_IN_FRAGMENT_CLASS: String = "fragmentClass"
    }

    private var pagerAdapter: FragmentPagerAdapter<BaseFragment<*>>? = null

    override fun initObserve() {
        pagerAdapter = FragmentPagerAdapter<BaseFragment<*>>(this).apply {
            addFragment(HomeFragment())
            addFragment(CategoryFragment())
            addFragment(CartFragment())
            addFragment(MineFragment())
        }
        // 升级对话框
        AppUpdateDialog.Builder(this)
            // 版本名
            .setVersionName("5.2.0")
            // 是否强制更新
            .setForceUpdate(false)
            // 更新日志
            .setUpdateLog("到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥\n到底更新了啥")
            // 下载 URL
            .setDownloadUrl("https://dldir1.qq.com/weixin/android/weixin807android1920_arm64.apk")
            // 文件 MD5
            .setFileMd5("df2f045dfa854d8461d9cefe08b813c8")
            .show()

    }


    override fun initBinding() {
        mBinding.bottomNav.apply {
            itemIconTintList = null
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    nav_home -> {
                        switchFragment(0)
                    }
                    nav_category -> {
                        switchFragment(1)
                    }
                    nav_cart -> {
                        switchFragment(2)
                    }
                    nav_mine -> {
                        switchFragment(3)
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
            0, 1, 2, 3 -> {
                mBinding.mainPager.currentItem = fragmentIndex
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            GlobalUtil.getString(home_exit_hint).showToast()
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