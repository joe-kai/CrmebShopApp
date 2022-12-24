package com.joekay.module_product.ui

import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import com.alibaba.fastjson2.JSON
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.gyf.immersionbar.ImmersionBar
import com.joekay.base.ext.*
import com.joekay.module_base.base.BaseActivity
import com.joekay.module_base.utils.BundleKey
import com.joekay.module_product.R
import com.joekay.module_product.databinding.ActivityProductDetailBinding
import com.joekay.module_product.model.ProductDetailModel
import com.joekay.module_product.ui.dialog.ProductSpecDialog
import com.joekay.network.liveData.observeLoading
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import com.joekay.resource.R.*

@Route(path = RouterPath.act_productDetail)
@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>() {

    private var productId = ""

    private val viewModel by viewModels<ProductDetailViewModel>()
    private lateinit var productDetailModel: ProductDetailModel


    override fun initObserve() {

        productId = getBundleString(BundleKey.PRODUCT_ID, "68")
        viewModel.getProductDetails(productId)

        viewModel.productDetail.observeLoading(this) {
            onSuccess {
                productDetailModel = it
                initProductInfo()
                initProductWeb()

            }
        }


    }

    override fun initBinding() {
        mBinding.tvCartNum.text = productId
        initProductHeader()

    }

    private fun initProductHeader() {
        mBinding.detailHeader.imvBack.setOnClickListener {
            finish()
        }
        ImmersionBar.setTitleBar(this, mBinding.detailHeader.detailHeaderLayout)

        val tabLayout = mBinding.detailHeader.detailHeaderTabLayout
        tabLayout.removeAllTabs()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    // 根据选中的tab滚动 页面至对应的模块
                    scrollToPositionByTab(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    // 根据选中的tab滚动 页面至对应的模块
                    scrollToPositionByTab(tab.position)
                }
            }

        })
        viewModel.tabs.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }
        mBinding.scrollLayout.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->
            handleScroll(scrollY)
        })
        mBinding.tvAddCart.setOnClickListener {
            ProductSpecDialog.Builder(getContext(), productDetailModel)
                .show()

        }
        mBinding.tvBuyNow.setOnClickListener {
            ProductSpecDialog.Builder(getContext(), productDetailModel)
                .show()
        }

    }

    private var currentTabPosition: Int = 0

    private fun handleScroll(dy: Int) {
        mBinding.run {
            detailHeader.run {
                val alpha = dy / 500f
                if (dy >= getStatusBarHeight()) {
                    detailHeaderLayout.alpha = if (alpha >= 1) {
                        1f
                    } else {
                        alpha
                    }
                    detailHeaderLayout.setBackgroundResource(color.white)
                    detailHeaderTabLayout.visible()
                } else {
                    detailHeaderLayout.alpha = 1f
                    detailHeaderLayout.setBackgroundResource(color.transparent)
                    detailHeaderTabLayout.gone()
                }
            }
            val appraiseToTop =
                detailAppraise.detailAppraiseLayout.top - mBinding.detailHeader.detailHeaderLayout.height
            val goodsDesToTop =
                detailWeb.detailWebLayout.top - mBinding.detailHeader.detailHeaderLayout.height
            val tabPosition = when {
                dy in 0 until appraiseToTop -> 0
                dy < goodsDesToTop -> 1
                else -> 2
            }
            if (currentTabPosition != tabPosition) {
                currentTabPosition = tabPosition
                detailHeader.detailHeaderTabLayout.setScrollPosition(tabPosition, 0f, true)
            }

        }
    }

    private fun scrollToPositionByTab(position: Int) {
        when (position) {
            0 -> mBinding.scrollLayout.smoothScrollTo(0, 0, 200)
            1 -> mBinding.scrollLayout.smoothScrollTo(
                0,
                mBinding.detailAppraise.detailAppraiseLayout.top - mBinding.detailHeader.detailHeaderLayout.height,
                200
            )
            2 -> mBinding.scrollLayout.smoothScrollTo(
                0,
                mBinding.detailWeb.detailWebLayout.top - mBinding.detailHeader.detailHeaderLayout.height,
                200
            )
            //3 -> mBinding.scrollLayout.smoothScrollTo(
            //    0,
            //    mBinding.goodsWeb.top - 50f,
            //    200
            //)

        }

    }

    private fun initProductInfo() {
        val info = productDetailModel.productInfo
        val bannerList: List<String> = JSON.parseArray(info.sliderImage, String::class.java)
        mBinding.detailInfo.run {
            banner.setAdapter(object : BannerImageAdapter<String>(bannerList) {
                override fun onBindView(
                    holder: BannerImageHolder?, data: String?, position: Int, size: Int
                ) {
                    holder!!.imageView.load(data!!)
                    //Glide.with(holder!!.itemView).load(data!!)
                    //    .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                    //    .into(holder.imageView)
                }
            }).addBannerLifecycleObserver(this@ProductDetailActivity).indicator =
                CircleIndicator(this@ProductDetailActivity)
            txvOriginalPrice.text = "¥${info.price}"
            txvGoodsName.text = info.storeName
            viewModel.goodsSpec = productDetailModel.productValue.keys.first()
            txvSpec.text = viewModel.goodsSpec
        }

    }

    private fun initProductWeb() {
        val info = productDetailModel.productInfo
        mBinding.detailWeb.run {
            webDetail.run {
                settings.setSupportZoom(true)//设置是否支持缩放
                settings.useWideViewPort = true
                settings.loadWithOverviewMode = true
                settings.loadsImagesAutomatically = true //支持自动加载图片
                settings.defaultTextEncodingName = "utf-8" //设置编码格式

                loadDataWithBaseURL(
                    null, getHtmlData(info.content), "text/html", "utf-8", null
                )
            }
        }
    }

    private fun getHtmlData(bodyHTML: String): String {
        val head =
            ("<head>" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " + "<style>img{max-width: 100%; width:auto; height:auto;}</style>" + "</head>")
        return "<html>$head<body>$bodyHTML</body></html>"
    }

}