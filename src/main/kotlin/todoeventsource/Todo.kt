package todoeventsource

import java.util.UUID

data class Todo(
    val id: UUID,
    val title: String,
    val completed: Boolean = false,
    val deleted: Boolean = false
) {

    fun toggleCompleted() = copy(completed = !completed)

    fun delete() = copy(deleted = true)
}

sealed class TodoEvent {
    data class Created(val id: UUID = UUID.randomUUID(), val title: String) : TodoEvent()
    data class CompletedToggled(val id: UUID) : TodoEvent()
    data class Deleted(val id: UUID) : TodoEvent()
}

fun List<Todo>.search(term: String) = this.filter { it.title.contains(term) }

fun List<TodoEvent>.toProjection(): List<Todo> = this.fold(listOf()) { todoList, event -> applyEvent(todoList, event) }

private fun applyEvent(todoList: List<Todo>, event: TodoEvent): List<Todo> = when (event) {
    is TodoEvent.Created -> todoList + Todo(id = event.id, title = event.title)
    is TodoEvent.CompletedToggled -> todoList.map { todo ->
        if (todo.id == event.id) {
            todo.toggleCompleted()
        } else {
            todo
        }
    }
    is TodoEvent.Deleted -> todoList.map { todo ->
        if (todo.id == event.id) {
            todo.delete()
        } else {
            todo
        }
    }
}
