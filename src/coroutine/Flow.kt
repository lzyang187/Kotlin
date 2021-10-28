package coroutine

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

/**
 * 挂起函数可以异步的返回单个值，但是该如何异步返回多个计算好的值呢？这正是 Kotlin 流（Flow）的用武之地
 */

fun main() {
//    val seq = seq()
//    seq.forEach {
//        println("seq：$it")
//    }
    runBlocking {
//        simple().forEach {
//            println("simple：$it")
//        }
        // 流使用 collect 函数 收集 值
//        flowSimple().collect {
//            println("flow：$it")
//        }
        // 该流在每次收集的时候启动， 这就是为什么当我们再次调用 collect 时我们会看到“Flow started”
//        println("Calling simple function...")
//        val flow = flowCold()
//        println("Calling collect...")
//        flow.collect { value -> println("flowCold：$value") }
//        println("Calling collect again...")
//        flow.collect { value -> println("flowCold again：$value") }

        // 流的超时取消
        withTimeoutOrNull(2500) {
            flowSimple().collect {
                println("flow：$it")
            }
        }
        println("Done")

        // flowOf 构建器定义了一个发射固定值集的流
        flowOf(listOf(5, 1)).collect {
            println("flowOf：$it")
        }
        // 使用 .asFlow() 扩展函数，可以将各种集合与序列转换为流
        listOf(1, 3).asFlow().collect {
            println("asFlow：$it")
        }
    }
}

/**
 * 序列，会一个一个返回值
 */
private fun seq(): Sequence<Int> {
    return sequence {
        for (i in 0..3) {
            Thread.sleep(1000)
            yield(i)// 产生下一个值
        }
    }
}

/**
 * seq()函数的计算过程阻塞运行该代码的主线程。
 * 当这些值由异步代码计算时，我们可以使用 suspend 修饰符标记函数， 这样它就可以在不阻塞的情况下执行其工作并将结果作为列表返回
 */
private suspend fun simple(): List<Int> {
    delay(1000)
    return listOf(0, 1, 2, 3)
}

/**
 * 使用 List 结果类型，意味着我们只能一次返回所有值。
 * 为了表示异步计算的值流（stream），我们可以使用 Flow 类型（正如同步计算值会使用 Sequence 类型）
 */
private fun flowSimple(): Flow<Int> {
    // 流构建器
    return flow {
        for (i in 0..3) {
            delay(1000)
            println("Emitting $i")
            // 流使用 emit 函数 发射 值
            emit(i)
        }
    }
}

/**
 * Flow 是一种类似于序列的冷流 — 这段 flow 构建器中的代码直到流被收集的时候才运行
 */
private fun flowCold(): Flow<Int> {
    return flow {
        println("Flow started")
        for (i in 0..3) {
            delay(1000)
            emit(i)
        }
    }
}







