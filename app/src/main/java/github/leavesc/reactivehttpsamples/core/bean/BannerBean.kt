package github.leavesc.reactivehttpsamples.core.bean

import com.google.gson.annotations.SerializedName

data class BannerBean(
        @SerializedName("title") val title: String, @SerializedName("image") val image: String,
        @SerializedName("url") val url: String
)