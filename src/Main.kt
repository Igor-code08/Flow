import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

data class Person(val name: String, val role: String)

fun getPersonsFlow(): Flow<Person> = flow {
    val persons = listOf(
        Person("Иван", "developer"),
        Person("Петр", "engineer"),
        Person("Дмитрий", "doctor"),
        Person("София", "military")
    )
    for (person in persons) {
        emit(person)
        delay(100)
    }
}

fun getPhoneFlow(): Flow<String> = flow {
    repeat(4) {
        val phoneNumber = "+7917" + (1..6).map { Random.nextInt(0, 10) }.joinToString("")
        emit(phoneNumber)
        delay(100)
    }
}

fun main() = runBlocking {

    val combinedFlow = getPersonsFlow().zip(getPhoneFlow()) { person, phone ->
        "${person.name} (${person.role}) -> $phone"
    }

    combinedFlow.collect { result ->
        println(result)
    }
}