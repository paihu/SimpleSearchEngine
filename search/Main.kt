package search
import java.io.File

class SearchEngine(private val peoples: List<String>) {
    private val searchIndex: Map<String,List<Int>> =makeSearchIndex()

    private fun makeSearchIndex(): Map<String,List<Int>> {
        val searchIndex: MutableMap<String,List<Int>>  = mutableMapOf()
        peoples.forEachIndexed(){ i, people -> people.split(" ").forEach {
            key -> searchIndex[key.toUpperCase()] = searchIndex.getOrDefault(key.toUpperCase(), mutableListOf())+i
        }}
        return searchIndex
    }

    fun getMenu(): List<String>{
        return listOf(
                "1. Find a person",
                "2. Print all people",
                "0. Exit"
        )
    }

    fun findAll(): List<String>{
        return peoples
    }
    fun findPeopleNumber(searchWord: String): Int {
        return searchIndex.getOrDefault(searchWord.toUpperCase(),listOf()).size
    }
    fun findPeople(searchWord: String):List<String> {
        return searchIndex.getOrDefault(searchWord.toUpperCase(),listOf()).map{v -> peoples[v]}
    }
}
fun main(args: Array<String>) {
    val fileName = args[1]
    val peoples = File(fileName).readLines()
    val searchEngine = SearchEngine(peoples)
    loop@ do {
       println("=== Menu ===")
       searchEngine.getMenu().forEach { v -> println(v)}
       when(readLine()!!){
           "1" -> {
               println("Enter a name or email to search all suitable people.")
               val searchWord = readLine()!!
               when (val found: Int = searchEngine.findPeopleNumber(searchWord)){
                   0 -> println("No matching people found.")
                   else -> {
                   println("%d persons found:".format(found))
                   searchEngine.findPeople(searchWord).forEach { v->println(v)}
                   }
               }
           }
           "2" -> {println("=== List of people ===");searchEngine.findAll().forEach{v->println(v)}}
           "0"-> break@loop
       }
    }while(true)
    println("Bye!")
}
