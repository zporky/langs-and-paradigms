package StateMachine;

use strict;

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

# Terminals
my %terminals = (
	"+" => TERM_S,
	"-" => TERM_S,
	"." => TERM_P,
	0   => TERM_Z,
	""  => TERM_E
);
foreach my $i ( 1 .. 9 ) {
	$terminals{$i} = TERM_D;
}

# Constructor
sub create {
	my $class = shift;
	my $self  = {
		'state'  => STATE_S0,
		'output' => ""
	};
	return ( bless( $self, $class ) );
}

sub reset {
	my $self = shift;

	$self->{'state'}  = STATE_S0;
	$self->{'output'} = "";

	return $self;
}

sub do {
	my $self = shift;

	# read the parameter
	my $str = shift;

	# declare local variables
	my $c = "";

	# process the $str
	$c = "";

	# if the first character isn't TERM_S then call action_s
	if ( $str =~ /^(.)(.*)/ ) {
		$c = $1;

		# if $c isn't s then do empty
		if ( $terminals{$c} ne TERM_S ) {
			$self->action_s("");
		}
	}
	else {
		$self->fail();
	}

	while ( $str =~ s/^(.)(.*)$/$2/ ) {
		$c = $1;

		if ( exists $terminals{$c} ) {

			# $c is valid terminal symbol
			if ( $terminals{$c} == TERM_S ) {
				$self->action_s($c);
			}
			elsif ( $terminals{$c} == TERM_P ) {
				$self->action_p($c);
			}
			elsif ( $terminals{$c} == TERM_Z ) {
				$self->action_z($c);
			}
			elsif ( $terminals{$c} == TERM_E ) {
				$self->action_e($c);
			}
			elsif ( $terminals{$c} == TERM_D ) {
				$self->action_d($c);
			}
			else {
				$str = "";
				$self->fail();
			}
		}
		else {

			# $c is invalid terminal symbol
			$str = "";
			$self->fail();
		}

		# if there is error then exit from loop
		if ( $self->{'state'} == STATE_ERR ) {
			$str = "";
		}
	}

	if ( $self->{'state'} == STATE_T4 ) {
		$self->action_p(".");
		$self->action_d("0");
	}
	elsif ($self->{'state'} == STATE_I0
		|| $self->{'state'} == STATE_S0
		|| $self->{'state'} == STATE_S1 )
	{
		# invalid end state therefore fail
		$self->fail();
	}

	return $self->{'output'};
}

sub fail {
	my $self = shift;

	$self->{'state'}  = STATE_ERR;
	$self->{'output'} = 'FAIL';
}

# empty action
sub action_e {
	my $self = shift;

}

# s action
sub action_s {
	my $self = shift;
	my $c    = shift;
	unless ( defined $c ) {
		$c = "";
	}

	# STATE_S0 --> STATE_S1
	if ( $self->{'state'} == STATE_S0 ) {
		$self->{'state'} = STATE_S1;
	}

	# FAIL
	else {
		$self->fail();
	}

	#	if ( $self->{'state'} != STATE_ERR ) {
	#		$self->{'output'} .= $c;
	#	}
}

# p action
sub action_p {
	my $self = shift;
	my $c    = shift;
	unless ( defined $c ) {
		$c = "";
	}

	# STATE_S1 --> STATE_I0
	if ( $self->{'state'} == STATE_S1 ) {
		$self->{'state'} = STATE_I0;
	}

	# STATE_T2 --> STATE_T3
	elsif ( $self->{'state'} == STATE_T2 ) {
		$self->{'state'} = STATE_T3;
	}

	# STATE_T4 --> STATE_T5
	elsif ( $self->{'state'} == STATE_T4 ) {
		$self->{'state'} = STATE_T5;
	}

	# FAIL
	else {
		$self->fail();
	}

	if ( $self->{'state'} != STATE_ERR ) {
		if ( $self->{'state'} == STATE_I0 ) {
			$self->{'output'} = "0$c";
		}
		else {
			$self->{'output'} .= $c;
		}
	}
}

# z action
sub action_z {
	my $self = shift;
	my $c    = shift;
	unless ( defined $c ) {
		$c = "";
	}

	# STATE_S1 --> STATE_T2
	if ( $self->{'state'} == STATE_S1 ) {
		$self->{'state'} = STATE_T2;
	}

	# FAIL
	else {
		$self->fail();
	}

	if ( $self->{'state'} != STATE_ERR ) {
		$self->{'output'} .= $c;
	}
}

# d action
sub action_d {
	my $self = shift;
	my $c    = shift;
	unless ( defined $c ) {
		$c = "";
	}

	# STATE_I0 --> STATE_T1
	if ( $self->{'state'} == STATE_I0 ) {
		$self->{'state'} = STATE_T1;
	}

	# STATE_S1 --> STATE_T4
	elsif ( $self->{'state'} == STATE_S1 ) {
		$self->{'state'} = STATE_T4;
	}

	# STATE_T1 --> STATE_T1
	# STATE_T3 --> STATE_T3
	# STATE_T4 --> STATE_T4
	# STATE_T5 --> STATE_T5
	elsif ($self->{'state'} == STATE_T1
		|| $self->{'state'} == STATE_T1
		|| $self->{'state'} == STATE_T3
		|| $self->{'state'} == STATE_T4
		|| $self->{'state'} == STATE_T5 )
	{

		# do nothing
	}

	# FAIL
	else {
		$self->fail();
	}

	if ( $self->{'state'} != STATE_ERR ) {
		$self->{'output'} .= $c;
	}
}

sub getState {
	my $self = shift;
	return $self->{'state'};
}

sub getOutput {
	my $self = shift;
	return $self->{'output'};
}

1;

__END__

=head1 NAME

StateMachine

=head1 SYNOPSIS


=head1 DESCRIPTION

=head1 AUTHOR

=cut
