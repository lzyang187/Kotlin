package coroutine

import kotlinx.coroutines.*

fun main() {
//    basic1()
//    basic2()
//    basic3()
    basic4()
    basic5()
}

private fun basic1() {
    // GlobalScope：新协程的生命周期只受整个应用程序的生命周期限制
    GlobalScope.launch {// 在后台启动一个新的协程并继续
        //  delay 是一个特殊的挂起函数，它不会造成线程阻塞，但是会挂起协程，并且只能在协程中使用
        delay(1000)// 非阻塞的等待 1 秒钟
        print("world!")
    }
    print("hello,")// 协程已在等待时主线程还在继续
    Thread.sleep(2000)// 阻塞主线程 2 秒钟来保证 JVM 存活
}


private fun basic2() {
    // 调用了 runBlocking 的主线程会一直阻塞直到 runBlocking 内部的协程执行完毕。
    runBlocking {
        GlobalScope.launch {
            delay(1000)
            print("world!")
        }
        print("hello,")
        delay(2000)
    }
}

/**
 * 等待一个作业
 */
private fun basic3() {
    // basic2中的延迟一段时间来等待另一个协程运行并不是一个好的选择。让我们显式（以非阻塞方式）等待所启动的后台 Job 执行结束
    runBlocking {
        // 启动一个新协程并保持对这个作业的引用
        val job = GlobalScope.launch {
            delay(1000)
            print("world!")
        }
        print("hello,")
        job.join()// 等待直到子协程执行结束
    }
}

/**
 * 结构化的并发
 */
private fun basic4() {
    // 包括 runBlocking 在内的每个协程构建器都将 CoroutineScope 的实例添加到其代码块所在的作用域中。
    // 我们可以在这个作用域中启动协程而无需显式 join 之，因为外部协程（示例中的 runBlocking）直到在其作用域中启动的所有协程都执行完毕后才会结束。
    // 因此，可以将我们的示例简化为
    runBlocking {
        launch {
            delay(1000)
            println("world!")
        }
        print("hello,")
    }
}

/**
 * 作用域构建器
 */
private fun basic5() {
    // runBlocking 与 coroutineScope 可能看起来很类似，因为它们都会等待其协程体以及所有子协程结束。
    // 主要区别在于，runBlocking 方法会阻塞当前线程来等待， 而 coroutineScope 只是挂起，会释放底层线程用于其他用途。
    // 由于存在这点差异，runBlocking 是常规函数，而 coroutineScope 是挂起函数。
    runBlocking {
        launch {
            delay(200)
            println("task from runBlocking")
        }
        // 还可以使用 coroutineScope 构建器声明自己的作用域。它会创建一个协程作用域并且在所有已启动子协程执行完毕之前不会结束。
        coroutineScope {// 创建一个协程作用域
            launch {
                delay(500)
                println("task from nested launch")
            }
            delay(100)
            println("task from coroutine scope") // 这一行会在内嵌 launch 之前输出
        }
        println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
        // 请注意，（当等待内嵌 launch 时）紧挨“Task from coroutine scope”消息之后， 就会执行并输出“Task from runBlocking”——尽管 coroutineScope 尚未结束。
    }
}