//object关键字的使用
fun main(args: Array<String>) {
    ConstantField.name = "延迟初始化属性的值"
    ConstantField.printName()
    println(ConstantField.PI_VALUE)
}


object ConstantField {
    const val PI_VALUE: Double = 3.14

    lateinit var name: String
    fun printName() {
        if (this::name.isInitialized) {
            println(name)
        }
    }
}