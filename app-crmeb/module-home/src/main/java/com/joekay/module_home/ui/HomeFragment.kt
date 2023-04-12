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
import com.joekay.base.ext.*
import com.joekay.base.multiStateView.MultiStateView
import com.joekay.base.multiStateView.bindMultiState
import com.joekay.base.multiStateView.showLoading
import com.joekay.base.multiStateView.showSuccess
import com.joekay.base.multiStateView.state.LoadingState
import com.joekay.base.multiStateView.state.SuccessState
import com.joekay.base.paging.FooterAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_base.login.interceptor.LoginInterceptChain
import com.joekay.module_base.login.interceptor.NextIntercept
import com.joekay.module_base.login.loginIntercept
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
import com.therouter.TheRouter
import com.therouter.router.Route
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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

    private var mCounter: Runnable? = null

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
            refreshLayout.setOnRefreshListener {
                viewModel.onRefresh()
                postDelayed({ refreshLayout.finishRefresh() }, 2000)
            }
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
        mBinding.homeSecKillLayout.run {
            tvTime.text = "${sec.time.substring(0, 5)} 场"
            var mCount = sec.timeSwap.toLong() - System.currentTimeMillis() / 1000
            if (mCounter == null) {
                mCounter = object : Runnable {
                    override fun run() {
                        mCount--
                        val hour = (mCount / 60 / 60).toInt()
                        tvHour.text = if (hour > 9) "$hour" else "0${hour}"
                        val minute = ((mCount - hour * 60 * 60) / 60).toInt()
                        tvMinute.text = if (minute > 9) "$minute" else "0${minute}"
                        val second = ((mCount - hour * 60 * 60 - minute * 60).toInt())
                        tvSecond.text = if (second > 9) "$second" else "0${second}"
                        if (mCount == 0L) {
                            viewModel.getHomeSecKill()
                        }
                        postDelayed(this, 1000)
                    }
                }
                post(mCounter!!)
            }
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
        }).addBannerLifecycleObserver(this).indicator = CircleIndicator(activity)
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
                    when (homeMenuAdapter.getItem(position).name) {
                        "我的收藏" -> {
                            loginIntercept {
                                "我的收藏".showToast()
                            }
                            //TheRouter.build(ARouterPath.collection_activity).navigation()
                        }

                        "地址管理" -> {
                            loginIntercept {
                                "地址管理".showToast()
                            }
                            //TheRouter.build(ARouterPath.addressList_activity).navigation()
                        }

                        "订单管理" -> {
                            LoginInterceptChain.addInterceptor(NextIntercept {
                                TheRouter.build(RouterPath.act_order).navigation()
                            }).process()
                        }
                        else -> {
                            homeMenuAdapter.getItem(position).name.showToast()
                        }

                    }
                }
            })
            rvHomeMenu.adapter = homeMenuAdapter
        }
    }


    private fun initProductTab(list: MutableList<ExplosiveMoney>) {
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