# OSGi, Clojure and nREPL

> Disclaimer:
>
> This is nothing new:
> http://clojure101.blogspot.se/2009/05/creating-clojure-repl-in-your.html
> and also some other pages I haven't been able to track down.
>
> Also - I'm no expert at Clojure *or* the nRepl. The way I'm using (or abusing) namespaces is maybe both naive and erroneous.

We're using OSGi at [HRM Software](http://www.hrmsoftware.se) ..

Even though you should do TDD ([or not](http://david.heinemeierhansson.com/2014/tdd-is-dead-long-live-testing.html)) and integration testing (using, for example, [Pax Exam](https://ops4j1.jira.com/wiki/display/PAXEXAM3/Pax+Exam) and [Tiny Bundles](https://github.com/ops4j/org.ops4j.pax.tinybundles)). Some times you just want to poke around in the OSGi container to see how things are connected at runtime and maybe invoke a service that you've just deployed.

Using Apache GoGo and a remote shell (or running your bundles in Apache Karaf) gets you part of the way, but to get really intimate with the OSGi runtime you want to hack and slash ... 

Wouldn't it be cool to connect to the OSGi container in a Clojure REPL session? 

* Link to this project - provide 'short' instructions for how to build/download and install in Karaf.

* What's a good scenario?
    - List the bundles
    - Find a service
    - Invoke the LogService 

* Drop the bundle - show that it works -- simple example.

* Introduce two or three functions that can be used to simplify the above.

    (find-service [clazz])
    (find-services [clazz])
    (find-all-services)
    (filter #(partial object-name-filter "xxx") find-all-services)

    (export a service .. that implements an API)

* Clojure's runtime-model (one 'container' per class-loader).
* Stuff to add ..
* Referencing local paths.. everything is local to the container. Bindings are persisted across sessions.

* Then - of course, if we've got this far we should really be able to implement
  a bundle by simple deploying a bundle-activator at runtime (maybe by using
  tiny-bundles to give it a proper runtime-environment).
