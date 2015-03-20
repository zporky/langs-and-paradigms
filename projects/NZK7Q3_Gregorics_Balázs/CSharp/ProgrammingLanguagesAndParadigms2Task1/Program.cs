using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;

namespace ProgrammingLanguagesAndParadigms2Task1
{
    class Program
    {
        static void Main(string[] args)
        {
            if(args.Length < 1)
            {
                Console.WriteLine("No input filename! Use default? (Y/N)");
                if (ConsoleKey.Y == Console.ReadKey().Key)
                {
                    Console.Clear();
                    args = new string[1];
                    args[0] = "./Examples/Example1.txt";
                }
                else
                {
                    return;
                }
            }

            String filename = args[0];

            if (!File.Exists(filename))
            {
                Console.WriteLine("File not exists!");
            }
            else
            {
                Console.WriteLine("Starting file parse...");
                using (StreamReader sr = new StreamReader(filename))
                {
                    do
                    {
                        String input = sr.ReadLine();
                        StateMachines.FloatStateMachine m = new StateMachines.FloatStateMachine();
                        Console.WriteLine(m.Evaluate(input));
                    } while (!sr.EndOfStream);
                }
            }

            Console.WriteLine("Press ANY key to close.");
            Console.ReadKey();
        }
    }
}
