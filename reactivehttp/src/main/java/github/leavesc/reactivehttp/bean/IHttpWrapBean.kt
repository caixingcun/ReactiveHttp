package github.leavesc.reactivehttp.bean

/**
 * @Author: leavesC
 * @Date: 2020/4/30 15:18
 * @Desc: 规范网络请求返回结果必须包含的几种参数类型
 * @GitHub：https://github.com/leavesC
 */
//通用网络请求 基本元素（后台约定）
interface IHttpWrapBean<Data> {

    /**
     * 服务器返回的数据中，用来标识当前是否请求成功的标识符
     */
    val httpCode: Int

    /**
     * 服务器返回的数据中，用来标识当前请求状态的字符串，一般是用于保存失败原因
     */
    val httpMsg: String

    /**
     * 服务器返回的实际数据
     */
    val httpData: Data

    /**
     * 交由外部来判断当前接口是否请求成功
     */
    val httpIsSuccess: Boolean

    val httpIsFailed: Boolean
        get() = !httpIsSuccess

}