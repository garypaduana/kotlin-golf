package golf;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Utilities implements Utility {

    public int findOccurrenceCount(int number, int digit){
        String numStr = Integer.toString(number);
        int count = 0;
        for(int i = 0; i < numStr.length(); i++){
            if(Integer.valueOf(numStr.substring(i, i + 1)).toString().equals(Integer.toString(digit))){
                count++;
            }
        }
        return count;
    }

    public long factorial(long number){

        if (number < 0) {
            throw new IllegalArgumentException("number must be >= 0");
        }

        if(number == 0){
            return 1;
        }
        long product = 1;
        for(long i = number; i > 0; i--){
            product = product * i;
        }

        return product;
    }

    public JSONObject sanitizeJson(JSONObject json, List<String> keysToRemove) {

        Iterator<String> it = json.keys();
        while (it.hasNext()) {
            String key = it.next();
            if (keysToRemove.contains(key)) {
                it.remove();
            }
            else {
                Object obj = json.get(key);
                if (obj instanceof JSONObject) {
                    json.put(key, sanitizeJson((JSONObject) obj, keysToRemove));
                }
                else if (obj instanceof JSONArray) {
                    JSONArray array = (JSONArray) obj;

                    for (int i = 0; i < array.length(); i++) {
                        if (array.get(i) instanceof JSONObject) {
                            JSONObject cleaned = sanitizeJson((JSONObject) array.get(i), keysToRemove);

                            if (cleaned.length() > 0) {
                                array.put(i, cleaned);
                            }
                            else {
                                array.remove(i);
                            }
                        }
                    }
                }
            }
        }
        return json;
    }

    @Override
    public Map<String, List<Person>> groupPeopleByCategory(Set<Person> people, Category groupingCategory) {

        if (groupingCategory == null) {
            throw new IllegalArgumentException("groupingCategory is null");
        }

        switch (groupingCategory) {
            case FULL_ADDRESS:
                return people.stream().collect(Collectors.groupingBy(Person::getFullAddress));
            case CITY:
                return people.stream().collect(Collectors.groupingBy(Person::getCity));
            case STATE:
                return people.stream().collect(Collectors.groupingBy(Person::getState));
            case ZIP:
                return people.stream().collect(Collectors.groupingBy(Person::getZip));
            default:
                throw new IllegalStateException("unexpected groupingCategory");
        }
    }

    @Override
    public Set<Person> loadFromFile(File file) {

        Set<Person> people = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                if (line.startsWith("id")) {
                    continue;
                }
                String[] pieces = line.split("\t");
                people.add(new Person(pieces[0], pieces[1], pieces[2], pieces[3], pieces[4], pieces[5], pieces[6]));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return people;
    }
}
