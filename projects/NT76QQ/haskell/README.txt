Usage
    Run the state_machine program. The program reads from standard input (STDIN),
    and write to the standard output (STDOUT).
    In the console read mode you can stop with Ctrl+D.
    
    Read from and write to console:
        state_machine
        
    Read from file and write to console:
        state_machine <input_file
        
    Read from file and write to file:
        state_machine <input_file >output_file
    
    Read from console and write to file:
        state_machine >output_file

Install and compile
    You have to compile the source code by ghc compiler.
    ghc state_machine
    
    After the compiling will be a state_machine runable program.
    
Prereqirements
    ghc - haskell compiler
