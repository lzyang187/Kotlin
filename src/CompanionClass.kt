//伴生对象
fun main(args: Array<String>) {
    MyClass.companionFun()
}

class MyClass() {
    //伴生对象的成员看起来像其他语言的静态成员，在运行时他们仍然是真实对象的实例成员
    companion object {
        fun companionFun() {
            println("伴生类的方法")
        }
    }
}

interface Factory<T> {
    fun create(): T
}

class MyCla() {
    //伴生对象还可以实现接口
    companion object : Factory<MyCla> {
        override fun create(): MyCla = MyCla()
    }
}