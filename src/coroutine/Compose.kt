package coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        // 测量执行两个挂起函数所需要的总时间
        val time = measureTimeMillis {
//            defaultOrder()

            // 如果 doSomethingUsefulOne 与 doSomethingUsefulTwo 之间没有依赖，并且我们想更快的得到结果，可以使用 async 并发
            // 在概念上，async 就类似于 launch。它启动了一个单独的协程，这是一个轻量级的线程并与其它所有的协程一起并发的工作。
            // 不同之处在于 launch 返回一个 Job 并且不附带任何结果值，而 async 返回一个 Deferred —— 一个轻量级的非阻塞 future，
            // 这代表了一个将会在稍后提供结果的 promise。你可以使用 .await() 在一个延期的值上得到它的最终结果，
            // 但是 Deferred 也是一个 Job，所以如果需要的话，你可以取消它。
//            val oneAsync = async { doSomethingUsefulOne() }
//            val twoAsync = async { doSomethingUsefulTwo() }
//            println("ont + two = ${oneAsync.await() + twoAsync.await()}")

            // 惰性启动的 async ，通过将 start 参数设置为 CoroutineStart.LAZY 而变为惰性的
            // 在这个模式下，只有结果通过 await 获取的时候协程才会启动，或者在 Job 的 start 函数调用的时候
            val oneLazy = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
            val twoLazy = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
//            oneLazy.start()// 启动第一个
//            twoLazy.start()// 启动第二个
            // 如果我们只是在 println 中调用 await，而没有在单独的协程中调用 start，这将会导致顺序调用行为
            println("ont + two = ${oneLazy.await() + twoLazy.await()}")

        }
        println("Completed in $time ms")
    }
}

/**
 * 默认顺序调用，适用于两个挂起函数有依赖关系，我们要根据第一个函数的结果来决定是否我们需要调用第二个函数或者决定如何调用它时
 */
private suspend fun defaultOrder() {
    val one = doSomethingUsefulOne()
    val two = doSomethingUsefulTwo()
    println("ont + two = ${one + two}")
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000)
    return 10
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000)
    return 20
}