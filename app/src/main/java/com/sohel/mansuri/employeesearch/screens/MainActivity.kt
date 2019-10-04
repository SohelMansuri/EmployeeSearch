package com.sohel.mansuri.employeesearch.screens

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.inputmethod.EditorInfo
import com.google.gson.Gson
import com.sohel.mansuri.employeesearch.R
import com.sohel.mansuri.employeesearch.adapters.EmployeeAdapter
import com.sohel.mansuri.employeesearch.models.RootEmployees
import com.sohel.mansuri.employeesearch.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var rootEmployees: RootEmployees
    private lateinit var adapter: EmployeeAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jsonData = FileUtils.getJSONDataFromAssets(this, "employees.json")
        rootEmployees = Gson().fromJson(jsonData, RootEmployees::class.java)

        setUpRecyclerView()
    }

    /**
     * Used to set up recycler view, consisting of layout manager
     * initialization and adapter set up.
     */
    private fun setUpRecyclerView() {
        layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter(this, rootEmployees.employees ?: arrayListOf())

        main_activity_recycler_view.adapter = adapter
        main_activity_recycler_view.layoutManager = layoutManager
        main_activity_recycler_view.setHasFixedSize(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        setUpSearchView(searchView)
        return true
    }

    /**
     * Used to set up search view with custom query hint and IME options.
     *
     * @param searchView    SearchView to customize and add query text listener.
     */
    private fun setUpSearchView(searchView: SearchView) {
        searchView.queryHint = resources.getString(R.string.search_for_employees_by_location)
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE
        searchView.maxWidth = Integer.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }
}
