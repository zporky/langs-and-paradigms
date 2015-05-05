
(ns float-parse.core
  (:gen-class))


;; Parser combinators
;;--------------------------------------------------

;; Fixpoint combinator for emulating value recursion.
;; (a -> a) -> a
(defn Y [f]
  ((fn [x] (x x))
   (fn [x]
       (f (fn [& args]
              (apply (x x) args))))))

;; a -> Parser a
(defn ret [a]
  (fn [xs]
    [a xs]))

;; Parser a
(defn fail [xs] nil)

;; (>>=) :: Parser a -> (a -> Parser b) -> Parser b
(defn bind [ma]
  (fn [f]
    (fn [xs]
      (let [res (ma xs)]
        (when res
          (let [[a xs] res]
            ((f a) xs)))))))

;; (<$>) :: (a -> b) -> Parser a -> Parser b
(defn fmap [f]
  (fn [fa]
    ((bind fa)
     (fn [a]
       (ret (f a))))))

;; (<$) :: b -> Parser a -> Parser b
(defn cfmap [a]
  (fn [fa]
    ((fmap (fn [_] a)) fa)))

;; (<|>) :: Parser a -> Parser a -> Parser a
(defn choose [ma]
  (fn [mb]
    (fn [xs]
      (let [res (ma xs)]
        (if res
          res
          (mb xs))))))

;; (<*>) :: Parser (a -> b) -> Parser a -> Parser b
(defn ap [mf]
  (fn [ma]
    ((bind mf)
     (fn [f]
       ((fmap f) ma)))))

;; (<*) :: Parser a -> Parser b -> Parser a
(defn apl [ma]
  (fn [mb]
    ((ap ((fmap (fn [a] (fn [_] a))) ma)) mb)))    

;; (*>) :: Parser a -> Parser b -> Parser b
(defn apr [ma]
  (fn [mb]
    ((ap ((fmap (fn [_] (fn [b] b))) ma)) mb)))

;; The actual parsers
;; --------------------------------------------------

(defn sat [p]
  (fn [xs]
    (when (and (seq xs) (p (first xs)))
      [(first xs) (rest xs)])))

(defn eof [xs]
  (if (empty? xs) [true xs] nil))

(defn digit? [c]
  (and (<= (int \0) (int c)) (>= (int \9) (int c))))

(defn pchar [c]
  (sat (partial = c)))

(def digit
  ((fmap #(- (int %) (int \0))) (sat digit?)))

(def nonzero
  ((bind digit) #(if (= 0 %) fail (ret %))))

(def zero
  ((cfmap 0) (pchar \0)))

(def fraction1
  (Y
   (fn [fraction1]
     ((fmap #(/ % 10))
      ((ap
        ((fmap (fn [a] #(+ a %))) digit))
       ((choose fraction1) (ret 0)))))))

(def fraction
  ((choose fraction1) (ret 0)))

(def integral
  ((bind nonzero)
   (fn go [acc]
     ((choose
       ((bind digit) #(go (+ % (* 10 acc)))))
      (ret acc)))))

(def sign
  ((choose
    ((cfmap identity) (pchar \+)))
    ((cfmap -) (pchar \-))))

(def double'
  ((choose
    ((apr (pchar \.)) fraction1))
   ((choose
     ((ap
       ((fmap (fn [a] #(+ a %))) zero))
      ((choose ((apr (pchar \.)) fraction)) (ret 0))))
    ((ap
      ((fmap (fn [a] #(+ a %))) integral))
     ((choose ((apr (pchar \.)) fraction)) (ret 0))))))

(def exponent
  ((apr
   ((choose (pchar \e)) (pchar \E)))
   ((choose
    ((ap sign) integral))
    integral)))

(def pdouble
  ((bind
    ((choose ((ap sign) double')) double'))
   (fn [mantissa]
     ((bind
       ((choose
         ((fmap (fn [e] (fn [n] (* n (Math/pow 10 e))))) exponent))
        (ret identity)))
      (fn [f]
        (ret (f mantissa)))))))      


;; main
;;--------------------------------------------------

(defn -main []  
  (let [in (read-line)]
    (when (not= in "end")
      (let [res (pdouble in)]
        (do
          (if res
            (println (str "OK " (float (first res))))
            (println "FAIL"))
          (-main))))))
    
