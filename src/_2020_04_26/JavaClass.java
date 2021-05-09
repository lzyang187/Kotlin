package _2020_04_26;

public class JavaClass {
    public static void main(String[] args) {
        KotlinClass kotlinClass = new KotlinClass();
        kotlinClass.kotlinFun("java调用kotlin");
    }

    public void javaFun(String javaStr) {
        System.out.println("javaStr=" + javaStr);
    }
}
