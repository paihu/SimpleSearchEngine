package search

fun main() {
    println("Enter the number of people:")
    val numPeoples = readLine()!!.toInt()
    val peoples: MutableList<String> = mutableListOf()
    for ( i in 1..numPeoples){
        peoples.add(readLine()!!)
    }
    println("Enter the number of search queries:")
    val numQueries = readLine()!!.toInt()
    for ( i in 1..numQueries){
        val searchWord = readLine()!!.toUpperCase()
        if (peoples.find{ people -> people.toUpperCase().contains(searchWord)} is String){
            println("Found people: ")
            for (people in peoples){
                if (people.toUpperCase().contains(searchWord)){
                    println(people)
                }
            }
        } else{
            println("No matching people found.")
        }
    }
}
