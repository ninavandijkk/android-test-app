package com.example.testapplication

import com.example.testapplication.data.RecipesRepository
import com.example.testapplication.recipe.list.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    private lateinit var repository: RecipesRepository

    private lateinit var viewModel: RecipeViewModel

    @Before
    fun setup() {
        viewModel = RecipeViewModel(repository)
    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun getEmptyFavorites() {
        given(repository.getAllRecipesStream()).willReturn(emptyFlow())
    }
}
