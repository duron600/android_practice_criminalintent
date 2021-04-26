package run.fa20.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CrimeListFragment : Fragment() {

    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter : CrimeAdapter? = null
    private var createViewCounter = 0
    private var bindViewCounter = 0

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(APP_TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view)
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI() {
        adapter = CrimeAdapter(crimeListViewModel.crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime

        private val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        private val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        init {
            view.setOnClickListener(this)
        }

        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()

        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) : RecyclerView.Adapter<CrimeHolder>() {
        // 滚动时执行一次，够用就不执行。
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
            createViewCounter += 1
            Log.i(APP_TAG, "on create view holder, type: $viewType, counter: $createViewCounter")
            val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
            return CrimeHolder(view)
        }

        override fun getItemCount() = crimes.size

        // onCreateViewHolder 想取到不同的 view type 可以覆盖此方法
        // 注意这里以及两个 onXX 方法其实违反了面向对象的原则
        override fun getItemViewType(position: Int): Int = position % 2

        // 滚动时反复执行
        override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
            bindViewCounter += 1
            Log.i(APP_TAG, "on bind view holder: $bindViewCounter")
            val crime = crimes[position]
            holder.bind(crime)
        }

    }

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}