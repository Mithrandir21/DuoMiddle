package com.duopoints

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@SpringBootApplication
class Main

fun main(args: Array<String>) {
    SpringApplication.run(Main::class.java, *args)
}
