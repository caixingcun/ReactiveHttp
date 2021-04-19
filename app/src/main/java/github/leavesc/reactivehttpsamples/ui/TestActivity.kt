package github.leavesc.reactivehttpsamples.ui

import android.os.Bundle
import com.google.gson.Gson
import github.leavesc.reactivehttpsamples.base.BaseActivity
import github.leavesc.reactivehttpsamples.core.viewmodel.TestViewModel

class TestActivity : BaseActivity() {


    val testRequestViewModel by getViewModel(TestViewModel::class.java) {
        liveData.observe(this@TestActivity) {
            showToast(Gson().toJson(it))
            //更新数据
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}