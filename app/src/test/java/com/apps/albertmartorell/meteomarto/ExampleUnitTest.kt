package com.apps.albertmartorell.meteomarto

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    // abans de cada test d'aquesta suit s'executa el setup. Així aïllem aquest test dels altres
    @Before
    fun setup() {


    }

    // després de cada test d'aquesta suit s'executa el tearDown. Així aïllem aquest test dels altres
    @After
    fun tearDown() {


    }

    @Test
    fun hol() {

        // només necessari per a corrutines
        runBlocking {

        }
        // GIVEN: la part de configuraió del test. l?estat que el volem posar abans de començar

        // WHEN: el codi a execugtar

        // THEN: comprovem si s'han generat els resultats esperats a WHEN. Sinó, el test ha fallat

        //assertEquals(4, 2 + 2)

    }

}
