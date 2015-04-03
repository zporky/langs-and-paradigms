-module(floatparser).

-export([run/0]).

run() ->
	L = read_file("input.txt"),
	[ io:format("~s~n", [parse(X)]) || X <- L],
	ok.

read_file(FileName) ->
	{ok,Bin} = file:read_file(FileName),
	string:tokens(binary_to_list(Bin),"\r\n").

parse(X) ->
	RetS = s(X),
	case s2(RetS) of
		{ok, t1} -> "OK "++"0"++RetS;
		{ok, t4} -> "OK "++RetS++".0";
		{ok,  _} -> "OK "++RetS;
		{fail,_} -> "FAIL";
		_ -> "FAIL"
	end.

%% S szabály
s([])     -> {fail,empty};
s([X|Xs]) ->
	case X of
		$+ -> Xs;
		$- -> Xs;
		_  -> [X|Xs]
	end.

%% S' rule
s2([])     -> {fail,invalid};
s2([X|Xs]) -> 
	D = d(X),
	case X of
		$. -> l0(Xs);
		$0 -> t2(Xs);
		D  -> t4(Xs);
		_  -> {fail,invalid}
	end.
	
%% l0 rule
l0([])     -> {fail,invalid};
l0([X|Xs]) ->
	D = d(X),
	case X of
		D -> t1(Xs);
		_ -> {fail,invalid}
	end.

%% t1 rule	
t1([])     -> {ok, t1};
t1([X|Xs]) ->
	D = d(X),
	case X of
		D -> t1(Xs);
		_ -> {fail,invalid}
	end.

%% t2 rule
t2([])     -> {ok,t2};
t2([X|Xs]) ->
	case X of
		$. -> t3(Xs);
		_  -> {fail,invalid}
	end.

	
%% t3 rule
t3([])     -> {ok,t3};
t3([X|Xs]) ->
	D = d(X),
	case X of
		D -> t3(Xs);
		_ -> {fail,invalid}
	end.

%% t4 rule
t4([])     -> {ok,t4};
t4([X|Xs]) ->
	D = d(X),
	case X of
		D  -> t4(Xs);
		$. -> t5(Xs);
		_ -> {fail,invalid}
	end.

%% t5 rule
t5([])     -> {ok,t5};
t5([X|Xs]) ->
	D = d(X),
	case X of
		D -> t5(Xs);
		_ -> {fail,invalid}
	end.
	
%% Digit vizsgálat	
d(X) ->
	Digit = [$1,$2,$3,$4,$5,$6,$7,$8,$9],
	case lists:dropwhile(fun(E) -> E /= X end,Digit) of
		[H|_] -> H;
		[]    -> {fail,invalid}
	end.