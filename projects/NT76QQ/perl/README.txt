Usage
    Run the state_machine.pl program. The program reads from standard input (STDIN),
    and write to the standard output (STDOUT).
    In the console read mode you can stop with Ctrl+D.
    If the path of the perl interpreter isn't /usr/bin/perl, you have to run this
    way "/path/the/perl /path/the/state_machine.pl". Or change the first line of
    the state_machine.pl.
    
    Read from and write to console:
        state_machine.pl
     or
        perl state_machine.pl
        
    Read from file and write to console:
        state_machine.pl <input_file
     or
        perl state_machine.pl <input_file
        
    Read from file and write to file:
        state_machine.pl <input_file >output_file
     or
        perl state_machine.pl <input_file >output_file
    
    Read from console and write to file:
        state_machine.pl >output_file
     or
        perl state_machine.pl >output_file

Install and compile
    Nothing
    
Prereqirements
    perl interpreter - minimum version of perl: v5.8
