package com.nb.kotlinui.contactlist

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bigkoo.quicksidebar.QuickSideBarTipsView
import com.bigkoo.quicksidebar.QuickSideBarView
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener
import com.nb.kotlinui.R
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration
import java.security.SecureRandom
import kotlin.coroutines.experimental.coroutineContext

/**
 * Created by NieBin on 2018-07-19
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 * 1. init the view.
 * 2. change the data.
 */

class SideBarHelper<DataItem : ContentItem> : OnQuickSideBarTouchListener {

    private var lettersMap: MutableMap<String, Int> = mutableMapOf()
    private var recyclerView: RecyclerView? = null
    private var quickSideBarView: QuickSideBarView? = null
    private var quickSideBarTipsView: QuickSideBarTipsView? = null
    private var mAdapter: ContentListWithHeadersAdapter<DataItem>? = null
    fun initAdapter(context: Context, parent: View, itemListener: IContentListListener<DataItem>? = null) {
        recyclerView = parent.findViewById<View>(R.id.recyclerView) as RecyclerView
        quickSideBarView = parent.findViewById<View>(R.id.quickSideBarView) as QuickSideBarView
        quickSideBarTipsView = parent.findViewById<View>(R.id.quickSideBarTipsView) as QuickSideBarTipsView
        quickSideBarView?.setOnQuickSideBarTouchListener(this)
        //设置列表数据和浮动header
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = layoutManager

        mAdapter = ContentListWithHeadersAdapter()
        mAdapter?.itemListener = itemListener
        recyclerView?.adapter = mAdapter
        val headersDecor = StickyRecyclerHeadersDecoration(mAdapter)
        recyclerView?.addItemDecoration(headersDecor)
        // Add decoration for dividers between list items
        recyclerView?.addItemDecoration(DividerDecoration(context))

    }

    fun changeData(sourceList: MutableList<DataItem>, hasFirst: Boolean = false) {
        var (sortList, letters, letMap) = sortList<DataItem>(sourceList)
        this.lettersMap.clear()
        lettersMap?.putAll(letMap)
        quickSideBarView?.letters = letters
        mAdapter?.addAll(sortList)
    }

    override fun onLetterTouching(touching: Boolean) {
        //可以自己加入动画效果渐显渐隐
        quickSideBarTipsView?.visibility = if (touching) View.VISIBLE else View.INVISIBLE
    }

    override fun onLetterChanged(letter: String?, position: Int, y: Float) {
        quickSideBarTipsView?.setText(letter, position, y)
        //有此key则获取位置并滚动到该位置
        var hasLetter: Boolean = lettersMap?.containsKey(letter) as Boolean
        if (hasLetter) {
            var pos = lettersMap?.get(letter) as Int
            recyclerView?.scrollToPosition(pos)
        }
    }


    companion object {
        /**It need to back List<ContentItem> , List<HeaderLetter> , Map with <letter, position>
         * @param list the original source that may not sort by its lettersMap.
         * @return list of ContentItem, list of first letter that used for group, map of first letter position.
         * */
        fun <Item : ContentItem> sortList(list: MutableList<Item>, hasFirst: Boolean = false): Triple<MutableList<Item>, MutableList<String>, MutableMap<String, Int>> {
            list.sortBy {
                if (hasFirst) {
                    it.firstLetter?.toUpperCase()
                } else {
                    it.content[0].toString().toUpperCase()
                }
            }
            var letters = mutableListOf<String>()
            var letterMap = mutableMapOf<String, Int>()
            for (i in 0..(list.size - 1)) {
                var first: String = if (hasFirst) {
                    list[i].firstLetter
                } else {
                    list[i].content[0].toUpperCase().toString()
                }
                if (first !in letterMap.keys) {
                    letters.add(first)
                    letterMap[first] = i
                }
                list[i].firstLetter = first
            }
            return Triple(list, letters, letterMap)
        }
    }
}

/**this is for your test.*/
class ContentListWithHeadersAdapter<Item : ContentItem> : ContentListAdapter<Item, RecyclerView.ViewHolder>(), StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private val randomColor: Int
        get() {
            return Color.parseColor("#C0C9D2")
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.nb_view_item, parent, false)
        return object : RecyclerView.ViewHolder(view) {

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val textView = holder.itemView as TextView
        textView.text = getItem(position).content
        holder.itemView.setOnClickListener {
            itemListener?.onItemClick(getItem(position))
        }
    }

    override fun getHeaderId(position: Int): Long {
        return getItem(position).firstLetter[0].toLong()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.nb_view_header, parent, false)
        return object : RecyclerView.ViewHolder(view) {

        }
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val textView = holder.itemView as TextView
        textView.text = getItem(position).firstLetter.toString()
        holder.itemView.setBackgroundColor(randomColor)
    }

    var itemListener: IContentListListener<Item>? = null
}

interface IContentListListener<Item : ContentItem> {
    fun onItemClick(item: Item)
}