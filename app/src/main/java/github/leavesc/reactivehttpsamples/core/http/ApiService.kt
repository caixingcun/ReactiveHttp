package github.leavesc.reactivehttpsamples.core.http

import github.leavesc.reactivehttpsamples.core.bean.*
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: leavesC
 * @Date: 2020/2/25 16:34
 * @Desc:
 * @GitHub：https://github.com/leavesC
 */
interface ApiService {

    @GET("config/district")
    suspend fun getProvince(): HttpWrapBean<List<DistrictBean>>

    @GET("config/district")
    suspend fun getCity(@Query("keywords") keywords: String): HttpWrapBean<List<DistrictBean>>

    @GET("config/district")
    suspend fun getCounty(@Query("keywords") keywords: String): HttpWrapBean<List<DistrictBean>>

    @GET("weather/weatherInfo?extensions=all")
    suspend fun getWeather(@Query("city") city: String): HttpWrapBean<List<ForecastsBean>>

    @GET("api/v2/banners")
    suspend fun getBanner():HttpWrapBean200<List<BannerBean>>
}