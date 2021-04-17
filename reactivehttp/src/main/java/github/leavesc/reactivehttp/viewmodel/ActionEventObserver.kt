package github.leavesc.reactivehttp.viewmodel

import android.content.Context
import androidx.lifecycle.*
import github.leavesc.reactivehttp.coroutine.ICoroutineEvent
import kotlinx.coroutines.Job

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:23
 * @Desc: 用于定义 View 和  ViewModel 均需要实现的一些 UI 层行为
 * @GitHub：https://github.com/leavesC
 */
// 网络请求 常规UI状态接口
interface IUIActionEvent : ICoroutineEvent {

    fun showLoading(job: Job?)

    fun dismissLoading()

    fun showToast(msg: String)

    fun finishView()

}
// 定义一些 liveData ，用来存储UI变更通知消息
interface IViewModelActionEvent : IUIActionEvent {

    val showLoadingEventLD: MutableLiveData<ShowLoadingEvent>

    val dismissLoadingEventLD: MutableLiveData<DismissLoadingEvent>

    val showToastEventLD: MutableLiveData<ShowToastEvent>

    val finishViewEventLD: MutableLiveData<FinishViewEvent>

    override fun showLoading(job: Job?) {
        showLoadingEventLD.value = ShowLoadingEvent(job)
    }

    override fun dismissLoading() {
        dismissLoadingEventLD.value = DismissLoadingEvent
    }

    override fun showToast(msg: String) {
        showToastEventLD.value = ShowToastEvent(msg)
    }

    override fun finishView() {
        finishViewEventLD.value = FinishViewEvent
    }

}
// UI 状态变更事件 监听
interface IUIActionEventObserver : IUIActionEvent {

    val lContext: Context?

    val lLifecycleOwner: LifecycleOwner
    // 根据字节码 获取ViewModel
    fun <VM> getViewModel(clazz: Class<VM>,
                          factory: ViewModelProvider.Factory? = null,
                          initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null): Lazy<VM> where VM : ViewModel, VM : IViewModelActionEvent {
        return lazy {
            getViewModelFast(clazz, factory, initializer)
        }
    }

    fun <VM> getViewModelFast(clazz: Class<VM>,
                              factory: ViewModelProvider.Factory? = null,
                              initializer: (VM.(lifecycleOwner: LifecycleOwner) -> Unit)? = null): VM where VM : ViewModel, VM : IViewModelActionEvent {
        return when (val localValue = lLifecycleOwner) {
            is ViewModelStoreOwner -> {
                if (factory == null) {
                    ViewModelProvider(localValue).get(clazz)
                } else {
                    ViewModelProvider(localValue, factory).get(clazz)
                }
            }
            else -> {
                factory?.create(clazz) ?: clazz.newInstance()
            }
        }.apply {
            generateActionEvent(this)
            initializer?.invoke(this, lLifecycleOwner)
        }
    }
    // 根据VM生成 注册事件
    fun <VM> generateActionEvent(viewModel: VM) where VM : ViewModel, VM : IViewModelActionEvent {
        viewModel.showLoadingEventLD.observe(lLifecycleOwner, Observer {
            this@IUIActionEventObserver.showLoading(it.job)
        })
        viewModel.dismissLoadingEventLD.observe(lLifecycleOwner, Observer {
            this@IUIActionEventObserver.dismissLoading()
        })
        viewModel.showToastEventLD.observe(lLifecycleOwner, Observer {
            if (it.message.isNotBlank()) {
                this@IUIActionEventObserver.showToast(it.message)
            }
        })
        viewModel.finishViewEventLD.observe(lLifecycleOwner, Observer {
            this@IUIActionEventObserver.finishView()
        })
    }

}