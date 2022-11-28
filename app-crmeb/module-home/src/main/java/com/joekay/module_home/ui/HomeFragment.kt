package com.joekay.module_home.ui

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.gyf.immersionbar.ImmersionBar
import com.joekay.base.adapter.BaseAdapter
import com.joekay.base.ext.showToast
import com.joekay.base.paging.FooterAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_home.databinding.FragmentHomeBinding
import com.joekay.module_home.model.Banner
import com.joekay.module_home.model.ExplosiveMoney
import com.joekay.module_home.model.Menus
import com.joekay.module_home.ui.adapter.HomeMenuAdapter
import com.joekay.module_home.ui.adapter.HomeProductAdapter
import com.joekay.module_home.ui.adapter.HomeProductTabAdapter
import com.joekay.network.liveData.observeLoading
import com.joekay.resource.R.dimen
import com.joekay.resource.RouterPath
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

    @RequiresApi(Build.VERSION_CODES.S)
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
            rvProduct.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rvProduct.adapter =
                productAdapter.withLoadStateFooter(FooterAdapter { productAdapter.retry() })
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
                Glide.with(holder!!.itemView).load(data!!.pic)
                    .transform(
                        RoundedCorners(
                            resources.getDimension(
                                dimen.dp_20
                            ).toInt()
                        )
                    )
                    .into(holder.imageView);
            }
        }).addBannerLifecycleObserver(this).setIndicator(CircleIndicator(activity));
    }


    private fun initMenus(list: MutableList<Menus>) {
        mBinding.run {
            rvHomeMenu.layoutManager = GridLayoutManager(activity, 5)
            homeMenuAdapter.setData(list)
            homeMenuAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
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