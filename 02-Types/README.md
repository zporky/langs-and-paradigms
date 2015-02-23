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

Let's see the following examples.

### ```"4" == "4"```

| Language | Value | Reason |
|:--------:|-------|--------|
|C/C++     | unspecified | "4" is ```char*```   |
|Javascript| ```true```  | Objects are compared |
|GO        | ```true```  | |
|C#        | ```True```  | |
|PHP       | ```true```  | |
|Perl      | ```true```  | |
|Ruby      | ```true```  | |

### ```4 == "4"```

| Language | Value | Reason |
|:--------:|-------|--------|
|C/C++     | unspecified | "4" is ```char*```   |
|Javascript|   | |
|GO        |   | |
|C#        |   | |
|PHP       |   | |
|Perl      |   | |
|Ruby      |   | |


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

* Built-in types
* Algebaric types

### Type equivalence

* Structural
* Name

### Subtyping

#### Liskov substitution principle

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
* C++
* Scala

### Dynamic languages

* lint
* dialyzer



