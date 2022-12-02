package com.joekay.module_mine.ui

import androidx.fragment.app.activityViewModels
import com.gyf.immersionbar.ImmersionBar
import com.joekay.base.dialog.BaseDialog
import com.joekay.base.dialog.PayPasswordDialog
import com.joekay.base.ext.load
import com.joekay.base.ext.setSingleClickListener
import com.joekay.base.ext.showToast
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.other.PASSWORD_KEY
import com.joekay.module_base.utils.MMKVUtils
import com.joekay.module_mine.R
import com.joekay.module_mine.databinding.FragmentMineBinding
import com.joekay.resource.R.drawable
import com.joekay.resource.RouterPath
import com.therouter.TheRouter
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
            ImmersionBar.setTitleBar(getAttachActivity(), llTop)

            ivAvatar.load(drawable.ic_avatar, 50f)
            tvStayPayment.text = "1"
            tvStayShipped.text = "11"
            tvStayReceived.text = "21"
            tvStayEvaluate.text = "99+"
            tvAfterSales.text = "29"

            tvPay.setOnClickListener {
                PayPasswordDialog.Builder(requireContext())
                    .setTitle("一个支付Dialog")
                    .setSubTitle("用于购买一个女盆友")
                    .setMoney("100.00")
                    // 设置点击按钮后不关闭对话框
                    .setAutoDismiss(false)
                    .setListener(object : PayPasswordDialog.OnListener {
                        override fun onCompleted(
                            dialog: PayPasswordDialog.Builder,
                            password: String
                        ) {
                            if (MMKVUtils.getString(PASSWORD_KEY) == password) {
                                "正确".showToast()
                                dialog.dismiss()
                            }
                            dialog.clearRecord()
                            "密码错误".showToast()
                        }

                        override fun onCancel(dialog: PayPasswordDialog.Builder) {
                            "取消了".showToast()
                            dialog.dismiss()
                        }
                    })
                    .show()
            }

            tvSettingPayPwd.setOnClickListener {
                var pwd = ""
                PayPasswordDialog.Builder(requireContext())
                    .setTitle("设置支付密码")
                    // 设置点击按钮后不关闭对话框
                    .setAutoDismiss(false)
                    .setListener(object : PayPasswordDialog.OnListener {
                        override fun onCompleted(
                            dialog: PayPasswordDialog.Builder,
                            password: String
                        ) {
                            if (pwd.isNotEmpty()) {
                                if (password == pwd) {
                                    "设置成功".showToast()
                                    MMKVUtils.put(PASSWORD_KEY, password)
                                } else {
                                    "验证失败，请重新设置密码".showToast()
                                }
                                dialog.dismiss()
                            } else {
                                pwd = password
                                dialog.clearRecord()
                                dialog.setSubTitle("请验证密码")
                                "请确认密码".showToast()
                            }
                        }

                        override fun onCancel(dialog: PayPasswordDialog.Builder) {
                            dialog.dismiss()
                        }
                    })
                    .show()
            }
            tvAbout.setOnClickListener {
                TheRouter.build(RouterPath.act_about).navigation()
            }
        }
    }


}