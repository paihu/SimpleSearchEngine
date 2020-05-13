package search
import java.io.File

class SearchEngine(val peoples: List<String>) {
    val menu = listOf("Find a person", "Print all people", "Exit")
    fun printMenu() {
        println("=== Menu ===")
        println("1. Find a person")
        println("2. Print all people")
        println("0. Exit")
    }

    fun findAll():List<String> {
        return peoples
    }
    fun findPeople(searchWord: String):List<String> {
        return peoples.filter { v-> v.toUpperCase().contains(searchWord.toUpperCase())}
    }
}
fun main(args: Array<String>) {
    val fileName = args[1]
    val peoples = File(fileName).readLines()
    val searchEngine = SearchEngine(peoples)
    loop@ do {
       searchEngine.printMenu()
       when(readLine()!!){
           "1" -> {println("Enter a name or email to search all suitable people."); searchEngine.findPeople(readLine()!!).forEach { v->println(v)}}
           "2" -> {println("=== List of people ===");searchEngine.findAll().forEach{v->println(v)}}
           "0"-> break@loop
       }
    }while(true)
    println("Bye!")
}
