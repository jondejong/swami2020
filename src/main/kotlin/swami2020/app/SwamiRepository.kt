package swami2020.app

import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL

open class SwamiRepository : SwamiConfigurable {
    lateinit var context: DSLContext

    override fun setUp(factory: AppFactory) {
        context = DSL.using(factory.dataSource, SQLDialect.POSTGRES)
    }
}