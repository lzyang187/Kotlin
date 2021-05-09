//内部类
fun main(args: Array<String>) {
    val out = Out()
    println(out.In().inFun())
}

class Out() {
    private val outVal = 1
    fun outFun() {
        //外部类不能访问内部类的private
        println("外部类的方法 $outVal  ${In().pubInVal}")
    }

    inner class In() {
        private val inVal = 2
        val pubInVal = 3
        fun inFun() {
            outFun()
            println("内部类的方法 $inVal  $outVal")
        }
    }
}