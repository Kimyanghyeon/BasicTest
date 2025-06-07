import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.FileUtils
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import util.FileUtil


class App:Application(){
    init {
        instance=this
    }//초기화

    private var actReferences = 0
    private var isActchangingConfig = false

    override fun onCreate() {
        super.onCreate()
        Logger.addLogAdapter(AndroidLogAdapter())
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //엑티비티가 생성되었을 때 호출
            }//end of Created

            override fun onActivityStarted(activity: Activity) {
                if(++actReferences==1&&!isActchangingConfig){
                    //포그라운드 진입
                    Log.i("AppForeground","onActivityStarted")
                    isForeground=true
                    doOpenWhenStop=true

                }//end of if
            }//end of Started

            override fun onActivityResumed(activity: Activity) {
                //다시 시작되었을 때 호출
            }//end of Resumed

            override fun onActivityPaused(activity: Activity) {
                //일시 중지 되었을 때 호출
            }//end of Paused

            override fun onActivityStopped(activity: Activity) {
                isActchangingConfig = activity.isChangingConfigurations
                if (--actReferences==0&&!isActchangingConfig){
                    //백드라운드로 전환되었을 때
                    isForeground = false
                    Log.e("AppForeground","onAvtivityStoppend, doOpenWhenStop : $doOpenWhenStop")
                    if(doOpenWhenStop){
                        Handler(Looper.myLooper()!!).postDelayed({
                            Log.e("AppForeGround","onActivityStopped check : $isForeground")
                            if(!isForeground){
                                bringAppToForeground()
                            }//end of if
                        },10_000L)//end of handler
                    }//end of if
                }//end of if
            }//end of Stopped

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }//end of SaveInstanceState

            override fun onActivityDestroyed(activity: Activity) {
                //액티비티가 파괴 되었을 때 호출
            }//end of Destroyed
        })//end of LifecycleCallbacks

    }//end of onCreate

    private fun bringAppToForeground(){
        var actManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        var appTasks = actManager.appTasks
        appTasks.firstOrNull()?.moveToFront()
    }//end of bringAppToForeground

    companion object{
        //클래스 내부에서 정의되는 동반 객체, Static
        //클래스의 인스턴스를 생성하지 않고도 접근할 수 있는 공통 함수나 변수를 정의
        var isPlaying : Boolean = false
        var isForeground = true
        var doOpenWhenStop = true
        var isInProgress:Boolean = false
        var latestScheduleJson : String = ""

        private var instance:App?=null
        //클래스가 반드시 Application을 상속한 클래스여야 함

        fun context():Context{
            return instance!!.applicationContext
            //activity나 service의 context는 수명 제한으로 인해 전역 사용 불가
            //전역 참조를 위한 싱글톤 구조
            // not-null 단정 연산자 사용, 바로 crash ! 반드시 초기화 할 것 !
        }//end of context



    }//end of object

}//end of class