//方法和属性的重写
fun main(args: Array<String>) {
    val derived = Derived("Li", "Zhaoyang")
    derived.v()

    val c = C()
    c.f()
}

open class Base(val name: String) : Any() {
    init {
        println("Initializing Base")
    }

    open val size: Int = name.length.also { println("Initializing size in Base: $it") }

    open val baseVal = "基类属性值"
    open fun v() {
        println("基类的v函数 属性：$baseVal")
    }

    fun nv() {}
}


class Derived(name: String, val lastName: String) :
    Base(name.capitalize().also { println("Argument for Base: $it") }) {

    init {
        println("Initializing Derived")
    }

    override val size: Int = (super.size + lastName.length).also { println("Initializing size in Derived: $it") }

    override var baseVal = "子类属性值"
    override fun v() {
        super.v()
        println("子类复写的v函数 属性：$baseVal")
    }
}

open class A {
    open fun f() {
        print("A")
    }

    open fun a() {
        print("a")
    }
}

interface B {
    //接口成员默认就是“open”的
    fun f() {
        print("B")
    }

    fun b() {
        print("b")
    }
}

class C : A(), B {
    override fun f() {
        super<A>.f()
        super<B>.f()
    }
}

abstract class AbsA : A() {
    //可以用一个抽象成员覆盖一个非抽象的开放成员
    abstract override fun a()
}

