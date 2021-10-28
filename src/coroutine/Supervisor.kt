package coroutine

import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main() {
    runBlocking {
//        supervisorJob()
        supervisorScope()
    }
}

/**
 * 监督作业：
 * 如果 UI 组件被销毁了（并且它的作业也被取消了），由于它的结果不再被需要了，它有必要使所有的子作业执行失败。
 * SupervisorJob 可以用于这些目的，它类似于常规的 Job，唯一的不同是：SupervisorJob 的取消只会向下传播
 */
private suspend fun supervisorJob() {
    val supervisorJob = SupervisorJob()
    with(CoroutineScope(coroutineContext + supervisorJob)) {
        val handler = CoroutineExceptionHandler { coroutineContext, throwable ->

        }
        // 启动第一个子作业——这个示例将会忽略它的异常（不要在实践中这么做！）
        val firstChild = launch(handler) {
            println("The first child is failing")
            throw AssertionError("The first child is cancelled")
        }
        // 启动第二个子作业
        val secondChild = launch {
            firstChild.join()
            // 取消了第一个子作业且没有传播给第二个子作业
            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // 但是取消了监督的传播
                println("The second child is cancelled because the supervisor was cancelled")
            }
        }
        // 等待直到第一个子作业失败且执行完成
        firstChild.join()
        println("Cancelling the supervisor")
        supervisorJob.cancel()
        secondChild.join()
    }
}

/**
 * 监督作用域：
 * 对于作用域的并发，可以用 supervisorScope 来替代 coroutineScope 来实现相同的目的。
 * 它只会单向的传播并且当作业自身执行失败的时候将所有子作业全部取消。作业自身也会在所有的子作业执行结束前等待， 就像 coroutineScope 所做的那样
 */
private suspend fun supervisorScope() {
    try {
        supervisorScope {
            val child = launch {
                try {
                    println("The child is sleeping")
                    delay(Long.MAX_VALUE)
                } finally {
                    println("The child is cancelled")
                }
            }
            // 使用 yield 来给我们的子作业一个机会来执行打印
            yield()
            println("Throwing an exception from the scope")
            throw AssertionError()
        }
    } catch (e: AssertionError) {
        println("Caught an assertion error")
    }
}



