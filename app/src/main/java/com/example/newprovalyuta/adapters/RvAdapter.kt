package com.example.newprovalyuta.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newprovalyuta.R
import com.example.newprovalyuta.databinding.RvItemBinding
import com.example.newprovalyuta.room.entity.ValyutaEntity
import com.example.newprovalyuta.utils.MyInterpolator

class RvAdapter(
    var list: ArrayList<ValyutaEntity>,
    var itemClick: itemOnCLick,
    var context: Context
) : ListAdapter<ValyutaEntity, RvAdapter.Vh>(MyDiffUtil()) {
    inner class Vh(var item: RvItemBinding) : RecyclerView.ViewHolder(item.root) {

        fun onBind(valyuta: ValyutaEntity, position: Int) {
            item.tv.text = valyuta.code
            if (valyuta.saved) {
//                val myAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
//                val interpolator = MyInterpolator(0.2, 20.0)
//                myAnim.interpolator = interpolator
                item.save.setImageResource(R.drawable.ic_star)
//                item.save.startAnimation(myAnim)
            } else {
                item.save.setImageResource(R.drawable.ic_unstar)
            }
            item.tv2.text = "UZS"
            item.price.text = "1 ${valyuta.code} = ${valyuta.cb_price} UZS "
            item.image1.setImageResource(valyuta.icon)
            item.image2.setImageResource(R.drawable.ic_uzbekistan_flag)
            item.save.setOnClickListener {
                if (!valyuta.saved) {
                    val myAnim: Animation = AnimationUtils.loadAnimation(context, R.anim.bounce)
                    val interpolator = MyInterpolator(0.2, 20.0)
                    myAnim.interpolator = interpolator
                    item.save.setImageResource(R.drawable.ic_star)
                    item.save.startAnimation(myAnim)
                } else {
                    item.save.setImageResource(R.drawable.ic_unstar)
                }
                itemClick.itemSaveClick(valyuta, position,item)
            }
            item.root.setOnClickListener {
                itemClick.itemClick(valyuta, position)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh = Vh(
        RvItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    class MyDiffUtil() : DiffUtil.ItemCallback<ValyutaEntity>() {


        override fun areItemsTheSame(oldItem: ValyutaEntity, newItem: ValyutaEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ValyutaEntity, newItem: ValyutaEntity): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface itemOnCLick {
        fun itemClick(valyuta: ValyutaEntity, position: Int)
        fun itemSaveClick(valyuta: ValyutaEntity, position: Int,item: RvItemBinding)
    }
}
//
//class RvAdapter(
//    var list: ArrayList<ValyutaEntity>,
//    var itemClick: itemOnCLick,
//    var context: Context
//) :
//    RecyclerView.Adapter<RvAdapter.Vh>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh = Vh(
//        RvItemBinding.inflate(
//            LayoutInflater.from(parent.context), parent, false
//        )
//    )
//
//    override fun onBindViewHolder(holder: Vh, position: Int) {
//        holder.onBind(list[position], position)
//    }
//
//    override fun getItemCount(): Int = list.size
//

//}