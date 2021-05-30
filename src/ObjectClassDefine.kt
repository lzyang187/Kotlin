//object关键字的使用
fun main(args: Array<String>) {
    ConstantField.name = "延迟初始化属性的值"
    ConstantField.printName()
    println(ConstantField.PI_VALUE)
    println(ConstantField.score2Star(60))
}


object ConstantField {
    const val PI_VALUE: Double = 3.14

    lateinit var name: String
    fun printName() {
        if (this::name.isInitialized) {
            println(name)
        }
    }

    fun score2Star(score: Int): Int {
        return when (score) {
            in 0 until 60 -> 1
            in 60 until 80 -> 2
            in 80..100 -> 3
            else -> 0
        }
    }
}