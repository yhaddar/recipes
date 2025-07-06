package com.recipes.blogs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BlogsApplication

fun main(args: Array<String>) {
	runApplication<BlogsApplication>(*args)
}