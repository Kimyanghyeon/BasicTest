package com.example.basictest

import android.content.BroadcastReceiver
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.exoplayer.ExoPlayer
import com.example.basictest.databinding.ActivityProgressBinding

class ProgressActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProgressBinding.inflate(layoutInflater) }
//    private val progressViewList by lazy{
//        arrayListOf(
//
//        )
//    }

    override fun onDestroy() {
        super.onDestroy()

    }//액티비티가 완전히 종류되기 직전에 호출, 리소스 해제, 백업 저장 등의 정리 작업을 수행

    private lateinit var receiver : BroadcastReceiver
    private var player : ExoPlayer? = null
    private var thisFlag = false

    override fun onPause() {
        super.onPause()
        Log.e("start!--","[App] Main")

    }//다른 액티비티가 화면에 보이고, 사용자와 상호작용 가능해질 때 호출

    override fun onResume() {
        super.onResume()
    }//액티비가 화면에 보이고 사용자와 상호작용 가능해질 때 호출

    override fun onBackPressed() {
        super.onBackPressed()
    }//사용자가 뒤로 가기 버튼을 눌렀을 때 호출

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
    }//end of on create
}//end of class