//泛型
fun main(args: Array<String>) {
    val point = Point(1, 2)
    println("${point.x} ${point.y}")

}

//泛型类
class Point<T>(val x: T, val y: T)

//声明处型变，由于它在类型参数声明处提供 out表示只能返回，不能作为函数的输入参数使用
interface Source<out T> {
    fun outT(): T
}

fun demoOut(strSource: Source<String>) {
    //这个没问题，因为T是一个out参数
    val anySource: Source<Any> = strSource
}

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


