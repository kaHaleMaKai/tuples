# tuples ![](https://travis-ci.org/kaHaleMaKai/tuples.svg?branch=master)

a tuple class and type-safe tuple list

---

## how use it
```java
Tuple<Integer, String> tuple = Tuple.of(1, "a"); // or: new Tuple<>(1, "a");
assert tuple.first() == 1;
assert tuple.last() == "a";
```

```java
TupleList<Integer, String> tupleList = TupleList.of(Integer.class, String.class);
tupleList.add(1, "a");
tupleList.add(2, "b");

// or:
List<Object> list = new LinkedList<>();
list.add(1);
list.add("a");
list.add(2);
list.add("b");
TupleList<Integer, String> tupleList = TupleList
        .of(Integer.class, String.class)
        .from(list);
```

## features
* type-safe
* `subList()` returns a co-modification-aware TupleList implementation
* `TupleList.unmodifiableTupleList()` return a TupleList implementation
