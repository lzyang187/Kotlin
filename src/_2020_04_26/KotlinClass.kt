package _2020_04_26

fun main(args: Array<String>) {
    val javaClass = JavaClass()
    javaClass.javaFun("kotlin调用java")
}

class KotlinClass {
    fun kotlinFun(kotlinStr: String) {
        println("kotlinStr= $kotlinStr")
    }
}