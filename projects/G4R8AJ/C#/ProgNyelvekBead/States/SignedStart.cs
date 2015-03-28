using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class SignedStart : AbstractState
    {

        public SignedStart(String word, String curVal) : base(word, curVal) { }

        protected override void calculateNextState(Char curChar)
        {
            if(curChar == '-' || curChar == '+' )
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.followingState = new UnsignedStart(this.remWord,this.curVal);
            }
            else
            {
                this.followingState = new UnsignedStart(this.remWord,this.curVal);   
            }

            this.isStepped = true;
        }
    }
}
