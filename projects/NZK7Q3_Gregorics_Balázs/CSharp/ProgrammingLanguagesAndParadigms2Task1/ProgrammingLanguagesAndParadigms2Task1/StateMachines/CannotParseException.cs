using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ProgrammingLanguagesAndParadigms2Task1.StateMachines
{
    public class CannotParseException : Exception
    {
        public CannotParseException() : base()
        { }

        public CannotParseException(String msg) : base(msg)
        { }

    }
}
