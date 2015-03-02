# Using libraries, package managers, dependency hell

## Why use libraries?
* Code reuse
* Enforce modularity, encapsulation, abstractions
* Libraries can be products

![Image of Yaktocat](http://imgs.xkcd.com/comics/python.png)

## Language design aspects
* Language feature vs library feature
* Include to the standard libraries or not?
* Language and standard library entanglement (e.g.: C++ type id)

## Library distribution
* What license?
* Are debug and profiling builds distributed?
  * How to debug misuses?
  * Reverse engineering
  * cabal builds every package with both profiling turned on and off
* Static linking
  * Well tested set of libraries
  * Smaller disk space footprint
* Dynamic linking (shared objects/dlls)
  * Security updates
  * Which libraries to include
  * Impossible to test all configurations the user might have
  * Better memory footprint (same text segment mapped to several process's address space)
  * Security issues (e.g.: hooking dll load system call)
* Bytecode distribution
  * Reverse engineering
  * Code hotloading (erlang)
* Source only distribution (e.g.: header only library)
  * Easier to integrate into the build process
  * Easier to debug
  * Same advantages/disadvantages as static linking
* Software As A Service
  * Easy to update the whole stack (fix security issues)
  * Users use consistent version, less support effort required
  * More infrastructural costs (server farms)

## Size of a library
* Library
  * Set of functionality to help solve a specific task
* Framework
  * Set of libraries
  * Extensible
  * Inversion of control (e.g.: event loop in gui frameworks)
  * Default behavior
* Software platform
  * Whole environment for programs to run in
  * High level of abstractions

## Type of a library
* Stateless API calls
* Statefull API calls (e.g.: strtok)
  * Concurrency?
* State stored in objects (e.g.: std::regex)
* Class hierarchies (e.g.: java frameworks)
* Generic programming
  * Proxy classes, templates, generics
* API or EDSL?
* Goal: easy to use well and hard to misuse
  * It is better to enforce a rule with type system rather than relying on users reading documentation
  * Define soft undefined behaviors in documentation (e.g.: result of sqrt is undefined on negatives)

## Library interactions
* Libraries developed in multiple languages
  * Some features do not work across binary boundaries
* Foreign Function Interfaces (FFI)
* Calling conventions
* Wrapper generators (e.g.: swig)

## Code evolution
* Every library will have new versions
  * API, ABI will change
  * Architecture might change
  * Backward compatibility requirements
* Library versioning
  * C++11 inline namespaces (see code examples)
  * Semantic versioning scheme
  * Install multiple versions side by side
    * libname.so symlink to the newest versioned lib
    * libname.newver.so and libname.oldver.so the actual libraries
* API compatibility
  * Set of functions (with parameters), classes, namespaces
* ABI compatibility
  * Multiple major versions of the same library in the same adress space?
  * Major version number in mangled names?
  * Not always trivial to check (e.g.: added a new virtual method)
  * PIMPL idiom
  * Possible solution: always rebuild everything, but you can not rebuild at your user's machine
* License might change (e.g.: FreeBSD had to use outdated gcc for years due to license issues. Now they use clang.)

## Library dependency hell
* Conflicting dependencies
  * Easiest example
    * Library A requires library C version 1
    * Library B requires library C version 2
    * Library C version 1 and library C version 2 can not be loaded to the same address space
  * Real world example
    * Firefox have gtk3 port
    * Flash plugin requres gtk2
  * Microservices as a possible solution
    * Use several separate processes (address spaces), one per responsibility
    * Less library per address space
    * No conflict when different processes use different version of the same library
    * Processes communicating over network, scales well horizontally 
    * IDLs to describe the protocols/RPC interface (Google's protobuf, Facebook's thrift)
  * Library incompatibility
    * Two different libraries may use different threading and locking methods that do not work together
* Too many dependencies
  * Hard to set up development environment
  * Several thousand characters long compilation command lines impossible to type
  * Big distribution package, long download times
  * Hard to test all possible end user configurations (the user will blame your application)
  * Possible solutions: containers (docker), virtualization, package manager
* Long chain of dependencies
  * Hard to define proper linking order
* Circular dependencies
* A dependency might have incompatible license

## Build systems
* Make, CMake, Autotools, ant, waf, scons
* Sometimes integrated into the compiler/language
* Manages dependencies, documentation generation, packaging, deployment
* Continous integration may run test suite on systems deployed in different environments
* Rewarding to automate more and more tasks (devops requires huge effort on automation)

## Redistribution package
* What to include in the package?
  * Which libraries to link statically which ones to link dynamically
  * Which dynamic libraries to include in the installer/package
  * Which dynamic libraries assumed to be installed separately
  * Which versions to use
* How to update? Manually? OS updates? Package manager? Custom updater?
  * Typical user have several background services running on Windows only to track updates
* Some distribution platform are restrictive on the dependencies (AppStore, Google Play)
* The more libraries included...
  * the greater size the package has
  * security updates needed more often
  * the greater the memory footprint is (less sharing through address spaces)
* How to test that all the dependencies are properly handled during build, deployment, execution?
  * chroot environments

## Package manager for libraries
* More and more popular concept
* Hackage, PIP, go, Rust
* Make development easier
* Trust

## Package manager for applications
* The repositories might be out to date, depending on distribution model
* Dependency hell for applications and libraries
* Easy to patch the security issues
* Trust
  * May run with elevated privileges
  * Corrupt repositories
  * Executing scripts
  * Man in the middle
