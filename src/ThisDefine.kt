fun main(args: Array<String>) {
    val aa = AA()
    aa.BB().foo()
}

class AA {
    inner class BB {
        fun foo() {
            val a = this@AA
            val b = this@BB
            val c = this
            println("$a  $b  $c ")
        }
    }
}