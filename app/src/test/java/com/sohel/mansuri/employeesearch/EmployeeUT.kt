package com.sohel.mansuri.employeesearch

import com.sohel.mansuri.employeesearch.models.Employee
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Local unit test, which will execute on the development machine (host).
 *
 * Used for testing Employee model and behavior.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class EmployeeUT {
    private lateinit var employee: Employee
    private lateinit var benchedEmployee: Employee

    @Before
    fun testDataSetUp() {
        employee = Employee("Karen", arrayListOf("Chicago", "New York", "Austin"), "Sales Engineer")
        benchedEmployee = Employee("Timmy", null, "Designer")
    }

    @Test
    fun checkEmployeePartOfLocation() {
        assertTrue(employee.isEmployeePartOfLocation("Chi"))
        assertTrue(employee.isEmployeePartOfLocation("chicago"))
        assertTrue(employee.isEmployeePartOfLocation("New Y"))
        assertTrue(employee.isEmployeePartOfLocation("New York"))
        assertTrue(employee.isEmployeePartOfLocation("O"))
    }

    @Test
    fun checkEmployeeNotPartOfLocation() {
        assertFalse(employee.isEmployeePartOfLocation("Sp"))
        assertFalse(employee.isEmployeePartOfLocation("springfield"))
    }

    @Test
    fun checkBenchedEmployee() {
        assertFalse(benchedEmployee.isEmployeePartOfLocation("Chicago"))
    }
}
