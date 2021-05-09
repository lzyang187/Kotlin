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

    init {
        println("主构造函数的参数 name=$name age=$age")
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