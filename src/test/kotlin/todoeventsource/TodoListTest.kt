package todoeventsource

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.contains
import strikt.assertions.hasSize

class TodoListTest {
    @Test fun `create a todolist from events`() {
        val buyMilk = TodoEvent.Created(title = "Buy Milk")
        val buyEggs = TodoEvent.Created(title = "Buy Eggs")
        val buyBread = TodoEvent.Created(title = "Buy Bread")
        val todoList = listOf(
            buyMilk,
            buyEggs,
            buyBread,
            TodoEvent.CompletedToggled(id = buyMilk.id)
        ).toProjection()
        expectThat(todoList).contains(
            Todo(id = buyMilk.id, title = "Buy Milk", completed = true),
            Todo(id = buyEggs.id, title = "Buy Eggs", completed = false),
            Todo(id = buyBread.id, title = "Buy Bread", completed = false),
        )
    }

    @Test fun `search todos`() {
        val buyMilk = TodoEvent.Created(title = "Buy Milk")
        val buyEggs = TodoEvent.Created(title = "Buy Eggs")
        val todoList = listOf(
            buyMilk,
            buyEggs,
        ).toProjection()
        val searchResults = todoList.search("Milk")
        expectThat(searchResults){
            contains(Todo(id = buyMilk.id, title = "Buy Milk"))
            hasSize(1)
        }
    }
}
