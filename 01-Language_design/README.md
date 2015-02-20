# Language design

## Concepts
* Syntax, semantics, pragmatics
* Language categories
* Specification
* Design concepts
* Implentation
* Standardization
* Programming language evolution

## Syntax, Semantics, Pragmatics
* Syntax
  - The correct grammar of the language

* Semantics
  - The meaning of a syntactically correct phrase

* Pragmatics
  - How to use the given phrase for a useful purpose

## Syntax

* Help the lexer/parser
  - C declaration syntax ```double (*funptr)(double);```

* Help the programmer to write correct code
  - Pascal or C type of use

* Too lazy
  - PL/I generated missing ```end``` keyword
  - Algol68 ```begin``` ```(``` interchangeable
  - Newline appears in strings?

* Too strict
  - Algol68 only implementation for ```skip``` statement

* Goto?

* Exception?

* Block statement
  - The goto fail error

    ```c
    if (cond)
       goto fail;
       goto fail;
    i = 6;
    ```

* Dangling else statement

    ```c
    if ( i < 10 )
        if ( j < 20 ) i += j;
    else
        i = j;
    ```
		
* Switch statement in C
  - 90% of case statements require ```break```

* C++11
  
```C++
vactor<vector<Node>> parents;
int n = index_of_parents();
```

```C++
for( Node n in parens[n] )
```

## Semantics

* The meaning of the code
  - Axiomatic
  - Denotational
  - Operational
  - Textual

## Pragmatics

* How to write good code
* Programmers practices, design rules

* Scala
  - Optimize for immutable
* C++
  - Use RAII, Pimpl, use const correctness
* C
  ```if ( 5 == strlen(str) )```

## Language specification

* Fortran: BNF
* Pascal: EBNF, "Railways notation"
* ALGOL68: first textual
  - After 1973 revised in Van Wijngaarden grammar
  - Context sensitive
  - Turing complete
* C/C++ textual + abstract machine

## Language design concepts

* Well-defined syntax and semantics
* Expressivity
  - An APL 256 operators
  - Redundancy is important
* Orthogonality
  - C++: protected abtract virtual base pure virtual private desctructor
    "If you think C++ is not overly complicated, just what is a protected abstract virtual base pure virtual private destructor and when was the last time you needed one? — Tom Cargill"
  - The first 90% of the code accounts for the first 90% of the development time. The remaining 10% of the code accounts for the other 90% of the development time.

* Generality
  - C++ templates
  - Java generics
* Modularity
  - Java package vs. C++ namespace
  - C++ included headers
  - Erlang flat modules
* Portability
  - Source / bytecode / binary
  - Pascal P code, COBOL
* Performance
  - Garbage collection
  - Optimization vs debuggings
* Learnability

## Implementation

* Compilation
  - Phases: (Preprocessing), Compiling, Linking
  - Static or dynamic linking
  - Generates HW and OS-specific executable
  - Effective optimizations
* Interpretation
  - Faster developing process
  - Less correctness-checking possibilities
* Hybrid model
  - Compiler generates platform independent intermediate code
  - Intermediate code executed by "virtual machine"
  - Fair correctness checking and optimization
  - More optimization: Just-in-time compilers
* Samples
  - Pascal P-code, Java virtual machine, MS IL

## Standardization

* Reasons
  - Portability of source
  - Maintanability
  - Portability of programmers
  - acceptability
  - Faster development
* Standard library must included
* C++ ISO (since 1998)
* C# ECMA-334 C# version 2.0
* Java nope

## Language evolution

* New features/keywords
  - Reverse compatibility issues
  - Depricated elements
  - Silent semantic changes?
* Successful
  - C++: delete functions, auto keyword, overload
* Issues
  - Python 2 vs Python 3
  - C to C++
