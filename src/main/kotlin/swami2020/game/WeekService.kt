package swami2020.game

import swami2020.app.AppFactory
import swami2020.app.SwamiConfigurable

class WeekService() : SwamiConfigurable {

    lateinit var weekRepository: WeekRepository

    override fun setUp(factory: AppFactory) {
        this.weekRepository = factory.weekRepository
    }
}