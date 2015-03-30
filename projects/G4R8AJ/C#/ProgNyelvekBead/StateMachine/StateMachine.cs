using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProgNyelvekBead.States;

namespace ProgNyelvekBead.StateMachine
{
    class StateMachine
    {
        protected IState startState;

        /**
         * Get the accumulated value of the state machine 
         */
        public String Value
        {
            get
            {
                return readValue;
            }
        }

        protected String readValue;

        /**
         * 
         * Create the stateMachine object
         * 1.st parameter is the statring state of the machine
         */
        public StateMachine(IState sS)
        {
            startState = sS;
        }
        
        /** 
         * start the state machine
         */
        public bool run()
        {
            Boolean success = true;
            IState nextState = startState;
            String currentString = nextState.Remaining;
            while(currentString != null && currentString.Length != 0 && success )
            {
                nextState.step();
                nextState = nextState.Next;
                if (nextState != null)
                {
                    currentString = nextState.Remaining;
                }
                else
                {
                    success = false;
                }
            }
            if (success && nextState.Next == null)
            {
                success = false;
            }
            else if(success)
            {
                readValue = nextState.currentValue;
            }
            return success;
        }
    }
}
