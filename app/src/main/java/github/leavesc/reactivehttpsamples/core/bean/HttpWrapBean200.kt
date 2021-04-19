package github.leavesc.reactivehttpsamples.core.bean

import com.google.gson.annotations.SerializedName
import github.leavesc.reactivehttp.bean.IHttpWrapBean
import github.leavesc.reactivehttpsamples.core.http.HttpConfig

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:22
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
class HttpWrapBean200<T>(
        @SerializedName("status") var code: Int = 0,
        @SerializedName("message") var message: String? = null,
        @SerializedName("data") var data: T) : IHttpWrapBean<T> {

    companion object {

        fun <T> success(data: T): HttpWrapBean200<T> {
            return HttpWrapBean200(HttpConfig.CODE_SERVER_SUCCESS, "success", data)
        }

        fun <T> failed(data: T): HttpWrapBean200<T> {
            return HttpWrapBean200(-200, "服务器停止维护了~~", data)
        }

    }

    override val httpCode: Int
        get() = code

    override val httpMsg: String
        get() = message ?: ""

    override val httpData: T
        get() = data

    override val httpIsSuccess: Boolean
        get() = code == HttpConfig.CODE_SERVER_SUCCESS_200 || message == "OK"

    override fun toString(): String {
        return "HttpResBean(code=$code, message=$message, data=$data)"
    }

}