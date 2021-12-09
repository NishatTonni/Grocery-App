package com.tonni.groceryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroceryRVAdapter(
    var list: List<GroceryItems> ,
    val groceryItemClickInterface: GroceryItemClickInterface
     ) : RecyclerView.Adapter<GroceryRVAdapter.GroceryViewHolder>() {



    inner class GroceryViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){

        val nameTV = itemView.findViewById<TextView>(R.id.TVItemNameId)
        val quentityTV = itemView.findViewById<TextView>(R.id.TVQuantityId)
        val priceTV = itemView.findViewById<TextView>(R.id.TVPriceId)
        val amountTV = itemView.findViewById<TextView>(R.id.TVTotalAmountId)
        val deleteTV = itemView.findViewById<ImageView>(R.id.IVDeleteId)

    }


    interface GroceryItemClickInterface{
        fun onItemClick(groceryItems: GroceryItems)
    }

    override fun onCreateViewHolder(parent: ViewGroup ,viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.grocery_rv_item,parent,false)
        return GroceryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryViewHolder ,position: Int) {
        holder.nameTV.text = list.get(position).itemName
        holder.quentityTV.text = list.get(position).itemQuantity.toString()
        holder.priceTV.text = "Tk. "+list.get(position).itemPrice.toString()
        val itemTotal : Int = list.get(position).itemPrice * list.get(position).itemQuantity
        holder.amountTV.text = "Tk. "+itemTotal.toString()
        holder.deleteTV.setOnClickListener {
            groceryItemClickInterface.onItemClick(list.get(position))
        }

    }

    override fun getItemCount(): Int {
        return list.size

    }

}