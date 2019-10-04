package com.sohel.mansuri.employeesearch.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.sohel.mansuri.employeesearch.R
import com.sohel.mansuri.employeesearch.models.Employee

/**
 * Created by sohelmansuri on 8/21/19.
 *
 * EmployeeAdapter which contains EmployeeHolder and filtering logic.
 *
 */
class EmployeeAdapter(private val context: Context,
                      private var dataSource: ArrayList<Employee>) : RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder>(), Filterable {

    private var dataSourceOriginal: ArrayList<Employee> = arrayListOf()

    /**
     * A copy of data source needs to be held onto without any modifications,
     * for use when undoing the filtering back to original data.
     */
    init {
        dataSourceOriginal.addAll(dataSource)
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bind(dataSource[position])
    }

    override fun getItemCount(): Int {
        return dataSource.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        return EmployeeHolder(LayoutInflater.from(context).inflate(R.layout.employee_item, parent, false))

    }

    /**
     * Employee ViewHolder in charge of binding data to view elements.
     */
    inner class EmployeeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.employee_item_name)
        private val title = itemView.findViewById<TextView>(R.id.employee_item_title)
        private val locations = itemView.findViewById<TextView>(R.id.employee_item_locations)

        fun bind(employee: Employee) {
            name.text = employee.name ?: ""
            title.text = employee.title ?: ""
            locations.text = employee.locations?.joinToString() ?: ""
        }
    }

    override fun getFilter(): Filter {
        return employeeFilter
    }

    /**
     * Custom filter that perform filtering as search query is typed
     * (on the fly, no need to press enter or done).
     *
     * Filtering is done by employee's location.
     */
    private var employeeFilter = object : Filter() {
        override fun performFiltering(search: CharSequence?): FilterResults {
            val filteredList = arrayListOf<Employee>()

            if(search.isNullOrEmpty()) {
                filteredList.addAll(dataSourceOriginal)
            } else {
                val trimmedSearchString = search?.toString()?.toLowerCase()?.trim() ?: ""
                dataSourceOriginal.filterTo(filteredList) { it.isEmployeePartOfLocation(trimmedSearchString) }
            }

            val results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(search: CharSequence?, results: FilterResults?) {
            dataSource.clear()
            results?.values.let {
                dataSource.addAll(it as ArrayList<Employee>)
            }
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): Employee {
        return dataSource[position]
    }
}