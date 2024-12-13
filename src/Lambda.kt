import java.util.*

/**
 * Lambda表达式：Lambda就是一小段可以作为参数传递的代码
 * Lambda表达式的语法结构：
 * {参数名1: 参数类型, 参数名2: 参数类型 -> 函数体}
 */
fun main() {
    val list = listOf("a", "bb", "ccc")
    val lambda = { str: String -> str.length }
    // 原始调用
    val maxBy1 = list.maxBy(lambda)
    // 移除lambda变量的声明
    val maxBy2 = list.maxBy({ str: String -> str.length })
    // 当lambda参数是函数的最后一个参数时，可以将lambda表达式移到函数括号的外面
    list.maxBy() { str: String -> str.length }
    // 如果lambda参数是函数的唯一一个参数，还可以将函数的括号省略
    list.maxBy { str: String -> str.length }
    // 由于Kotlin拥有出色的类型推导机制，Lambda表达式中的参数列表其实在大多数情况下不必声明参数类型
    list.maxBy { str -> str.length }
    // 最后，当Lambda表达式的参数列表中只有一个参数时，也不必声明参数名，而是可以使用it关键字来代替
    val maxBy3 = list.maxBy { it.length }
    println("$maxBy1 $maxBy2 $maxBy3")

    val map = list.map { it.uppercase(Locale.getDefault()) }
    println(map)

}