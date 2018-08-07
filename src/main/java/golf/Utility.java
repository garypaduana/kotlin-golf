package golf;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Utility {

    /**
     * Determines the number of times a digit occurs within a larger number.
     *
     * @param number
     * @param digit
     * @return
     */
    int findOccurrenceCount(int number, int digit);

    /**
     * Compute the factorial for a given number.  Limited to 64-bit long return values.
     *
     * @param number
     * @return
     */
    long factorial(long number);

    /**
     * Remove all specified keys from a JSONObject.
     *
     * @param json
     * @param keysToRemove
     * @return
     */
    JSONObject sanitizeJson(JSONObject json, List<String> keysToRemove);

    /**
     * Groups people by a desired category.
     *
     * @param people
     * @param groupingCategory
     * @return
     */
    Map<String, List<Person>> groupPeopleByCategory(Set<Person> people, Category groupingCategory);

    /**
     * Reads a tab-delimited file and creates a Set of Person.  There is one entry per line and the columns are:
     * id	first_name	last_name	address	city	state	zip
     *
     * @param file
     * @return
     */
    Set<Person> loadFromFile(File file);
}
