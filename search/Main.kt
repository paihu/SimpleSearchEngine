package search

fun main() {
    val input = readLine()!!.split(" ")
    val searchWord = readLine()

    for ( i in 1..input.size){
        if (searchWord == input[i-1]) {
            print(i)
            return
        }
    }
    print("Not found")

}
