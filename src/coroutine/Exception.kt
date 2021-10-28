package coroutine

import kotlinx.coroutines.*

fun main() {
    runBlocking {
//        exception()
//        handler()

        // 当一个协程使用 Job.cancel 取消的时候，它会被终止，但是它不会取消它的父协程
        val parent = launch {
            val child = launch {
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    println("Child is cancelled")
                }
            }
            yield()
            println("Cancelling child")
            child.cancel()
            child.join()
            yield()
            println("Parent is not cancelled")
        }
        parent.join()

    }
}

private suspend fun exception() {
    val launchJob = GlobalScope.launch {
        println("Throwing exception from launch")
        throw IndexOutOfBoundsException() // 我们将在控制台打印 Thread.defaultUncaughtExceptionHandler
    }
    launchJob.join()

    val deferred = GlobalScope.async {
        println("Throwing exception from async")
        throw ArithmeticException() // 没有打印任何东西，依赖用户去调用等待
    }
    try {
        deferred.await()
        println("Unreached")
    } catch (e: Exception) {
        println("async的异常： $e")
    }
}

private suspend fun handler() {
    val handler = CoroutineExceptionHandler { _, throwable ->
        println("handler接收的异常：$throwable")
    }
    val job = GlobalScope.launch(handler) {
        throw AssertionError()
    }
    val deferred = GlobalScope.async(handler) { // 同样是根协程，但使用 async 代替了 launch
        throw ArithmeticException() // 没有打印任何东西，依赖用户去调用 deferred.await()
    }
    joinAll(job, deferred)
//    deferred.await()
}



