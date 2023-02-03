import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.server.Undertow
import org.http4k.server.asServer

fun main() {
    val handler: HttpHandler = routes(
        "/hello/{name}" bind Method.GET to ::greet,
        "/receive-data" bind Method.POST to ::receiveData
    )
    handler.asServer(Undertow(9000)).start()
}

fun greet(req: Request): Response {
    return Response(Status.OK).body("Hello, ${req.path("name")}")
}

fun receiveData(req: Request): Response {
    return Response(Status.OK).body(req.bodyString())
}