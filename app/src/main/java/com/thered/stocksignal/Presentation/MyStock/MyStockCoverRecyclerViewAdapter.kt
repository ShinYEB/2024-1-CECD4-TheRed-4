package com.thered.stocksignal.Presentation.MyStock

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

import com.thered.stocksignal.Presentation.MyStock.placeholder.PlaceholderContent.PlaceholderItem
import com.thered.stocksignal.databinding.FragmentStockCoverBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MystockCoverRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<MystockCoverRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentStockCoverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        holder.priceView.text = item.price
        holder.earnRateView.text = item.earnRate

        Glide.with(holder.imageView.context)
            .load("https://previews.123rf.com/images/vectorchef/vectorchef1506/vectorchef150610882/41187816-금융-주식-아이콘.jpg")
            .apply(RequestOptions().transform(RoundedCorners(80)))
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentStockCoverBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content
        val priceView: TextView = binding.price
        val earnRateView: TextView = binding.earnRate
        val imageView: ImageView = binding.imageView

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}