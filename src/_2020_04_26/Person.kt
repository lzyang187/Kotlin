package _2020_04_26

class Person constructor(firstName: String) {
    val firstProperty = "first property:$firstName".also(::println)

    init {
        println("first init block : $firstName")
    }

    val secondProperty = "second property:$firstName".also(::println)

    init {
        println("second init block :$firstName")
    }

}