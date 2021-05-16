# decision-tree

Build decision trees natively in Clojure. The library was designed to
be maximally flexible in terms of creating functional outputs. One
goal is to ensure that node splits do not have to be based upon keys
but can be arbitrary functions.

Datasets should look like:

```
[{:day 1 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
 {:day 2 :weather "Rainy" :just-ate "yes" :late-at-work "yes" :will-go-running "no"}
 {:day 3 :weather "Sunny" :just-ate "no" :late-at-work "yes" :will-go-running "yes"}
 {:day 4 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "no"}
 {:day 5 :weather "Rainy" :just-ate "no" :late-at-work "no" :will-go-running "yes"}
 {:day 6 :weather "Sunny" :just-ate "yes" :late-at-work "no" :will-go-running "yes"}
 {:day 7 :weather "Rainy" :just-ate "no" :late-at-work "yes":will-go-running "no"}]
```

Trees look like:

```
[:weather
 {"Sunny" [:late-at-work
           {"yes" [:just-ate
                   {"no" "no"
                    "yes" "no"}]
            "no" [:just-ate {"no" "no"
                             "yes" "no"}]}]
  "Rainy" "no"}]
```

## Usage

FIXME

## License

Copyright © 2021 FIXME

Licensed under Apache v2
