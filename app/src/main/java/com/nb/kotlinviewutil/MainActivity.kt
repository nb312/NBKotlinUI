package com.nb.kotlinviewutil

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.nb.extension.startAct
import com.nb.kotlinui.KotlinUi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        KotlinUi.context = this
        btn_recycler.setOnClickListener {
            startAct(RecyclerDemoActivity::class.java)
        }
    }
}
