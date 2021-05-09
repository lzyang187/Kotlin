//委托 Delegate 类可以通过将其所有公有成员都委托给指定对象来实现一个接口 IShow
fun main(args: Array<String>) {
    val showImpl = ShowImpl(10)
    Delegate(showImpl).show()
}

interface IShow {
    fun show()
}

class ShowImpl(val x: Int) : IShow {
    override fun show() {
        println(x)
    }
}

//by-子句表示 iShow 将会在 Delegate 中内部存储， 并且编译器将生成转发给 iShow 的所有 IShow 的方法。
class Delegate(iShow: IShow) : IShow by iShow {
    //会使用 override 覆盖的实现而不是委托对象中的
    override fun show() {
        println("重写show方法的实现")
    }
}