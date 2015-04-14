using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ProgrammingLanguagesAndParadigms2Task1.StateMachines
{
    public class EndOfStringException : Exception
    {
        public EndOfStringException() : base()
        { }

        public EndOfStringException(String msg) : base(msg)
        { }
    }
}
