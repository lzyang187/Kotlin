//解构声明
fun main(args: Array<String>) {
    val girl = Girl("marry", 10)
    //一个解构声明同时创建多个变量。 我们已经声明了两个新变量： name 和 age，并且可以独立使用它们
    val (name, age) = girl
    println("$name   $age")
}

data class Girl(val name: String, val age: Int)