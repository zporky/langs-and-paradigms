using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    class UnsignedStart : AbstractState
    {
        public UnsignedStart(String word, String curVal) : base(word, curVal) { }

        protected override void calculateNextState(Char curChar)
        {
            if(curChar == '.')
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = "0.";
                this.followingState = new PointState(this.remWord,this.curVal);
                
            }
            else if(curChar == '0')
            {
                this.remWord = this.remWord.Remove(0, 1);
                this.curVal = "0";
                this.followingState = new ZeroState(this.remWord, this.curVal); 
            }
            else if( Char.IsDigit(curChar))
            {
                this.followingState = new IntegralState(this.remWord, this.curVal);
            }
            else
            {
                this.followingState = null;
            }

            this.isStepped = true;
        }
    }
}
