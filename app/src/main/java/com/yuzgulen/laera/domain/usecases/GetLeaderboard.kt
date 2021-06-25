package com.yuzgulen.laera.domain.usecases

import com.google.firebase.database.DataSnapshot
import com.yuzgulen.laera.services.LeaderboardService
import com.yuzgulen.laera.utils.ICallback

class GetLeaderboard {
    companion object {
        private var INSTANCE: GetLeaderboard? = null
        fun getInstance(): GetLeaderboard {
            if (INSTANCE == null) {
                INSTANCE = GetLeaderboard()
            }
            return INSTANCE!!
        }
    }

    fun execute(callback: ICallback<Iterable<DataSnapshot>>) : Any {
        return LeaderboardService.getInstance().getLeaderboard(callback)
    }
}