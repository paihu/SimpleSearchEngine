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
    fun findPeopleIndex(searchWord: String):List<Int> {
        return searchIndex.getOrDefault(searchWord.toUpperCase(),listOf())
    }

    fun findAnd(searchWords: List<String>): List<String>{
        return searchWords.fold(this, {acc, v -> SearchEngine(acc.findPeople(v))}).findAll()
    }
    fun findAny(searchWords: List<String>): List<String>{
        return searchWords.fold(listOf<Int>(), {acc, v -> acc + findPeopleIndex(v)}).toSet().toList().sorted().map{v-> peoples[v]}
    }
    fun findNone(searchWords: List<String>): List<String>{
       val notReturnIndex =  searchWords.fold(listOf<Int>(), {acc, v -> acc + findPeopleIndex(v)}).toSet()
       return peoples.indices.fold(listOf<String>() ,{ acc, v -> if (notReturnIndex.contains(v)) acc else acc + peoples[v]})
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
               println("Select a matching stragegy: ALL, ANY, NONE")
               val strategy = readLine()!!
               println("Enter a name or email to search all suitable people.")
               val searchWords = readLine()!!.split(" ").filter{ v-> v.length>0}
               val finds = when (strategy){
                   "ALL" -> searchEngine.findAnd(searchWords)
                   "ANY" -> searchEngine.findAny(searchWords)
                   else -> searchEngine.findNone(searchWords)
               }
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
