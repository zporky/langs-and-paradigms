using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using ProgNyelvekBead.StateMachine;
using ProgNyelvekBead.States;


namespace ProgNyelvekBead
{
    class Program
    {
        static void Main(string[] args)
        {
            String line = Console.ReadLine();
            while(line != null)
            { 
                SignedStart start = new SignedStart(line, "");
                StateMachine.StateMachine machine = new StateMachine.StateMachine(start);
                bool success = machine.run();

                if(success)
                {
                    Console.WriteLine("OK " + machine.Value);
                }
                else
                {
                    Console.WriteLine("FAIL");
                }
                line = Console.ReadLine();
            }
        }
    }
}
