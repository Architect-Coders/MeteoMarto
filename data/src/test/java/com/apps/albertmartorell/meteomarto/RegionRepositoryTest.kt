package com.apps.albertmartorell

import albertmartorell.com.data.repositories.PermissionChecker
import albertmartorell.com.data.repositories.RegionRepository
import albertmartorell.com.data.sources.LocationDataSource
import albertmartorell.com.domain.cityweather.Coordinates
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

// Amb aquesta anotació, es crea totes les dependencies Mock i després les neteja cada vegada que s'executa un test
@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    // NO està permés variables globals

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    // Abans de cada test d'aquesta suit s'executa el setup. Així aïllem aquest test dels altres. D'aquesta manera tots els
    // són independents: és igual l'ordre o si executem un i un altre no.
    @Before
    fun setup() {

        // Construïm el region repository amb els mocks que hem fet abans.
        // Així cada vegada que executem els testos el RegionRepository es crea de nou, i no tindrem un estat antic en aquest objecte.
        regionRepository = RegionRepository(locationDataSource, permissionChecker)

    }

    // després de cada test d'aquesta suit s'executa el tearDown. Així aïllem aquest test dels altres
    @After
    fun tearDown() {


    }

    @Test
    fun `returns default when permission is denied`() {

        // Només necessari quan testejem corrutines: per assegurar que el test no acabi fins que la corrutina ho faci
        runBlocking {

            // GIVEN: la part de configuració del test. L'estat que el volem posar abans de començar
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                false
            )
            // WHEN: el codi a executar
            val coordinates = regionRepository.findLastRegion()
            val coordinatesExpected = Coordinates(0F, 0F)

            // THEN: comprovem si s'han generat els resultats esperats a WHEN. Sinó, el test ha fallat
            assertEquals(coordinatesExpected, coordinates)

        }

    }

    @Test
    fun `returns default when permission is granted`() {

        // Només necessari quan testejem corrutines: per assegurar que el test no acabi fins que la corrutina ho faci
        runBlocking {

            // GIVEN: la part de configuració del test. L'estat que el volem posar abans de començar
            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(
                true
            )
            // WHEN: el codi a executar
            val coordinates = regionRepository.findLastRegion()

            val coordinatesExpected =
                Coordinates(RegionRepository.DEFAULT_LATITUDE, RegionRepository.DEFAULT_LONGITUDE)

            // THEN: comprovem si s'han generat els resultats esperats a WHEN. Sinó, el test ha fallat
            assertEquals(coordinatesExpected, coordinates)

        }

    }

}
