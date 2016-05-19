# tuples [![travis-ci.org](https://travis-ci.org/kaHaleMaKai/tuples.svg?branch=master)](https://travis-ci.org/kaHaleMaKai/tuples.svg?branch=master) [![codecov.io](https://codecov.io/github/kaHaleMaKai/tuples/coverage.svg?branch=master)](https://codecov.io/github/kaHaleMaKai/tuples?branch=master) [ ![Download](https://api.bintray.com/packages/kahalemakai/maven/tuples/images/download.svg) ](https://bintray.com/kahalemakai/maven/tuples/_latestVersion)

tuples and type-safe tuple lists

---

## how use it
#### Tuple
```java
Tuple<Integer, String> tuple = Tuple.of(1, "a"); 
tuple.first(); // => 1
tuple.last();  // => "a"
```

#### TupleList
`TupleList<Integer, String> tuples = TupleList.of(Integer.class, String.class);`

`TupleList` extends the `List` interface.

The following convenience methods are added:
* `TupleList<T, U> slurp(Iterable<?> iterable)`: read `iterable` of even length and convert it into `Tuple<T, U>` instances
* `TupleList<T, U> zip(Iterable<? extends T> first, Iterable<? extends U> last)`: zip values from both `iterables` into tuples
* `TupleList<T, U> fromMap(Map<? extends T, ? extends U> map)`: convert key-value pairs into tuples
* `void put(T first, U last)`: add a new tuple to the end of the list
* `void put(int index, T first, U last)`: add a new tuple at position `index`
* `List<? super Object> flatten()`: return a flat list of all tuple entries
* `List<List<? super Object>> asList()`: return a list of 2-element lists
* `Map<T, U> asMap()`: turn the tuples into key-value pairs

## features
* compile type-safe
* `subList()` returns a co-modification-aware TupleList implementation
* `TupleList.unmodifiableTupleList()` returns a TupleList implementation
* as of version `0.3.0` breaking api-changes will be avoided
