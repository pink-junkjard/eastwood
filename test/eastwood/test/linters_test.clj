(ns eastwood.test.linters-test
  (:use [clojure.test])
  (:use [eastwood.core]))


(deftest test1
  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f01
                       [:misplaced-docstrings :def-in-def :redefd-vars]
                       {}))
         {
          {:linter :redefd-vars,
           :msg
           "Var #'eastwood.test.testcases.f01/i-am-redefd def'd 2 times at lines: 4 5",
           :line 5}
          1,
          {:linter :misplaced-docstrings,
           :msg "Possibly misplaced docstring, #'eastwood.test.testcases.f01/foo2",
           :line 12}
          1,
          {:linter :misplaced-docstrings,
           :msg "Possibly misplaced docstring, #'eastwood.test.testcases.f01/foo5",
           :line 31}
          1,
          {:linter :redefd-vars,
           :msg "Var #'eastwood.test.testcases.f01/test1-redefd def'd 3 times at lines: 42 46 49",
           :line 46}
          1,
          {:linter :redefd-vars,
           :msg "Var #'eastwood.test.testcases.f01/i-am-redefd2 def'd 2 times at lines: 70 73",
           :line 73}
          1,
          {:linter :def-in-def,
           :msg "There is a def of #'eastwood.test.testcases.f01/def-in-def1 nested inside def TBD",
           :line 82}
          1,
          }))

  ;; TBD: I do not know why the i-am-inner-defonce-sym warning appears
  ;; twice in the result.  Once would be enough.
  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f02
                       [:misplaced-docstrings :def-in-def :redefd-vars]
                       {}))
         {
          {:linter :redefd-vars,
           :msg "Var #'eastwood.test.testcases.f02/i-am-defonced-and-defmultid def'd 2 times at lines:  ",
           :line nil}
          1,
          {:linter :redefd-vars,
           :msg "Var #'eastwood.test.testcases.f02/i-am-a-redefd-defmulti def'd 2 times at lines:  ",
           :line nil}
          1,
          {:linter :def-in-def,
           :msg "There is a def of #'eastwood.test.testcases.f02/i-am-inner-defonce-sym nested inside def TBD",
           :line nil}
          2,
          }
         ))

  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f03
                       [:misplaced-docstrings :def-in-def :redefd-vars]
                       {}))
         {}))
  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f04
                       [:misplaced-docstrings :def-in-def :redefd-vars]
                       {}))
         {}))
  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f05
                       [:misplaced-docstrings :def-in-def :redefd-vars]
                       {}))
         {}))
  (is (= (frequencies (lint-ns-noprint
                       'eastwood.test.testcases.f06
                       [:unused-fn-args :misplaced-docstrings :def-in-def
                        :redefd-vars]
                       {}))
         {
          {:linter :unused-fn-args,
           :msg "Function args [y] of (or within) fn-with-unused-args are never used",
           :line 5}
          1,
          {:linter :unused-fn-args,
           :msg "Function args [y] of (or within) fn-with-unused-args2 are never used",
           :line 9}
          1,
          {:linter :unused-fn-args,
           :msg "Function args [w] of (or within) fn-with-unused-args3 are never used",
           :line 19}
          1,
          {:linter :unused-fn-args,
           :msg "Function args [body] of (or within) macro2 are never used",
           :line 33}
          1,
          {:linter :unused-fn-args,
           :msg "Function args [z] of (or within) fn-with-unused-args4 are never used",
           :line 50}
          1,
          }
         ))
)