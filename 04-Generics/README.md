# Generic programming

## The Standard Template Library


Part of the C++ standard. The most important example for generic programming. 
Generic programming: make more abstract routines without loosing efficiency 
using parameterization (both data and algorithm).


Motivation:

```cpp
    int t[] = { 1, 3, 5, ... };

    // find the first occurance of a value
    int *pi = find( t, t+sizeof(t)/sizeof(t[0]), 55);

    if ( pi )
    {
        *pi = 56;
    }
```


A very specific implementation:


```cpp
    int *find( int *begin, int *end, int x)
    {
        while ( begin != end )
        {
            if ( *begin == x )
            {
                return begin;
            }
            ++begin;
        }
        return 0;
    }
```

Step1: generalization on type    int -> T


```cpp
    template <typename T>
    T *find( T *begin, T *end, const T& x)
    {
        while ( begin != end )
        {
            if ( *begin == x )
            {
                return begin;
            }
            ++begin;
        }
        return 0;
    }
```


Step2: Generalization on data structure


```cpp
    template <typename It, typename T>
    It find( It begin, It end, const T& x)
    {
        while ( begin != end )
        {
            if ( *begin == x )
            {
                return begin;
            }
            ++begin;
        }
        return end;  // not 0
    }
```


Uniform usage


```cpp
    int t[] = { 1, 3, 5, ... };

    int *pi = find( t, t+sizeof(t)/sizeof(t[0]), 55);

    if ( pi )
    {
        *pi = 56;
    }

    vector<int> v;
    v.push_back(1); v.push_back(3); v.push_back(5); ...

    vector<int>::iterator vi = find( v.begin(), v.end(), 55);

    if ( vi != v.end() )
    {
        *vi = 56;
    }

    list<double> l;
    l.push_back(1.1); l.push_back(3.3); l.push_back(5.5); ...

    list<double>::iterator li = find( l.begin(), l.end(), 55.55);

    if ( li != l.end() )
    {
        *li = 56.66;
    }
```


Constant safety:


```cpp
    const int t[] = { 1, 3, 5, ... };

    const int *pi = find( t, t+sizeof(t)/sizeof(t[0]), 55);

    if ( pi )
    {
        // *pi = 56;    Syntax error
        cout << *pi;
    }

    vector<int> v;
    v.push_back(1); v.push_back(3); v.push_back(5); ...

    const list<double> cl( v.begin(), v.end());

    list<double>::const_iterator cli = find( cl.begin(), cl.end(), 55.55);

    if ( cli != cl.end() )
    {
        // *cli = 56.66;    Syntax error
        cout << *cli;
    }
```

Be care: const_iterator != const iterator


More generalization. Find the 3rd occurance:


```cpp
    list<double> l; ...
    list<double>::iterator li;

    li = find( l.begin(), l.end(), 3.14);
    li = find( li, l.end(), 3.14);
    li = find( li, l.end(), 3.14);

    // or: 
    li = find(find(find(l.begin(),l.end(),3.14),l.end(),3.14),l.end(),3.14);
```

It is obvious that this way there is a limitation. We have to change our
strategy here. Find the third element whis is less than 55.


```cpp
    template <typename It, typename Pred>
    It find_if( It begin, It end, Pred p)
    {
        while ( begin != end )
        {
            if ( p(*begin) )
            {
                return begin;
            }
            ++begin;
        }
        return end;
    }



    // Pred1: not too good
    bool less55_3rd( int x)
    {
        static int cnt = 0;
        if ( x < 55 )
            ++cnt;
        return 3 == cnt;
    }

    vector<int> v; ...
    vector<int>::iterator = find_if( v.begin(), v.end(), less55_3rd);
    vector<int>::iterator = find_if( v.begin(), v.end(), less55_3rd); // ??
```

```cpp
    // Pred2
    struct less55_3rd
    {
        less55_3rd() : cnt(0) { }
        bool operator()(int x)
        {
            if ( x < 55 )
               ++cnt;
            return 3 == cnt;
        }
    private:
        int cnt;
    };


    vector<int> v; ...
    less55_3rd  pp;
    vector<int>::iterator = find_if( v.begin(), v.end(), pp);

    // or:
    vector<int>::iterator = find_if( v.begin(), v.end(), less55_3rd());
```

```cpp
    // Pred3: more generic

    template <typename T>
    struct less_nth
    {
        less_nth( const T& t, int n) : t_(t), n_(n), cnt_(0) { }
        bool operator()(const T& t)
        {
            if ( t < t_ )
               ++cnt;
            return n_ == cnt;
        }
    private:
        T   t_;
        int n_;
        int cnt_;
    };


    vector<int> v; ...
    vector<int>::iterator = find_if(v.begin(),v.end(),less_nth<int>(55,3));
```


Bit in longer term, it is better to avoid predicates with state.



## A detailed sample: merge two input sequences/containers.

First version: reading from files, and write the merged output to the cout.

This is the classical solution, with pre-reading elements from both sequences.
 
