package com.android.practicalexamround.ui.homescreen

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.practicalexamround.R
import com.android.practicalexamround.databinding.ItemDataListBinding
import com.android.practicalexamround.model.ListDataModelItem
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class DataListAdapter(var listener: (listDataModelItem: ListDataModelItem) -> Unit) :
    RecyclerView.Adapter<DataListAdapter.ViewHolder>() {

    var listData = ArrayList<ListDataModelItem>()

    public fun addData(data: ArrayList<ListDataModelItem>) {
        listData.clear()
        listData.addAll(data)
        notifyDataSetChanged()
    }


    public class ViewHolder(
        private val itemBinding: ItemDataListBinding,
        private val listener: (listDataModelItem: ListDataModelItem) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(listDataModelItem: ListDataModelItem) {

            Glide.with(itemView.context)
                .load(listDataModelItem.download_url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .downsample(DownsampleStrategy.AT_MOST)
                .placeholder(R.drawable.loading)
                .into(itemBinding.imgView)

            itemBinding.imgView.setOnClickListener {
                listener.invoke(listDataModelItem)
            }


            /*Glide.with(itemBinding.root).asBitmap().load(listDataModelItem.download_url)
                .placeholder(R.drawable.loading)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        var bitmap = Bitmap.createScaledBitmap(
                            resource,
                            listDataModelItem.width,
                            listDataModelItem.height/5,
                            false
                        )
                        itemBinding.imgView.setImageBitmap(bitmap)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }

                })*/

            itemBinding.tvAuthername.text = listDataModelItem.author

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemDataListBinding =
            ItemDataListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size
}