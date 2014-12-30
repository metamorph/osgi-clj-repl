(ns osgi)

(defn bundle-as-map "Transforms a Bundle reference to a Map"
  [b]
  {:symbolic-name (.getSymbolicName b)
   :version (.getVersion b) })

(defn bundles "All bundles in the context" 
  []
  (map bundle-as-map (seq (.getBundles *context*))))

; Stuff to add -- locate service. Maybe using a 'let' and 'finally'.
; Locate a service - and then release it - put the side-effect in a function.

