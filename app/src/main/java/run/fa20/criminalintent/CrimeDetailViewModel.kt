package run.fa20.criminalintent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.util.*

class CrimeDetailViewModel : ViewModel() {

    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    // crimeIdLiveData 有新值设置，就会触发更新
    // 更新的结果将被保存到 crimeLiveDate 的 value 属性
    var crimeLiveDate: LiveData<Crime?> = Transformations.switchMap(crimeIdLiveData) {crimeId ->
        crimeRepository.getCrime(crimeId)
    }

    fun loadCrime(crimeId: UUID) {
        crimeIdLiveData.value = crimeId
    }

    fun saveCrime(crime: Crime) {
        crimeRepository.updateCrime(crime)
    }
}