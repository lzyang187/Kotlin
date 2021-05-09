//拓展
fun main(args: Array<String>) {
    val list = mutableListOf(1, 2, 3)
    list.swap(1, 2)
    println(list)

    val dog = Dog()
    printFoo(dog)
    println(dog.name)
}

//扩展函数 扩展一个类的新功能而无需继承
fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    val temp = this[index1]
    //this关键字在扩展函数内部对应到MutableList的对象
    this[index1] = this[index2]
    this[index2] = temp
}

open class Animal

class Dog : Animal()

fun Animal.foo() {
    println("animal")
}

fun Dog.foo() {
    println("dog")
}

//用的扩展函数是由函数调用所在的表达式的类型来决定的，而不是由表达式运行时求值结果决定的
fun printFoo(animal: Animal) {
    animal.foo()
}

//扩展属性
val <T> List<T>.lastIndex: Int
    get() = size - 1

val Animal.name: String
    get() = "动物名称"