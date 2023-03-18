// Data = must be immutable
@Target(AnnotationTarget.CLASS)
annotation class Data

/*
Pure functions, return same data for some input.
Number of times matters (so println is not a calculation, because calling it twice does have a different effect)
*/
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class Calculation

// All code that isn't a calculation, side effects
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR)
@RequiresOptIn(message = "actions are evil?")
annotation class Action

/*
These categorisations are interesting, if you annotated all your code, it would tell you something

Ideally, we want very few actions.

If you see something that's an action, you may be able to pull up the action part of it out,
to further separate out from your domain and convert into a calculation.

Which is easier to re-use, test, etc. Calculations are a lot simpler to refactor, moving them around
can generally be fine, but changing order of actions and stuff is dicier.
 */

@Action
fun getNumberFromDisk() = 2

@Action // removing this will cause a compilation error
fun multiplyByThingOnDisk() = getNumberFromDisk() * 2

@Calculation
fun multiplyByThing(getThing: () -> Int) = getThing() * 2

@Action //main will always be an action :)
fun main() {
    println(multiplyByThingOnDisk())
    println(multiplyByThing(::getNumberFromDisk))
}
