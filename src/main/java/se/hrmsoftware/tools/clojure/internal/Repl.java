package se.hrmsoftware.tools.clojure.internal;

import clojure.lang.RT;
import clojure.lang.Var;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.concurrent.Callable;

public class Repl implements BundleActivator {


	public static final String NREPL_PORT_PROP = "nrepl.port";
	private static final int DEFAULT_PORT = 7888;

	/**
	 * Run an action with the TCCL set to the classloader of the {@link se.hrmsoftware.tools.clojure.Repl} class.
	 * @param action the action to perform.
	 * @param <T> the return type.
	 * @return the value of the evaluated {@link java.util.concurrent.Callable}.
	 * @throws Exception .
	 */
	private static <T> T withLoader(Callable<T> action) throws Exception {
		ClassLoader oldCl = Thread.currentThread().getContextClassLoader();
		try {
			Thread.currentThread().setContextClassLoader(Repl.class.getClassLoader());
			return action.call();
		}
		finally {
			Thread.currentThread().setContextClassLoader(oldCl);
		}
	}

	/** A reference to the nRepl server. */
	private Object serverRef = null;

	@Override
	public void start(final BundleContext context) throws Exception {
		final int serverPort = serverPort(context, DEFAULT_PORT);
		serverRef = withLoader(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				RT.loadResourceScript("bootstrap.clj");
				// Publish the OSGi context to the 'osgi' namespace.
				RT.var("osgi", "*context*", context);
				Var startFn = RT.var("bootstrap", "start");
				return startFn.invoke(serverPort);
			}
		});
		System.out.println("Started nREPL @ "+serverPort);
	}

	private static int serverPort(BundleContext bundleContext, int defaultPort) {
		try {
			return Integer.valueOf(bundleContext.getProperty(NREPL_PORT_PROP));
		}
		catch (NumberFormatException e) {
			return defaultPort;
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		withLoader(new Callable<Void>() {
			@Override
			public Void call() throws Exception {
				Var stopFn = RT.var("bootstrap", "stop");
				stopFn.invoke(serverRef);
				serverRef = null;
				return null;
			}
		});
	}
}
