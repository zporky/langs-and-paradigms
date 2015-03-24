#!/usr/bin/perl

use strict;

# States
my $startState = "S";
my @states = ("S", "S'", "I0", "T1", "T2", "T3", "T4", "T5");


### START main ###
print "start task1\n";

# read from STDIN while not EOF
while (<>) {
	# remove last \n
	chomp($_);
	
	#process line
	process($_);
}

### END main ###

sub process {
	# read the parameter
	my $str = shift;
	
	# Terminals
	my @s = ("+", "-");
	my @p = (".");
	my @z = (0);
	my @d = (1..9);
	my %terminals = {
		 "+" => "s"
		,"-" => "s"
		,"." => "p"
		,0   => "z"
	};
	foreach my $i (1..9) { $terminals{$i} = "d"}
	
	# declare local variables
	my $state = $startState;
	my $c = "";
	my $terminalClass ="";
	
	# process the $str
	while ($str =~ s/(.)(.*)/$2/) {
		$c = $1;
		
		# determine the terminalClass of $c1
		
		print "$c:$str\n";
	}
}