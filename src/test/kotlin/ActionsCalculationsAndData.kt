// Data = must be immutable
@Target(AnnotationTarget.CLASS)
annotation class Data

/*
Pure functions, return same data for some input.
Number of times matters (so println is not a calculation, because calling it twice does have a different effect)
*/
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
annotation class Calculation

// All code that isn't a calculation, side effects
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@RequiresOptIn(message = "actions are evil?")
annotation class Action

/*
These categorisations are interesting, if you annotated all your code, it would tell you something

Ideally, we want very few actions
 */


@Action
fun writeToDisk() {}

@Action // try removing this action and see what the IDE suggests
fun somethingThatCallsWriteToDisk() {
    writeToDisk()
}