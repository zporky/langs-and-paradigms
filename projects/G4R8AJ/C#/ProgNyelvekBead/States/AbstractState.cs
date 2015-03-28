using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    abstract class AbstractState : IState
    {
        public String Remaining
        {
            get
            {
                return this.remWord;
            }
            set
            {
                this.remWord = value;
            }
        }

        public IState Next
        {
            get
            {
                return this.followingState;
            }
            set
            {
                this.followingState = value;
            }
        }

        public String currentValue
        {
            get
            {
                return this.curVal;
            }
            set
            {
                this.curVal = value;
            }
        }

        public Boolean StepDone
        {
            get
            {
                return this.isStepped;
            }
            set
            {
                this.isStepped = value;
            }
        }

        protected String remWord;
        protected IState followingState;
        protected String curVal;

        protected Boolean isStepped;

        public AbstractState(String remWord, String curVal)
        {
            this.remWord = remWord;
            this.curVal = curVal;
            this.followingState = null;
            this.isStepped = false;
        }

        /**
         * The method preparing for the step
         * take the first element of the input and give it to the calculating method
         */

        public void step()
        {
            Char curChar = this.remWord.First();
            this.calculateNextState(curChar);
        }

        /**
         * 
         * Calculate the next state of the stateMachine, if the Next is null -> invalid input state 
         */
        protected abstract void calculateNextState(Char curChar);
    }
}
