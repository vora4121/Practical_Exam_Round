package com.android.practicalexamround.ui.homescreen

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.android.practicalexamround.R
import com.android.practicalexamround.databinding.ActivityMainBinding
import com.android.practicalexamround.databinding.DialogViewBinding
import com.android.practicalexamround.model.ListDataModelItem
import com.android.practicalexamround.utils.CheckConnection
import com.android.practicalexamround.utils.NetworkResult
import com.android.practicalexamround.utils.PaginationScrollListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val noteViewModel by viewModels<MainViewModel>()
    private var progressDialog: ProgressDialog? = null
    private var dataListAdapter: DataListAdapter? = null
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val pageStart: Int = 1
    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var totalPages: Int = 20
    private var currentPage: Int = pageStart
    private var arrDataList = ArrayList<ListDataModelItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progressDialog = ProgressDialog(this)
        progressDialog.apply {
            title = "Loading...."
        }

        binding.referase.setOnRefreshListener {
            loadFirstPage()
        }

        binding.rvDataList.layoutManager = GridLayoutManager(this, 2)

        loadFirstPage()
        bindObservers()

        binding.rvDataList.addOnScrollListener(object :
            PaginationScrollListener(binding.rvDataList.layoutManager as GridLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                if (!isLastPage) {
                    loadNextPage()
                }
            }

            override fun getTotalPageCount(): Int {
                return totalPages
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }
        })
    }

    private fun loadNextPage() {
        if (CheckConnection.isConnected(this)) {
            noteViewModel.getData(currentPage.toString(), 20)
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFirstPage() {
        if (CheckConnection.isConnected(this)) {
            noteViewModel.getData("1", 20)
        } else {
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show()
        }
    }


    private fun bindObservers() {

        noteViewModel.LiveData.observe(this) { it ->

            when (it) {
                is NetworkResult.Success -> {
                    progressDialog?.hide()

                    if (currentPage == totalPages) {
                        isLastPage = true
                    }

                    if (binding.referase.isRefreshing) {
                        binding.referase.isRefreshing = false
                    }

                    if (it.data!!.isNotEmpty()) {
                        arrDataList.addAll(it.data)

                        // TODO handle image click and show dialogs
                        dataListAdapter = DataListAdapter {

                            val dialogBinding: DialogViewBinding by lazy {
                                DialogViewBinding.inflate(
                                    layoutInflater
                                )
                            }
                            val dialog = Dialog(this)
                            dialog.setContentView(dialogBinding.root)

                            val window: Window = dialog.window!!
                            window.setLayout(
                                WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.WRAP_CONTENT
                            )
                            window.setGravity(Gravity.CENTER)

                            Glide.with(this)
                                .load(it.download_url)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .downsample(DownsampleStrategy.AT_MOST)
                                .placeholder(R.drawable.loading)
                                .into(dialogBinding.image)

                            dialog.show()

                        }

                        binding.rvDataList.adapter = dataListAdapter
                        dataListAdapter!!.addData(arrDataList)

                    } else {
                        Toast.makeText(this, "Data not available", Toast.LENGTH_SHORT).show()
                    }
                }

                is NetworkResult.Error -> {
                    if (binding.referase.isRefreshing) {
                        binding.referase.isRefreshing = false
                    }
                    progressDialog?.hide()
                    Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is NetworkResult.Loading -> {
                    progressDialog?.show()
                }
            }

        }
    }
}