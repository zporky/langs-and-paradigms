use strict;
use warnings;

use Test::More;

use_ok('FloatFSM');

my $float_fsm = FloatFSM->new();

isa_ok( $float_fsm, 'FloatFSM' );

is( $float_fsm->validate('1'),       'OK 1.0',  'validate 1 = OK 1.0' );
is( $float_fsm->validate('-3.14'),   'OK 3.14', 'validate -3.14 = OK 3.14' );
is( $float_fsm->validate('0'),       'OK 0',    'validate 0 = OK 0' );
is( $float_fsm->validate('+0.1'),    'OK 0.1',  'validate +0.1 = OK 0.1' );
is( $float_fsm->validate('.1'),      'OK 0.1',  'validate .1 = OK 0.1' );
is( $float_fsm->validate('.'),       'FAIL',    'validate . = FAIL' );
is( $float_fsm->validate('+ x1'),    'FAIL',    'validate + x1 = FAIL' );
is( $float_fsm->validate('-'),       'FAIL',    'validate - = FAIL = FAIL' );
is( $float_fsm->validate('3.14e-2'), 'FAIL',    'validate 3.14e-2 = FAIL' );

is( $float_fsm->validate('+0.1a'), 'FAIL', 'validate +0.1 = OK 0.1' );

is( $float_fsm->validate('--0'),  'FAIL', 'validate --0 = FAIL' );
is( $float_fsm->validate('00.1'), 'FAIL', 'validate 00.1 = FAIL' );

is( $float_fsm->validate(''),   'FAIL',  'validate  = FAIL' );
is( $float_fsm->validate('0.'), 'OK 0.', 'validate 0. = OK 0.' );

is( $float_fsm->validate('111'), 'OK 111.0', 'validate 111 = 111.0' );

done_testing;
