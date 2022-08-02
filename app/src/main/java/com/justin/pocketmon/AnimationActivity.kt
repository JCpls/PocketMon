package com.justin.pocketmon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import kotlinx.coroutines.delay
import com.justin.pocketmon.databinding.ActivityAnimationBinding

class AnimationActivity : AppCompatActivity() {

    lateinit var binding: ActivityAnimationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_animation)

        Handler().postDelayed({
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}