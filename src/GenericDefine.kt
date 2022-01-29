/**
 * Java 中的泛型是不型变的，这意味着 List<String> 并不是 List<Object> 的子类型
 */

//泛型
fun main(args: Array<String>) {
    val point = Point(1, 2)
    println("${point.x} ${point.y}")

}

//泛型类
class Point<T>(val x: T, val y: T)

//声明处型变，由于它在类型参数声明处提供 out表示只能返回（生产），不能作为函数的输入参数使用（消费）
interface Source<out T> {
    fun outT(): T
}

fun demoOut(strSource: Source<String>) {
    //这个没问题，因为T是一个out参数
    val anySource: Source<Any> = strSource
}

// 类型参数逆变：只可以被消费而不可以被生产
interface Comparable<in T> {
    fun compareTo(other: T): Int
}

fun demoIn(x: Comparable<Number>) {
    x.compareTo(1.0)//1.0 拥有类型 Double，它是 Number 的子类型
    val y: Comparable<Double> = x
}

//泛型方法，类型参数要放在函数名称之前
fun <T> getList(item: T): List<T> {
    return listOf(item)
}


