package com.udacity.sandwichclub.utils;

import android.support.annotation.NonNull;

import com.udacity.sandwichclub.model.Sandwich;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        // We will use this index int to keep track of our position in the json string
        // We will start with 26 because this is the index of the first value of every json String
        int index = 26;

        // These are all the Strings and Lists that we will find and use to create the sandwich object
        String mainName;
        List<String> alsoKnownAs = new ArrayList<String>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<String>();

        // first let's find the mainName
        // All of the json Strings in this project follow the same format, so we can take some shortcuts.
        // For all the json Strings, the value of mainName begins on the 26th index.
        // Starting from the 26th index, we will loop through this portion of the string until
        // we reach the '/' character indicating the end of the value.
        // We will use two integers, front and back, to give us the beginning and end of the substring
        // we need.
        int front = index;
        while (json.charAt(index) != '/') {
            index++;
        }
        int back = index - 1;
        // We can also take the opportunity in this method to assign placeholder text if the value is
        // found to be empty.
        if (front == back)
            mainName = "No data for this field.";
        else
            mainName = json.substring(front, back);

        // now we add 20 to index to skip ahead to the alsoKnownAs value and apply the same idea as before.
        // This time, however, we are dealing with a list, so the process will be a little different.
        // We are not only looping through characters, we are actually looping through Strings and
        // adding them to a list.
        // First, we must account for the list being empty. We can do this by wrapping the code in a
        // while loop.
        index += 20;
        while (json.charAt(index) != ']') {
            // Since we are not at the end of the list, we move forward to the first index of the text:
            index += 2;
            if (json.charAt(index) == ',')
                index += 3; // if we have encountered a comma, then we move forward to the index of the next text
            else { // Here we use the same process as for mainName to find the String
                front = index;
                while (json.charAt(index) != '/')
                    index++;
                back = index - 1;
                if (front == back)
                    alsoKnownAs.add("No data for this entry.");
                else
                    alsoKnownAs.add(json.substring(front, back));
            }
        }

        // Now we add 23 to index to advance and find placeOfOrigin the same way as mainName
        index += 23;
        front = index;
        while (json.charAt(index) != '/') {
            index++;
        }
        back = index - 1;
        if (front == back)
            placeOfOrigin = "No data for this field.";
        else
            placeOfOrigin = json.substring(front, back);

        // We add 21 to index and find description
        index += 21;
        front = index;
        while (json.charAt(index) != '/') {
            index++;
        }
        back = index - 1;
        if (front == back)
            description = "No data for this field.";
        else
            description = json.substring(front, back);

        // We add 15 to index and find image
        index += 15;
        front = index;
        while (json.charAt(index) != '/') {
            index++;
        }
        back = index - 1;
        if (front == back)
            image = "No data for this field.";
        else
            image = json.substring(front, back);

        // We add 22 to index and find the ingredients list using the same process as for alsoKnownAs
        index += 22;
        while (json.charAt(index) != ']') {
            // Since we are not at the end of the list, we move forward to the first index of the text:
            index += 2;
            if (json.charAt(index) == ',')
                index += 3; // if we have encountered a comma, then we move forward to the index of the next text
            else { // Here we use the same process as for mainName to find the String
                front = index;
                while (json.charAt(index) != '/')
                    index++;
                back = index - 1;
                if (front == back)
                    ingredients.add("No data for this entry.");
                else
                    ingredients.add(json.substring(front, back));
            }
        }

        // Now we return our results.
        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
