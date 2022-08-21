//变量和值的声明使用
fun main(args: Array<String>) {
    var a: Int = 1
    val b = 2
    val c: Int
    c = 3

    var x = 5
    x += 1
    val PI = 3.14

    //这是注释
    /*这是注释*/

    val s1 = "a is $a"
    a = 2
    val s2 = "${s1.replace("is", "was")}, but now is $a"
    println(s2)

    var getSet = GetSet(mutableListOf(1, 2, 3))
    println(getSet.size)
    getSet.name = "name"

    val listWithNull = listOf(null, 1, 2)
    for (it in listWithNull) {
        //要只对非空值执行某个操作，安全调用操作符可以与 let 一起使用
        it?.let { println(it) }
    }
    //空安全在链式调用中十分有用
}

class GetSet(val list: MutableList<Int>) {
    val size: Int
        get() = list.size

    var name: String = ""
        set(value) {
            println("调用了set value:$value")
            // 每个属性背后都隐藏着响应的幕后字段（field）被用来存储实际的数据，幕后字段只能通过get或set来访问
            field = value
        }
}