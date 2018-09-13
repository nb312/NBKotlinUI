package com.nb.kotlinui.contactlist

import android.support.v7.widget.RecyclerView


/**
 * Adapter holding a list of animal names of type String. Note that each item must be unique.
 */
abstract class ContentListAdapter<Item : ContentItem, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    private val items = mutableListOf<Item>()

    init {
        setHasStableIds(true)
    }

    fun add(item: Item) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun add(index: Int, item: Item) {
        items.add(index, item)
        notifyDataSetChanged()
    }

    fun addAll(collection: MutableList<Item>?) {
        if (collection != null) {
            items.clear()
            items.addAll(collection)
            notifyDataSetChanged()
        }
    }

    fun appendAll(collection: MutableList<Item>?) {
        if (collection != null) {
            items.addAll(collection)
            notifyDataSetChanged()
        }
    }


    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Item {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
