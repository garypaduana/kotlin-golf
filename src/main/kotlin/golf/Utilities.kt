package golf

import org.json.JSONObject
import java.io.File

class UtilitiesKt() : Utility {

    override fun loadFromFile(file: File?): MutableSet<Person> {
        TODO("not implemented")
    }

    override fun groupPeopleByCategory(people: MutableSet<Person>?, groupingCategory: Category?): MutableMap<String, MutableList<Person>> {
        TODO("not implemented")
    }

    override fun sanitizeJson(json: JSONObject?, keysToRemove: MutableList<String>?): JSONObject {
        TODO("not implemented")
    }

    override fun factorial(number: Long): Long {
        TODO("not implemented")
    }

    override fun findOccurrenceCount(number: Int, digit: Int): Int {
        TODO("not implemented")
    }
}