package com.joekay.module_category.ui

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.joekay.base.adapter.BaseAdapter
import com.joekay.module_base.base.BaseFragment
import com.joekay.module_category.databinding.FragmentCategoryBinding
import com.joekay.module_category.ui.adapter.CategoryLeftAdapter
import com.joekay.module_category.ui.adapter.CategoryRightAdapter
import com.joekay.network.liveData.observeLoading
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.joekay.resource.R.*

@Route(path = RouterPath.frag_category)
@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>() {

    private val viewModel by viewModels<CategoryViewModel>()

    @Inject
    lateinit var leftAdapter: CategoryLeftAdapter

    @Inject
    lateinit var rightAdapter: CategoryRightAdapter

    override fun initObserve() {

        viewModel.categoryList.observeLoading(this, true) {
            onSuccess {
                leftAdapter.setData(it)
                rightAdapter.setData(it)
            }
        }

    }

    override fun initBinding() {
        mBinding.run {
            //ImmersionBar.setTitleBar(getAttachActivity(), toolBar)
            rvLeft.adapter = leftAdapter
            rvRight.adapter = rightAdapter
            val rightManager = mBinding.rvRight.layoutManager as LinearLayoutManager
            rvRight.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val firstItemPosition = rightManager.findFirstVisibleItemPosition()
                    if (firstItemPosition != -1) {
                        rvLeft.smoothScrollToPosition(firstItemPosition)
                        leftAdapter.setSelectedPosition(firstItemPosition)
                    }
                }

            })

            leftAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener {
                override fun onItemClick(
                    recyclerView: RecyclerView?,
                    itemView: View?,
                    position: Int
                ) {
                    leftAdapter.setSelectedPosition(position)
                    rightManager.scrollToPositionWithOffset(
                        position,
                        0
                    )
                }
            })
        }
    }
}