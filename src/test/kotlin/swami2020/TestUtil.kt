package swami2020

import swami2020.app.AppFactory
import swami2020.properties.DatabaseProperties
import swami2020.properties.ServerProperties
import swami2020.properties.SwamiProperties

object TestUtil {

    const val port = 9000

    //TODO: Load properties from YAML
    val swamiProperties = SwamiProperties(
            ServerProperties(port),
            DatabaseProperties(
                    "jdbc:postgresql://localhost:5432/swami",
                    "swami_user",
                    "Password_1"
            )
    )

    private val app = App(AppFactory(swamiProperties))

    val start = {app.start()}
    val stop = {app.stop()}

}