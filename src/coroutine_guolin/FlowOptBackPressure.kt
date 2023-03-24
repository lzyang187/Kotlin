package coroutine_guolin

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

// 背压：collectLatest、buffer、conflate，而这3个函数处理这类问题的方式以及适用的场景都各不相同，
// 所以我们必须将每种函数的功能和差异都搞清楚，再根据对应的场景选择合适的处理方式。

fun main() {
    runBlocking {
        // 这里每条数据都是要耗费2秒时长才能处理完。因为默认情况下，collect函数和flow函数会运行在同一个协程当中，
        // 因此collect函数中的代码没有执行完，flow函数中的代码也会被挂起等待。也就是说，我们在collect函数中处理数据需要花费1秒，
        // flow函数同样就要等待1秒。collect函数处理完成数据之后，flow函数恢复运行，发现又要等待1秒，这样2秒钟就过去了才能发送下一条数据。
        flow {
            emit(1)
            delay(1000)
            emit(2)
            delay(1000)
            emit(3)
        }.onEach {
            println("$it is ready")
        }.collect {
            delay(1000)
            println("$it is handled")
        }


        // buffer函数会让flow函数和collect函数运行在不同的协程当中，这样flow中的数据发送就不会受collect函数的影响了，
        // 它提供了一份缓存区，当Flow数据流速不均匀的时候，使用这份缓存区来保证程序运行效率
        flow {
            emit(1)
            delay(1000)
            emit(2)
            delay(1000)
            emit(3)
        }.onEach {
            println("$it is ready")
        }.buffer(5).collect {
            delay(1000)
            println("$it is handled")
        }


        // collectLatest：当有新数据到来时而前一个数据还没有处理完，则会将前一个数据剩余的处理逻辑全部取消。
        // 所以，finish部分的日志是永远得不到输出的。
//        flow {
//            var count = 0
//            val condition = true
//            while (condition) {
//                emit(count)
//                delay(1000)
//                count++
//            }
//        }.collectLatest {
//            println("start handle $it")
//            delay(2000)
//            println("finish handle $it")
//        }

        // conflate：正在处理的数据无论如何都应该处理完，然后准备去处理下一条数据时，直接处理最新的数据即可，中间的数据就都可以丢弃掉了。
        // 拿股票软件举例，服务器端会将股票价格的实时数据源源不断地发送到客户端这边，而客户端这边只需要永远显示最新的股票价格即可，
        // 将过时的数据展示给用户是没有意义的。
        flow {
            var count = 0
            val condition = true
            while (condition) {
                emit(count)
                delay(1000)
                count++
            }
        }.conflate().collect {
            println("start handle $it")
            delay(2000)
            println("finish handle $it")
        }

    }
}
