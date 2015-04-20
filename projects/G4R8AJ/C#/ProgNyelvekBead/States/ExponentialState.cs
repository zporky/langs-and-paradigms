using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class ExponentialState : AbstractState
    {
        public ExponentialState(String word, String curVal) : base(word, curVal) { }

        protected override void calculateNextState(char curChar)
        {
            if(curChar == '-' || curChar == '+')
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = this.curVal + curChar.ToString();
                this.followingState = new ExponentialInegralState(this.remWord,this.curVal);
            }else if(Char.IsDigit(curChar))
            {
                this.followingState = new ExponentialInegralState(this.remWord, this.curVal);
            }
            else
            {
                this.followingState = null;
            }
        }
    }
}
