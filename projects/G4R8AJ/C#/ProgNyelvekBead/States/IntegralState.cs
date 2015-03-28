using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class IntegralState : AbstractState
    {
        public IntegralState(String word, String curVal) : base(word,curVal){}

        protected override void calculateNextState(Char curChar)
        {
            if(Char.IsDigit(curChar))
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = this.curVal + curChar.ToString();
                this.followingState = this;
            } else if(curChar == '.')
            {

                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = this.curVal + ".";
                this.followingState = new FractureState(remWord,curVal);
            }
            else
            {
                this.followingState = null;
            }

            if(remWord.Length == 0)
            {
                this.curVal = this.curVal + ".0";
                this.followingState = this;
            }
        }
    }
}
