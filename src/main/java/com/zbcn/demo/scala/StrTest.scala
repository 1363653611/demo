/**
  * String 测试
  */
object Test {
        val greeting: String = "Hello, World!"

        def main(args: Array[String]) {
                println( greeting )
                testStr()
                println( lenStr() )
                contatStr()
        }

        //字符串相加
        private def testStr(): Unit ={
                var buff = new StringBuilder;
                buff += 'a'
                buff ++= "bcdef"
                buff ++= "ghk"
                println(buff);
        }

        //字符串长度
        def lenStr(): Int ={
                //定义字符串
                val palindrome = "www.runoob.com"
                //字符串长度
                return  palindrome.length;
        }
        //String 类中使用 concat() 方法来连接两个字符串：
        def contatStr(): Unit ={
                val s1: String = "dsfsfdsfsd"
                println(s1.concat("测试字符串链接"))
        }
}