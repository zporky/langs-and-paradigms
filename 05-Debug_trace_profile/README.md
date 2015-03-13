# Logging, debugging, tracing, profiling

![img](http://www.phdcomics.com/comics/archive/phd011406s.gif)

## What to do when something is not working?

1. Identify the sympthom!

2. Break down the problem to managable chunks!

3. Investigate each separately!

4. Fix the issue!

## Identifing the symthom

* Check the intended usage with ours!
* Reproduce the problem!
* Automate reproduction if possible!
* Be very specific about the problem!

## Break down the problem

### Identify the problematic part of the software
* packages
* modules
* thread
* process
* classes
* functions
* statements
* data types

### Inspect the problematic part
#### by printing

* works on all platforms with console access
* source modifiaction is needed
* not ideal on live systems
* preformance issues when used too heavily

#### by debugging

* lldb example
* no source modificaion
* recompilatation may be needed
* ideal for sequential programs
* integrates well with IDEs
* not working on embedded or live environment
* not working on concurrent systems
* performance overhead

#### by tracing

* egol example
* no source modification
* ideal on concurrent systems
* not working on embedded systems with limited resources
* performance overhead

#### by logging

* built-in to the source code
* debug levels
  * INFO, LOW, MEDIUM, HIGH, CRITICAL
  * 0, ..., 9
* works on live systems
* no performance overhead
* might be runtime performance issue

### What about runtime properties?

* No fault in logic
* Slow performance
* Memory leaks
* Memory consumption
* Network usage

#### by logging

* Offline or online analytics
* Metrics tools

#### by profiling

* Saving runtime details to intermediary format
* Analyzing data offline
* Detailed/interactive report
* Ideal to find bottlenecks
* Memory leak detection
* Kind of art to interpret data
* Two kinds
  * Intrumentation based
  * Sampling based
* Valgrind-family
