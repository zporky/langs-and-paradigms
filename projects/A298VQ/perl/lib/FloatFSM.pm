package FloatFSM;

use strict;
use warnings;

sub new {
    my ($class) = @_;

    my $self = {
        float_value => undef,          #
        input_str   => undef,          #
        state       => undef,          #
        symbol_p    => qr/^\.$/,       #
        symbol_s    => qr/^(\+|-)/,    #
        symbol_d    => qr/[1-9]/,
        symbol_z    => qr/0/,
        symbol_e    => qr/(e|E)/,
    };

    bless $self, $class;
    return $self;
}

sub validate {
    my ( $self, $input_str ) = @_;

    $self->{input_str}   = $input_str;
    $self->{float_value} = '';

    return $self->state_start();
}

sub pop_input_str {
    my ($self) = @_;

    my $input_str = reverse $self->{input_str};
    my $char      = chop $input_str;

    $self->{input_str} = reverse $input_str;

    return $char;
}

sub state_error {
    my ($self) = @_;
    return "FAIL";
}

sub state_ok {
    my ($self) = @_;
    return "OK $self->{float_value}";
}

sub state_start {
    my ($self) = @_;

    if ( $self->{input_str} =~ $self->{symbol_s} ) {
        $self->pop_input_str();
    }
    $self->{state} = \&state_start_;

    return &{ $self->{state} };
}

sub state_start_ {
    my ($self) = @_;

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_p} ) {
        $self->{float_value} .= '0.';
        $self->{state} = \&state_i_0;
    }
    elsif ( $char =~ $self->{symbol_z} ) {
        $self->{float_value} .= '0';
        $self->{state} = \&state_t_2;
    }
    elsif ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
        $self->{state} = \&state_t_4;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_i_0 {
    my ($self) = @_;

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
        $self->{state} = \&state_t_1;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_t_1 {
    my ($self) = @_;
    return $self->state_ok() unless ( $self->{input_str} );

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
    }
    elsif ( $char =~ $self->{symbol_e} ) {
        $self->{float_value} .= 'e';
        $self->{state} = \&state_e;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_t_2 {
    my ($self) = @_;
    return $self->state_ok() unless ( $self->{input_str} );

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_p} ) {
        $self->{float_value} .= $char;
        $self->{state} = \&state_t_3;
    }
    elsif ( $char =~ $self->{symbol_e} ) {
        $self->{float_value} .= 'e';
        $self->{state} = \&state_e;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_t_3 {
    my ($self) = @_;

    return $self->state_ok() unless ( $self->{input_str} );

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
        $self->{state} = \&state_t_1;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_t_4 {
    my ($self) = @_;

    if ( $self->{input_str} eq '' ) {
        $self->{float_value} .= ".0";
        return $self->state_ok();
    }

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_p} ) {
        $self->{float_value} .= '.';
        $self->{state} = \&state_t_3;
    }
    elsif ( $char =~ $self->{symbol_e} ) {
        $self->{float_value} .= 'e';
        $self->{state} = \&state_e;
    }
    elsif ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_e {
    my ($self) = @_;

    if ( $self->{input_str} =~ $self->{symbol_s} ) {
        my $char = $self->pop_input_str();
        $self->{float_value} .= $char;
    }
    else
    {
        $self->{float_value} .= '+';
    }
    $self->{state} = \&state_i_1;

    return &{ $self->{state} };
}

sub state_i_1 {
    my ($self) = @_;

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
        $self->{state} = \&state_e_;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

sub state_e_ {
    my ($self) = @_;

    return $self->state_ok() unless ( $self->{input_str} );

    my $char = $self->pop_input_str();

    if ( $char =~ $self->{symbol_d} ) {
        $self->{float_value} .= $char;
    }
    else {
        $self->{state} = \&state_error;
    }

    return &{ $self->{state} };
}

1;