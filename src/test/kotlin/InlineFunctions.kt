import org.junit.jupiter.api.Test

/*
when you create lambdas, especially ones that have a clojure, that has perf implications
a lambda will create an anon class in java 6/7 (interop concerns). Adds more stuff to the stack _and_ heap
if you have a clojure

tip: use show kotlin bytecode, and then decompile
 */

class NotInlineFunctions {
    fun op(op: () -> Unit) {
        println("before op")
        op()
        println("after op")
    }

    @Test
    fun `inline functions`() {
        op { println("hello") }
    }
}

/*
public final class NotInlineFunctions {
   public final void op(@NotNull Function0 op) {
      Intrinsics.checkNotNullParameter(op, "op");
      String var2 = "before op";
      System.out.println(var2);
      op.invoke();
      var2 = "after op";
      System.out.println(var2);
   }

   @Test
   public final void inline_functions/* $FF was: inline functions*/() {
      this.op((Function0)null.INSTANCE);
   }
}
 */

class InlineFunctions() {
    inline fun op(op: () -> Unit) {
        println("before op")
        op()
        println("after op")
    }

    @Test
    fun `inline functions`() {
        /*
        see the generated code
        - literally inlines the code
        - removes needless anonymous classes, calls, e.t.c
        note, it will copy the code over and over, for each call
        another note: this does affect the tracing with exceptions, which intellij can handle, but elsewhere _could_ be confusing
         */
        op { println("hello") }
    }
}

/*
public final class InlineFunctions {
   public final void op(@NotNull Function0 op) {
      int $i$f$op = 0;
      Intrinsics.checkNotNullParameter(op, "op");
      String var3 = "before op";
      System.out.println(var3);
      op.invoke();
      var3 = "after op";
      System.out.println(var3);
   }

   @Test
   public final void inline_functions/* $FF was: inline functions*/() {
      int $i$f$op = false;
      String var3 = "before op";
      System.out.println(var3);
      int var4 = false;
      String var5 = "hello";
      System.out.println(var5);
      var3 = "after op";
      System.out.println(var3);
   }
}
 */