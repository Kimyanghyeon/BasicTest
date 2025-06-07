package com.example.basictest

import App
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.basictest.databinding.ActivityMainBinding
import com.example.basictest.ui.theme.BasicTestTheme

class MainActivity : ComponentActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private var currentContentsId : Int? = null
    private lateinit var player: ExoPlayer

    private var nowPlaying = false
    private var nowState = Player.STATE_IDLE

    //초기화 - 뷰 연결 및 상태 추적
    private fun initPlayer(){
        player = ExoPlayer.Builder(this).build ().also { exoPlayer ->
            binding.playerview.player = exoPlayer // 플레이어 연결
            exoPlayer.repeatMode = Player.REPEAT_MODE_ONE
            //현재 재생 중인 미디어를 계속 반복 재생
            //반복 옵션 REPEAT_MODE_OF(없음),REPEAT_MODE_ONE(한개).REPEAT_MODE_ALL(전체)
            exoPlayer.playWhenReady = true
            //준비가 완료되면 자동 재생 시작
            //fasle , player.play() 호출 해야 실행
            exoPlayer.addListener(object :Player.Listener{ //이벤트 리스너 연결
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    Log.i("PLAYER_LISTENER","실시간 재생 상태 변경 : $isPlaying")
                    nowPlaying = isPlaying // 상태 변경
                    App.isPlaying=(nowPlaying||nowState==Player.STATE_READY||nowState==Player.STATE_BUFFERING)
                    //App.isPlaying 앱 전체 참조 변수 상태도 변경
                }//end ofAdd names in comment to call arguments
            })//end of listener
        }//end of play
    }//end of initPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() //컨텐츠 영역 확장, 영역 겹침에 따른 여백 처리
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)){v, insets ->
            val sysBars =insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sysBars.left,sysBars.top,sysBars.right,sysBars.bottom)
            insets
        }//end of Listener
    }//end of OnCreate


}//end of class

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicTestTheme {
        Greeting("Android")
    }
}