```cpp
#include <iostream>
#include <fstream>
#include <string>

using namespace std;

// simple merge
int main()
{
    string s1, s2;

    ifstream f1("file1.txt");
    ifstream f2("file2.txt");

    f1 >> s1;
    f2 >> s2;

    // the usual way:
    while (f1 || f2)
    {
        if (f1 && ((s1 <= s2) || !f2))
        {
            cout << s1 << endl;
            f1 >> s1;
        }
        if (f2 && ((s1 >= s2) || !f1))
        {
            cout << s2 << endl;
            f2 >> s2;
        }
    }
    return 0;
}
```

Based on STL one can implement this in a much simpler way:

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>    // merge( b1, e1, b2, e2, b3 [,opc_rend])
#include <vector>

using namespace std;

int main()
{
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    string s;

    vector<string> v1;
    while ( if1 >> s ) v1.push_back(s);

    vector<string> v2;
    while ( if2 >> s ) v2.push_back(s);

    // allocate the spacefor the result
    vector<string> v3(v1.size() + v2.size());   // very expensive...

    merge( v1.begin(), v1.end(),
           v2.begin(), v2.end(),
           v3.begin());             // v3[i] = *current 

    for ( int i = 0; i < v3.size(); ++i)
        cout << v3[i] << endl;

    return 0;
}
```

The problem with the above solution is that `std::vector<>` has limited capacity. 
We read both sequences into the memory, and we also have the merged result in the 
memory before outputting it. 

There is an other efficiency problem: when allocatin the vectors, we run the constructor
then overwrite the newly created elements.

We will use *inserters*: objects acting like iterators, but call `insert`.

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <vector>

using namespace std;

int main()
{
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    string s;

    vector<string> v1;
    while ( if1 >> s ) v1.push_back(s);

    vector<string> v2;
    while ( if2 >> s ) v2.push_back(s);

    vector<string> v3;
    v3.reserve( v1.size() + v2.size() );    // allocates but not construct
                                            // size == 0
    merge( v1.begin(), v1.end(),
           v2.begin(), v2.end(),
           back_inserter(v3));      // v3.push_back(*current)

    for ( int i = 0; i < v3.size(); ++i)
        cout << v3[i] << endl;

    return 0;
}
```

But do we need to load/unload all elements into memory?

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <iterator>     // input- and output-iterators

using namespace std;

int main()
{
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    // istream_iterator(if1) -> if1 >> *current
    // istream_iterator() -> EOF
    // ostream_iterator(of,x) -> of << *current << x 
    merge( istream_iterator<string>(if1), istream_iterator<string>(),
           istream_iterator<string>(if2), istream_iterator<string>(),
           ostream_iterator<string>(cout,"\n") );
    return 0;
}
```

So far so good. But what if the requirements change? We want to compare in a case-insensitive way:

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <cctype>
#include <algorithm>
#include <iterator>

// function object: "functor"
struct my_less
{
    bool operator()(const std::string& s1, const std::string& s2)
    {
        std::string us1 = s1;
        std::string us2 = s2;

    // TODO: use locale object 
        transform( s1.begin(), s1.end(), us1.begin(), toupper);
        transform( s2.begin(), s2.end(), us2.begin(), toupper);

        return us1 < us2;
    }
};

using namespace std;

int main()
{
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    merge( istream_iterator<string>(if1), istream_iterator<string>(),
           istream_iterator<string>(if2), istream_iterator<string>(),
           ostream_iterator<string>(cout,"\n"), my_less() );
    return 0;
}
```
Or what about zapping the two stream?

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <iterator>

using namespace std;

// one from left, one from right:
struct zipp
{
    zipp() : flag(true) { }
    bool operator()(const string& s1, const string& s2)
    {
        flag = !flag;
        return flag;
    }
    bool flag;
};

int main()
{
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    merge( istream_iterator<string>(if1), istream_iterator<string>(),
           istream_iterator<string>(if2), istream_iterator<string>(),
           ostream_iterator<string>(cout,"\n"), zipp() );
    return 0;
}
```

Or do something weird:

```cpp
#include <iostream>
#include <fstream>
#include <string>
#include <algorithm>
#include <iterator>

using namespace std;

template <typename T>
class distr
{
public:
    distr(int l, int r, bool fl = true) :
                       left(l), right(r), from_left(fl), cnt(0) { }
    // formal reasons: "compare" has two parameters of type T
    bool operator()( const T&, const T&)
    {
        bool ret = from_left;
        const int  max = from_left ? left : right;
        if ( ++cnt == max )
        {
            cnt = 0;
            from_left = ! from_left;
        }
        return ret;
    }
private:
    const int left;
    const int right;
    int from_left;
    int cnt;
};

int main()
{
    int left, right;
    cin >> left >> right;
    ifstream if1("file1.txt");
    ifstream if2("file2.txt");

    merge( istream_iterator<string>(if1), istream_iterator<string>(),
           istream_iterator<string>(if2), istream_iterator<string>(),
           ostream_iterator<string>(cout,"\n"),
                                    distr<std::string>(left,right) );
    return 0;
}
```




