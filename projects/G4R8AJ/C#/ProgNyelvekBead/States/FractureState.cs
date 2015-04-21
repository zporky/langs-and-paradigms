using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class FractureState : AbstractState
    {
        public FractureState(String word, String curVal) : base(word, curVal) { }

        protected override void calculateNextState(Char curChar)
        {
 	        if(Char.IsDigit(curChar))
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = this.curVal + curChar.ToString();
                this.followingState = this;
            }
            else
            {
                this.followingState = null;
            }
        }
    }
}
