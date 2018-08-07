package golf

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.json.JSONObject
import org.junit.BeforeClass
import org.junit.Test
import java.io.File

class TestUtilities {

    companion object {
        lateinit var utility: Utility

        init {
            fun findCharCount(path: String) = File(path).walkTopDown()
                    .filter { it.isFile }
                    .filter { it.canRead() }
                    .map { it.readText() }
                    .map { "\\s".toRegex().replace(it, "") }
                    .sumBy { it.length }

            listOf("src/main/java", "src/main/kotlin")
                    .map { Pair(it, findCharCount(it)) }
                    .forEach { pair -> println("Searching ${pair.first} found ${pair.second} chars not including whitespace") }
        }

        @BeforeClass
        @JvmStatic
        fun setup() {
            utility = Utilities()
        }
    }

    @Test
    fun `test containsCount`() {
        assertEquals(utility.findOccurrenceCount(8837433, 7), 1)
        assertEquals(utility.findOccurrenceCount(8837433, 9), 0)
        assertEquals(utility.findOccurrenceCount(8837433, 3), 3)
    }

    @Test
    fun `test factorial`() {
        mapOf<Long, Long>(0L to 1,
              1L to 1,
              2L to 2,
              3L to 6,
              4L to 24,
              5L to 120,
              6L to 720).entries.forEach { assertEquals(utility.factorial(it.key), it.value) }
    }

    @Test
    fun `test sanitizeJson`() {
        val json = """
            {
                "organization": {
                    "employees": [
                        {
                            "name": "Joe",
                            "occupation": "Engineer",
                            "sensitive": "123-45-6789"
                        }
                    ],
                    "address": "123 Main Street",
                    "numberArray": [1, 2, 3, 4, 5, 6, "seven", { "sensitive": "unexpected object in array" }],
                    "stringArray": ["one", "two", "three"],
                    "departments": {
                        "engineering": {
                            "bldg": "4/4/4",
                            "sensitive": "SECRET",
                            "nested": {
                                "sensitive": {
                                    "saveAnything": false,
                                },
                                "secure": true
                            }
                        },
                        "hr": {
                            "bldg": "1/1/1",
                            "sensitive": "SECRET"
                        }
                    }
                }
            }
        """.trimIndent()

        val jsonObject = JSONObject(json)

        val keysToRemove = listOf("sensitive")
        val result = utility.sanitizeJson(jsonObject, keysToRemove).toString(4)
        println(result)
        keysToRemove.forEach { assertTrue(!result.contains(it)) }
    }

    @Test
    fun `test loadFromFile`() {
        val people = utility.loadFromFile(File("src/main/resources/500people.txt"))
        assertTrue(people.size == 500)
    }

    @Test
    fun `test groupPeopleByCategory`() {
        val people = utility.loadFromFile(File("src/main/resources/500people.txt"))
        val groupedByFullAddress = utility.groupPeopleByCategory(people, Category.CITY)

        val result = groupedByFullAddress.keys
                .map { Pair<String, MutableList<Person>>(it, groupedByFullAddress[it] ?: mutableListOf()) }
                .find { it.first == "Erie" }

        assertTrue(result?.second?.size == 3)
    }
}