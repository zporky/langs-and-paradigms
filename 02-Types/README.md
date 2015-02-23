# Types in programming languages

## Theory

* Set of data
* Operations defined on them

## Typeless languages

In assembly you can only deal with buts, bytes, and
words. Representing the operations on even the most basic numeric
types require tedious manual work. Like in the following example where
two long numbers are added.

```asm
a1: .long 4000000000
a2: .long 4000000000
result_lo: .long 0
result_hi: .long 0

movl a1, %eax                ; load a1 into %eax
addl a2, %eax                ; add a2 to %eax
movl %eax, result_lo         ; store low 32 bits of result

movl $0, %eax                ; clear %eax
adcl $0, %eax                ; add carry + 0 to %eax
movl %eax, result_hi         ; store high 32 bits of result
```

The programmer had to remember to add the carry bit. (E.g. a one
letter typo in ```adcl```) If She missed to do so, there are no
warnings, error reports just the wrong result. If the code segment was
inside a big numeric computation it was very difficult to find this
small bug.

## What is a type error?

We can define type error as a misuse of a language contruct causing an
undesired interpretation of data.

Let's see the following examples. You can try them out at [http://www.tutorialspoint.com/codingground.htm].

### ```"4" == "4"```

| Language | Value | Reason |
|:--------:|-------|--------|
|C/C++     | unspecified | "4" is ```char*```   |
|D         | ```true```  | |
|Javascript| ```true```  | Objects are compared |
|GO        | ```true```  | |
|C#        | ```True```  | |
|PHP       | ```true```  | |
|Perl      | ```true```  | |
|Python    | ```True```  | |
|Ruby      | ```true```  | |

### ```4 == "4"```

| Language | Value | Reason |
|:--------:|-------|--------|
|C/C++     | error | |
|D         | error | |
|Javascript| ```true``` | |
|GO        | error | |
|C#        | error | |
|PHP       | ```true``` | |
|Perl      | ```true``` | |
|Python    | ```False``` | |
|Ruby      | ```false``` | |

## Type checking

### Type definitions

* Basic types
  - byte
  - integer
  - char
  - string
  - float / double / fix

* Aggregate types
  - record
  - array
  - union
  - enumeration
  - set
  - class

* Built-in types vs. User-defined types
* Algebaric types
  - Product types
 
    ```haskell
    data MTime = Dt (Int, Int, Int)
    ```

  - Sum types
 
    ```haskell
    data List a = Nil | Cons a (List a)
    ```


### Type equivalence

* Structural
* Name

### Subtyping

#### Liskov substitution principle

"It states that, in a computer program, if S is a subtype of T, then objects of type T may be replaced with objects of type S (i.e., objects of type S may substitute objects of type T) without altering any of the desirable properties of that program (correctness, task performed, etc.)."
Source: [http://en.wikipedia.org/wiki/Liskov_substitution_principle]

### Design by contract

by Bertrand Meyer

[http://en.wikipedia.org/wiki/Design_by_contract](Wikipedia)

* Side effects
* Preconditions
* Post conditions
* Invariance

## Type inferring

### Curry calculus

* W-algorithm
* I-algorithm

* Haskell, clean
* lisp

### Modern language incarnations

* C#

    ```C#
    // Query variable is an IEnumerable<IGrouping<string, Student>> 
    var studentQuery3 =
        from student in students
        group student by student.Last;
    ```

* C++
  - ```auto```
  - ```decltype```

### Dynamic languages

* lint
* dialyzer



