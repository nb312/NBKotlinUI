package com.nb.kotlinviewutil

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.nb.kotlinui.adapter.NBaseAdapter
import com.nb.kotlinui.adapter.NBaseDataItemInter
import com.nb.kotlinui.extension.*
import com.nb.kotlinui.inter.IRecyclerFunc
import com.nb.kotlinviewutil.databinding.ItemRecyclerDemoBinding
import kotlinx.android.synthetic.main.activity_recycler.*

/**
 * Created by NieBin on 18-9-13
 * Github: https://github.com/nb312
 * Email: niebin312@gmail.com
 */
class RecyclerDemoActivity : Activity(), IRecyclerFunc {
    private lateinit var demoAdapter: DemoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)
        initRecycler()
    }

    override fun initRecycler() {
        recycler_demo.verticalLine(50)
        demoAdapter = DemoAdapter(this)
        demoAdapter.clickListener = {
            Toast.makeText(this, "you select ${it.content}", Toast.LENGTH_SHORT).show()
        }
        recycler_demo.adapter = demoAdapter
        initRecyclerData()
        recycler_demo.scroll2Top()
    }

    private fun initRecyclerData() {
        var contents = mutableListOf("China", "US", "Apple", "Table")
        var contentItems = mutableListOf<DemoItem>()
        for (index in 1..20) {
            var demoItem = DemoItem("${contents[index.rem(4)]} :$index")
            contentItems.add(demoItem)
        }
        demoAdapter.changeAppendPart(contentItems)
    }

}

class DemoItem(var content: String = "") : NBaseDataItemInter

class DemoAdapter(context: Context) : NBaseAdapter<DemoItem, ItemRecyclerDemoBinding>(context) {
    override var layoutId: Int = R.layout.item_recycler_demo
    override fun ItemRecyclerDemoBinding.onBindView(dataItem: DemoItem) {
        tvContent.text = dataItem.content
    }
}