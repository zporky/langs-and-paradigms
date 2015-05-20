-module(floatparser).

-export([run/0,parse/1]).

run() ->
	L = read_file("input.txt"),
	[ io:format("~s~n", [parse(X)]) || X <- L],
	ok.

read_file(FileName) ->
	{ok,Bin} = file:read_file(FileName),
	string:tokens(binary_to_list(Bin),"\r\n").

parse(X) ->
	case s(X,"") of
		{ok, Result} -> "OK "++Result;
		{fail,_} -> "FAIL";
		_ -> "FAIL"
	end.

%% S szabály
s([],_)     -> {fail,empty};
s([X|Xs],Acc) ->
	case X of
		$+ -> s2(Xs,Acc);
		$- -> s2(Xs,Acc);
		_  -> s2([X|Xs],Acc)
	end.

%% S' rule
s2([],_)     -> {fail,invalid};
s2([X|Xs],Acc) -> 
	D = d(X),
	case X of
		$. -> l0(Xs,Acc++[$0]++[$.]);
		$0 -> t2(Xs,Acc++[$0]);
		D  -> t4(Xs,Acc++[D]);
		_  -> {fail,invalid}
	end.
	
%% l0 rule
l0([],_)     -> {fail,invalid};
l0([X|Xs],Acc) ->
	D = d(X),
	case X of
		D -> t1(Xs,Acc++[D]);
		_ -> {fail,invalid}
	end.

%% T1 rule	
t1([],Acc)     -> {ok, Acc};
t1([X|Xs],Acc) ->
	D = d(X),
	case X of
		D  -> t1(Xs,Acc++[D]);
		$e -> e(Xs,Acc++[$e]);
		$E -> e(Xs,Acc++[$e]);
		_  -> {fail,invalid}
	end.

%% T2 rule
t2([],Acc)     -> {ok,Acc};
t2([X|Xs],Acc) ->
	case X of
		$. -> t3(Xs,Acc++[$.]);
		$e -> e(Xs,Acc++[$e]);
		$E -> e(Xs,Acc++[$e]);
		_  -> {fail,invalid}
	end.

	
%% T3 rule
t3([],Acc)     -> {ok,Acc++[$0]};
t3([X|Xs],Acc) ->
	D = d(X),
	case X of
		D -> t1(Xs,Acc++[D]);
		_ -> {fail,invalid}
	end.

%% T4 rule
t4([],Acc)     -> {ok,Acc++[$.]++[$0]};
t4([X|Xs],Acc) ->
	D = d(X),
	case X of
		D  -> t4(Xs,Acc++[D]);
		$. -> t3(Xs,Acc++[$.]);
		$e -> e(Xs,Acc++[$e]);
		$E -> e(Xs,Acc++[$e]);
		_ -> {fail,invalid}
	end.
	
%% E rule
e([],_) -> {fail, invalid};
e([X|Xs],Acc) -> 
	case X of
		$+ -> l1(Xs,Acc++[$+]);
		$- -> l1(Xs,Acc++[$-]);
		_  -> l1([X|Xs],Acc++[$+])
	end.

%% l1 rule
l1([],_)     -> {fail,invalid};
l1([X|Xs],Acc) ->
	D = d(X),
	case X of
		D -> e2(Xs,Acc++[D]);
		_ -> {fail,invalid}
	end.
	
%% E' rule
e2([],Acc) -> {ok, Acc};
e2([X|Xs],Acc) -> 
	D = d(X),
	case X of
		D  -> e2(Xs,Acc++[D]);
		_  -> {fail,invalid}
	end.
	
	
%% Digit vizsgálat	
d(X) ->
	Digit = [$1,$2,$3,$4,$5,$6,$7,$8,$9],
	case lists:dropwhile(fun(E) -> E /= X end,Digit) of
		[H|_] -> H;
		[]    -> {fail,invalid}
	end.