package com.joekay.module_home.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.joekay.base.adapter.Adapter
import com.joekay.base.ext.load
import com.joekay.base.ext.showToast
import com.joekay.base.multiStateView.MultiStateView
import com.joekay.base.multiStateView.bindMultiState
import com.joekay.base.multiStateView.state.*
import com.joekay.base.paging.FooterAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_home.R
import com.joekay.module_home.databinding.FragmentHomeBinding
import com.joekay.module_home.model.Banner
import com.joekay.module_home.model.ExplosiveMoney
import com.joekay.module_home.model.Menus
import com.joekay.module_home.ui.adapter.HomeMenuAdapter
import com.joekay.module_home.ui.adapter.HomeProductAdapter
import com.joekay.module_home.ui.adapter.HomeProductTabAdapter
import com.joekay.network.liveData.observeLoading
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

    private lateinit var couponState: MultiStateView
    private lateinit var secKillState: MultiStateView
    private lateinit var groupState: MultiStateView
    private lateinit var haggleState: MultiStateView

    override fun initObserve() {
        viewModel.getHomeData()
        viewModel.homeModel.observeLoading(this, true) {
            onSuccess {
                initBanner(it.banner)
                initMenus(it.menus)
                initProductTab(it.explosiveMoney)
            }
        }
        //viewModel.homeProductList.observeLoading(this, false) {
        //    onSuccess {
        //        productAdapter.addData(it.list)
        //    }
        //}
    }

    override fun initBinding() {
        couponState = mBinding.llCoupon.bindMultiState()
        couponState.show<EmptyState>{
            it.setEmptyMsg("空空如也")
        }
        secKillState = mBinding.llSecKill.bindMultiState()
        secKillState.show<ErrorState> {
            it.setErrorMsg("chu cuo le")
            it.retry {
                "啥都没用".showToast()
            }
        }
        groupState = mBinding.llGroup.bindMultiState()
        groupState.show<LoadingState> {
            it.setLoadingMsg("快快加载···")
        }
        haggleState = mBinding.llHaggle.bindMultiState()
        haggleState.show<LottieState> {
        }
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
                    TheRouter.build(RouterPath.act_productDetail).navigation()
                    productAdapter.getAdapterItem(position).storeName.showToast()
                }

            })
            productAdapter.setOnChildClickListener(
                R.id.imv_product, R.id.txv_product_name,
                listener = object : Adapter.OnChildClickListener {
                    override fun onChildClick(
                        recyclerView: RecyclerView?,
                        childView: View?,
                        position: Int
                    ) {
                        when (childView?.id) {
                            R.id.imv_product -> {
                                "点击了图片".showToast()
                            }
                            R.id.txv_product_name -> {
                                "点击了商品名称".showToast()

                            }
                        }
                    }

                })
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