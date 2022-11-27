package com.joekay.base.dialog

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joekay.base.R
import com.joekay.base.databinding.FragmentShareDialogBinding
import com.joekay.base.ext.setDrawable
import com.joekay.base.ext.share
import com.joekay.base.utils.*

/**
 * @author:  JoeKai
 * @date:  2022/11/27
 * @explain：
 */
class ShareDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentShareDialogBinding? = null

    private val binding
        get() = _binding!!


    private lateinit var shareContent: String

    private lateinit var attachedActivity: Activity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentShareDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let { act ->
            attachedActivity = act
            binding.tvToWechatFriends.setDrawable(ContextCompat.getDrawable(act, R.drawable.ic_share_wechat_black_30dp), 30f, 30f, 1)
            binding.tvShareToWeibo.setDrawable(ContextCompat.getDrawable(act, R.drawable.ic_share_weibo_black_30dp), 30f, 30f, 1)
            binding.tvShareToQQ.setDrawable(ContextCompat.getDrawable(act, R.drawable.ic_share_qq_black_30dp), 30f, 30f, 1)
            binding.tvShareToQQzone.setDrawable(ContextCompat.getDrawable(act, R.drawable.ic_share_qq_zone_black_30dp), 30f, 30f, 1)

            binding.tvShareToQQ.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_QQ)
                dismiss()
            }
            binding.tvToWechatFriends.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_WECHAT)
                dismiss()
            }
            binding.tvShareToWeibo.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_WEIBO)
                dismiss()
            }
            binding.tvShareToQQzone.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_QQZONE)
                dismiss()
            }
            binding.llMore.setOnClickListener {
                share(attachedActivity, shareContent, SHARE_MORE)
                dismiss()
            }
            binding.tvCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    fun showDialog(activity: AppCompatActivity, shareContent: String) {
        //if (shareContent.contains(WebViewActivity.DEFAULT_URL)) {
        //    MobclickAgent.onEvent(activity, Const.Mobclick.EVENT1)
        //} else {
        //    MobclickAgent.onEvent(activity, Const.Mobclick.EVENT2)
        //}
        show(activity.supportFragmentManager, "share_dialog")
        this.shareContent = shareContent
    }
}