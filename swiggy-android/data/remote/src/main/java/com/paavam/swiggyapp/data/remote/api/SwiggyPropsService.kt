package com.paavam.swiggyapp.data.remote.api

import com.paavam.swiggyapp.core.data.PreviewData
import com.paavam.swiggyapp.core.data.model.HelloBar
import com.paavam.swiggyapp.core.data.model.QuickTile
import com.paavam.swiggyapp.data.remote.model.response.PropsResponse
import com.paavam.swiggyapp.data.remote.model.response.State
import retrofit2.Response
import javax.inject.Inject

class SwiggyPropsService @Inject constructor() {

    suspend fun fetchTilesContent(): Response<PropsResponse<List<QuickTile>>> =
        Response.success(PropsResponse(State.SUCCESS, "", PreviewData.prepareTilesContent()))

    suspend fun fetchHelloBarContent(): Response<PropsResponse<List<HelloBar>>> =
        Response.success(PropsResponse(State.SUCCESS, "", PreviewData.prepareHelloBarContent()))

}