package com.example.todoapp.viewmodel

import android.util.Log
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.repository.TodoRepository
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class TodoViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: TodoRepository
    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk(relaxed = true)
        viewModel = TodoViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkStatic(Log::class)
    }

    @Test
    fun `addTodo should call repository_addTodo`() = runTest {
        val title = "Test Todo"

        viewModel.addTodo(title)
        testScheduler.advanceUntilIdle()

        coVerify { repository.addTodo(title) }
    }

    @Test
    fun `toggleCompleted should update todo with flipped completed status`() = runTest {
        val todo = Todo(id = 1, title = "Test Todo", completed = false)

        viewModel.toggleCompleted(todo)
        testScheduler.advanceUntilIdle()

        val expectedTodo = todo.copy(completed = !todo.completed)
        coVerify {repository.updateTodo(expectedTodo)}
    }

    @Test
    fun `deleteTodo should call repository_deleteTodo`() = runTest {
        val todo = Todo(id = 1, title = "Delete me", completed = false)

        viewModel.deleteTodo(todo)
        testScheduler.advanceUntilIdle()

        coVerify { repository.deleteTodo(todo) }
    }

    @Test
    fun `init should call repository_fetch_Todos`() = runTest {
        // モックを使用し、ViewModelを再作成
        repository = mockk(relaxed = true)
        viewModel = TodoViewModel(repository)

        testScheduler.advanceUntilIdle()

        coVerify { repository.fetchTodos() }
    }

}