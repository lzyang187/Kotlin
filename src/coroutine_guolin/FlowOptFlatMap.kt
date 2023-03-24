package coroutine_guolin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * 操作符函数到了flatMap系列，难度就开始骤升了。前面我们所学的所有内容都是在一个flow上进行操作，而从flatMap开始，要上升到对两个flow进行操作了。
 * flatMap的核心，就是将两个flow中的数据进行映射、合并、压平成一个flow，最后再进行输出。
 */
fun main() {
    runBlocking {
        // flatMapConcat
        flowOf(1, 2, 3).flatMapConcat {
            // it就是来自第一个flow中的数据
            println("it = $it")
            flowOf("a$it", "b$it")
        }.collect {
            println("flatMapConcat collect = $it")
        }
        // flatMapConcat使用场景：想要获取用户的数据，但是获取用户数据必须要有token授权信息才行，
        // 因此我们得先发起一个请求去获取token信息，然后再发起另一个请求去获取用户数据。
        sendGetTokenRequest().flatMapConcat {
            sendGetUserInfoRequest(it)
        }.flowOn(Dispatchers.IO).collect {
            println("flatMapConcat userInfo = $it")
        }

        // flatMapMerge：concat是连接的意思，merge是合并的意思。连接一定会保证数据是按照原有的顺序连接起来的，
        // 而合并则只保证将数据合并到一起，并不会保证顺序
        flowOf(300, 200 ,100)
            .flatMapMerge {
                flow {
                    delay(it.toLong())
                    emit("a$it")
                    emit("b$it")
                }
            }.collect {
                println("flatMapMerge collect = $it")
            }

        // flatMapLatest：flow1中的数据传递到flow2中会立刻进行处理，但如果flow1中的下一个数据要发送了，
        // 而flow2中上一个数据还没处理完，则会直接将剩余逻辑取消掉，开始处理最新的数据
        flow {
            emit(1)
            delay(150)
            emit(2)
            delay(50)
            emit(3)
        }.flatMapLatest {
            flow {
                delay(100)
                emit("$it")
            }
        }.collect {
            println("flatMapLatest collect = $it")
        }
    }
}

// 发起获取token的请求
fun sendGetTokenRequest(): Flow<String> {
    return flow {
        delay(500)
        emit("token")
    }
}

// 发起用token获取用户信息的请求
fun sendGetUserInfoRequest(token: String): Flow<String> = flow {
    // send request with token to get user info
    delay(500)
    emit("userInfo")
}

