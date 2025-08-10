package com.example.todoapp.data.repository

import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.network.TodoApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TodoRepositoryTest {
    private val api = mockk<TodoApi>(relaxed = true)
    private val repository = TodoRepository(api)

    @Test
    fun `fetchTodos should call api_getTodos`() = runTest {
        val server = listOf(
            Todo(id = 1, title = "A", completed = false),
            Todo(id = 1, title = "B", completed = true)
        )
        coEvery { api.getTodos() } returns server

        repository.fetchTodos()

        assertEquals(server, repository.todos.value)
        coVerify(exactly = 1) { api.getTodos() }
    }

    @Test
    fun `addTodo posts new item and refreshes list`() = runTest {
        val posted = slot<Todo>()
        coEvery { api.addTodo(capture(posted)) } returns TodoApi.MessageResponse("OK")
        val after = listOf(Todo(id = 10, title = "New", completed = false))
        coEvery { api.getTodos() } returns after

        repository.addTodo("New")

        assertEquals(after, repository.todos.value)
        assertTrue(posted.captured.title == "New" && !posted.captured.completed)
        coVerify { api.addTodo(any()) }
        coVerify { api.getTodos() }
    }

    @Test
    fun `updateTodo calls api and refreshes`() = runTest {
        val todo = Todo(id = 5, title = "A", completed = true)
        coEvery { api.updateTodo(todo.id, todo) } returns TodoApi.MessageResponse("OK")
        val after = listOf(Todo(id = 5, title = "A", completed = false))
        coEvery { api.getTodos() } returns after

        repository.updateTodo(todo)

        assertEquals(after, repository.todos.value)
        coVerify { api.updateTodo(5, todo) }
        coVerify { api.getTodos() }
    }

    @Test
    fun `deleteTodo calls api and refreshes`() = runTest {
        val todo = Todo(id = 3, title = "ToDel", completed = false)
        coEvery { api.deleteTodo(todo.id) } returns TodoApi.MessageResponse("OK")
        val after = emptyList<Todo>()
        coEvery { api.getTodos() } returns after

        repository.deleteTodo(todo)

        assertEquals(after, repository.todos.value)
        coVerify { api.deleteTodo(3) }
        coVerify { api.getTodos() }
    }
}