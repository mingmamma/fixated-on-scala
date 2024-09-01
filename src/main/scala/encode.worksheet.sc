// basic examples of type class and their concrete implementations
// c.f https://docs.scala-lang.org/scala3/book/ca-type-classes.html
// c.f https://typelevel.org/cats/typeclasses.html

// type class in Scala 3 is best expressed as (generic) parameterised trait with
// abstract methods. Monoid is the type class declared in this case
trait Monoid[A]:
    def empty: A
    def combine(x: A, y: A): A

case class Pair[A, B](a: A, b: B)    

object Monoid:
    // implementation of a instance of type class for Int type, with its addition operation, satisfying
    // the properties and hence is a Monoid. The definition of `empty` and `combine` methods are trivially
    // intuitive to the usual integers and their addition
    given intAddMonoid: Monoid[Int] with
        def empty: Int = 0
        def combine(x: Int, y: Int): Int = x + y

    // basic implementation of deriving a new type class instance by composing existing instances
    given pointwisePairMonoid[A, B](using A: Monoid[A], B: Monoid[B]): Monoid[Pair[A, B]] with
        def empty: Pair[A, B] = Pair(A.empty, B.empty)
        def combine(x: Pair[A, B], y: Pair[A, B]): Pair[A, B] = Pair(A.combine(x.a, y.a), B.combine(x.b, y.b))

def combineAll[A](list: List[A])(using aMonoid: Monoid[A]) = list.foldRight[A](aMonoid.empty)(aMonoid.combine)

// examples of using the type class instances in calling code
combineAll[Int](List(1, 2, 3))
combineAll[Pair[Int, Int]](List(Pair(4, 2), Pair(1, 8)))

// Borrowing the idea of "composing" into the building an encoder of fixed width file format, the following is the 
// high-level overview of how the fixed-length library implement Encoder, whose key responsibility is to take an instance of
// arbitrary data type (think of an instance of a class/case class) in Scala to a textual String that is fixed width formatted properly

// Following is tentative code not yet working

// trait Encoder[A]
//      def encode(obj: A): String

// object Encoder:

//      c.f. https://github.com/atais/Fixed-Length/blob/master/src/main/scala/com/github/atais/fixedlength/Encoder.scala#L15-L38
//      the idea of this method is to return a Encoder instance for an arbitrary type, s.t. the encoder would take the string representation
//      of this instance, and given extra formatting configuration, produce the formatted string for this single data item
//      For example, given an Int 42, and the specification directive to make the item of fixed length 5, the return value would be a String "42   "
//      def fixed[A](...): Encoder:
//          def encode(obj: A): String = ???

//      c.f. https://github.com/atais/Fixed-Length/blob/master/src/main/scala/com/github/atais/fixedlength/Encoder.scala#L55-L69
//      with an coder that can handle a single item type, the key is to "compose" encoders of more primitive types into encoders that can handle
//      more complex data types. In the fixed-length library implementation, such the derivation of new encoders from existing ones of different
//      data types is facilitated by shapeless HList (heterogenous list), as in the historical Scala 2 context. It is yet to be determined how
//      the comparable implementation is to work in Scala3 