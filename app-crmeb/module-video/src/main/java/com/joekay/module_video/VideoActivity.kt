package com.joekay.module_video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.joekay.resource.RouterPath
import com.therouter.router.Route
import dagger.hilt.android.AndroidEntryPoint

@Route(path = RouterPath.act_video)
@AndroidEntryPoint
class VideoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }
}