package _2020_04_26

import java.util.*

val fruitArray = listOf("apple", "banana", "pear")

fun main() {
    sum(3, 5)
    printSum()
    max(5, 6)
    val a = canNullFun(5)
    val b = canNullFun(1)
    // 直接使用 `a、b` 会导致编译错误，因为它们可能为 null
    if (b == null) {
        println("b的值是null")
    }
    if (a != null && b != null) {
        sum(a, b)
    }
    val str = ""
    getStrLen(str)
    forFun()
    whileFun()
    whenFun(1)
    whenFun("str")
    val f = 1.0f
    whenFun(f)
    inFun(1)
    arrayFun()
    letFun(null)
}

fun sum(a: Int, b: Int): Int {
    val sum = a + b;
    println("$a + $b = $sum")
    return sum
}

//函数返回无意义的值，其中Unit可以省略
fun printSum(): Unit {
    println("函数返回无意义的值")
}

//条件表达式函数
fun max(a: Int, b: Int): Int {
    val max = if (a > b) a else b
    println("$a 与$b 的最大值是$max")
    return max
}

//返回值是Int类型可为空，声明处的类型后添加 ?
fun canNullFun(a: Int): Int? {
    return if (a > 3) 3 else null
}

//类型判断
fun getStrLen(obj: Any): Int {
    var len = -1
    if (obj is String) {
        // `obj` 在该条件分支内自动转换成 `String`
        len = obj.length
    }
    // 在离开类型检测分支后，`obj` 仍然是 `Any` 类型
    println("字符串长度：$len")
    return len
}

//for循环
fun forFun() {
    for (item in fruitArray) {
        println(item)
    }
    for (index in fruitArray.indices) {
        println("索引为$index 的值为${fruitArray[index]}")
    }
}

//while循环
fun whileFun() {
    val list = listOf(111, 222, 333)
    var index = 0
    while (index < list.size) {
        println("索引为$index 的值为 ${list[index]}")
        index++
    }
}

//when表达式
fun whenFun(obj: Any): String {
    val type = when (obj) {
        is Int -> "Int"
        is String -> "String"
        else -> "Other"
    }
    println("$obj 的类型为$type")
    return type
}

//使用区间：range
fun inFun(a: Int): Boolean {
    val b = a in 1..10
    println("$a 是否在1-10之间：$b")
    return b
}

//集合
fun arrayFun() {
    //使用 lambda 表达式来过滤（filter）与映射（map）集合
    fruitArray.filter { it.startsWith("a") }
            .sortedBy { it }
            .map { it.uppercase(Locale.getDefault()) }
            .forEach { println(it) }
}

//let
fun letFun(list: List<String>?) {
    //if not null 执行代码
    println(list?.size ?: "empty")
    list?.let { println("obj不为空执行的语句") }
}