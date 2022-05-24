package coroutine_guolin

import kotlinx.coroutines.*

fun main() {
    // GlobalScope.launch函数可以创建一个协程的作用域，每次创建的都是一个顶层协程，这种协程当应用程序运行结束时也会跟着一起结束
    GlobalScope.launch {
        // delay()函数可以让当前协程延迟指定时间后再运行，但它和Thread.sleep()方法不同。
        // delay()函数是一个非阻塞式的挂起函数，它只会挂起当前协程，并不会影响其他协程的运行。
        // 而Thread.sleep()方法会阻塞当前的线程，这样运行在该线程下的所有协程都会被阻塞
        delay(100)
        println("顶层协程")
    }
    // 保证顶层协程能执行完
//     Thread.sleep(200)

    // runBlocking函数同样会创建一个协程的作用域，但是它可以保证在协程作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。
    // 需要注意的是，runBlocking函数通常只应该在测试环境下使用，在正式环境中使用容易产生一些性能上的问题
    runBlocking {
        println("runBlocking协程构建器")
    }

    runBlocking {
        // launch函数和我们刚才所使用的GlobalScope.launch函数不同。首先它必须在协程的作用域中才能调用，
        // 其次它会在当前协程的作用域下创建子协程。子协程的特点是如果外层作用域的协程结束了，该作用域下的所有子协程也会一同结束。
        launch {
            println("子协程1")
            delay(100)
            println("子协程1运行结束")
        }
        launch {
            println("子协程2")
            delay(100)
            println("子协程2运行结束")
        }
        // 两个子协程中的日志是交替打印的，说明它们确实是像多线程那样并发运行的。
        // 然而这两个子协程实际却运行在同一个线程当中，只是由编程语言来决定如何在多个协程之间进行调度，让谁运行，让谁挂起。
        // 调度的过程完全不需要操作系统参与，这也就使得协程的并发效率会出奇得高。
    }

//    printDot()
//    printDot2()
    // 另外，coroutineScope函数和runBlocking函数还有点类似，它可以保证其作用域内的所有代码和子协程在全部执行完之前，外部的协程会一直被挂起
    // 只有当它作用域内的所有代码和子协程都执行完毕之后，coroutineScope函数之后的代码才能得到运行
    runBlocking {
        // 调用coroutineScope函数创建了一个子协程
        coroutineScope {
            // 又调用launch函数创建了一个子协程
            launch {
                for (i in 1..5) {
                    println(i)
                    delay(1000)
                }
            }
        }
        println("coroutineScope finished")
    }
    println("runBlocking finished")
    // coroutineScope函数只会阻塞当前协程，既不影响其他协程，也不影响任何线程，因此是不会造成任何性能上的问题的。
    // 而runBlocking函数由于会阻塞外部线程，如果你恰好又在主线程中当中调用它的话，那么就有可能会导致界面卡死的情况

}

// suspend关键字，使用它可以将任意函数声明成挂起函数，而挂起函数之间都是可以互相调用的
suspend fun printDot() {
    delay(100)
    println(".")
}
// 但是，suspend关键字只能将一个函数声明成挂起函数，是无法给它提供协程作用域的。
// 比如你现在尝试在printDot()函数中调用launch函数，一定是无法调用成功的，因为launch函数要求必须在协程作用域当中才能调用。

// 这个问题可以借助coroutineScope函数来解决。coroutineScope函数也是一个挂起函数，因此可以在任何其他挂起函数中调用。
// 它的特点是会继承外部的协程的作用域并创建一个子协程，借助这个特性，我们就可以给任意挂起函数提供协程作用域了
suspend fun printDot2() = coroutineScope {
    launch {
        println(".")
    }
}