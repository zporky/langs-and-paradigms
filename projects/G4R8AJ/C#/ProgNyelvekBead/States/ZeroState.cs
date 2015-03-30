using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class ZeroState : AbstractState
    {
        public ZeroState(String word, String curVal) : base(word, curVal) { }

        protected override void calculateNextState(Char curChar)
        {
            if(curChar == '.')
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = this.curVal + ".";
                this.followingState = new FractureState(remWord, curVal);
            }
            else
            {
                this.followingState = null;
            }
        }
    }
}
