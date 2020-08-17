package com.duopoints

import org.jooq.ExecuteContext
import org.jooq.impl.DefaultExecuteListener
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
import org.springframework.jdbc.support.SQLExceptionTranslator

class JooqExceptionTransformer : DefaultExecuteListener() {
    override fun exception(context: ExecuteContext) {
        val dialect = context.configuration().dialect()
        val translator: SQLExceptionTranslator = SQLErrorCodeSQLExceptionTranslator(dialect.name)
        context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()))
    }
}