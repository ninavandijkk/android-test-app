package com.example.testapplication

import com.example.testapplication.data.Recipe
import com.example.testapplication.data.RecipesRepository
import com.example.testapplication.recipe.RecipeModel
import com.example.testapplication.recipe.Recipes
import com.example.testapplication.recipe.list.RecipeViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.never
import org.mockito.kotlin.whenever

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class ExampleUnitTest {
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private lateinit var repositoryMock: RecipesRepository

    private lateinit var listViewModel: RecipeViewModel

    private lateinit var itemViewModel: com.example.testapplication.recipe.item.RecipeViewModel

    @Before
    fun setup() {
        listViewModel = RecipeViewModel(repositoryMock)
        itemViewModel = com.example.testapplication.recipe.item.RecipeViewModel(repositoryMock)
    }

    @Test
    fun getRandomRecipes() = runTest {
        val recipes = Recipes(
            recipeList = listOf(
                RecipeModel(
                    iid = 1,
                    title = "test Recipe",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
                RecipeModel(
                    iid = 2,
                    title = "test Recipe 2",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
                RecipeModel(
                    iid = 3,
                    title = "test Recipe 3",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
            ),
            searchResults = emptyList()
        )
        whenever(repositoryMock.getRandomRecipes()).thenReturn(recipes)

        listViewModel.getRandomRecipes()

        verify(repositoryMock).getRandomRecipes()
    }

    @Test
    fun getEmptyRandomRecipes() = runTest {
        whenever(repositoryMock.getRandomRecipes()).thenReturn(Recipes(emptyList(), emptyList()))

        listViewModel.getRandomRecipes()

        verify(repositoryMock).getRandomRecipes()
    }

    @Test
    fun searchRecipes() = runTest {
        val query = "test"
        val recipes = Recipes(
            recipeList = emptyList(),
            searchResults = listOf(
                RecipeModel(
                    iid = 1,
                    title = "test Recipe",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
                RecipeModel(
                    iid = 2,
                    title = "test Recipe 2",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
                RecipeModel(
                    iid = 3,
                    title = "test Recipe 3",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    nrOfServings = 1,
                ),
            )
        )
        whenever(repositoryMock.searchRecipes(query)).thenReturn(recipes)

        listViewModel.searchRecipes(query)

        verify(repositoryMock).searchRecipes(query)
    }

    @Test
    fun emptySearchRecipes() = runTest {
        val query = "test"
        whenever(repositoryMock.searchRecipes(query)).thenReturn(Recipes(emptyList(), emptyList()))

        listViewModel.searchRecipes(query)

        verify(repositoryMock).searchRecipes(query)
    }

    @Test
    fun getFavorites() = runTest {
        val recipes = flowOf(
            listOf(
                Recipe(
                    iid = 1,
                    title = "test Recipe",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    steps = emptyList(),
                    nrOfServings = 1,
                ),
                Recipe(
                    iid = 2,
                    title = "test Recipe 2",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    steps = emptyList(),
                    nrOfServings = 1,
                ),
                Recipe(
                    iid = 3,
                    title = "test Recipe 3",
                    imageUrl = null,
                    isVegan = false,
                    isVegetarian = false,
                    prepTime = 0,
                    ingredients = emptyList(),
                    steps = emptyList(),
                    nrOfServings = 1,
                ),
            )
        )
        whenever(repositoryMock.getAllRecipesStream()).thenReturn(recipes)

        listViewModel.getFavorites()

        verify(repositoryMock).getAllRecipesStream()
    }


    @Test
    fun getEmptyFavorites() = runTest {
        whenever(repositoryMock.getAllRecipesStream()).thenReturn(emptyFlow())

        listViewModel.getFavorites()

        verify(repositoryMock).getAllRecipesStream()
    }

    @Test
    fun addRecipeToFavorites() = runTest {
        val id = 1
        val recipe = Recipe(
            id = 1,
            iid = 1,
            title = "test Recipe",
            imageUrl = null,
            isVegan = false,
            isVegetarian = false,
            prepTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            nrOfServings = 1,
        )
        whenever(repositoryMock.getRecipeById(eq(id))).thenReturn(recipe)
        whenever(repositoryMock.isFavorite(eq(id))).thenReturn(false)

        listViewModel.addRecipeToFavorites(id)

        verify(repositoryMock).insertItem(recipe)
        verify(repositoryMock, never()).deleteItem(recipe)
    }

    @Test
    fun deleteRecipeFromFavorites() = runTest {
        val id = 1
        val recipe = Recipe(
            id = 1,
            iid = 1,
            title = "test Recipe",
            imageUrl = null,
            isVegan = false,
            isVegetarian = false,
            prepTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            nrOfServings = 1,
        )
        whenever(repositoryMock.getRecipeById(eq(id))).thenReturn(recipe)
        whenever(repositoryMock.isFavorite(eq(id))).thenReturn(true)

        listViewModel.addRecipeToFavorites(id)

        verify(repositoryMock, never()).insertItem(recipe)
        verify(repositoryMock).deleteItem(recipe)
    }

    @Test
    fun getRecipeById() = runTest {
        val id = 1
        val recipe = Recipe(
            id = 1,
            iid = 1,
            title = "test Recipe",
            imageUrl = null,
            isVegan = false,
            isVegetarian = false,
            prepTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            nrOfServings = 1,
        )
        whenever(repositoryMock.getRecipeById(eq(id))).thenReturn(recipe)

        itemViewModel.getRecipeById(id)

        verify(repositoryMock).getRecipeById(id)
    }

    @Test
    fun addRecipeToFavoritesFromItemDetail() = runTest {
        val recipe = Recipe(
            id = 1,
            iid = 1,
            title = "test Recipe",
            imageUrl = null,
            isVegan = false,
            isVegetarian = false,
            prepTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            nrOfServings = 1,
        )
        whenever(repositoryMock.isFavorite(eq(recipe.iid))).thenReturn(false)

        itemViewModel.addRecipeToFavorites(recipe)

        verify(repositoryMock).insertItem(recipe)
        verify(repositoryMock, never()).deleteItem(recipe)
    }

    @Test
    fun deleteRecipeFromFavoritesFromItemDetail() = runTest {
        val recipe = Recipe(
            id = 1,
            iid = 1,
            title = "test Recipe",
            imageUrl = null,
            isVegan = false,
            isVegetarian = false,
            prepTime = 0,
            ingredients = emptyList(),
            steps = emptyList(),
            nrOfServings = 1,
        )
        whenever(repositoryMock.isFavorite(eq(recipe.iid))).thenReturn(true)

        itemViewModel.addRecipeToFavorites(recipe)

        verify(repositoryMock, never()).insertItem(recipe)
        verify(repositoryMock).deleteItem(recipe)
    }
}
