package com.cesar.domain.repository

import com.cesar.domain.model.App
import com.cesar.domain.model.Dashboard
import kotlinx.coroutines.flow.Flow


interface IListRepository {

  fun getAppsMoreUsed() : Flow<MutableList<App>>
  fun getAppsNotUsed() : Flow<MutableList<App>>
  suspend fun getAppsMobileData() : Flow<MutableList<App>>
  suspend fun getAppsDashboard() : Flow<Dashboard>

}