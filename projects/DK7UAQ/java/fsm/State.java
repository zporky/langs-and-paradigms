
package pny2.fsm;

import java.util.Objects;

public class State {
    
    private final boolean accepted;
    private final String ID;

    public State(boolean accepted, String ID) {
        this.accepted = accepted;
        this.ID = ID;
    }

    public boolean isAccepted() {
        return accepted;
    }
    public String getID() {
        return ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {return true;}
        if (!(obj instanceof State)) {return false;}
        State o = (State)obj;
        return o.ID.equals(this.ID) && o.accepted == this.accepted;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.accepted ? 1 : 0);
        hash = 67 * hash + Objects.hashCode(this.ID);
        return hash;
    }

    
}
