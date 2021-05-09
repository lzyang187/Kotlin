package _2020_04_26

class Circle : Shape {
    var name = ""
        set(value) {
            //幕后字段
            field = value
        }

    override fun name() {

    }

}