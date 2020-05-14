package search
import java.io.File

class SearchEngine(private val database: List<String>) {
    private val searchIndex: Map<String,List<Int>> =makeSearchIndex()

    private fun makeSearchIndex(): Map<String,List<Int>> {
        val searchIndex: MutableMap<String,List<Int>>  = mutableMapOf()
        database.forEachIndexed(){ i, people -> people.split(" ").forEach {
            key -> searchIndex[key.toUpperCase()] = searchIndex.getOrDefault(key.toUpperCase(), mutableListOf())+i
        }}
        return searchIndex
    }
    fun findAll(): List<String>{
        return database
    }
    private fun findIndex(searchWord: String):List<Int> {
        return searchIndex.getOrDefault(searchWord.toUpperCase(),listOf())
    }
    private fun findAny(searchWords: List<String>): List<Int>{
        return searchWords.fold(listOf<Int>(), {acc, v -> acc + findIndex(v)}).distinct().sorted()
    }
    private fun findAnd(searchWords: List<String>): List<Int>{
        return searchWords.fold(database.indices.toList(), { acc, v -> acc.filter{ t-> findIndex(v).contains(t) }})
    }
    private fun findNone(searchWords: List<String>): List<Int>{
       return  searchWords.fold(database.indices.toList(), { acc, v -> acc.filter{ t -> !findIndex(v).contains(t)}})
    }
    private fun findIndex(strategy: String, searchWords: List<String>): List<Int>{
        return when (strategy) {
            "ANY" -> findAny(searchWords)
            "ALL" -> findAnd(searchWords)
            "NONE" -> findNone(searchWords)
            else -> listOf()
        }
    }
    fun find(strategy: String, searchWords: List<String>): List<String>
    {
        return findIndex(strategy,searchWords).map{v-> database[v]}
    }
}

fun getMenu(): List<String>{
    return listOf(
            "1. Find a person",
            "2. Print all people",
            "0. Exit"
    )
}
fun getStrategy(): List<String>{
    return listOf(
            "ALL",
            "ANY",
            "NONE"
    )
}
fun main(args: Array<String>) {
    val fileName = args[1]
    val peoples = File(fileName).readLines()
    val searchEngine = SearchEngine(peoples)
    loop@ do {
       println("=== Menu ===")
       getMenu().forEach { v -> println(v)}
       when(readLine()!!){
           "1" -> {
               println("Select a matching strategy: %s".format(getStrategy().joinToString(", ")))
               val strategy = readLine()!!
               println("Enter a name or email to search all suitable people.")
               val searchWords = readLine()!!.split(" ").filter{ v-> v.isNotEmpty() }
               val finds = searchEngine.find(strategy, searchWords)
               when (finds.size){
                   0 -> println("No matching people found.")
                   else -> {
                       println("%d persons found:".format(finds.size))
                       finds.forEach { v->println(v)}
                   }
               }
           }
           "2" -> {println("=== List of people ===");searchEngine.findAll().forEach{v->println(v)}}
           "0"-> break@loop
       }
    }while(true)
    println("Bye!")
}
