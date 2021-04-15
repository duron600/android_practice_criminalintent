package run.fa20.criminalintent

import java.util.*

open class Crime(val id: UUID = UUID.randomUUID(),
            var title: String = "",
            var date: Date = Date(),
            var isSolved: Boolean = false) {

}