(ns bootstrap)
(use '[clojure.tools.nrepl.server :only (start-server stop-server)])
(defn start "Starts the nRepl server" [port] (start-server :port port))
(defn stop "Stops the provided nRepl server" [srv] (stop-server srv))

