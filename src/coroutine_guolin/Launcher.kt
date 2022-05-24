package coroutine_guolin

import kotlinx.coroutines.*

/**
 * GlobalScope.launch和runBlocking函数是可以在任意地方调用的，coroutineScope函数可以在协程作用域或挂起函数中调用，
 * 而launch和async函数只能在协程作用域中调用
 */
fun main() {
    runBlocking {
        val start = System.currentTimeMillis()
        // async函数必须在协程作用域当中才能调用，它会创建一个新的子协程并返回一个Deferred对象
        val deferred = async {
            println("async作用域")
            delay(1000)
            5 + 5
        }
        // 调用Deferred对象的await()获取async函数代码块的执行结果
//        val result = deferred.await()
//        println(result)

        // 在调用了async函数之后，代码块中的代码就会立刻开始执行。当调用await()方法时，如果代码块中的代码还没执行完，那么await()方法
        // 会将当前协程阻塞住，直到可以获得async函数的执行结果
        val deferred2 = async {
            delay(1000)
            1 + 1
        }
//        val result2 = deferred2.await()
//        println(result + result2)
//        val end = System.currentTimeMillis()
//        println("耗时：${end - start}")
        // 两个async函数确实是一种串行的关系，前一个执行完了后一个才能执行

        // 不在每次调用async函数之后就立刻使用await()方法获取结果了，而是仅在需要用到async函数的执行结果时才调用await()方法进行获取，
        // 这样两个async函数就变成一种并行关系了
        println("result is ${deferred.await() + deferred2.await()}.")
        val end = System.currentTimeMillis()
        println("耗时：${end - start}")


        // withContext()函数是一个挂起函数，大体可以将它理解成async函数的一种简化版写法
        // 调用withContext()函数之后，会立即执行代码块中的代码，同时将外部协程挂起。当代码块中的代码全部执行完之后，会将最后一行的执行结果作为
        // withContext()函数的返回值返回，因此基本上相当于val result = async{ 5 + 5 }.await()的写法。
        // 唯一不同的是，withContext()函数强制要求我们指定一个线程参数
        val withContext = withContext(Dispatchers.Default) {
            5 + 5
        }
        println(withContext)
        // 通过线程参数给协程指定一个具体的运行线程
        // Dispatchers.Default表示会使用一种默认低并发的线程策略，当你要执行的代码属于计算密集型任务时，开启过高的并发反而可能会影响任务的运行效率，此时就可以使用Dispatchers.Default。
        // Dispatchers.IO表示会使用一种较高并发的线程策略，当你要执行的代码大多数时间是在阻塞和等待中，比如说执行网络请求时，为了能够支持更高的并发数量，此时就可以使用Dispatchers.IO。
        // Dispatchers.Main则表示不会开启子线程，而是在Android主线程中执行代码，只能在Android项目中使用

        // 事实上，在我们刚才所学的协程作用域构建器中，除了coroutineScope函数之外，其他所有的函数都是可以指定这样一个线程参数的，
        // 只不过withContext()函数是强制要求指定的，而其他函数则是可选的


    }
}

