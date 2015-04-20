#!/usr/bin/perl

use strict;
use warnings;

use FloatFSM;

my $float_fsm = FloatFSM->new();

foreach my $float ( <STDIN> ) {
	chomp $float;
	print "$float\t" . $float_fsm->validate($float) . "\n";
}
