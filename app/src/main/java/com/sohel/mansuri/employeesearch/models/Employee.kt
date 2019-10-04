package com.sohel.mansuri.employeesearch.models

/**
 * Created by sohelmansuri on 8/20/19.
 */
data class Employee(val name: String?,
                    val locations: ArrayList<String>?,
                    val title: String?) {

    /**
     * Determine if an employee is part of the location that is
     * being searched for.  Search can be a partial match.
     *
     * @param searchString  The string to search for, could be a partial match.
     *
     * @return              Boolean value correlated to employee's location match.
     */
    fun isEmployeePartOfLocation(searchString: String): Boolean {
        var matchingLocationFound = false
        locations?.let {
            locations
                    .filter { it.contains(searchString, true) }
                    .forEach { matchingLocationFound = true }
        }

        return matchingLocationFound
    }
}