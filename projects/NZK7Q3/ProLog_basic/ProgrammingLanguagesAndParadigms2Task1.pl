%% -*- Mode: Prolog; coding: utf-8 -*- 

:- set_prolog_flag(toplevel_print_options,
    [quoted(true),numbervars(true),portrayed(true),
                                   max_depth(1000)]).

:- set_prolog_flag(legacy_char_classification,on).

main :- main('Example1.txt', 'Output.txt').
main(InFileName, OutFileName) :- 
	open(InFileName, read, InStr), 
	read_lines(InStr,Lines), 
	close(InStr), 
	process(Lines, Output),
	open(OutFileName, write, OutStr),
	write_lines(OutStr, Output), 
	close(OutStr), nl. 
    
read_lines(Stream,[]) :- at_end_of_stream(Stream). 
read_lines(Stream,[X|Xs]) :- 
	\+  at_end_of_stream(Stream), 
	read(Stream, X), 
	read_lines(Stream, Xs).

write_lines(_, []).
write_lines(Stream, [X|Xs]) :-
	format(Stream,"~a\n", [X]),
	write_lines(Stream, Xs).
	
process([], []).
process([I|Is], Os) :- 
	eval_automata(I, O1),
	process(Is, O2),
	Os = [O1|O2].
	
	
minus_or_nothing('+', '').
minus_or_nothing('-', '-').
	
isStartSymbol('+').
isStartSymbol('-').

isZeroSymbol('0').

isPointSymbol('.').

isDecimalSymbol('1').
isDecimalSymbol('2').
isDecimalSymbol('3').
isDecimalSymbol('4').
isDecimalSymbol('5').
isDecimalSymbol('6').
isDecimalSymbol('7').
isDecimalSymbol('8').
isDecimalSymbol('9').
	
concat_all([], '').
concat_all([X|Xs], Os) :- concat_all(Xs, Os2), atom_concat(X, Os2, Os).
	
eval_automata([], []).
eval_automata(In, Os) :- atom_chars(In, Is),
	( step(Is, 'S', O) -> (OT=['OK '|O], concat_all(OT, Os))
	; Os = 'FAIL'
	).
	
step([C|Cs], 'S', O) :- isStartSymbol(C),
	step(Cs, 'SV', O1), minus_or_nothing(C, E), O=[E|O1]
	;
	step([C|Cs], 'SV', O2), O=O2.
step([C|Cs], 'SV', O) :- 
	isPointSymbol(C), step(Cs, 'I0', O1), OT=[C|O1], O=['0'|OT]
	;
	isZeroSymbol(C), step(Cs, 'T2', O2), O=[C|O2]
	;
	isDecimalSymbol(C),step(Cs, 'T4', O3), O=[C|O3].
step([C|Cs], 'I0', O) :- isDecimalSymbol(C), 
	step(Cs, 'T1', O1), O=[C|O1].
step([], 'T1', []).
step([C|Cs], 'T1', O) :- isDecimalSymbol(C),
	step(Cs, 'T1', O1), O=[C|O1].
step([], 'T2', []).
step([C|Cs], 'T2', O) :- isPointSymbol(C),
	step(Cs, 'T3', O1), O=[C|O1].
step([], 'T3', []).
step([C|Cs], 'T3', O) :- isDecimalSymbol(C),
	step(Cs, 'T3', O1), O=[C|O1].
step([], 'T4', []).
step([C|Cs], 'T4', O) :- isDecimalSymbol(C), 
	step(Cs, 'T4', O1), O=[C|O1].
step([C|Cs], 'T4', O) :- isPointSymbol(C), 
	step(Cs, 'T5', O1), O=[C|O1].
step([], 'T5', []).
step([C|Cs], 'T5', O) :- isDecimalSymbol(C),
	step(Cs, 'T5', O1), O=[C|O1].
	
	
	