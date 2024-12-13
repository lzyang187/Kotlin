import java.util.*

/**
 * 函数类型：编程语言有整形、布尔型等字段类型，而Kotlin又增加了一个函数类型的概念。
 * 函数类型的语法规则为：(String, Int) -> Unit
 * 像apply函数 apply(block: T.() -> Unit) 在函数类型的前面加上ClassName（T）. 就表示这个函数类型是定义在哪个类当中的。好处就是当我们调用apply
 * 函数时传入的Lambda表达式将会自动拥有T的上下文
 */
fun main(args: Array<String>) {
//    println(sumSimple(3, 5))
//    printSum(10, 20)
//    println(maxOfSimple(3, 5))
//    println(parseInt("a"))
//    println(getStrLength("123"))
//    printForLoop()
//    printWhileLoop()
//    println(switchCase(1))
//    println(inRange(5))
    inRangeStep()
//    useCollections()
//    useMap()
//    defaultValue("tom")
    val a = arrayOf(7, 8, 9)
    //已经有一个数组并希望将其内容传给该函数，我们使用伸展（spread）操作符（在数组前面加 *）
    println(asList(asList(1, 2, 3, *a)))
    closureFun(::complete)
    println(stringLengthFun("android"))
}

fun sum(a: Int, b: Int): Int {
    return a + b
}

// 当一个函数只有一行代码时，可以省略函数体部分，直接将这一行代码使用等号串连在函数定义的尾部。
fun sumSimple(a: Int, b: Int) = a + b

fun printSum(a: Int, b: Int) {
    println("sum of $a and $b is ${a + b}")
}

fun maxOf(a: Int, b: Int): Int {
    if (a > b) {
        return a
    } else {
        return b
    }
}

// 虽然maxOfSimple()函数不止只有一行代码，但是它和只有一行代码的作用是相同的，只是return了一下if语句的返回值而已，符合该语法糖的使用条件
fun maxOfSimple(a: Int, b: Int) = if (a > b) a else b

fun parseInt(a: String): Int? {
    return a.toIntOrNull()
}

fun getStrLength(any: Any): Int? {
    if (any is String) {
        return any.length
    }
    return null
}

fun printForLoop() {
    val items = listOf("apple", "pear", 1)
    for (item in items) {
        println(item)
    }
    for (index in items.indices) {
        println(items[index])
    }
}

fun printWhileLoop() {
    val items = listOf(1, 10, 100)
    var index = 0
    while (index < items.size) {
        println(items[index])
        index++
    }
}

fun switchCase(any: Any): String {
    return when (any) {
        1, 2 -> "one or two"
        "hello" -> "world"
        is Long -> "is Long"
        else -> "default case"
    }
}

fun inRange(a: Int): Boolean {
    return a in 1..10
}

fun inRangeStep() {
    for (x in 1..10 step 2) {
        println(x)
    }

    for (x in 9 downTo 0 step 3) {
        println(x)
    }

    //左闭右开区间
    for (x in 1 until 10) {
        println(x)
    }
}

fun useCollections() {
    //只读list
    val items = listOf("kiwifruit", "apple", "banana", "avocado")
    items.filter { it.startsWith("a") }.sortedBy { it }.map { it.uppercase(Locale.getDefault()) }.forEach { println(it) }
    //在可能为空的集合中取第一个元素
    val str = items.firstOrNull() ?: ""
    println("取出的第一个元素：$str")
}

fun useMap() {
    //只读map
    val map = mapOf("a" to "aValue", "b" to "bValue")
    println(map["a"])
    val mutableMap = mutableMapOf<String, String>()
    mutableMap["c"] = "cValue"
    println(mutableMap["c"])
}

fun defaultValue(name: String, age: Int = 10) {
    println("$name, $age")
}

//可变数量的参数（Varargs）
fun <T> asList(vararg ts: T): List<T> {
    val result = ArrayList<T>()
    for (t in ts) {
        result.add(t)
    }
    return result
}

//高阶函数：将其他函数用作参数的函数，或者返回值类型是另一个函数。此模式对组件之间的通信（其方式与在 Java 中使用回调接口相同）很有用
fun closureFun(complete: (Int, String) -> String) {
    println(complete(1, "a"))
}

fun complete(a: Int, string: String): String {
    return "$a$string"
}

// 匿名函数
val stringLengthFun: (String) -> Int = {
    it.length
}



