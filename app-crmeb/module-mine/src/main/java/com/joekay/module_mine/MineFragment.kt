package com.joekay.module_mine

import android.util.Log
import androidx.fragment.app.activityViewModels
import com.joekay.base.ktx.setSingleClickListener
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_mine.databinding.FragmentMineBinding
import java.util.*

class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private var viewModel = activityViewModels<MineViewModel>()

    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {
            root.setSingleClickListener {
                val calendar: Calendar = Calendar.getInstance()
                val unixTime: Long = calendar.getTimeInMillis() //这是时间戳
                Log.d("calendar--->>>","当前时间为：" + calendar.get(Calendar.YEAR) +
                        "年 " + calendar.get(Calendar.MONTH) +
                        "月 " + calendar.get(Calendar.DAY_OF_MONTH) +
                        "日 " + calendar.get(Calendar.HOUR_OF_DAY) +
                        "时 " + calendar.get(Calendar.MINUTE) +
                        "分 " + calendar.get(Calendar.SECOND) +
                        "秒")

                println("---${calendar.get(Calendar.SECOND)}")
                //PayPasswordDialog.Builder(mActivity)
                //    .setTitle(getString(R.string.pay_title))
                //    .setSubTitle("用于购买一个女盆友")
                //    .setMoney("￥ 100.00")
                //    // 设置点击按钮后不关闭对话框
                //    .setAutoDismiss(false)
                //    .setListener(object : PayPasswordDialog.OnListener {
                //        override fun onCompleted(dialog: BaseDialog?, password: String) {
                //            if ("666666".equals(password)){
                //                dialog!!.dismiss()
                //            }
                //            password.showToast()
                //        }
                //        override fun onCancel(dialog: BaseDialog?) {
                //            "取消了".showToast()
                //            dialog!!.dismiss()
                //        }
                //    })
                //    .show()
            }
        }
    }


}