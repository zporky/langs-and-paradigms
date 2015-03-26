#!/usr/bin/perl

use strict;
use warnings 'all';
use Test::More;
use Data::Dumper;
use StateMachine;

use constant {
	STATE_ERR => 0,
	STATE_S0  => 1,
	STATE_S1  => 2,
	STATE_I0  => 3,
	STATE_T1  => 4,
	STATE_T2  => 5,
	STATE_T3  => 6,
	STATE_T4  => 7,
	STATE_T5  => 8,
	TERM_S    => 9,
	TERM_P    => 10,
	TERM_Z    => 11,
	TERM_E    => 12,
	TERM_D    => 13
};

my $testName = "";

print "Testing\n";

### StateMachine tests ###
require_ok('StateMachine');

$testName = "StateMachine constructor";
my $sm = StateMachine->create();
is_deeply(
	$sm,
	bless(
		{
			'state'  => STATE_S0,
			'output' => ''
		},
		'StateMachine'
	),
	$testName
);

$testName       = "StateMachine->reset()";
$sm->{'state'}  = STATE_S1;
$sm->{'output'} = "A";
my $sm2 = $sm->reset();
ok( $sm->getState() == STATE_S0 && $sm->getOutput() eq "", $testName );
is( $sm2, $sm, "\$var = $testName" );

$testName = "StateMachine->fail()";
$sm->fail();
ok( $sm->{'state'} == STATE_ERR && $sm->{'output'} eq "FAIL", $testName );

$testName = "StateMachine->action_e()";
my $oldState  = $sm->getState();
my $oldOutput = $sm->getOutput();
$sm->action_e();
ok( ( $oldOutput eq $sm->getOutput() ) && ( $oldState == $sm->getState() ),
	$testName );

$testName = "StateMachine->action_s()";
$sm->reset();
$sm->action_s();
ok($sm->getState() == STATE_S1,
	"$testName STATE_S0 --> STATE_S1" );
$sm->action_s();
ok( $sm->getState() == STATE_ERR, "$testName STATE_S1 --> STATE_ERR" );

$testName = "StateMachine->action_p()";
my $c = ".";
$sm->reset();
$sm->action_p($c);
ok( $sm->getState() == STATE_ERR, "$testName STATE_S0 --> STATE_ERR" );
$sm->reset();
$sm->action_s();
$sm->action_p($c);
ok( $sm->getState() == STATE_I0 && "0$c" eq $sm->getOutput() , "$testName STATE_S1 --> STATE_I0" );
$sm->reset();
$sm->{'state'} = STATE_T1;
$sm->action_p($c);
ok( $sm->getState() == STATE_ERR, "$testName STATE_T1 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T2;
$sm->{'output'} = "12";
$sm->action_p($c);
ok( $sm->getState() == STATE_T3 && "12$c" eq $sm->getOutput(), "$testName STATE_T2 --> STATE_T3" );
$sm->reset();
$sm->{'state'} = STATE_T3;
$sm->action_p($c);
ok( $sm->getState() == STATE_ERR, "$testName STATE_T3 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T4;
$sm->{'output'} = "12";
$sm->action_p($c);
ok( $sm->getState() == STATE_T5 && "12$c" eq $sm->getOutput(), "$testName STATE_T4 --> STATE_T5" );
$sm->reset();
$sm->{'state'} = STATE_T5;
$sm->{'output'} = "12";
$sm->action_p($c);
ok( $sm->getState() == STATE_ERR, "$testName STATE_T5 --> STATE_ERR" );


$testName = "StateMachine->action_z()";
$c = "0";
$sm->reset();
$sm->action_z();
ok( $sm->getState() == STATE_ERR, "$testName STATE_S0 --> STATE_ERR" );
$sm->reset();
$sm->action_s();
$sm->action_z($c);
ok( $sm->getState() == STATE_T2 && "$c" eq $sm->getOutput() , "$testName STATE_S1 --> STATE_T2" );
$sm->reset();
$sm->{'state'} = STATE_I0;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_I0 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T1;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T1 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T2;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T2 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T3;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T3 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T4;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T4 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T5;
$sm->action_z($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T5 --> STATE_ERR" );


$testName = "StateMachine->action_d()";
$c = "4";
$sm->reset();
$sm->{'state'} = STATE_S0;
$sm->action_d($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_S0 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_S1;
$sm->action_d($c);
ok( $sm->getState() == STATE_T4 && "$c" eq $sm->getOutput(), "$testName STATE_S1 --> STATE_T4" );
$sm->reset();
$sm->{'state'} = STATE_I0;
$sm->{'output'} = "0.";
$sm->action_d($c);
ok( $sm->getState() == STATE_T1 && "0.$c" eq $sm->getOutput(), "$testName STATE_S1 --> STATE_T1" );
$sm->reset();
$sm->{'state'} = STATE_T1;
$sm->{'output'} = "0.1";
$sm->action_d($c);
ok( $sm->getState() == STATE_T1 && "0.1$c" eq $sm->getOutput(), "$testName STATE_T1 --> STATE_T1" );
$sm->reset();
$sm->{'state'} = STATE_T2;
$sm->action_d($c);
ok( $sm->getState() == STATE_ERR , "$testName STATE_T2 --> STATE_ERR" );
$sm->reset();
$sm->{'state'} = STATE_T3;
$sm->{'output'} = "0.1";
$sm->action_d($c);
ok( $sm->getState() == STATE_T3 && "0.1$c" eq $sm->getOutput(), "$testName STATE_T3 --> STATE_T3" );
$sm->reset();
$sm->{'state'} = STATE_T4;
$sm->{'output'} = "11";
$sm->action_d($c);
ok( $sm->getState() == STATE_T4 && "11$c" eq $sm->getOutput(), "$testName STATE_T4 --> STATE_T4" );
$sm->reset();
$sm->{'state'} = STATE_T5;
$sm->{'output'} = "11.4";
$sm->action_d($c);
ok( $sm->getState() == STATE_T5 && "11.4$c" eq $sm->getOutput(), "$testName STATE_T5 --> STATE_T5" );

$c = "1";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "1.0", $testName);

$c = "-3.14";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "3.14", $testName);

$c = "0";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "0", $testName);

$c = "+0.1";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "0.1", $testName);

$c = ".1";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "0.1", $testName);

$c = "1";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "1.0", $testName);

$c = "1.";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "1.", $testName);

$c = ".";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "FAIL", $testName);

$c = "+x1";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "FAIL", $testName);

$c = "-";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "FAIL", $testName);

$c = "3.14e-2";
$testName = "StateMachine->do($c)";
$sm->reset();
is($sm->do($c), "FAIL", $testName);


### End of test ###
done_testing();
