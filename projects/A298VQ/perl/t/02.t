use strict;
use warnings;

use Test::More;

use_ok('FloatFSM');

my $float_fsm = FloatFSM->new();

isa_ok( $float_fsm, 'FloatFSM' );

is( $float_fsm->validate('3.14e-2'), 'OK 3.14e-2', 'validate 3.14e-2 = OK' );
is( $float_fsm->validate('1e2'),     'OK 1e2',     'validate 1e2 = OK' );
is( $float_fsm->validate('2.4E-5'),  'OK 2.4E-5',  'validate 2.4E-5 = OK' );
is( $float_fsm->validate('.1e+3'),   'OK 0.1e+3',  'validate .1e+3 = OK' );
is( $float_fsm->validate('0e12'),    'OK 0e12',    'validate 0e12 = OK' );

is( $float_fsm->validate('ee'),     'FAIL', 'validate ee = FAIL' );
is( $float_fsm->validate('1.0e'),   'FAIL', 'validate 1.0e = FAIL' );
is( $float_fsm->validate('3.14e-'), 'FAIL', 'validate 3.14e- = FAIL' );
is( $float_fsm->validate('.1e'),    'FAIL', 'validate .1e = FAIL' );
is( $float_fsm->validate('.1e++3'), 'FAIL', 'validate .1e++3 = FAIL' );
is( $float_fsm->validate('.1ee+3'), 'FAIL', 'validate .1ee+3 = FAIL' );

done_testing;
