package coroutine

import kotlinx.coroutines.*

fun main() {
//    dispatcher()
    subCoroutine()
}

/**
 * 各调度器
 */
private fun dispatcher() {
    runBlocking {
        // 不传参数，它从启动了它的 CoroutineScope 中承袭了上下文（以及调度器）。在这个案例中，它从 main 线程中的 runBlocking 主协程承袭了上下文
        launch {
            println("launch : I'm working in thread ${Thread.currentThread().name}")
        }
        // 不受限的——在调用它的线程启动了一个协程，但它仅仅只是运行到第一个挂起点。挂起后，它恢复线程中的协程，而这完全由被调用的挂起函数来决定。
        // 非受限的调度器非常适用于执行不消耗 CPU 时间的任务，以及不更新局限于特定线程的任何共享数据（如UI）的协程
        launch(Dispatchers.Unconfined) {
            println("Unconfined : I'm working in thread ${Thread.currentThread().name}")
        }
        // 将会获取默认调度器， 默认调度器使用共享的后台线程池
        launch(Dispatchers.Default) {
            println("Default : I'm working in thread ${Thread.currentThread().name}")
        }
        //
        launch(Dispatchers.IO) {
            println("IO : I'm working in thread ${Thread.currentThread().name}")
        }
        // newSingleThreadContext 为协程的运行启动了一个线程。 一个专用的线程是一种非常昂贵的资源。
        // 在真实的应用程序中两者都必须被释放，当不再需要的时候，使用 close 函数，或存储在一个顶层变量中使它在整个应用程序中被重用。
        launch(newSingleThreadContext("MyOwnThread")) {
            println("newSingleThreadContext : I'm working in thread ${Thread.currentThread().name}")
        }
    }
}

/**
 * 子携程：当一个协程被其它协程在 CoroutineScope 中启动的时候， 它将通过 CoroutineScope.coroutineContext 来承袭上下文，并且这个新协程的 Job 将会成为父协程作业的 子 作业。
 * 当一个父协程被取消的时候，所有它的子协程也会被递归的取消。
 * 然而，当使用 GlobalScope 来启动一个协程时，则新协程的作业没有父作业。 因此它与这个启动的作用域无关且独立运作。
 */
private fun subCoroutine() {
    runBlocking {
        val job = launch {
            // 通过 GlobalScope 启动
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            // 承袭了父协程的上下文
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        job.cancel()
        delay(1000)
        println("main: Who has survived request cancellation?")
    }
}

/**
 * 父协程的职责：一个父协程总是等待所有的子协程执行结束。父协程并不显式的跟踪所有子协程的启动，并且不必使用 Job.join 在最后的时候等待它们
 */
