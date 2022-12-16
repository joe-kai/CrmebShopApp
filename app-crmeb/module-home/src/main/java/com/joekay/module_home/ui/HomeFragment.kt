package com.joekay.module_home.ui

import android.annotation.SuppressLint
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.joekay.base.adapter.Adapter
import com.joekay.base.ext.gone
import com.joekay.base.ext.isVisible
import com.joekay.base.ext.load
import com.joekay.base.ext.showToast
import com.joekay.base.multiStateView.*
import com.joekay.base.multiStateView.state.*
import com.joekay.base.paging.FooterAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.utils.RouterUtils
import com.joekay.module_home.databinding.FragmentHomeBinding
import com.joekay.module_home.model.Banner
import com.joekay.module_home.model.ExplosiveMoney
import com.joekay.module_home.model.Menus
import com.joekay.module_home.model.SecKillResponse
import com.joekay.module_home.ui.adapter.*
import com.joekay.network.liveData.observeLoading
import com.joekay.network.liveData.observeState
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject


@Route(path = RouterPath.frag_home)
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var homeMenuAdapter: HomeMenuAdapter

    @Inject
    lateinit var productTabAdapter: HomeProductTabAdapter

    @Inject
    lateinit var productAdapter: HomeProductAdapter

    @Inject
    lateinit var couponAdapter: HomeCouponAdapter

    @Inject
    lateinit var combinationAdapter: HomeCombinationAdapter

    @Inject
    lateinit var secKillAdapter: HomeSecKillAdapter

    @Inject
    lateinit var bargainAdapter: HomeBargainAdapter
    override fun initObserve() {
        viewModel.getHomeData()
        viewModel.homeModel.observeLoading(this, true) {
            onSuccess {
                initBanner(it.banner)
                initMenus(it.menus)
                initProductTab(it.explosiveMoney)
            }
        }
        viewModel.getHomeCoupon()
        viewModel.homeCouponList.observeState(this) {
            onSuccess {
                couponAdapter.setData(it)
                mBinding.homeCouponLayout.clCoupon.isVisible(it.isNotEmpty())
            }
            onEmpty {
                mBinding.homeCouponLayout.clCoupon.gone()
            }
        }
        viewModel.getCombinationProductList()
        viewModel.homeCombinationModel.observeState(this) {
            onSuccess {
                combinationAdapter.setData(it.productList)
                mBinding.homeCombinationLayout.rlCombination.isVisible(it.productList.isNotEmpty())
            }
            onEmpty {
                mBinding.homeCombinationLayout.rlCombination.gone()
            }
        }

        viewModel.getHomeSecKill()
        viewModel.homeSecKillModel.observeState(this) {
            onSuccess {
                secKillAdapter.setData(it.productList)
                mBinding.homeSecKillLayout.clSecKill.isVisible(it.productList.isNotEmpty())
                initSecKill(it.secKillResponse)
            }
            onEmpty {
                mBinding.homeSecKillLayout.clSecKill.gone()
            }
        }

        viewModel.getHomeBargain()
        viewModel.homeBargainModel.observeState(this) {
            onSuccess {
                bargainAdapter.setData(it.productList)
                mBinding.homeBargainLayout.clBargain.isVisible(it.productList.isNotEmpty())
            }
            onEmpty {
                mBinding.homeBargainLayout.clBargain.gone()

            }
        }

    }


    override fun initBinding() {
        mBinding.run {
            ImmersionBar.setTitleBar(getAttachActivity(), tbHomeTitle)
            appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
                val alpha = (0 - verticalOffset) / 2
                tbHomeTitle.background.alpha = if (alpha >= 255) {
                    255
                } else {
                    alpha
                }

            }
            rvProduct.adapter =
                productAdapter.withLoadStateFooter(FooterAdapter { productAdapter.retry() })
            productAdapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
                override fun onItemClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    position: Int
                ) {
                    //TheRouter.build(RouterPath.act_productDetail).navigation()
                    RouterUtils.goToProductDetail(productAdapter.getAdapterItem(position).id)
                }

            })
            //productAdapter.setOnChildClickListener(
            //    R.id.imv_product, R.id.txv_product_name,
            //    listener = object : Adapter.OnChildClickListener {
            //        override fun onChildClick(
            //            recyclerView: RecyclerView?,
            //            childView: View?,
            //            position: Int
            //        ) {
            //            when (childView?.id) {
            //                R.id.imv_product -> {
            //                    "点击了图片".showToast()
            //                }
            //                R.id.txv_product_name -> {
            //                    "点击了商品名称".showToast()
            //
            //                }
            //            }
            //        }
            //
            //    })
            refreshLayout.setEnableRefresh(false)
            productAdapter.addLoadStateListener {
                when (it.append) {
                    is LoadState.NotLoading -> {
                        if (it.source.append.endOfPaginationReached) {
                            refreshLayout.setEnableLoadMore(true)
                            refreshLayout.finishLoadMoreWithNoMoreData()
                        } else {
                            refreshLayout.setEnableLoadMore(false)
                        }
                    }

                    is LoadState.Loading -> {}
                    is LoadState.Error -> {
                    }
                }
            }
            homeCouponLayout.rvCoupon.adapter = couponAdapter
            homeSecKillLayout.rvSecKill.adapter = secKillAdapter
            homeCombinationLayout.rvCombination.adapter = combinationAdapter
            homeBargainLayout.rvBargain.adapter = bargainAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initSecKill(sec: SecKillResponse) {
        if (sec==null){
            return
        }
        mBinding.homeSecKillLayout.run {
            tvTime.text = "${sec.time.substring(0, 5)} 场"
            var mCount = sec.timeSwap.toLong() - System.currentTimeMillis() / 1000
            var hour = 0
            var minute = 0
            var second = 0
            val mCounter = object : Runnable {
                override fun run() {
                    mCount--
                    hour = (mCount / 60 / 60).toInt()
                    tvHour.text = if (hour > 9) "$hour" else "0${hour}"
                    minute = ((mCount - hour * 60 * 60) / 60).toInt()
                    tvMinute.text = if (minute > 9) "$minute" else "0${minute}"
                    second = ((mCount - hour * 60 * 60 - minute * 60).toInt())
                    tvSecond.text = if (second > 9) "$second" else "0${second}"
                    if (mCount <= 0) {
                        viewModel.getHomeSecKill()
                    }
                    postDelayed(this, 1000)
                }
            }
            post(mCounter)
        }
    }

    fun getHomeProduct(type: String) {
        lifecycleScope.launchWhenCreated {
            viewModel.getHomeProduct(type).collectLatest {
                productAdapter.submitData(it)
                mBinding.rvProduct.scrollToPosition(0)
            }
        }
    }

    private fun initBanner(bannerList: List<Banner>) {
        mBinding.homeBanner.setAdapter(object : BannerImageAdapter<Banner>(bannerList) {
            override fun onBindView(
                holder: BannerImageHolder?, data: Banner?, position: Int, size: Int
            ) {
                holder!!.imageView.load(data!!.pic, 20f)
                //Glide.with(holder!!.itemView).load(data!!.pic)
                //    .transform(
                //        RoundedCorners(
                //            resources.getDimension(
                //                dimen.dp_20
                //            ).toInt()
                //        )
                //    )
                //    .into(holder.imageView)
            }
        }).addBannerLifecycleObserver(this).setIndicator(CircleIndicator(activity));
    }


    private fun initMenus(list: MutableList<Menus>) {
        mBinding.run {
            rvHomeMenu.layoutManager = GridLayoutManager(activity, 5)
            homeMenuAdapter.setData(list)
            homeMenuAdapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
                override fun onItemClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    position: Int
                ) {
                    homeMenuAdapter.getItem(position).name.showToast()
                }
            })
            rvHomeMenu.adapter = homeMenuAdapter
        }
    }


    private fun initProductTab(list: MutableList<ExplosiveMoney>) {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.HORIZONTAL
        mBinding.rvProductTab.layoutManager = GridLayoutManager(activity, 4)
        productTabAdapter.setData(list)
        mBinding.rvProductTab.adapter = productTabAdapter
        productTabAdapter.setOnProductTabListener(object :
            HomeProductTabAdapter.OnProductTabListener {
            override fun onTabItemSelected(position: Int): Boolean {
                productTabAdapter.setSelectedPosition(position)
                getHomeProduct(productTabAdapter.getItem(position).type)
                return true
            }
        })
        getHomeProduct(list[0].type)
    }

}