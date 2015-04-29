all: build test

build:
	erlc double.erl

test:
	erl -noshell -s double test -s erlang halt
