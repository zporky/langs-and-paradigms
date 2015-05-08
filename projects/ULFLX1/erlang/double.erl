-module(double).

-behaviour(gen_fsm).

-define(REPLY(S, D), {reply, S, S, D}).
-define(STOP, {stop, normal, aborted, []}).
-define(STOP(S), {stop, normal, S, []}).
-define(IS_DIGIT(C), C >= $0 andalso C =< $9).
-define(IS_EXP(C), C == $e orelse C == $E).
-define(IS_TERMINAL(State), hd(atom_to_list(State)) == $t).
-define(INITIAL_STATE, s).
 
-export([parse/1, test/0]).
%% gen_fsm callbacks - should not be called directly
-export([init/1, handle_sync_event/4, terminate/3,
        s/3, s_/3, i0/3, t1/3, t2/3, t3/3, t4/3, t5/3, i6/3, t7/3]).

test() ->
    PositiveTests = ["1", "-3.14","0", "+0.1", ".1", "1"],
    NegativeTests = [".", "+ x1", "-", "3.14e-2", "1e2", "2.4E-5", ".1e+3"],
    Result =[parse(Str) || Str <- PositiveTests] ++
        [parse(Str) || Str <- NegativeTests],
    io:format("~p~n", [Result]).

parse(Str) when is_list(Str) ->
    start(),
    EndState = parse_rec(?INITIAL_STATE, Str),
    stop(EndState).

parse_rec(State, []) -> % is it a terminal state?
    hd(atom_to_list(State)) == $t;
parse_rec(aborted, _) -> % parser is aborted
    false;
parse_rec(_State, [ C | Cs ]) ->
    parse_rec(gen_fsm:sync_send_event(?MODULE, C), Cs).

start() ->    
    gen_fsm:start({local, ?MODULE}, ?MODULE, [], []).

stop(false) ->
    "FAIL";
stop(true) ->
    try gen_fsm:sync_send_all_state_event(?MODULE, stop)
    catch exit:Term -> Term % already stopped
    end.

init(_) ->
    {ok, ?INITIAL_STATE, []}.

handle_sync_event(stop, _From, t4, StateData) -> ?STOP(StateData ++ ".0");
handle_sync_event(stop, _From, _State, StateData) -> ?STOP(StateData).

terminate(_, _, _) -> ok.

%%% FSM starts here
s(C, _, _)  when C == $+ orelse C == $- ->  ?REPLY(s_, "OK ");
s(C, F, _) -> s_(C, F, "OK "). % Kleene event

% s' is syntactically incorrect
s_($., _, D) -> ?REPLY(i0, D ++ "1.");
s_($0, _, D) -> ?REPLY(t2, D ++ "0");
s_(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t4, D ++ [C]);
s_(_, _, _) -> ?STOP.

i0(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t1, D ++ [C]);
i0(_, _, _) -> ?STOP.

t1(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t1, D ++ [C]);
t1(C, _, D) when ?IS_EXP(C) -> ?REPLY(i6, D ++ [C]);
t1(_, _, _) -> ?STOP.

t2($., _, D) -> ?REPLY(t3, D ++ ".");
t2(_, _, _) -> ?STOP.

t3(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t3, D ++ [C]);
t3(C, _, D) when ?IS_EXP(C) -> ?REPLY(i6, D ++ [C]);
t3(_, _, _) -> ?STOP.

t4($., _, D) -> ?REPLY(t5, D ++ ".");
t4(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t4, D ++ [C]);
t4(C, _, D) when ?IS_EXP(C) -> ?REPLY(i6, D ++ [C]);
t4(_, _, _) -> ?STOP.

t5(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t5, D ++ [C]);
t5(C, _, D) when ?IS_EXP(C) -> ?REPLY(i6, D ++ [C]);
t5(_, _, _) -> ?STOP.

i6($- = C, _, D) -> ?REPLY(i6, D ++[C]);
i6($+, _, D) -> ?REPLY(i6, D);
i6(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t7, D ++ [C]);
i6(_, _, _) -> ?STOP.

t7(C, _, D) when ?IS_DIGIT(C) -> ?REPLY(t7, D ++ [C]);
t7(_, _, _) -> ?STOP.
