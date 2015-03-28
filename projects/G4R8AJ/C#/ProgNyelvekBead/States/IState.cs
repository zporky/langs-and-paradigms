using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ProgNyelvekBead.States
{
    interface IState
    {
        void step();

        String Remaining{ get; set;}
        IState Next { get; set; } 
        String currentValue { get; set; }
        Boolean StepDone { get; set; }


    }
}
