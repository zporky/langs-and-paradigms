-module(double).

-behaviour(gen_fsm).

-define(REPLY(S), {reply, S, S, []}).
-define(STOP, {stop, normal, aborted, []}).
-define(IS_DIGIT(C), C >= $0 andalso C =< $9).
-define(IS_TERMINAL(State), hd(atom_to_list(State)) == $t).
-define(INITIAL_STATE, s).
 
-export([parse/1, test/0]).
%% gen_fsm callbacks - should not be called directly
-export([init/1, handle_sync_event/4, terminate/3,
        s/3, s_/3, i0/3, t1/3, t2/3, t3/3, t4/3, t5/3]).

test() ->
    PositiveTests = ["1", "-3.14","0", "+0.1", ".1", "1"],
    NegativeTests = [".", "+ x1", "-", "3.14e-2"],
    Result =[parse(Str) == true || Str <- PositiveTests] ++
        [parse(Str) == false || Str <- NegativeTests],
    io:format("~p~n", [Result]).

parse(Str) when is_list(Str) ->
    start(),
    Result = parse_rec(?INITIAL_STATE, Str),
    stop(),
    Result.

parse_rec(State, []) -> % is it a terminal state?
    hd(atom_to_list(State)) == $t;
parse_rec(aborted, _) -> % parser is aborted
    false; 
parse_rec(_State, [ C | Cs ]) ->
    parse_rec(gen_fsm:sync_send_event(?MODULE, C), Cs).

start() ->    
    gen_fsm:start({local, ?MODULE}, ?MODULE, [], []).

stop() ->
    try gen_fsm:sync_send_all_state_event(?MODULE, stop)
    catch exit:Term -> Term % already stopped
    end.

init(_) ->
    {ok, ?INITIAL_STATE, []}.

handle_sync_event(stop, _From, _State, _StateData) -> ?STOP.

terminate(_, _, _) -> ok.

%%% FSM starts here
s(C, _, _)  when C == $+ orelse C == $- ->  ?REPLY(s_);
s(C, F, D) -> s_(C, F, D). % Kleene event

% s' is syntactically incorrect
s_($., _, _) -> ?REPLY(i0);
s_($0, _, _) -> ?REPLY(t2);
s_(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t4);
s_(_, _, _) -> ?STOP.

i0(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t1);
i0(_, _, _) -> ?STOP.

t1(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t1);
t1(_, _, _) -> ?STOP.

t2($., _, _) -> ?REPLY(t3);
t2(_, _, _) -> ?STOP.

t3(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t3);
t3(_, _, _) -> ?STOP.

t4($., _, _) -> ?REPLY(t5);
t4(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t4);
t4(_, _, _) -> ?STOP.

t5(C, _, _) when ?IS_DIGIT(C) -> ?REPLY(t5);
t5(_, _, _) -> ?STOP.
