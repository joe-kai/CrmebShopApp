package com.joekay.module_product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joekay.resource.RouterPath
import com.therouter.router.Route

@Route(path = RouterPath.act_product)
class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)
    }
}