package search

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

fun main() {
    println("Enter all people:")
    val numPeoples = readLine()!!.toInt()
    val peoples: MutableList<String> = mutableListOf()
    for ( i in 1..numPeoples){
        peoples.add(readLine()!!)
    }
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
