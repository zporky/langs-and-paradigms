# Using libraries, package managers, dependency hell

## Why use libraries?
* Code reuse
* Enforce modularity, encapsulation, abstractions
* Libraries can be products

## Library distribution
* What license?
* Are debug builds distributed?
  * How to debug misuses?
  * Reverse engineering
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
* License might change 

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
