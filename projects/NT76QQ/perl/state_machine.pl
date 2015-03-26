#!/usr/bin/perl

use strict;
use StateMachine;

### START main ###
my $sm = StateMachine->create();
#print "start task1\n";

# read from STDIN while not EOF
while (my $line = <STDIN>) {
	# remove last \n
	chomp($line);
	
	$sm->reset();
	
	#process line
	$sm->do($line);
	print "$line\t";
	if($sm->getState()) {
		print "OK "
	}
	print $sm->getOutput()."\n";
}

### END main ###

__END__

=head1 What is this?

TODO

=head1 AUTHOR

Magnucz Ferenc (fmagnucz@gmail.com)

=cut
