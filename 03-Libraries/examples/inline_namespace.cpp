// Library code
namespace MyLibrary {
  inline namespace V2 {
    int f(int); // old feature
    int g(); // new feature
  }

  namespace V1 {
    int f(int);
  }
}

// User code
int main() {
  MyLibrary::f(5); // Calls the new version.
  MyLibrary::V2::f(5); // Calls the new version explicitly.
  MyLibrary::V1::f(5); // Calls the old version.
}
