package com.joekay.module_mine.ui

import android.widget.Toast
import com.joekay.base.R
import com.joekay.base.widgets.roulette.LuckyView.OnLuckAnimationEndListener
import com.joekay.base.widgets.roulette.Prize
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_mine.databinding.ActivityRouletteBinding
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint


@Route(path = RouterPath.act_roulette)
@AndroidEntryPoint
class RouletteActivity : BaseActivity<ActivityRouletteBinding>() {

    private var mIsClickStart = false//默认为false避免还没点击开始转动就会提示

    private var prizeList = mutableListOf<Prize>()
    override fun initObserve() {
    }

    override fun initBinding() {
        mBinding.run {

            prizeList.add(Prize("单反相机", R.mipmap.danfan, R.color.progress_color))
            prizeList.add(Prize("IPad", R.mipmap.ipad, R.color.common_accent_color))
            prizeList.add(Prize("谢谢惠顾", R.mipmap.f015, R.color.common_cancel_text_color))
            prizeList.add(Prize("衣服一套", R.mipmap.meizi, R.color.common_confirm_text_color))
            prizeList.add(Prize("IPad", R.mipmap.ipad, R.color.colorAccent))
            prizeList.add(Prize("Iphone", R.mipmap.iphone, R.color.common_accent_color))
            prizeList.add(Prize("衣服一套", R.mipmap.meizi, R.color.grayDark))
            prizeList.add(Prize("恭喜发财", R.mipmap.f040, R.color.refresh_header_color))

            lsLucky.setPrizeList(prizeList)
            lsLucky.setOnSpanRollListener { speed ->
                if (0.0 == speed) {
                    //已经停止下来了 提示中奖名并释放按钮
                    ivStart.isEnabled = true
                    if (mIsClickStart) {
                        runOnUiThread {
                            Toast.makeText(
                                applicationContext,
                                "恭喜${prizeList[lsLucky.luckyIndex].prizeName}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            mIsClickStart = false
                        }
                    }
                }
            }

            ivStart.setOnClickListener {
                ivStart.isEnabled = false
                mIsClickStart = true
                //传入的参数由后台返回指定中哪个奖项
                lsLucky.luckyStart(2, 3)
            }
            luckyView.setLuckAnimationEndListener { pos, msg ->
                Toast.makeText(
                    applicationContext,
                    "恭喜${msg}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}