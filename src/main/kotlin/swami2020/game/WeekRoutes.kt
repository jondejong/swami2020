package swami2020.game

import swami2020.api.request.RequestHandler
import swami2020.app.AppFactory

class WeekRoutes : RequestHandler() {

    lateinit var weekService: WeekService

    override fun setUp(factory: AppFactory) {
        super.setUp(factory)
        this.weekService = factory.weekService
    }
}