//反射
fun main(args: Array<String>) {
    //获取类的运行时引用
    val r = ReflectClass::class
    println(r.qualifiedName)
    //可以将其作为一个函数类型的值，将其传给另一个函数
    val numbers = listOf(1, 2, 3)
    println(numbers.filter(::isOdd))


}

class ReflectClass(val name: String)

fun isOdd(x: Int): Boolean = x % 2 != 0
