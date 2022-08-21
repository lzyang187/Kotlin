//构造函数
fun main(args: Array<String>) {
    val customer = Person("tom", 12, Book())
    println(customer.book!!.bookName)
    DefaultValue("tom")
}

//主构造函数
class Person constructor(var name: String, var age: Int = 18) {
    var nameUp = name.toUpperCase()
    var book: Book? = null

    // 只有主构造函数才能使用init代码块，实例初始化期间，初始化块按照它们出现在类体中的顺序执行，与属性初始化器交织在一起
    init {
        println("主构造函数的参数 name=$name age=$age book=$book")
    }

    init {
        println("初始化块2")
    }

    //次构造函数
    constructor(name: String, age: Int, book: Book) : this(name, age) {
        this.book = book
        println("次构造函数：${book.bookName}")
    }
}

//构造函数的参数默认值
data class Book(var bookName: String = "三国演义")

//构造函数私有化
class DontCreateMe private constructor()

class DefaultValue constructor(name: String, age: Int = 10)