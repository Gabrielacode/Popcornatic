package com.solt.popcornatic.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.solt.popcornatic.BASE_IMAGE_URL
import com.solt.popcornatic.POSTER_IMAGE_SIZE
import com.solt.popcornatic.R
import com.solt.popcornatic.databinding.ProductionCompanyListItemBinding
import com.solt.popcornatic.movies.data.model.MovieDetailPackage.ProductionCompany

val productionCompaniesDiffUtill = object :DiffUtil.ItemCallback<ProductionCompany?>(){
    override fun areItemsTheSame(oldItem: ProductionCompany, newItem: ProductionCompany): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ProductionCompany,
        newItem: ProductionCompany
    ): Boolean
       =  oldItem == newItem


}

class ProductionCompaniesListAdapter (val movieItemActions: MovieItemActions):ListAdapter<ProductionCompany?,ProductionCompaniesListAdapter.ProductionCompanyViewHolder>(productionCompaniesDiffUtill){
     inner class ProductionCompanyViewHolder( val binding:ProductionCompanyListItemBinding,val movieItemActions: MovieItemActions):ViewHolder(binding.root){
                 fun bind(item:ProductionCompany?){
                     Glide.with(binding.logoImage).load("$BASE_IMAGE_URL$POSTER_IMAGE_SIZE${item?.logoPath}").into(binding.logoImage)
                     binding.root.setOnClickListener{
                         movieItemActions.onClick(binding.root,item?.id?:0)
                     }
                 }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionCompanyViewHolder {
      val binding  = ProductionCompanyListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductionCompanyViewHolder(binding,movieItemActions)
    }

    override fun onBindViewHolder(holder: ProductionCompanyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}