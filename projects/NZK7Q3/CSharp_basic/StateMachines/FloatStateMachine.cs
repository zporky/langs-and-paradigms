using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ProgrammingLanguagesAndParadigms2Task1.StateMachines
{
    public class FloatStateMachine
    {  

        #region States

        private enum States
        {
            S,
            SV,
            I0,
            T1,
            T2,
            T3,
            T4,
            T5
        }

        private class S { }
        private class SV { }
        private class I0 { }
        private class T1 { }
        private class T2 { }
        private class T3 { }
        private class T4 { }
        private class T5 { }

        #endregion

        private static HashSet<Char> SIGNING
        {
            get
            {
                HashSet<Char> a = new HashSet<Char>();
                a.Add('+');
                a.Add('-');
                return a;
            }
        }
        private static HashSet<Char> POINT
        {
            get
            {
                HashSet<Char> a = new HashSet<Char>();
                a.Add('.');
                return a;
            }
        }
        private static HashSet<Char> ZERO
        {
            get
            {
                HashSet<Char> a = new HashSet<Char>();
                a.Add('0');
                return a;
            }
        }
        private static HashSet<Char> DECIMAL
        {
            get
            {
                HashSet<Char> a = new HashSet<Char>();
                a.Add('1');
                a.Add('2'); a.Add('3');
                a.Add('4'); a.Add('5');
                a.Add('6'); a.Add('7');
                a.Add('8'); a.Add('9');
                return a;
            }
        }
        private static HashSet<States> ENDS
        {
            get
            {
                HashSet<States> a = new HashSet<States>();
                a.Add(States.T1);
                a.Add(States.T2);
                a.Add(States.T3);
                a.Add(States.T4);
                a.Add(States.T5);
                return a;
            }
        }

        #region Variables

        private States _state;

        #endregion

        #region Ctors

        public FloatStateMachine()
        {
            _state = States.S;
        }

        #endregion

        #region Methods

        public String Evaluate(String word)
        {
            try
            {
                return Step(new S(), word);
            }
            catch(CannotParseException)
            {
                return "FAIL";
            }
            catch(EndOfStringException)
            {
                return "FAIL";
            }
        }

        #endregion

        #region Rules

        private Char PreStep(ref String inp)
        {
            if (inp.Length <= 0)
                throw new EndOfStringException();

            Char c = inp[0];
            inp = inp.Substring(1);
            return c;
        }

        private String Step(S s, String inp)
        {
            Char c = PreStep(ref inp);

            if (SIGNING.Contains(c))
            {
                if (c == '+')
                    return "" + Step(new SV(), inp);
                else
                    return c.ToString() + Step(new SV(), inp);
            }
            return Step(new SV(), c + inp);
        }

        private String Step(SV s, String inp)
        {
            Char c = PreStep(ref inp);

            if (POINT.Contains(c))
            {
                return "0." + Step(new I0(), inp);
            }
            else if(ZERO.Contains(c))
            {
                return "0" + Step(new T2(), inp);
            }
            else if(DECIMAL.Contains(c))
            {
                return c.ToString() + Step(new T4(), inp);
            }
            throw new CannotParseException();
        }

        private String Step(I0 s, String inp)
        {
            Char c = PreStep(ref inp);

            if (DECIMAL.Contains(c))
            {
                return c.ToString() + Step(new T1(), inp);
            }
            throw new CannotParseException();
        }

        private String Step(T1 s, String inp)
        {
            Char c;
            try
            {
                c = PreStep(ref inp);
            }
            catch(EndOfStringException)
            {
                return "";
            }

            if (DECIMAL.Contains(c))
            {
                return c.ToString() + Step(new T1(), inp);
            }

            throw new CannotParseException();
        }

        private String Step(T2 s, String inp)
        {
            Char c;
            try
            {
                c = PreStep(ref inp);
            }
            catch (EndOfStringException)
            {
                return "";
            }

            if (POINT.Contains(c))
            {
                return c.ToString() + Step(new T3(), inp);
            }

            throw new CannotParseException();
        }

        private String Step(T3 s, String inp)
        {
            Char c;
            try
            {
                c = PreStep(ref inp);
            }
            catch (EndOfStringException)
            {
                return "";
            }

            if (DECIMAL.Contains(c))
            {
                return c.ToString() + Step(new T3(), inp);
            }

            throw new CannotParseException();
        }

        private String Step(T4 s, String inp)
        {
            Char c;
            try
            {
                c = PreStep(ref inp);
            }
            catch (EndOfStringException)
            {
                return "";
            }

            if (DECIMAL.Contains(c))
            {
                return c.ToString() + Step(new T4(), inp);
            }
            else if (POINT.Contains(c))
            {
                return c.ToString() + Step(new T5(), inp); ;
            }

            throw new CannotParseException();
        }

        private String Step(T5 s, String inp)
        {
            Char c;
            try
            {
                c = PreStep(ref inp);
            }
            catch (EndOfStringException)
            {
                return "";
            }

            if (DECIMAL.Contains(c))
            {
                _state = States.T5;
                return c.ToString() + Step(new T5(), inp);
            }

            throw new CannotParseException();
        }

        #endregion

    }
}
