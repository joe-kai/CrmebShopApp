package com.joekay.module_mine.ui

import androidx.fragment.app.activityViewModels
import com.joekay.base.ext.setSingleClickListener
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_mine.databinding.FragmentMineBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.frag_mine)
class MineFragment : BaseFragment<FragmentMineBinding>() {

    companion object {
        fun newInstance() = MineFragment()
    }

    private val viewModel by activityViewModels<MineViewModel>()

    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {
            root.setSingleClickListener {
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