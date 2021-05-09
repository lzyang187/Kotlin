import java.lang.IllegalArgumentException

//空安全使用
fun main(args: Array<String>) {
    //声明一个变量为可空字符串
    val str: String? = null
    val len = if (str != null) str.length else 0
    //elvis操作符 如果 ?: 左侧表达式非空，elvis 操作符就返回其左侧表达式，否则返回右侧表达式
    val length = str?.length ?: -1
    println("$len  $length")
    println(str?.length)
    //安全调用在链式调用中很有用
    //person?.body?.hand

    //非空断言运算符（!!）将任何值转换为非空类型，若该值为空则抛出异常
    val l = str!!.length
    println(l)

    val a = "1"
    //安全的类型转换 尝试转换不成功则返回 null
    val aInt: Int? = a as? Int
}

fun foo(string: String?) {
    val length = string?.length ?: throw IllegalArgumentException("excepted")
}