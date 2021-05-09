//数据类的使用
fun main(args: Array<String>) {
    val boy1 = Boy("tom")
    boy1.age = 10
    val boy2 = Boy("tom")
    boy2.age = 20
    println("boy1: $boy1")
    //会视为相等
    println("boy1 == boy2: ${boy1 == boy2}")
    println("boy1 === boy2: ${boy1 === boy2}")
    //复制一个对象并只改变某一些属性
    val boy3 = boy1.copy(name = "john")
    println(boy3)
}

data class Boy(val name: String) {
    var age: Int = 10
}