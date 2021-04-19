package github.leavesc.reactivehttpsamples.core.viewmodel

import androidx.lifecycle.MutableLiveData
import github.leavesc.reactivehttpsamples.base.BaseViewModel
import github.leavesc.reactivehttpsamples.core.bean.BannerBean
import github.leavesc.reactivehttpsamples.core.http.SelfRemoteDataSource200

class TestViewModel : BaseViewModel() {

    private val remoteDataSource200 by lazy {
        SelfRemoteDataSource200(this)
    }

    val liveData = MutableLiveData<List<BannerBean>>()

    fun getBanner() {
        remoteDataSource200.enqueue({
            getBanner()
        }) {
            onSuccess {
                liveData.value = it
            }
            onFailToast {
                true
            }
        }
    }
}